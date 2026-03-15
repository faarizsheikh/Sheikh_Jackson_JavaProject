// HelloApplication.java:

package org.example.sheikh_jackson_javaproject;

import java.util.*;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.sheikh_jackson_javaproject.database.*;
import org.example.sheikh_jackson_javaproject.tabs.*;
import org.example.sheikh_jackson_javaproject.utils.*;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {

        Log.info("Application starting...");
        stage.setMaximized(true);

        IntroAnimation.play(stage, () -> {

            try {
                Properties props = DBConfig.loadConfig();

                if (props != null) {

                    Log.info("Database config found. Attempting connection...");

                    Database.getInstance(
                            props.getProperty("user"),
                            props.getProperty("password"),
                            props.getProperty("server"),
                            props.getProperty("database")
                    );

                    Log.info("Database connection successful.");
                    showMainScene(stage);

                } else {

                    Log.warn("No saved DB configuration found.");
                    stage.setScene(LoginScene.create(stage));
                    stage.setTitle("Database Login");
                    stage.show();
                }

            } catch (Exception e) {

                Log.error("Application startup failed.", e);
                stage.setScene(LoginScene.create(stage));
                stage.setTitle("Database Login");
                stage.show();
            }

        });

        stage.setOnCloseRequest(e -> {
            e.consume();
            Log.info("Exit requested by user.");
            NodeConsts.showExitConfirmation();
        });
    }

    public static void showMainScene(Stage stage) {
        Log.info("Loading main UI.");

        BorderPane root = NodeConsts.root(stage);
        root.setTop(NodeConsts.mainMenu());

        TabPane pane = new TabPane();
        pane.setTabMinHeight(50);
        pane.setTabMaxHeight(50);
        pane.setTabMinWidth(300);
        pane.setTabMaxWidth(300);
        pane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab[] tabs = {
                new AddItemTab(), new ViewItemTab(), new UpdateItemTab(),
                new DeleteItemTab(), new StatisticsTab()
        };
        String[] tabClasses = {"add-tab", "view-tab", "update-tab", "delete-tab", "stats-tab"};

        for (int i = 0; i < tabs.length; i++)
            tabs[i].getStyleClass().addAll("tab-box", tabClasses[i]);

        pane.getTabs().addAll(tabs);
        root.setCenter(pane);

        Scene scene = new Scene(root, NodeConsts.SCENE_WIDTH, NodeConsts.SCENE_HEIGHT);
        NodeConsts.applyCSS(scene);

        stage.setScene(scene);
        stage.setTitle("Game Library Management System!");
        stage.show();

        Log.info("Main UI loaded successfully.");
    }

    public static void main(String[] args) {
        launch();
    }
}
