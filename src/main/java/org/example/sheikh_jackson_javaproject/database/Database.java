// Database.java:

package org.example.sheikh_jackson_javaproject.database;

import java.sql.*;
import org.example.sheikh_jackson_javaproject.utils.Log;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

/**
 * Manages database connectivity and initialization using the Singleton pattern.
 * This class is responsible for establishing a single shared database connection,
 * creating required tables, and seeding initial data.
 * Design Choice: The Singleton pattern ensures only one active database
 * connection exists throughout the application lifecycle, improving resource control
 * and consistency.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class Database {

    /**
     * Active SQL database connection used throughout the application.
     * This connection is shared across all database operations.
     */
    private final Connection connection;

    /**
     * Singleton instance of the Database class.
     * Ensures only one database connection manager exists in the application.
     */
    private static Database instance;

    /**
     * Creates a new database connection and initializes required tables.
     *
     * @param user database username
     * @param pass database password
     * @param server database server address
     * @param dbName database name
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    private Database(String user, String pass, String server, String dbName)
            throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://" + server + "/" + dbName + "?serverTimezone=UTC";
        connection = DriverManager.getConnection(url, user, pass);
        Log.info("Connected to database: " + dbName + "@" + server);

        // CREATE: Tables if they don't exist
        createTable(TABLE_DEVELOPER, CREATE_TABLE_DEVELOPER);
        createTable(TABLE_PLATFORM, CREATE_TABLE_PLATFORM);
        createTable(TABLE_GAME, CREATE_TABLE_GAMES);
        seedDevelopers();
        seedPlatforms();
    }

    /**
     * Seeds the developers table with default entries if it is empty.
     * This method runs only during initialization and prevents duplicate seeding
     * by checking existing records.
     *
     * @implNote Uses batch insertion for performance efficiency.
     */
    private void seedDevelopers() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + TABLE_DEVELOPER);

            if (rs.next() && rs.getInt(1) == 0) {
                String[] devs = {
                        "Activision", "Ant.Karlov", "BIG Games", "CapCom",
                        "Cheetah Mobile", "Copperbolt", "DVloper", "Epic Games",
                        "Facepunch Studios", "Homermafia1", "InnerSloth", "Linked Squad",
                        "Mediatonic", "Microsoft", "MiniToon", "Mojang Studios",
                        "MrNotSoHero", "Mystman12", "Natural Motion", "Naughty Dog",
                        "NetEase", "Nikilis", "Psyonix", "PUBG Corporation",
                        "Riot Games", "RockStar North", "Santa Monica Studio",
                        "Scott Cawthon", "Siege Camp (formerly Clapfoot)",
                        "SuperCell", "SYBO Games", "TeamTerrible", "Ubisoft",
                        "Valve Corporation"
                };

                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO " + TABLE_DEVELOPER + " (" + DEV_COLUMN_NAME + ") VALUES (?)"
                );

                for (String dev : devs) {
                    ps.setString(1, dev);
                    ps.addBatch();
                }
                ps.executeBatch();
                Log.info("Seeded developers table.");

            } else {
                Log.info("Developers already seeded.");
            }

        } catch (SQLException e) {
            Log.error("Failed seeding developers.", e);
        }
    }

    /**
     * Seeds the platforms table with default values if it is empty.
     * This method ensures required platform data is available on first run.
     *
     * @implNote Uses batch insertion for optimized performance.
     */
    private void seedPlatforms() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + TABLE_PLATFORM);

            if (rs.next() && rs.getInt(1) == 0) {
                String[] platforms = {
                        "Android", "iOS", "MacOS", "Nintendo",
                        "PC", "PlayStation", "Roblox", "Xbox"
                };

                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO " + TABLE_PLATFORM + " (" + PLAT_COLUMN_NAME + ") VALUES (?)"
                );

                for (String p : platforms) {
                    ps.setString(1, p);
                    ps.addBatch();
                }
                ps.executeBatch();
                Log.info("Seeded platforms table.");

            } else {
                Log.info("Platforms already seeded.");
            }

        } catch (SQLException e) {
            Log.error("Failed seeding platforms.", e);
        }
    }

    /**
     * Creates a database table if it does not already exist.
     * This method checks metadata before executing the creation query
     * to avoid duplicate table creation.
     *
     * @param tableName name of the table to check or create
     * @param tableQuery SQL CREATE TABLE statement
     */
    private void createTable(String tableName, String tableQuery) {
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet resultSet = md.getTables(null, null, tableName, null);

            if (!resultSet.next()) {
                Statement stmt = connection.createStatement();
                stmt.execute(tableQuery);
                Log.info("Created table: " + tableName);

            } else {
                Log.info("Table already exists: " + tableName);
            }

        } catch (SQLException e) {
            Log.error("Failed to create/check table: " + tableName, e);
        }
    }

    /**
     * Initializes the Singleton database instance if it has not been created.
     * If an instance already exists, it will be reused.
     *
     * @param user database username
     * @param pass database password
     * @param server database server
     * @param dbName database name
     *
     * @implNote Acts as an initialization method for the Singleton pattern.
     */
    public static void getInstance(String user, String pass, String server, String dbName) {
        if (instance == null) {
            try {
                instance = new Database(user, pass, server, dbName);
                Log.info("Database singleton instance created.");

            } catch (SQLException | ClassNotFoundException e) {
                Log.error("Failed to initialize database connection.", e);
                throw new RuntimeException(e);
            }

        } else {
            Log.info("Database instance already exists. Reusing singleton.");
        }
    }

    /**
     * Returns the existing database Singleton instance.
     *
     * @return active Database instance, or null if not initialized
     *
     * @apiNote Should be called only after initialization via getInstance(user, pass, server, dbName)
     */
    public static Database getInstance() {
        if (instance == null) Log.warn("Database instance requested before initialization.");
        return instance;
    }

    /**
     * Returns the active database connection.
     *
     * @return SQL Connection instance used by the application
     */
    public Connection getConnection() { return connection; }
}
