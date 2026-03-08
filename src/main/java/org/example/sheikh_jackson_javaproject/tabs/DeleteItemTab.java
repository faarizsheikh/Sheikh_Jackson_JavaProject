package org.example.sheikh_jackson_javaproject.tabs;

import org.example.sheikh_jackson_javaproject.pojo.Game;
import org.example.sheikh_jackson_javaproject.tables.GameTable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;

public class DeleteItemTab extends Tab {
    private ComboBox<Game> gameCombo;

    public DeleteItemTab() {
        this.setText("Delete Game");
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label label = new Label("Select a game to remove from the system:");

        gameCombo = new ComboBox<>();
        gameCombo.setPromptText("--- Select Game ---");
        gameCombo.setPrefWidth(250);

        refreshDropDown(); // Load games from DB immediately

        Button deleteBtn = new Button("Delete Record");
        deleteBtn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");

        deleteBtn.setOnAction(e -> {
            Game selected = gameCombo.getSelectionModel().getSelectedItem();

            if (selected != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to permanently delete '" + selected.getTitle() + "'?",
                        ButtonType.YES, ButtonType.NO);

                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {

                        GameTable.getInstance().deleteGame(selected.getId());

                        refreshDropDown();
                        new Alert(Alert.AlertType.INFORMATION, "Game deleted successfully!").show();
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING, "Please select a game first!").show();
            }
        });

        root.getChildren().addAll(label, gameCombo, deleteBtn);
        this.setContent(root);

        this.setOnSelectionChanged(event -> {
            if (this.isSelected()) {
                refreshDropDown();
            }
        });
    }

    private void refreshDropDown() {
        gameCombo.setItems(FXCollections.observableArrayList(GameTable.getInstance().getAllGames()));
    }
}