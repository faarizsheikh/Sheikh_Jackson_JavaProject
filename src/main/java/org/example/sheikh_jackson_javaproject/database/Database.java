// Database.java:

package org.example.sheikh_jackson_javaproject.database;

import java.sql.*;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

public class Database {
    private static Database instance;
    private final Connection connection;

    private Database(String user, String pass, String server, String dbName) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://" + server + "/" + dbName + "?serverTimezone=UTC";
        connection = DriverManager.getConnection(url, user, pass);
        System.out.println("Connected to Database.");

        // Create tables if they don't exist
        createTable(TABLE_DEVELOPER, CREATE_TABLE_DEVELOPER);
        createTable(TABLE_PLATFORM, CREATE_TABLE_PLATFORM);
        createTable(TABLE_GAME, CREATE_TABLE_GAMES);
    }

    private void createTable(String tableName, String tableQuery) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        ResultSet resultSet = md.getTables(null, null, tableName, null);
        if (!resultSet.next()) {
            Statement stmt = connection.createStatement();
            stmt.execute(tableQuery);
            System.out.println("Created table: " + tableName);
        }
    }

    public static Database getInstance(String user, String pass, String server, String dbName) throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new Database(user, pass, server, dbName);
        }
        return instance;
    }

    public static Database getInstance() {
        return instance;
    }

    public Connection getConnection() { return connection; }
}