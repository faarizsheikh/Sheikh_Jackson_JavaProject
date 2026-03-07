package org.example.sheikh_jackson_javaproject.tabs;

import org.example.sheikh_jackson_javaproject.pojo.*;
import org.example.sheikh_jackson_javaproject.tables.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.*;

public class AddItemTab extends Tab {
    public AddItemTab() {
        this.setText("Add Game");
        GridPane root = new GridPane();
        root.setHgap(10); root.setVgap(10);

        TextField titleField = new TextField(),
                yearField = new TextField(),
                genreField = new TextField(),
                imageField = new TextField();

        ComboBox<Developer> devCombo = new ComboBox<>();
        devCombo.getItems().addAll(GameTable.getInstance().getAllDevelopers());
        devCombo.setPromptText("Select Developer");

        ComboBox<Platform> platCombo = new ComboBox<>();
        platCombo.getItems().addAll(GameTable.getInstance().getAllPlatforms());
        platCombo.setPromptText("Select Platform");

        root.add(new Label("Title:"), 0, 0); root.add(titleField, 1, 0);
        root.add(new Label("Developer:"), 0, 1); root.add(devCombo, 1, 1);
        root.add(new Label("Release Year:"), 0, 2); root.add(yearField, 1, 2);
        root.add(new Label("Genre:"), 0, 3); root.add(genreField, 1, 3);
        root.add(new Label("Platform:"), 0, 4); root.add(platCombo, 1, 4);
        root.add(new Label("Image URL:"), 0, 5); root.add(imageField, 1, 5);

        Button addBtn = new Button("Add");
        root.add(addBtn, 1, 6);

        addBtn.setOnAction(e -> {
            try {
                String title = titleField.getText().trim(),
                        genre = genreField.getText().trim(),
                        imageUrl = imageField.getText().trim(),
                        yearText = yearField.getText().trim();

                if (title.isEmpty() || genre.isEmpty() || imageUrl.isEmpty() ||
                        devCombo.getSelectionModel().isEmpty() || platCombo.getSelectionModel().isEmpty()) {
                    throw new Exception("All fields must be filled!");
                }

                int devId = devCombo.getSelectionModel().getSelectedItem().getId(),
                        platId = platCombo.getSelectionModel().getSelectedItem().getId();

                Game newGame = new Game(
                        0,
                        title,
                        String.valueOf(devId),
                        Integer.parseInt(yearText),
                        genre,
                        String.valueOf(platId),
                        imageUrl
                );

                GameTable.getInstance().addGame(newGame);

                try (PrintWriter writer = new PrintWriter(new FileWriter("game_log.txt", true))) {
                    writer.println("Added: " + title);
                } catch (IOException ex) { ex.printStackTrace(); }

                titleField.clear();
                yearField.clear();
                genreField.clear();
                imageField.clear();
                devCombo.getSelectionModel().clearSelection();
                platCombo.getSelectionModel().clearSelection();

                System.out.println("Success: Game saved!");

            } catch (NumberFormatException nfe) {
                new Alert(Alert.AlertType.ERROR, "Release Year must be a valid number!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }
        });

        this.setContent(root);
    }
}