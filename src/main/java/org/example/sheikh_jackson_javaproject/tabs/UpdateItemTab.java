// UpdateItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import java.time.Year;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.example.sheikh_jackson_javaproject.database.DBConst;
import org.example.sheikh_jackson_javaproject.pojo.Game;
import org.example.sheikh_jackson_javaproject.tables.GameTable;
import org.example.sheikh_jackson_javaproject.utils.*;
import static org.example.sheikh_jackson_javaproject.utils.NodeConsts.*;

/**
 * Tab responsible for updating existing Game entries in the library.
 * Design Choices:
 * - Uses ComboBox to select an existing game
 * - Loads selected game data into editable fields
 * - Validates all inputs before updating
 * - Uses DAO (GameTable) for database operations
 * - Refreshes UI after update to ensure consistency
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class UpdateItemTab extends Tab {

    /**
     * Labels used for form fields in the update UI.
     * Index order maps directly to the inputs array:
     * [ComboBox, Title, Year, Genre, URL].
     */
    private static final String[] FORM_LABELS =
            {"Search", "New Title", "New Year", "New Genre", "New URL"};

    /**
     * ComboBox used to select an existing Game to update.
     * Also acts as the source for populating editable fields.
     */
    private final ComboBox<Game> cB = new ComboBox<>();

    /**
     * Singleton instance of GameTable used for database update operations.
     */
    private final GameTable gt = GameTable.getInstance();

    /**
     * TextField for editing the game's title.
     */
    private final TextField tF = new TextField();

    /**
     * TextField for editing the game's release year.
     */
    private final TextField yF = new TextField();

    /**
     * TextField for editing the game's genre.
     */
    private final TextField gF = new TextField();

    /**
     * TextField for editing the game's image URL.
     */
    private final TextField iF = new TextField();

    /**
     * Constructs the UpdateItemTab UI, initializes components,
     * and binds event handlers for user interaction.
     */
    public UpdateItemTab() {
        setGraphic(tabTitle("Update Game"));

        VBox container = NodeConsts.vBox();
        GridPane gP = NodeConsts.gP();

        Control[] inputs = {cB, tF, yF, gF, iF};

        for (Control input : inputs) {
            input.setPrefWidth(NodeConsts.FIELD_WIDTH);
            input.getStyleClass().add("form-input");
        }
        cB.getItems().setAll(gt.getAllGames());
        cB.setPromptText("-- Select Game to Update --");

        for (int i = 0; i < inputs.length; i++) gP.addRow(i, formLabel(FORM_LABELS[i] + ":"), inputs[i]);

        cB.setOnAction(e -> {
            Game sel = cB.getValue();

            if (sel != null) {
                tF.setText(sel.getTitle());
                yF.setText(String.valueOf(sel.getYear()));
                gF.setText(sel.getGenre());
                iF.setText(sel.getImageUrl());
            }
        });
        Button btn = NodeConsts.button("UPDATE IN LIBRARY", "update-btn");
        gP.add(btn, 1, 5);

        btn.setOnAction(e -> handleUpdate());
        container.getChildren().add(gP);
        setContent(container);

        setOnSelectionChanged(e -> {
            if (isSelected()) {
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
     * Handles update action including input validation,
     * model mutation, and persistence to the database.
     */
    private void handleUpdate() {
        Game sel = cB.getValue();

        if (sel == null) {
            Log.warn("Update attempted with no game selected");

            NodeConsts.alert(Alert.AlertType.WARNING, "Missing Selection", "WARNING!",
                    new Text("Please select a game first!")
                    {{ getStyleClass().add("side-note"); }}
            );
            return;
        }

        int currentYear = Year.now().getValue(), yearVal;

        String t = tF.getText().trim(),
                y = yF.getText().trim(),
                g = gF.getText().trim(),
                i = iF.getText().trim();

        try {
            if (t.isEmpty() || y.isEmpty() || g.isEmpty() || i.isEmpty()) {
                Log.warn("Attempted to update game with missing fields: ID=" + sel.getId());
                throw new Exception("All fields must be filled to update!");
            }

            if (exceedsMax(t, DBConst.MAX_TITLE, "Title") ||
                    exceedsMax(g, DBConst.MAX_GENRE, "Genre") ||
                    exceedsMax(i, DBConst.MAX_URL, "URL")) {
                return;
            }
            yearVal = Integer.parseInt(y);

            if (yearVal < 1950 || yearVal > currentYear) {
                Log.warn("Invalid year for update: " + yearVal + " (Game ID=" + sel.getId() + ")");

                NodeConsts.alert(Alert.AlertType.WARNING, "Invalid Year", "WARNING!",
                        new Text("Year must be between 1950 and " + currentYear)
                        {{ getStyleClass().add("side-note"); }}
                );
                return;
            }

            String iLow = i.toLowerCase();
            if (!iLow.startsWith("http://") && !iLow.startsWith("https://") && !iLow.startsWith("www.")) {
                Log.warn("Invalid URL entered for update: " + i + " (Game ID=" + sel.getId() + ")");

                NodeConsts.alert(Alert.AlertType.WARNING, "Invalid URL", "WARNING!",
                        new Text("URL must start with http://, https://, or www.")
                        {{ getStyleClass().add("side-note"); }}
                );
                return;
            }
            sel.setTitle(t);
            sel.setYear(yearVal);
            sel.setGenre(g);
            sel.setImageUrl(i);
            gt.updateGame(sel);
            Log.action("UPDATE", t + " (" + yearVal + ")");

            NodeConsts.alert(Alert.AlertType.INFORMATION, "Updated in Library", "EDIT DETAILS",
                    new Text(String.format("We've updated a game: %s (%d)", t, yearVal))
                    {{ getStyleClass().add("side-note"); }}
            );
            clearFields();

            cB.getSelectionModel().clearSelection();
            cB.setValue(null);
            cB.getItems().setAll(gt.getAllGames());

        } catch (NumberFormatException nfe) {
            Log.warn("Non-numeric year entered for update: " + y + " (Game ID=" + sel.getId() + ")");

            NodeConsts.alert(Alert.AlertType.WARNING, "Invalid Year", "WARNING!",
                    new Text("Year must be a number")
                    {{ getStyleClass().add("side-note"); }}
            );

        } catch (Exception ex) {
            Log.error("Failed to update game: ID=" + sel.getId(), ex);

            NodeConsts.alert(Alert.AlertType.WARNING, "Missing Fields", "WARNING!",
                    new Text(ex.getMessage())
                    {{ getStyleClass().add("side-note"); }}
            );
        }
    }

    /**
     * Clears all editable input fields in the form.
     * Does not modify the ComboBox selection.
     */
    private void clearFields() {
        tF.clear();
        yF.clear();
        gF.clear();
        iF.clear();
    }
}
