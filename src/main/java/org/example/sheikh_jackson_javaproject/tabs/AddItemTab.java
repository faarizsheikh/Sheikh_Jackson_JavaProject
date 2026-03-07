package org.example.sheikh_jackson_javaproject.tabs;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class AddItemTab extends Tab {
    public AddItemTab() {
        this.setText("Add Game");
        GridPane root = new GridPane();
        root.setHgap(10); root.setVgap(10);

        // Form Fields
        TextField titleField = new TextField();
        TextField devField = new TextField();
        TextField yearField = new TextField();
        TextField genreField = new TextField();
        TextField platformField = new TextField();
        TextField imageField = new TextField();

        root.add(new Label("Title:"), 0, 0); root.add(titleField, 1, 0);
        root.add(new Label("Developer:"), 0, 1); root.add(devField, 1, 1);
        root.add(new Label("Release Year:"), 0, 2); root.add(yearField, 1, 2);
        root.add(new Label("Genre:"), 0, 3); root.add(genreField, 1, 3);
        root.add(new Label("Platform:"), 0, 4); root.add(platformField, 1, 4);
        root.add(new Label("Image URL:"), 0, 5); root.add(imageField, 1, 5);

        Button saveBtn = new Button("Save Game");
        root.add(saveBtn, 1, 6);

        saveBtn.setOnAction(e -> {
            // 1. Logic to save to Database would go here...

            // 2. FILE I/O implementation (As requested by your teacher)
            // This creates a "log" of every game added to the system
            try (PrintWriter writer = new PrintWriter(new FileWriter("game_log.txt", true))) {
                writer.println("New Game Added: " + titleField.getText() + " | Platform: " + platformField.getText());
                System.out.println("Saved to file successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        this.setContent(root);
    }
}
