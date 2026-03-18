// IntroAnimation.java:

package org.example.sheikh_jackson_javaproject.utils;

import javafx.animation.*;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Handles the intro animation displayed when application starts.

 * This class creates a fade-in/out, scale, and pause animation sequence
 * for the application logo before transitioning to the next scene.

 * Design Choice:
 * Uses JFX animation classes (FadeTransition, ScaleTransition, SequentialTransition)
 * to provide a smooth and professional UI experience.

 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class IntroAnimation {

    /**
     * Private constructor to prevent instantiation.
     */
    private IntroAnimation() {}

    /**
     * Plays the intro animation on the given stage.

     * @param stage the primary stage where the animation will be displayed
     * @param onFinished callback to execute after animation completes
     */
    public static void play(Stage stage, Runnable onFinished) {
        StackPane root = new StackPane();
        root.getStyleClass().add("intro-bg");

        Text logo = new Text("Nodes of Noobs");
        logo.getStyleClass().add("intro-logo");
        logo.setOpacity(0);

        root.getChildren().add(logo);

        Scene scene = new Scene(root, NodeConsts.SCENE_WIDTH, NodeConsts.SCENE_HEIGHT);
        NodeConsts.applyCSS(scene);
        stage.setScene(scene);
        stage.show();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), logo);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        SequentialTransition sequence = getSequentialTransition(onFinished, logo, fadeIn);
        sequence.play();
    }

    /**
     * Creates the full animation sequence.

     * @param onFinished callback after animation completes
     * @param logo the text node being animated
     * @param fadeIn fade-in animation
     * @return configured SequentialTransition
     */
    private static SequentialTransition getSequentialTransition(Runnable onFinished, Text logo, FadeTransition fadeIn) {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(2), logo);
        scale.setFromX(0.7);
        scale.setFromY(0.7);
        scale.setToX(1);
        scale.setToY(1);

        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), logo);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        SequentialTransition sequence =
                new SequentialTransition(
                        new ParallelTransition(fadeIn, scale),
                        pause,
                        fadeOut
                );
        sequence.setOnFinished(e -> onFinished.run());
        return sequence;
    }
}
