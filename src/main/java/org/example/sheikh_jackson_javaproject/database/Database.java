// Database.java:

package org.example.sheikh_jackson_javaproject.database;

import java.sql.*;
import org.example.sheikh_jackson_javaproject.utils.Log;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

public class Database {

    private static Database instance;
    private final Connection connection;

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

    public static Database getInstance() {
        if (instance == null) Log.warn("Database instance requested before initialization.");
        return instance;
    }

    public Connection getConnection() { return connection; }
}
