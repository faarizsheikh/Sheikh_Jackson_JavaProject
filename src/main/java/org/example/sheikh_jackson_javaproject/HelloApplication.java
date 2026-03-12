// HelloApplication.java:

package org.example.sheikh_jackson_javaproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.sheikh_jackson_javaproject.database.DBConfig;
import org.example.sheikh_jackson_javaproject.database.Database;
import org.example.sheikh_jackson_javaproject.tabs.*;

import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        stage.setMaximized(true);

        try {
            Properties props = DBConfig.loadConfig();
            if (props != null) {
                // Attempt DB connection
                String user = props.getProperty("user");
                String pass = props.getProperty("password");
                String server = props.getProperty("server");
                String db = props.getProperty("database");

                Database.getInstance(user, pass, server, db);
                showMainScene(stage);
            } else {
                stage.setScene(LoginScene.create(stage));
                stage.setTitle("Database Login");
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            stage.setScene(LoginScene.create(stage));
            stage.setTitle("Database Login");
            stage.show();
        }

        // Handle window close
        stage.setOnCloseRequest(e -> {
            e.consume();
            showExitConfirmation(stage);
        });
    }

    public static void showMainScene(Stage stage) {
        BorderPane root = new BorderPane();
        root.prefWidthProperty().bind(stage.widthProperty());
        root.prefHeightProperty().bind(stage.heightProperty());

        // --- MENU BAR ---
        MenuBar menuBar = createMenu(stage);
        root.setTop(menuBar);

        // --- TAB PANE ---
        TabPane pane = new TabPane();
        pane.setTabMinHeight(50);
        pane.setTabMaxHeight(50);
        pane.setTabMinWidth(300);
        pane.setTabMaxWidth(300);
        pane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        AddItemTab addItemTab = new AddItemTab();
        ViewItemTab viewItemTab = new ViewItemTab();
        UpdateItemTab updateItemTab = new UpdateItemTab();
        DeleteItemTab deleteItemTab = new DeleteItemTab();
        StatisticsTab statisticsTab = new StatisticsTab();

        addItemTab.getStyleClass().addAll("tab-box", "add-tab");
        viewItemTab.getStyleClass().addAll("tab-box", "view-tab");
        updateItemTab.getStyleClass().addAll("tab-box","update-tab");
        deleteItemTab.getStyleClass().addAll("tab-box", "delete-tab");
        statisticsTab.getStyleClass().addAll("tab-box", "stats-tab");

        pane.getTabs().addAll(addItemTab, viewItemTab, updateItemTab, deleteItemTab, statisticsTab);
        root.setCenter(pane);

        // Scene
        Scene scene = new Scene(root, 1699, 989);
        scene.getStylesheets().add(
                Objects.requireNonNull(HelloApplication.class.getResource(
                                "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                        .toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("Game Library Management System!");
        stage.show();
    }

    /** MenuBar reused in login and main scene */
    public static MenuBar createMenu(Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu creditsMenu = new Menu("Credits");

        MenuItem exitItem = new MenuItem("Exit");
        MenuItem aboutItem = new MenuItem("Developers");
        MenuItem resItem = new MenuItem("Used Resources");

        fileMenu.getItems().add(exitItem);
        creditsMenu.getItems().addAll(aboutItem, resItem);
        menuBar.getMenus().addAll(fileMenu, creditsMenu);

        exitItem.setOnAction(e -> showExitConfirmation(stage));
        aboutItem.setOnAction(e -> devCredits());
        resItem.setOnAction(e -> resourceCredits());

        return menuBar;
    }

    private static void showExitConfirmation(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("EXITING SYSTEM");

        VBox box = new VBox(10);
        box.getStyleClass().add("alert-box");

        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(HelloApplication.class.getResource(
                                "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                        .toExternalForm()
        );

        Label askConfirm = new Label("Are you sure you want to exit the program?");
        Label sideNote = new Label("New changes will be saved.");
        askConfirm.getStyleClass().add("ask-confirm");
        sideNote.getStyleClass().add("side-note");
        box.getChildren().addAll(askConfirm, sideNote);

        alert.getDialogPane().setContent(box);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public static void devCredits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Developers Credits");
        alert.setHeaderText("DEVELOPERS");

        VBox box = new VBox(5);
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(HelloApplication.class.getResource(
                                "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                        .toExternalForm()
        );

        Label collabTitle = new Label("Collaborator(s):");
        Label projectLeadTitle = new Label("Project Lead:");
        Label teamNameTitle = new Label("Team Name:");

        Label collabsTxt = new Label("Isaac Jackson");
        Label projectLead = new Label("Faariz Sheikh (Current), Yazan Al-Umari (Formerly)");
        Label teamName = new Label("NODE of Noobs");
        Label thanks = new Label("THANKS FOR TEACHING US, CÂI FILIAULT! :)");

        collabTitle.getStyleClass().add("credit-label");
        projectLeadTitle.getStyleClass().add("credit-label");
        teamNameTitle.getStyleClass().add("credit-label");

        box.getChildren().addAll(
                teamNameTitle, teamName,
                projectLeadTitle, projectLead,
                collabTitle, collabsTxt,
                thanks
        );

        alert.getDialogPane().setContent(box);
        alert.showAndWait();
    }

    public static void resourceCredits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resources Credits");
        alert.setHeaderText("RESOURCES");

        VBox box = new VBox(5);
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(HelloApplication.class.getResource(
                                "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                        .toExternalForm()
        );

        Label resList = new Label(
                "• CSS\n• JavaFX Animations\n• Database\n• File I/O\n• IntelliJ IDEA (IDE)\n• Java\n• JavaFX (JFX / Java Effects)\n• PhpMyAdmin"
        );
        resList.getStyleClass().add("res-list");

        box.getChildren().add(resList);
        alert.getDialogPane().setContent(box);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}