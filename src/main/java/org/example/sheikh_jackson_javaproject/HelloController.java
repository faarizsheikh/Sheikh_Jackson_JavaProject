// HelloController.java:

package org.example.sheikh_jackson_javaproject;

import javafx.fxml.*;
import javafx.scene.control.*;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
