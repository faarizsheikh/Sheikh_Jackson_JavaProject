// UpdateItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.time.Year;
import org.example.sheikh_jackson_javaproject.pojo.Game;
import org.example.sheikh_jackson_javaproject.tables.GameTable;
import org.example.sheikh_jackson_javaproject.utils.*;
import static org.example.sheikh_jackson_javaproject.utils.NodeConsts.*;

public class UpdateItemTab extends Tab {

    private static final String[] FORM_LABELS =
            {"Search:", "New Title", "New Year", "New Genre", "New URL"};

    private final GameTable gt = GameTable.getInstance();
    private final ComboBox<Game> cB = new ComboBox<>();
    private final TextField tF = new TextField();
    private final TextField yF = new TextField();
    private final TextField gF = new TextField();
    private final TextField iF = new TextField();

    public UpdateItemTab() {
        setGraphic(tabTitle("Update Game"));

        VBox container = NodeConsts.vBox();
        GridPane gP = NodeConsts.gP();

        Control[] inputs = {cB, tF, yF, gF, iF};
        TextField[] txtFields = {tF, yF, gF, iF};

        for (Control input : inputs) {
            input.setPrefWidth(NodeConsts.FIELD_WIDTH);
            input.getStyleClass().add("form-input");
        }

        cB.getItems().setAll(gt.getAllGames());
        for (int i = 0; i < inputs.length; i++)
            gP.addRow(i, formLabel(FORM_LABELS[i] + ":"), inputs[i]);

        cB.setPromptText("-- Select Game to Update --");

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

        btn.setOnAction(e -> {
            Game sel = cB.getValue();

            if (sel == null) {
                Log.warn("Update attempted with no game selected");
                NodeConsts.alert(Alert.AlertType.WARNING, "Missing Selection", "WARNING!",
                        new Text("Please select a game first!")
                        {{ getStyleClass().add("side-note"); }}
                );
                return;
            }

            String t = tF.getText().trim();
            String y = yF.getText().trim();
            String g = gF.getText().trim();
            String i = iF.getText().trim();
            int yearVal;
            int currentYear = Year.now().getValue();

            try {
                if (t.isEmpty() || y.isEmpty() || g.isEmpty() || i.isEmpty()) {
                    Log.warn("Attempted to update game with missing fields: ID=" + sel.getId());
                    throw new Exception("All fields must be filled to update!");
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
                Log.info("Game updated: ID=" + sel.getId() + ", Title=" + t);

                NodeConsts.alert(Alert.AlertType.INFORMATION, "Updated in Library", "EDIT DETAILS",
                        new Text(String.format("We've updated a game: %s (%d)", t, yearVal))
                        {{ getStyleClass().add("side-note"); }}
                );

                // Clear input fields
                for (TextField txtField : txtFields) txtField.clear();
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
        });

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
}
