// LoginScene.java:

package org.example.sheikh_jackson_javaproject;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.sheikh_jackson_javaproject.database.*;
import org.example.sheikh_jackson_javaproject.utils.NodeConsts;
import static org.example.sheikh_jackson_javaproject.utils.NodeConsts.formLabel;

/**
 * Builds and manages the database login scene.
 * This class is responsible for collecting database credentials,
 * validating input, establishing a connection, and initializing
 * the application configuration.
 * Design Choice: Login logic is isolated from the main application
 * to maintain modularity and separation of concerns.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class LoginScene {

    /**
     * Labels used as placeholders for login form fields.
     */
    private static final String[] FORM_LABELS =
            {"Username", "Password", "Server (localhost or IP)", "Database Name"};

    /**
     * Creates the login scene for database authentication.
     * Initializes UI components, binds form behavior, and handles
     * database connection attempts. On successful login, transitions
     * to the main application scene.
     *
     * @param stage primary application window
     * @return fully configured login Scene
     *
     * @apiNote This method also initializes CSS styling and UI layout structure.
     */
    public static Scene create(Stage stage) {
        stage.setMaximized(true);

        BorderPane root = NodeConsts.root(stage);
        root.setTop(NodeConsts.mainMenu());

        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(50));
        loginBox.setMaxWidth(450);
        loginBox.getStyleClass().add("login-box");

        TextField userField = new TextField(),
                serverField = new TextField(),
                dbField = new TextField();

        PasswordField passField = new PasswordField();
        TextField[] textFields = {userField, passField, serverField, dbField};

        serverField.setText("localhost");
        serverField.getStyleClass().add("server-field");
        serverField.setDisable(true);

        final boolean[] dbManuallyEdited = {false};

        dbField.textProperty().addListener((obs, oldText, newText) -> {
            if (dbField.isFocused()) dbManuallyEdited[0] = !newText.isEmpty();
        });

        userField.textProperty().addListener((obs, oldText, newText) -> {
            if (!dbManuallyEdited[0]) dbField.setText(newText);
        });

        for (TextField tF : textFields) tF.getStyleClass().add("form-input");

        for (int i = 0; i < textFields.length; i++) textFields[i].setPromptText(FORM_LABELS[i]);

        Button loginBtn = NodeConsts.button("CONNECT", "connect-btn");
        loginBtn.setMaxWidth(Double.MAX_VALUE);

        loginBox.getChildren().addAll(
                formLabel("Database Login Credentials"),
                userField, passField, serverField, dbField, loginBtn
        );
        StackPane centerPane = new StackPane(loginBox);
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(centerPane);

        loginBtn.setOnAction(e -> {
            String user = userField.getText().trim(),
                    pass = passField.getText().trim(),
                    server = serverField.getText().trim(),
                    dbName = dbField.getText().trim();

            if (user.isEmpty() || pass.isEmpty() || server.isEmpty() || dbName.isEmpty()) {
                NodeConsts.alert(
                        Alert.AlertType.WARNING,
                        "Missing Information",
                        "WARNING!",
                        new Text("All fields must be filled!"){{
                            getStyleClass().add("side-note");
                        }}
                );
                return;
            }

            try {
                Database.getInstance(user, pass, server, dbName);
                DBConfig.saveConfig(user, pass, server, dbName);
                HelloApplication.showMainScene(stage);

            } catch (Exception ex) {
                NodeConsts.alert(
                        Alert.AlertType.ERROR,
                        "Connection Failed",
                        "ERROR",
                        new Text("Connection Failed: " + ex.getMessage()){{
                            getStyleClass().add("side-note");
                        }}
                );
            }
        });
        Scene scene = new Scene(root, NodeConsts.SCENE_WIDTH, NodeConsts.SCENE_HEIGHT);
        NodeConsts.applyCSS(scene);
        return scene;
    }
}
