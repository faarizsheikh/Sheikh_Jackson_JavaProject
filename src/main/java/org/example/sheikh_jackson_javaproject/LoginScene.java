package org.example.sheikh_jackson_javaproject;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.sheikh_jackson_javaproject.database.*;
import java.util.Objects;

public class LoginScene {

    public static Scene create(Stage stage) {
        stage.setMaximized(true);

        // --- ROOT BORDERPANE ---
        BorderPane root = new BorderPane();
        root.prefWidthProperty().bind(stage.widthProperty());
        root.prefHeightProperty().bind(stage.heightProperty());

        // --- MENU BAR LIKE MAIN SCENE ---
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu creditsMenu = new Menu("Credits");

        MenuItem exitItem = new MenuItem("Exit");
        MenuItem aboutItem = new MenuItem("Developers");
        MenuItem resItem = new MenuItem("Used Resources");

        fileMenu.getItems().add(exitItem);
        creditsMenu.getItems().addAll(aboutItem, resItem);
        menuBar.getMenus().addAll(fileMenu, creditsMenu);

        // Menu actions
        exitItem.setOnAction(e -> System.exit(0));
        aboutItem.setOnAction(e -> HelloApplication.devCredits());
        resItem.setOnAction(e -> HelloApplication.resourceCredits());

        root.setTop(menuBar);

        // --- LOGIN FORM IN CENTER ---
        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(50));
        loginBox.setMaxWidth(450); // Match AddItemTab width
        loginBox.setStyle("-fx-background-color: rgba(255,255,255,0.85); -fx-background-radius: 10;");

        TextField userField = new TextField();
        PasswordField passField = new PasswordField();
        TextField serverField = new TextField();
        TextField dbField = new TextField();

        // Add CSS classes like AddItemTab
        userField.getStyleClass().add("form-input");
        passField.getStyleClass().add("form-input");
        serverField.getStyleClass().add("form-input");
        dbField.getStyleClass().add("form-input");

        userField.setPromptText("Username");
        passField.setPromptText("Password");
        serverField.setPromptText("Server (localhost or IP)");
        dbField.setPromptText("Database Name");

        Button loginBtn = new Button("Connect");
        loginBtn.getStyleClass().addAll("btn", "add-btn"); // Styled like AddItemTab
        loginBtn.setPrefHeight(60);
        loginBtn.setMaxWidth(Double.MAX_VALUE);

        loginBox.getChildren().addAll(
                createHugeLabel("Database Login Credentials"),
                userField, passField, serverField, dbField, loginBtn
        );

        // Center loginBox
        StackPane centerPane = new StackPane(loginBox);
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(centerPane);

        // --- LOGIN BUTTON ACTION ---
        loginBtn.setOnAction(e -> {
            String user = userField.getText().trim();
            String pass = passField.getText().trim();
            String server = serverField.getText().trim();
            String dbName = dbField.getText().trim();

            if (user.isEmpty() || pass.isEmpty() || server.isEmpty() || dbName.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();
                return;
            }

            try {
                Database.getInstance(user, pass, server, dbName);
                DBConfig.saveConfig(user, pass, server, dbName);

                HelloApplication.showMainScene(stage);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Connection failed: " + ex.getMessage()).show();
            }
        });

        // --- CREATE SCENE ---
        Scene scene = new Scene(root, 1699, 989);
        scene.getStylesheets().add(
                Objects.requireNonNull(HelloApplication.class.getResource(
                                "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                        .toExternalForm()
        );

        return scene;
    }

    private static Label createHugeLabel(String text) {
        Label l = new Label(text);
        l.getStyleClass().add("form-label");
        return l;
    }
}