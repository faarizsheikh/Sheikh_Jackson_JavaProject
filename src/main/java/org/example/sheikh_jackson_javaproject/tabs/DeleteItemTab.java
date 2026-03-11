// DeleteItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import org.example.sheikh_jackson_javaproject.pojo.*;
import org.example.sheikh_jackson_javaproject.tables.*;
import org.example.sheikh_jackson_javaproject.Constants.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.geometry.*;

public class DeleteItemTab extends Tab {
    private ComboBox<Game> cb = new ComboBox<>();

    public DeleteItemTab() {
        setGraphic(NodeConsts.createTabTitle("Delete Game"));

        // 1. Outer VBox to center the GridPane
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50));

        // 2. GridPane for structured, centered layout
        GridPane gP = new GridPane();
        gP.setHgap(20);
        gP.setVgap(20);
        gP.setPadding(new Insets(40));
        gP.setAlignment(Pos.CENTER);

        // 3. Styling the ComboBox
        double wideWidth = 450;

        cb.setPrefWidth(wideWidth);
        cb.getStyleClass().add("form-input");
        cb.setPromptText("-- Select Game to Delete --");

        // 4. Adding components to Grid
        gP.addRow(0, createHugeLabel(), cb);

        // 5. Huge Delete Button
        Button btn = new Button("REMOVE FROM LIBRARY");
        btn.getStyleClass().addAll("btn", "delete-btn");
        btn.setPrefWidth(wideWidth);
        btn.setPrefHeight(60);

        gP.add(btn, 1, 1);

        btn.setOnAction(e -> {
            Game sel = cb.getValue();
            if (sel != null) {
                // Confirmation Alert (Good practice for Delete)
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + sel.getTitle() + "?", ButtonType.YES, ButtonType.NO);
                confirm.showAndWait();

                if (confirm.getResult() == ButtonType.YES) {
                    GameTable.getInstance().deleteGame(sel.getId());
                    new Alert(Alert.AlertType.INFORMATION, "Delete Successful!").show();

                    // Reset UI
                    cb.getSelectionModel().clearSelection();
                    cb.setItems(FXCollections.observableArrayList(GameTable.getInstance().getAllGames()));
                    cb.setValue(null);
                    cb.setSkin(null);
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please select a game to delete!").show();
            }
        });

        container.getChildren().add(gP);
        setContent(container);

        setOnSelectionChanged(e -> {
            if(isSelected()) cb.setItems(FXCollections.observableArrayList(GameTable.getInstance().getAllGames()));
        });
    }

    private Label createHugeLabel() {
        Label l = new Label("Select Game:");
        l.getStyleClass().add("form-label");
        return l;
    }
}