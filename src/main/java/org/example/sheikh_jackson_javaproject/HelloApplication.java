// HelloApplication.java:

package org.example.sheikh_jackson_javaproject;

import java.util.Properties;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sheikh_jackson_javaproject.database.*;
import org.example.sheikh_jackson_javaproject.tabs.*;
import org.example.sheikh_jackson_javaproject.utils.*;

/**
 * Main JavaFX application class responsible for application lifecycle,
 * database initialization, and UI navigation.
 * This class acts as the central controller for startup logic and scene management.
 * Design Choice: The application is structured to initialize configuration,
 * establish database connection if available, and load the appropriate UI flow.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class HelloApplication extends Application {

    /**
     * Initializes and starts the JavaFX application.
     * Handles intro animation, database configuration loading, and initial scene setup.
     *
     * @param stage primary application window
     *
     * @apiNote Entry point called automatically by JavaFX runtime.
     */
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

    /**
     * Builds and displays the main application UI.
     * Initializes the root layout, menu bar, tab system, and applies styling.
     * Also configures tab switching animations and scene properties.
     *
     * @param stage primary application window
     */
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

        for (int i = 0; i < tabs.length; i++) tabs[i].getStyleClass().addAll("tab-box", tabClasses[i]);

        pane.getTabs().addAll(tabs);

        pane.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldTab, newTab) -> {

            if (newTab != null && newTab.getContent() != null) {
                FadeTransition ft = new FadeTransition(Duration.millis(350), newTab.getContent());
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
            }
        });
        root.setCenter(pane);

        Scene scene = new Scene(root, NodeConsts.SCENE_WIDTH, NodeConsts.SCENE_HEIGHT);
        NodeConsts.applyCSS(scene);

        stage.setScene(scene);
        stage.setTitle("Game Library Management System!");
        stage.show();

        Log.info("Main UI loaded successfully.");
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
