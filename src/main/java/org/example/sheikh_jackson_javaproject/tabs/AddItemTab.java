// AddItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import javafx.geometry.*;
import org.example.sheikh_jackson_javaproject.pojo.*;
import org.example.sheikh_jackson_javaproject.tables.*;
import org.example.sheikh_jackson_javaproject.Constants.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.Year;

public class AddItemTab extends Tab {
    private ComboBox<Developer> dCB = new ComboBox<>();
    private ComboBox<Platform> pCB = new ComboBox<>();
    private TextField tF = new TextField(), yF = new TextField(), gF = new TextField(), iF = new TextField();

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
        gP.addRow(0, createHugeLabel("Title:"), tF);
        gP.addRow(1, createHugeLabel("Developer:"), dCB);
        gP.addRow(2, createHugeLabel("Year:"), yF);
        gP.addRow(3, createHugeLabel("Genre:"), gF);
        gP.addRow(4, createHugeLabel("Platform:"), pCB);
        gP.addRow(5, createHugeLabel("URL:"), iF);

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
                    throw new Exception("All fields must be filled!");

                int yearVal = Integer.parseInt(y);
                int currentYear = Year.now().getValue();
                if (yearVal < 1950 || yearVal > currentYear) {
                    new Alert(Alert.AlertType.WARNING, "Invalid Year: Range 1950 - " + currentYear).show();
                    return;
                }

                String iLow = i.toLowerCase();
                if (!iLow.startsWith("http://")
                        && !iLow.startsWith("https://")
                        && !iLow.startsWith("www.")) {
                    new Alert(Alert.AlertType.WARNING, "URL must start with http://, https://, or www.").show();
                    return;
                }

                int dId = dCB.getValue().getId(), pId = pCB.getValue().getId();

                for (Game game : GameTable.getInstance().getAllGames()) {
                    if (game.getTitle().equalsIgnoreCase(t) && game.getYear() == yearVal && game.getPlatform().equals(pCB.getValue().toString())) {
                        new Alert(Alert.AlertType.WARNING, "Duplicate found on this platform!").show();
                        return;
                    }
                }

                GameTable.getInstance().addGame(new Game(0, t, String.valueOf(dId), yearVal, g, String.valueOf(pId), i));
                new Alert(Alert.AlertType.INFORMATION, "Success: Added " + t).show();

                tF.clear();
                yF.clear();
                gF.clear();
                iF.clear();
                dCB.getSelectionModel().clearSelection();
                dCB.setValue(null);
                dCB.setSkin(null);
                pCB.getSelectionModel().clearSelection();
                pCB.setValue(null);
                pCB.setSkin(null);

            } catch (NumberFormatException nfe) {
                new Alert(Alert.AlertType.ERROR, "Year must be a number!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }
        });

        container.getChildren().add(gP);
        setContent(container);
    }

    private Label createHugeLabel(String text) {
        Label l = new Label(text);
        l.getStyleClass().add("form-label");
        return l;
    }
}