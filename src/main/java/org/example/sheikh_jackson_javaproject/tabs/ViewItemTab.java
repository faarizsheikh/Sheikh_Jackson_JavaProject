// ViewItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.example.sheikh_jackson_javaproject.pojo.Game;
import org.example.sheikh_jackson_javaproject.tables.GameTable;
import static org.example.sheikh_jackson_javaproject.utils.NodeConsts.*;

public class ViewItemTab extends Tab {

    private static final String[] COLS =
            {"ID", "TITLE", "DEVELOPER", "YEAR", "GENRE", "PLATFORM", "IMAGE"};

    private final VBox container = new VBox(5);

    public ViewItemTab() {
        setGraphic(tabTitle("View Games"));

        container.setPadding(new Insets(15));
        ScrollPane sp = new ScrollPane(container);
        sp.setFitToWidth(true);
        setContent(sp);
        setOnSelectionChanged(e -> { if(isSelected()) refresh(); });
        refresh();
    }

    private void refresh() {

        container.getChildren().clear();

        var games = GameTable.getInstance().getAllGames();

        if (games.isEmpty()) {
            Text msg = new Text(
                    "There are no games added to the library yet.\nPlease add a game first."
            );

            msg.getStyleClass().add("empty-msg");

            VBox emptyBox = new VBox(msg);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPrefHeight(600); // gives vertical centering space

            container.getChildren().add(emptyBox);
            return;
        }

        addRow(true, COLS);

        for (Game g : games) {
            addRow(false,
                    String.valueOf(g.getId()),
                    g.getTitle(),
                    g.getDeveloper(),
                    String.valueOf(g.getYear()),
                    g.getGenre(),
                    g.getPlatform(),
                    g.getImageUrl()
            );
        }
    }

    private void addRow(boolean isHeader, String... cols) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(10));
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add(isHeader ? "table-header" : "table-row");

        for (int i = 0; i < cols.length; i++) {
            if (i == 6 && !isHeader) {
                row.getChildren().add(gameImage(cols[i]));

            } else {
                Label lbl = tableLabel(cols[i], columnWidth(i));

                if (isHeader) {
                    lbl.getStyleClass().add("label");

                } else {
                    if (i == 0) lbl.getStyleClass().add("id-lbl");
                }
                row.getChildren().add(lbl);
            }
        }
        container.getChildren().add(row);
    }
}
