// Launcher.java:

package org.example.sheikh_jackson_javaproject;

import javafx.application.Application;

/**
 * Entry point launcher for the JavaFX application.
 * This class is responsible only for starting the JavaFX runtime and
 * delegating control to {@link HelloApplication}.
 * Design Choice: Separating the launcher class avoids JavaFX runtime
 * limitations in certain IDEs and modular environments.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class Launcher {

    /**
     * Starts the JavaFX application.
     *
     * @param args command-line arguments passed at launch
     *
     * @implNote Delegates execution to {@link HelloApplication}.
     */
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }
}
