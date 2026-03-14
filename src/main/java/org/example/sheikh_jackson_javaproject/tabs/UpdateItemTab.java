// UpdateItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.example.sheikh_jackson_javaproject.HelloApplication;
import org.example.sheikh_jackson_javaproject.pojo.Game;
import org.example.sheikh_jackson_javaproject.tables.GameTable;
import org.example.sheikh_jackson_javaproject.Constants.*;
import javafx.scene.control.*;
import java.time.Year;
import java.util.Objects;

public class UpdateItemTab extends Tab {

    private final ComboBox<Game> cB = new ComboBox<>();
    private final TextField tF = new TextField();
    private final TextField yF = new TextField();
    private final TextField gF = new TextField();
    private final TextField iF = new TextField();

    public UpdateItemTab() {

        setGraphic(NodeConsts.createTabTitle("Update Game"));

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50));

        GridPane gP = new GridPane();
        gP.setHgap(20);
        gP.setVgap(20);
        gP.setPadding(new Insets(40));
        gP.setAlignment(Pos.CENTER);

        double wideWidth = 450;

        Control[] inputs = {tF, yF, gF, iF, cB};
        for (Control input : inputs) {
            input.setPrefWidth(wideWidth);
            input.getStyleClass().add("form-input");
        }

        cB.getItems().setAll(GameTable.getInstance().getAllGames());
        cB.setPromptText("-- Select Game to Update --");

        gP.addRow(0, createHugeLbl("Search:"), cB);
        gP.addRow(1, createHugeLbl("New Title:"), tF);
        gP.addRow(2, createHugeLbl("New Year:"), yF);
        gP.addRow(3, createHugeLbl("New Genre:"), gF);
        gP.addRow(4, createHugeLbl("New URL:"), iF);

        cB.setOnAction(e -> {
            Game sel = cB.getValue();
            if (sel != null) {
                tF.setText(sel.getTitle());
                yF.setText(String.valueOf(sel.getYear()));
                gF.setText(sel.getGenre());
                iF.setText(sel.getImageUrl());
            }
        });

        Button btn = new Button("UPDATE IN LIBRARY");
        btn.getStyleClass().addAll("btn", "update-btn");
        btn.setPrefWidth(wideWidth);
        btn.setPrefHeight(60);

        gP.add(btn, 1, 5);

        btn.setOnAction(e -> {

            Game sel = cB.getValue();

            if (sel != null) {

                try {
                    String t = tF.getText().trim();
                    String y = yF.getText().trim();
                    String g = gF.getText().trim();
                    String i = iF.getText().trim();

                    if (t.isEmpty() || y.isEmpty() || g.isEmpty() || i.isEmpty())
                        throw new Exception("All fields must be filled to update!");

                    int yearVal = Integer.parseInt(y);
                    int currentYear = Year.now().getValue();

                    if (yearVal < 1950 || yearVal > currentYear) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Year");
                        alert.setHeaderText("WARNING!");

                        VBox box = new VBox(5);
                        alert.getDialogPane().getStylesheets().add(
                                Objects.requireNonNull(HelloApplication.class.getResource(
                                                "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                        .toExternalForm()
                        );

                        Text yearFail = new Text("Invalid Year: Range 1950 - " + currentYear);
                        yearFail.getStyleClass().add("side-note");

                        box.getChildren().add(
                                yearFail
                        );

                        alert.getDialogPane().setContent(box);
                        alert.showAndWait();
                        return;
                    }

                    String iLow = i.toLowerCase();

                    if (!iLow.startsWith("http://")
                            && !iLow.startsWith("https://")
                            && !iLow.startsWith("www.")) {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid URL");
                        alert.setHeaderText("WARNING!");

                        VBox box = new VBox(5);
                        alert.getDialogPane().getStylesheets().add(
                                Objects.requireNonNull(HelloApplication.class.getResource(
                                                "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                        .toExternalForm()
                        );

                        Text urlFail = new Text("URL must start with http://, https://, or www.");
                        urlFail.getStyleClass().add("side-note");

                        box.getChildren().add(
                                urlFail
                        );

                        alert.getDialogPane().setContent(box);
                        alert.showAndWait();
                        return;
                    }

                    sel.setTitle(t);
                    sel.setYear(yearVal);
                    sel.setGenre(g);
                    sel.setImageUrl(i);

                    GameTable.getInstance().updateGame(sel);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Updated in library");
                    alert.setHeaderText("EDIT DETAILS");

                    VBox box = new VBox(5);
                    alert.getDialogPane().getStylesheets().add(
                            Objects.requireNonNull(HelloApplication.class.getResource(
                                            "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                    .toExternalForm()
                    );

                    Text submitTxt = new Text(
                            String.format("We've updated a game: %s (%d)",
                                    t, yearVal)
                    );
                    submitTxt.getStyleClass().add("side-note");

                    box.getChildren().add(
                            submitTxt
                    );

                    alert.getDialogPane().setContent(box);
                    alert.showAndWait();

                    tF.clear();
                    yF.clear();
                    gF.clear();
                    iF.clear();

                    cB.getSelectionModel().clearSelection();
                    cB.setValue(null);

                    // Refresh list after update
                    cB.getItems().setAll(GameTable.getInstance().getAllGames());
                    cB.setItems(FXCollections.observableArrayList(GameTable.getInstance().getAllGames()));
                }
                catch (NumberFormatException nfe) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Year");
                    alert.setHeaderText("WARNING!");

                    VBox box = new VBox(5);
                    alert.getDialogPane().getStylesheets().add(
                            Objects.requireNonNull(HelloApplication.class.getResource(
                                            "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                    .toExternalForm()
                    );

                    Text yearFail = new Text("Year must be a number");
                    yearFail.getStyleClass().add("side-note");

                    box.getChildren().add(
                            yearFail
                    );

                    alert.getDialogPane().setContent(box);
                    alert.showAndWait();

                }
                catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Missing fields");
                    alert.setHeaderText("WARNING!");

                    VBox box = new VBox(5);
                    alert.getDialogPane().getStylesheets().add(
                            Objects.requireNonNull(HelloApplication.class.getResource(
                                            "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                    .toExternalForm()
                    );

                    Text missingFields = new Text(ex.getMessage());
                    missingFields.getStyleClass().add("side-note");

                    box.getChildren().add(
                            missingFields
                    );

                    alert.getDialogPane().setContent(box);
                    alert.showAndWait();
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

                Text gameSelectMissing = new Text("Please select a game first!");
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

    private Label createHugeLbl(String text) {
        Label lbl = new Label(text);
        lbl.getStyleClass().add("form-lbl");
        return lbl;
    }
}