// AddItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.time.Year;
import org.example.sheikh_jackson_javaproject.pojo.*;
import org.example.sheikh_jackson_javaproject.tables.*;
import org.example.sheikh_jackson_javaproject.utils.*;
import static org.example.sheikh_jackson_javaproject.utils.NodeConsts.*;

public class AddItemTab extends Tab {
    private final GameTable gt = GameTable.getInstance();

    private final ComboBox<Developer> dCB = new ComboBox<>();
    private final ComboBox<Platform> pCB = new ComboBox<>();
    private final TextField tF = new TextField();
    private final TextField yF = new TextField();
    private final TextField gF = new TextField();
    private final TextField iF = new TextField();

    private static final String[] FORM_LABELS =
            {"Title", "Developer", "Year", "Genre", "Platform", "URL"};

    private static final String[] PROMPTS =
            {"-- Select Developer --", "-- Select Platform --"};

    public AddItemTab() {
        setGraphic(tabTitle("Add Game"));

        VBox container = NodeConsts.vBox();
        GridPane gP = NodeConsts.gP();

        Control[] inputs = {tF, dCB, yF, gF, pCB, iF};
        ComboBox<?>[] comboBoxes = {dCB, pCB};
        TextField[] txtFields = {tF, yF, gF, iF};

        for (Control input : inputs) {
            input.setPrefWidth(NodeConsts.FIELD_WIDTH);
            input.getStyleClass().add("form-input");
        }
        dCB.getItems().setAll(gt.getAllDevelopers());
        pCB.getItems().setAll(gt.getAllPlatforms());

        for (int i = 0; i < inputs.length; i++) gP.addRow(i, formLabel(FORM_LABELS[i] + ":"), inputs[i]);
        for (int i = 0; i < comboBoxes.length; i++) comboBoxes[i].setPromptText(PROMPTS[i]);

        // Extra Large Button
        Button btn = NodeConsts.button("ADD TO LIBRARY", "add-btn");
        gP.add(btn, 1, 6);

        btn.setOnAction(e -> {
            try {
                String t = tF.getText().trim(), y = yF.getText().trim(),
                        g = gF.getText().trim(), i = iF.getText().trim();

                if (t.isEmpty() || dCB.getValue() == null || y.isEmpty()
                        || g.isEmpty() || pCB.getValue() == null || i.isEmpty()) {
                    Log.warn("Attempted to add game with missing fields");
                    throw new Exception("All fields must be filled to add!");
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

                if (!iLow.startsWith("http://")
                        && !iLow.startsWith("https://") && !iLow.startsWith("www.")) {
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
                            && game.getYear() == yearVal && game.getPlatform().equals(pCB.getValue().toString())) {
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
                Log.info("Game added: " + t + " (" + yearVal + ") by " + dCB.getValue());

                NodeConsts.alert(Alert.AlertType.INFORMATION, "Added to Library", "SUBMISSION DETAILS",
                        new Text(
                                String.format("We've added %s (%d), a game created by %s.",
                                        t, yearVal, dCB.getValue()))
                        {{
                            getStyleClass().add("side-note");
                        }}
                );

                for (TextField txtField : txtFields) txtField.clear();

                for (ComboBox<?> comboBox : comboBoxes) {
                    comboBox.getSelectionModel().clearSelection();
                    comboBox.setValue(null);
                }

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
        });
        container.getChildren().add(gP);
        setContent(container);
    }
}
