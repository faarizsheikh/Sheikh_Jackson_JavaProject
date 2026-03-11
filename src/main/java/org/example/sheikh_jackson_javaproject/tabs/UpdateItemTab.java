// UpdateItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import javafx.geometry.*;
import javafx.scene.layout.VBox;
import org.example.sheikh_jackson_javaproject.pojo.Game;
import org.example.sheikh_jackson_javaproject.tables.GameTable;
import org.example.sheikh_jackson_javaproject.Constants.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import java.time.Year;

public class UpdateItemTab extends Tab {
    private ComboBox<Game> cB = new ComboBox<>();
    private TextField tF = new TextField(), yF = new TextField(), gF = new TextField(), iF = new TextField();

    public UpdateItemTab() {
        setGraphic(NodeConsts.createTabTitle("Update Game"));
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50)); // Large outer padding

        GridPane gP = new GridPane();
        gP.setHgap(20);
        gP.setVgap(20);
        gP.setPadding(new Insets(40));
        gP.setAlignment(Pos.CENTER);

        double wideWidth = 450; // Much wider inputs

        Control[] inputs = {tF, yF, gF, iF, cB};
        for (Control input : inputs) {
            input.setPrefWidth(wideWidth);
            input.getStyleClass().add("form-input");
        }

        cB.setPromptText("-- Select Game to Update --");
        cB.setPrefWidth(300);

        gP.addRow(0, createHugeLabel("Search:"), cB);
        gP.addRow(1, createHugeLabel("New Title:"), tF);
        gP.addRow(2, createHugeLabel("New Year:"), yF);
        gP.addRow(3, createHugeLabel("New Genre:"), gF);
        gP.addRow(4, createHugeLabel("New URL:"), iF);

        cB.setOnAction(e -> {
            Game sel = cB.getValue();
            if(sel != null) {
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
                    String t = tF.getText().trim(), y = yF.getText().trim(), g = gF.getText().trim(), i = iF.getText().trim();

                    // 1. Basic Empty Validation
                    if (t.isEmpty() || y.isEmpty() || g.isEmpty() || i.isEmpty()) {
                        throw new Exception("All fields must be filled for an update!");
                    }

                    // 2. Year Validation (1950 - 2026)
                    int yearVal = Integer.parseInt(y);
                    int currentYear = Year.now().getValue();
                    if (yearVal < 1950 || yearVal > currentYear) {
                        new Alert(Alert.AlertType.WARNING, "Invalid Year: Please enter a year between 1950 and " + currentYear).show();
                        return;
                    }

                    // 3. URL Validation
                    if (!i.toLowerCase().startsWith("http://")
                            && !i.toLowerCase().startsWith("https://")
                            && !i.toLowerCase().startsWith("www.") ) {
                        new Alert(Alert.AlertType.WARNING, "Invalid URL: Image URL must start with http://, https://, or www.").show();
                        return;
                    }

                    // Apply updates to the object
                    sel.setTitle(t);
                    sel.setYear(yearVal);
                    sel.setGenre(g);
                    sel.setImageUrl(i);

                    GameTable.getInstance().updateGame(sel);
                    new Alert(Alert.AlertType.INFORMATION, "Update Successful!").show();

                  
                    tF.clear(); yF.clear(); gF.clear(); iF.clear();
                    cB.getSelectionModel().clearSelection();
                    cB.setValue(null);
                    cB.setSkin(null);

                } catch (NumberFormatException nfe) {
                    new Alert(Alert.AlertType.ERROR, "Year must be a valid number!").show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please select a game first!").show();
            }
        });

        setContent(gP);
        setOnSelectionChanged(e -> {
            if(isSelected()) cB.setItems(FXCollections.observableArrayList(GameTable.getInstance().getAllGames()));
        });
    }

    private Label createHugeLabel(String text) {
        Label l = new Label(text);
        l.getStyleClass().add("form-label");
        return l;
    }
}