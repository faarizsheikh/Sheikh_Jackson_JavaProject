/**
 * Main module definition for the Game Management System.
 * Contains all packages related to UI, database, DAO, and utilities.
 */

module org.example.sheikh_jackson_javaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.sheikh_jackson_javaproject to javafx.fxml;
    exports org.example.sheikh_jackson_javaproject;
    exports org.example.sheikh_jackson_javaproject.utils;
    opens org.example.sheikh_jackson_javaproject.utils to javafx.fxml;
}