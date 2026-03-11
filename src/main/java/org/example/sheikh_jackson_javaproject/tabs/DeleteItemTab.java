package org.example.sheikh_jackson_javaproject.tabs;

import org.example.sheikh_jackson_javaproject.pojo.*;
import org.example.sheikh_jackson_javaproject.tables.*;
import org.example.sheikh_jackson_javaproject.Constants.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class DeleteItemTab extends Tab {

    private ComboBox<Game> cb = new ComboBox<>();

    public DeleteItemTab() {

        setGraphic(NodeConsts.createTabTitle("Delete Game"));

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50));

        GridPane gP = new GridPane();
        gP.setHgap(20);
        gP.setVgap(20);
        gP.setPadding(new Insets(40));
        gP.setAlignment(Pos.CENTER);

        double wideWidth = 450;

        cb.setPrefWidth(wideWidth);
        cb.getStyleClass().add("form-input");
        cb.setPromptText("-- Select Game to Delete --");

        // Load games ONCE
        cb.getItems().setAll(GameTable.getInstance().getAllGames());

        gP.addRow(0, createHugeLabel(), cb);

        Button btn = new Button("REMOVE FROM LIBRARY");
        btn.getStyleClass().addAll("btn", "delete-btn");
        btn.setPrefWidth(wideWidth);
        btn.setPrefHeight(60);

        gP.add(btn, 1, 1);

        btn.setOnAction(e -> {

            Game sel = cb.getValue();

            if (sel != null) {

                Alert confirm = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete " + sel.getTitle() + "?",
                        ButtonType.YES,
                        ButtonType.NO
                );

                confirm.showAndWait();

                if (confirm.getResult() == ButtonType.YES) {

                    GameTable.getInstance().deleteGame(sel.getId());

                    new Alert(Alert.AlertType.INFORMATION, "Delete Successful!").show();

                    // Reset UI
                    cb.getSelectionModel().clearSelection();
                    cb.setValue(null);

                    // Refresh list safely
                    cb.getItems().setAll(GameTable.getInstance().getAllGames());

                    cb.setSkin(null);
                }

            }
            else {
                new Alert(Alert.AlertType.WARNING, "Please select a game to delete!").show();
            }

        });

        container.getChildren().add(gP);
        setContent(container);
    }

    private Label createHugeLabel() {
        Label l = new Label("Select Game:");
        l.getStyleClass().add("form-label");
        return l;
    }
}