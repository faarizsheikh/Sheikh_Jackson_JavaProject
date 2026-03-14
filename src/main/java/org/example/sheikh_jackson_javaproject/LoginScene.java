// LoginScene.java:

package org.example.sheikh_jackson_javaproject;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
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
        loginBox.setMaxWidth(450);
        loginBox.getStyleClass().add("login-box");

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
                createHugeLbl(),
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing information");
                alert.setHeaderText("ERROR!");

                VBox box = new VBox(5);
                alert.getDialogPane().getStylesheets().add(
                        Objects.requireNonNull(HelloApplication.class.getResource(
                                        "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                .toExternalForm()
                );

                Text fieldsMissing = new Text("All fields must be filled!");
                fieldsMissing.getStyleClass().add("side-note");

                box.getChildren().add(
                    fieldsMissing
                );

                alert.getDialogPane().setContent(box);
                alert.showAndWait();
                return;
            }

            try {
                Database.getInstance(user, pass, server, dbName);
                DBConfig.saveConfig(user, pass, server, dbName);

                HelloApplication.showMainScene(stage);

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Failed");
                alert.setHeaderText("ERROR!");

                VBox box = new VBox(5);
                alert.getDialogPane().getStylesheets().add(
                        Objects.requireNonNull(HelloApplication.class.getResource(
                                        "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                .toExternalForm()
                );

                Text connectFail = new Text("Connection Failed: " + ex.getMessage());
                connectFail.getStyleClass().add("side-note");

                box.getChildren().add(
                        connectFail
                );

                alert.getDialogPane().setContent(box);
                alert.showAndWait();
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

    private static Label createHugeLbl() {
        Label lbl = new Label("Database Login Credentials");
        lbl.getStyleClass().add("form-lbl");
        return lbl;
    }
}