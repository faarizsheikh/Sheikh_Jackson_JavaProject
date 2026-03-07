package org.example.sheikh_jackson_javaproject;

import org.example.sheikh_jackson_javaproject.database.Database;
import org.example.sheikh_jackson_javaproject.tabs.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();

        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("File");
        Menu credits = new Menu("Credits");
        MenuItem exit = new MenuItem("Exit");
        file.getItems().add(exit);

        menuBar.getMenus().addAll(file, credits);
        exit.setOnAction(e-> System.exit(0));

        TabPane pane = new TabPane();

        AddItemTab addItemTab = new AddItemTab();
        StatisticsTab statisticsTab = new StatisticsTab();
        pane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        pane.getTabs().addAll(addItemTab, statisticsTab);
        root.setTop(menuBar);
        root.setCenter(pane);
        Database db = Database.getInstance();
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}