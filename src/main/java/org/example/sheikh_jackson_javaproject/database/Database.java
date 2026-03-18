// Database.java:

package org.example.sheikh_jackson_javaproject.database;

import java.sql.*;
import org.example.sheikh_jackson_javaproject.utils.Log;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

/**
 * Handles database connection and initialization using the Singleton pattern.
 * Responsible for creating tables and seeding default data.

 * Design Choice:
 * Singleton ensures only one active database connection is used across the application.

 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class Database {

    private final Connection connection;
    private static Database instance;

    /**
     * Private constructor to initialize database connection and setup tables.

     * @param user database username
     * @param pass database password
     * @param server database server address
     * @param dbName database name
     * @throws SQLException if SQL error occurs
     * @throws ClassNotFoundException if JDBC driver not found
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
     * Seeds the developers table with default values if empty.
     */
    private void seedDevelopers() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + TABLE_DEVELOPER);

            if (rs.next() && rs.getInt(1) == 0) {
                String[] devs = {
                        "Activision Blizzard", "Ant.Karlov", "BIG Games", "CapCom",
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
     * Seeds the platforms table with default values if empty.
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
     * Creates a table if it does not already exist.

     * @param tableName name of table
     * @param tableQuery SQL create query
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
     * Initializes the database singleton instance.

     * @param user database username
     * @param pass database password
     * @param server database server
     * @param dbName database name
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
     * Returns the existing database instance.

     * @return Database instance
     */
    public static Database getInstance() {
        if (instance == null) Log.warn("Database instance requested before initialization.");
        return instance;
    }

    /**
     * Returns the active database connection.

     * @return SQL Connection
     */
    public Connection getConnection() { return connection; }
}
