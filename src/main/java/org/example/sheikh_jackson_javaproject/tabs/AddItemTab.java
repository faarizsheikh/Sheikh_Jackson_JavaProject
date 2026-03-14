// AddItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import javafx.geometry.*;
import javafx.scene.text.Text;
import org.example.sheikh_jackson_javaproject.HelloApplication;
import org.example.sheikh_jackson_javaproject.pojo.*;
import org.example.sheikh_jackson_javaproject.tables.*;
import org.example.sheikh_jackson_javaproject.Constants.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.Year;
import java.util.Objects;

public class AddItemTab extends Tab {
    private final ComboBox<Developer> dCB = new ComboBox<>();
    private final ComboBox<Platform> pCB = new ComboBox<>();
    private final TextField tF = new TextField();
    private final TextField yF = new TextField();
    private final TextField gF = new TextField();
    private final TextField iF = new TextField();

    public AddItemTab() {
        setGraphic(NodeConsts.createTabTitle("Add Game"));

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50)); // Large outer padding

        GridPane gP = new GridPane();
        gP.setHgap(20); // More horizontal space
        gP.setVgap(20); // More vertical space
        gP.setPadding(new Insets(40));
        gP.setAlignment(Pos.CENTER);

        // Form styling
        double wideWidth = 450; // Much wider inputs

        Control[] inputs = {tF, yF, gF, iF, dCB, pCB};
        for (Control input : inputs) {
            input.setPrefWidth(wideWidth);
            input.getStyleClass().add("form-input");
        }

        dCB.getItems().setAll(GameTable.getInstance().getAllDevelopers());
        dCB.setPromptText("-- Select Developer --");

        pCB.getItems().setAll(GameTable.getInstance().getAllPlatforms());
        pCB.setPromptText("-- Select Platform --");

        // Layout rows with Large Labels
        gP.addRow(0, createHugeLbl("Title:"), tF);
        gP.addRow(1, createHugeLbl("Developer:"), dCB);
        gP.addRow(2, createHugeLbl("Year:"), yF);
        gP.addRow(3, createHugeLbl("Genre:"), gF);
        gP.addRow(4, createHugeLbl("Platform:"), pCB);
        gP.addRow(5, createHugeLbl("URL:"), iF);

        // Extra Large Button
        Button btn = new Button("ADD TO LIBRARY");
        btn.getStyleClass().addAll("btn", "add-btn");
        btn.setPrefWidth(wideWidth);
        btn.setPrefHeight(60);
        gP.add(btn, 1, 6);

        btn.setOnAction(e -> {
            try {
                String t = tF.getText().trim(), y = yF.getText().trim(), g = gF.getText().trim(), i = iF.getText().trim();

                if (t.isEmpty() || y.isEmpty() || g.isEmpty() || i.isEmpty() || dCB.getValue() == null || pCB.getValue() == null)
                    throw new Exception("All fields must be filled to add!");

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

                int dId = dCB.getValue().getId(), pId = pCB.getValue().getId();

                for (Game game : GameTable.getInstance().getAllGames()) {
                    if (game.getTitle().equalsIgnoreCase(t) && game.getYear() == yearVal && game.getPlatform().equals(pCB.getValue().toString())) {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Double Trouble");
                        alert.setHeaderText("WARNING!");

                        VBox box = new VBox(5);
                        alert.getDialogPane().getStylesheets().add(
                                Objects.requireNonNull(HelloApplication.class.getResource(
                                                "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                        .toExternalForm()
                        );

                        Text doubleTrouble = new Text("Duplicate found on this platform!");
                        doubleTrouble.getStyleClass().add("side-note");

                        box.getChildren().add(
                                doubleTrouble
                        );

                        alert.getDialogPane().setContent(box);
                        alert.showAndWait();
                        return;
                    }
                }

                GameTable.getInstance().addGame(new Game(0, t, String.valueOf(dId), yearVal, g, String.valueOf(pId), i));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Added to library");
                alert.setHeaderText("SUBMISSION DETAILS");

                VBox box = new VBox(5);
                alert.getDialogPane().getStylesheets().add(
                        Objects.requireNonNull(HelloApplication.class.getResource(
                                        "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                                .toExternalForm()
                );

                Text submitTxt = new Text(
                        String.format("We've added %s (%d), a game created by %s.",
                                t, yearVal, dCB.getValue())
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
                dCB.getSelectionModel().clearSelection();
                dCB.setValue(null);
                pCB.getSelectionModel().clearSelection();
                pCB.setValue(null);

            } catch (NumberFormatException nfe) {
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

            } catch (Exception ex) {
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
        });

        container.getChildren().add(gP);
        setContent(container);
    }

    private Label createHugeLbl(String text) {
        Label lbl = new Label(text);
        lbl.getStyleClass().add("form-lbl");
        return lbl;
    }
}