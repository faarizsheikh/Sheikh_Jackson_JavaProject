// Database.java:

package org.example.sheikh_jackson_javaproject.database;

import java.sql.*;
import static org.example.sheikh_jackson_javaproject.database.Const.*;

public class Database {
    private static Database instance;
    private Connection connection;

    private Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/" + DB_NAME + "?serverTimezone=UTC",
                    DB_USER,
                    DB_PASS);

            System.out.println("Connected to Database.");

            // CREATE: All 3 tables
            createTable(DBConst.TABLE_DEVELOPER, DBConst.CREATE_TABLE_DEVELOPER, connection);
            createTable(DBConst.TABLE_PLATFORM, DBConst.CREATE_TABLE_PLATFORM, connection);
            createTable(DBConst.TABLE_GAME, DBConst.CREATE_TABLE_GAMES, connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tableName, String tableQuery, Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        ResultSet resultSet = md.getTables(DB_NAME, null, tableName, null);

        if (!resultSet.next()) {
            Statement statement = connection.createStatement();
            statement.execute(tableQuery);
            System.out.println("The " + tableName + " table has been created.");
        } else {
            System.out.println(tableName + " table already exists.");
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}