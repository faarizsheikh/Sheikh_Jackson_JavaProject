// DeleteItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.sheikh_jackson_javaproject.pojo.Game;
import org.example.sheikh_jackson_javaproject.tables.GameTable;
import org.example.sheikh_jackson_javaproject.utils.*;
import static org.example.sheikh_jackson_javaproject.utils.NodeConsts.*;

/**
 * Tab for deleting existing Game entries.

 * Design Choices:
 * - Confirmation dialog prevents accidental deletion
 * - Uses delay for better UX feedback

 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class DeleteItemTab extends Tab {

    private final ComboBox<Game> cB = new ComboBox<>();
    private final GameTable gt = GameTable.getInstance();

    /**
     * Constructs DeleteItemTab UI.
     */
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

        btn.setOnAction(e -> handleDelete());
        container.getChildren().add(gP);
        setContent(container);

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

    /**
     * Handles deletion logic with confirmation.
     */
    private void handleDelete() {
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
                Log.action("DELETE", "ID=" + sel.getId());
                PauseTransition pause = getPauseTransition();
                pause.play();
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
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private PauseTransition getPauseTransition() {
        PauseTransition pause = new PauseTransition(Duration.millis(500));

        pause.setOnFinished(ev -> {
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
        });
        return pause;
    }
}
