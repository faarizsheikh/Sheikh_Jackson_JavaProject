// AddItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import java.time.Year;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.example.sheikh_jackson_javaproject.database.DBConst;
import org.example.sheikh_jackson_javaproject.pojo.*;
import org.example.sheikh_jackson_javaproject.tables.*;
import org.example.sheikh_jackson_javaproject.utils.*;
import static org.example.sheikh_jackson_javaproject.utils.NodeConsts.*;

/**
 * JavaFX tab for adding new Game entries to the library.
 * This tab provides a form-based interface for creating and submitting
 * new Game objects into the database after validating user input.
 * Design Choices:
 * Uses reusable UI utilities (NodeConsts), DAO pattern (GameTable),
 * and input validation to ensure data integrity before insertion.
 *
 * @author Faariz Sheikh
 * @version 1.0
 */
public class AddItemTab extends Tab {

    /**
     * Labels used for the game input form fields in the UI.
     * Defines the display text for each corresponding input row.
     */
    private static final String[] FORM_LABELS =
            {"Title", "Developer", "Year", "Genre", "Platform", "URL"};

    /**
     * Placeholder prompt texts used for ComboBox selections
     * in the Add Game form.
     */
    private static final String[] PROMPTS =
            {"-- Select Developer --", "-- Select Platform --"};

    /**
     * ComboBox for selecting a Developer entity.
     */
    private final ComboBox<Developer> dCB = new ComboBox<>();

    /**
     * ComboBox for selecting a Platform entity.
     */
    private final ComboBox<Platform> pCB = new ComboBox<>();

    /**
     * Singleton instance of GameTable used for all
     * database operations related to Game entities.
     */
    private final GameTable gt = GameTable.getInstance();

    /**
     * Text field for entering the game title.
     */
    private final TextField tF = new TextField();

    /**
     * Text field for entering the release year of the game.
     */
    private final TextField yF = new TextField();

    /**
     * Text field for entering the game genre.
     */
    private final TextField gF = new TextField();

    /**
     * Text field for entering the image URL of the game.
     */
    private final TextField iF = new TextField();

    /**
     * Constructs the AddItemTab and initializes all UI components,
     * including form fields, dropdowns, and event handlers.
     */
    public AddItemTab() {
        setGraphic(tabTitle("Add Game"));

        VBox container = NodeConsts.vBox();
        GridPane gP = NodeConsts.gP();

        Control[] inputs = {tF, dCB, yF, gF, pCB, iF};

        for (Control input : inputs) {
            input.setPrefWidth(NodeConsts.FIELD_WIDTH);
            input.getStyleClass().add("form-input");
        }
        DeveloperTable dt = DeveloperTable.getInstance();
        PlatformTable pt = PlatformTable.getInstance();
        dCB.getItems().setAll(dt.getAllDevelopers());
        pCB.getItems().setAll(pt.getAllPlatforms());
        dCB.setPromptText(PROMPTS[0]);
        pCB.setPromptText(PROMPTS[1]);

        for (int i = 0; i < inputs.length; i++) gP.addRow(i, formLabel(FORM_LABELS[i] + ":"), inputs[i]);

        Button btn = NodeConsts.button("ADD TO LIBRARY", "add-btn");
        gP.add(btn, 1, 6);

        btn.setOnAction(e -> handleAdd());
        container.getChildren().add(gP);
        setContent(container);
    }

    /**
     * Handles the logic for adding a new game entry.
     * This includes input validation, duplicate checking,
     * URL and year validation, and database insertion using GameTable DAO.
     * Displays appropriate alerts for validation errors or success states.
     */
    private void handleAdd() {
            try {
                String t = tF.getText().trim(),
                        y = yF.getText().trim(),
                        g = gF.getText().trim(),
                        i = iF.getText().trim();

                if (t.isEmpty() || dCB.getValue() == null || y.isEmpty()
                        || g.isEmpty() || pCB.getValue() == null || i.isEmpty()) {
                    Log.warn("Attempted to add game with missing fields");
                    throw new Exception("All fields must be filled to add!");
                }

                if (exceedsMax(t, DBConst.MAX_TITLE, "Title") ||
                        exceedsMax(g, DBConst.MAX_GENRE, "Genre") ||
                        exceedsMax(i, DBConst.MAX_URL, "URL")) {
                    return;
                }
                int yearVal = Integer.parseInt(y),
                        currentYear = Year.now().getValue();

                if (yearVal < 1950 || yearVal > currentYear) {
                    Log.warn("Invalid year entered for game: " + yearVal);
                    NodeConsts.alert(Alert.AlertType.WARNING, "Invalid Year", "WARNING!",
                            new Text("Year must be between 1950 and " + currentYear)
                            {{
                                getStyleClass().add("side-note");
                            }}
                    );
                    return;
                }
                String iLow = i.toLowerCase();

                if (!iLow.startsWith("http://") &&
                        !iLow.startsWith("https://")
                        && !iLow.startsWith("www.")) {
                    Log.warn("Invalid URL entered: " + i);
                    NodeConsts.alert(Alert.AlertType.WARNING, "Invalid URL", "WARNING!",
                            new Text("URL must start with http://, https://, or www.")
                            {{
                                getStyleClass().add("side-note");
                            }}
                    );
                    return;
                }
                int dId = dCB.getValue().getId(),
                        pId = pCB.getValue().getId();

                for (Game game : gt.getAllGames()) {
                    if (game.getTitle().equalsIgnoreCase(t)
                            && game.getYear() == yearVal
                            && game.getPlatform().equals(pCB.getValue().toString())) {
                        Log.warn("Duplicate game detected: " + t + " (" + yearVal + ")");
                        NodeConsts.alert(Alert.AlertType.WARNING, "Double Trouble", "WARNING!",
                                new Text("Duplicate found on this platform!")
                                {{
                                    getStyleClass().add("side-note");
                                }}
                        );
                        return;
                    }
                }
                gt.addGame(
                        new Game(0, t, String.valueOf(dId), yearVal, g, String.valueOf(pId), i));

                Log.action("ADD", String.format("%s (%d) by %s",
                        t, yearVal, dCB.getValue()));

                NodeConsts.alert(Alert.AlertType.INFORMATION, "Added to Library", "SUBMISSION DETAILS",
                        new Text(
                                String.format("We've added %s (%d), a game created by %s.",
                                        t, yearVal, dCB.getValue()))
                        {{
                            getStyleClass().add("side-note");
                        }}
                );
                clearFields();

            } catch (NumberFormatException nfe) {
                Log.warn("Non-numeric year entered: " + yF.getText());
                NodeConsts.alert(Alert.AlertType.WARNING, "Invalid Year", "WARNING!",
                        new Text("Year must be a number")
                        {{
                            getStyleClass().add("side-note");
                        }}
                );

            } catch (Exception ex) {
                Log.error("Failed to add game: " + ex.getMessage(), ex);
                NodeConsts.alert(Alert.AlertType.WARNING, "Missing Fields", "WARNING!",
                        new Text(ex.getMessage())
                        {{
                            getStyleClass().add("side-note");
                        }}
                );
            }
    }

    /**
     * Resets all input fields and selection controls after a successful submission.
     */
    private void clearFields() {
        tF.clear();
        yF.clear();
        gF.clear();
        iF.clear();
        dCB.setValue(null);
        pCB.setValue(null);
    }
}
