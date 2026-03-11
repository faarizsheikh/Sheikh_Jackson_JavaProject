// ViewItemTab.java:

package org.example.sheikh_jackson_javaproject.tabs;
import org.example.sheikh_jackson_javaproject.pojo.Game;
import org.example.sheikh_jackson_javaproject.tables.GameTable;
import org.example.sheikh_jackson_javaproject.Constants.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import java.util.Objects;

public class ViewItemTab extends Tab {

    private VBox container = new VBox(5);

    public ViewItemTab() {
        setGraphic(NodeConsts.createTabTitle("View Games"));

        container.setPadding(new Insets(15));
        ScrollPane sp = new ScrollPane(container);
        sp.setFitToWidth(true);
        setContent(sp);
        setOnSelectionChanged(e -> { if(isSelected()) refresh(); });
        refresh();
    }

    private void refresh() {
        container.getChildren().clear();
        addRow(true, "ID", "TITLE", "DEVELOPER", "YEAR", "GENRE", "PLATFORM", "IMAGE");
        for (Game g : GameTable.getInstance().getAllGames()) {
            addRow(false, String.valueOf(g.getId()), g.getTitle(), g.getDeveloper(),
                    String.valueOf(g.getYear()), g.getGenre(), g.getPlatform(), g.getImageUrl());
        }
    }

    private void addRow(boolean isHeader, String... cols) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(10));
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add(isHeader ? "table-header" : "table-row");

        for (int i = 0; i < cols.length; i++) {
            // If it's the last column (index 6) and NOT the header, show an ImageView
            // Inside addRow method, replace the index 6 (Image) logic:

            if (i == 6 && !isHeader) {
                ImageView iv = new ImageView();
                iv.setFitHeight(300);
                iv.setFitWidth(350);
                iv.setPreserveRatio(true);

                try {
                    // 1. Check if string is actually a link
                    if (cols[i] == null || cols[i].trim().isEmpty()) {
                        throw new Exception("Empty URL");
                    }

                    // 2. Load Synchronously (Background = false) so we can check for errors immediately
                    Image img = new Image(cols[i], true);

                    if (img.isError()) {
                        iv.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/sheikh_jackson_javaproject/assets/default.png"))));
                    } else {
                        iv.setImage(img);
                    }
                } catch (Exception e) {
                    // 3. Fallback for total link failure or empty strings
                    iv.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/sheikh_jackson_javaproject/assets/default.png"))));
                }
                row.getChildren().add(iv);

            } else {
                // Otherwise, keep using a Label
                Label l = new Label(cols[i]);
                l.setPrefWidth(i == 0 ? 50 : 250); // Standard width for most columns

                if (isHeader) {
                    l.getStyleClass().add("label");
                } else {
                    if (i == 0) {
                        l.getStyleClass().add("id-label");
                    }
                }

                row.getChildren().add(l);
                l.getStyleClass().add("table-label");
            }
        }
        container.getChildren().add(row);
    }
}