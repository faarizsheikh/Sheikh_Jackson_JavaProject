// Launcher.java:

package org.example.sheikh_jackson_javaproject;

import javafx.application.Application;

/**
 * Entry point launcher for the JFX application.
 * Delegates execution to the main JFX Application class.
 * Design Choice:
 * Separating the launcher avoids JFX runtime issues in certain environments.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class Launcher {

    /**
     * Main method that launches the JFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }
}
