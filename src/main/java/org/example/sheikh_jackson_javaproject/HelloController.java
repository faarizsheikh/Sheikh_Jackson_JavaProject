// HelloController.java:

package org.example.sheikh_jackson_javaproject;

import javafx.fxml.*;
import javafx.scene.control.*;

/**
 * Controller class for handling UI interactions in the Hello view.

 * Design Choice:
 * Uses JFX FXML controller pattern to separate UI logic from layout.

 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class HelloController {

    @FXML
    private Label welcomeText;

    /**
     * Handles button click event and updates the welcome message.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
