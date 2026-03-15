// DeleteItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.example.sheikh_jackson_javaproject.pojo.*;
import org.example.sheikh_jackson_javaproject.tables.*;
import org.example.sheikh_jackson_javaproject.utils.*;
import static org.example.sheikh_jackson_javaproject.utils.NodeConsts.*;

public class DeleteItemTab extends Tab {
    private final GameTable gt = GameTable.getInstance();
    private final ComboBox<Game> cB = new ComboBox<>();

    public DeleteItemTab() {
        setGraphic(tabTitle("Delete Game"));

        VBox container = NodeConsts.vBox();
        GridPane gP = NodeConsts.gP();

        cB.setPrefWidth(NodeConsts.FIELD_WIDTH);
        cB.getStyleClass().add("form-input");
        cB.getItems().setAll(gt.getAllGames());
        cB.setPromptText("-- Select Game to Delete --");
        gP.addRow(0, formLabel("Select Game:"), cB);

        Button btn = NodeConsts.button("REMOVE FROM LIBRARY", "delete-btn");
        gP.add(btn, 1, 1);

        btn.setOnAction(e -> {
            Game sel = cB.getValue();

            if (sel != null) {
                Log.info("User selected game for deletion: " + sel.getTitle());

                Alert alert = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete " + sel.getTitle() + "?",
                        ButtonType.YES,
                        ButtonType.NO
                );
                alert.setTitle("Remove Confirm");
                alert.setHeaderText("CONFIRMATION");

               NodeConsts.applyCSS(alert);
               alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    Log.info("Deleting game: " + sel.getTitle() + " (ID=" + sel.getId() + ")");

                    gt.deleteGame(sel.getId());

                    try {
                        Thread.sleep(500);

                    } catch (InterruptedException ex) {
                        Log.error("Delete delay interrupted.", ex);
                    }
                    NodeConsts.alert(Alert.AlertType.INFORMATION, "Deleted in Library", "DELETE DETAILS",
                            new Text("Delete Successful!")
                            {{
                                getStyleClass().add("side-note");
                            }}
                    );
                    cB.getSelectionModel().clearSelection();
                    cB.setValue(null);
                    cB.getItems().setAll(gt.getAllGames());
                    Log.info("Game removed from library and list refreshed.");
                }

            } else {
                Log.warn("Delete attempted without selecting a game.");
                NodeConsts.alert(Alert.AlertType.WARNING, "Missing Selection", "WARNING!",
                        new Text("Please select a game first to delete!")
                        {{
                            getStyleClass().add("side-note");
                        }}
                );
            }
        });
        container.getChildren().add(gP);
        setContent(container);

        // Auto refresh when tab selected
        setOnSelectionChanged(e -> {
            if (isSelected()) {
                Log.info("Delete tab selected. Refreshing game list.");
                Game selected = cB.getValue();
                Integer selectedId = selected != null ? selected.getId() : null;

                cB.getItems().setAll(gt.getAllGames());

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
}
