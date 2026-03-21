// HelloController.java:

package org.example.sheikh_jackson_javaproject;

import javafx.fxml.*;
import javafx.scene.control.*;

/**
 * Controller class for handling user interactions in the Hello view.
 *
 * <p>This controller follows the JavaFX FXML MVC pattern, separating UI
 * logic from layout definitions for better maintainability.</p>
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class HelloController {

    /**
     * Default constructor for HelloController.
     * Initializes the controller.
     */
    public HelloController() {}

    /**
     * Label injected from FXML used to display welcome text.
     */
    @FXML
    private Label welcomeText;

    /**
     * Handles the button click event from the UI.
     * Updates the welcome label with a greeting message.
     *
     * @apiNote Triggered via FXML onAction binding.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
