// Database.java:

package org.example.sheikh_jackson_javaproject.database;

import java.sql.*;
import org.example.sheikh_jackson_javaproject.utils.Log;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

public class Database {

    private static Database instance;
    private final Connection connection;

    private Database(String user, String pass, String server, String dbName) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://" + server + "/" + dbName + "?serverTimezone=UTC";
        connection = DriverManager.getConnection(url, user, pass);
        Log.info("Connected to database: " + dbName + "@" + server);

        // Create tables if they don't exist
        createTable(TABLE_DEVELOPER, CREATE_TABLE_DEVELOPER);
        createTable(TABLE_PLATFORM, CREATE_TABLE_PLATFORM);
        createTable(TABLE_GAME, CREATE_TABLE_GAMES);
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
        if (instance == null) {
            Log.warn("Database instance requested before initialization.");
        }
        return instance;
    }

    public Connection getConnection() { return connection; }
}
