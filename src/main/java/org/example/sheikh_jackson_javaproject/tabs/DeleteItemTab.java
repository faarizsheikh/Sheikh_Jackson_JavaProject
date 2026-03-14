// DeleteItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import javafx.collections.FXCollections;
import javafx.scene.text.Text;
import org.example.sheikh_jackson_javaproject.HelloApplication;
import org.example.sheikh_jackson_javaproject.pojo.*;
import org.example.sheikh_jackson_javaproject.tables.*;
import org.example.sheikh_jackson_javaproject.Constants.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

import java.util.Objects;

public class DeleteItemTab extends Tab {

    private final ComboBox<Game> cB = new ComboBox<>();

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

        cB.setPrefWidth(wideWidth);
        cB.getStyleClass().add("form-input");
        cB.setPromptText("-- Select Game to Delete --");

        cB.getItems().setAll(GameTable.getInstance().getAllGames());

        gP.addRow(0, createHugeLbl(), cB);

        Button btn = new Button("REMOVE FROM LIBRARY");
        btn.getStyleClass().addAll("btn", "delete-btn");
        btn.setPrefWidth(wideWidth);
        btn.setPrefHeight(60);

        gP.add(btn, 1, 1);

        btn.setOnAction(e -> {

            Game sel = cB.getValue();

            if (sel != null) {
                Alert alert = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "",
                        ButtonType.YES,
                        ButtonType.NO
                );

                alert.setTitle("Remove Confirm");
                alert.setHeaderText("CONFIRMATION");

                VBox box = new VBox(5);
                alert.getDialogPane().getStylesheets().add(
                        Objects.requireNonNull(HelloApplication.class.getResource(
                                        "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                .toExternalForm()
                );

                Text confirmTxt = new Text("Are you sure you want to delete " + sel.getTitle() + "?");

                box.getChildren().add(confirmTxt);
                alert.getDialogPane().setContent(box);

                alert.showAndWait();


                if (alert.getResult() == ButtonType.YES) {
                    GameTable.getInstance().deleteGame(sel.getId());

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Updated in library");
                    alert1.setHeaderText("EDIT DETAILS");

                    VBox box1 = new VBox(5);
                    alert1.getDialogPane().getStylesheets().add(
                            Objects.requireNonNull(HelloApplication.class.getResource(
                                            "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                    .toExternalForm()
                    );

                    Text submitTxt = new Text("Delete Successful!");
                    submitTxt.getStyleClass().add("side-note");

                    box1.getChildren().add(
                            submitTxt
                    );

                    alert1.getDialogPane().setContent(box1);
                    alert1.showAndWait();

                    cB.getSelectionModel().clearSelection();
                    cB.setValue(null);

                    // Refresh list
                    cB.getItems().setAll(GameTable.getInstance().getAllGames());
                    cB.setItems(FXCollections.observableArrayList(GameTable.getInstance().getAllGames()));
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing game selection");
                alert.setHeaderText("WARNING!");

                VBox box = new VBox(5);
                alert.getDialogPane().getStylesheets().add(
                        Objects.requireNonNull(HelloApplication.class.getResource(
                                        "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                .toExternalForm()
                );

                Text gameSelectMissing = new Text("Please select a game first to delete!");
                gameSelectMissing.getStyleClass().add("side-note");

                box.getChildren().add(
                        gameSelectMissing
                );

                alert.getDialogPane().setContent(box);
                alert.showAndWait();
            }

        });

        container.getChildren().add(gP);
        setContent(container);

        // Auto refresh when tab selected
        setOnSelectionChanged(e -> {
            if (isSelected()) {

                Game selected = cB.getValue();
                Integer selectedId = selected != null ? selected.getId() : null;

                cB.getItems().setAll(GameTable.getInstance().getAllGames());

                if (selectedId != null) {
                    for (Game g : cB.getItems()) {
                        if (g.getId() == selectedId) {
                            cB.setValue(g);
                            break;
                        }
                    }
                }
            }
        });
    }

    private Label createHugeLbl() {
        Label lbl = new Label("Select Game:");
        lbl.getStyleClass().add("form-lbl");
        return lbl;
    }
}