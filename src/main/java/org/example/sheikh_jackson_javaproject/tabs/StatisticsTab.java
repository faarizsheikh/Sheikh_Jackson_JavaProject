// StatisticsTab.java:

package org.example.sheikh_jackson_javaproject.tabs;

import java.util.ArrayList;
import javafx.animation.ScaleTransition;
import javafx.collections.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.sheikh_jackson_javaproject.pojo.Game;
import org.example.sheikh_jackson_javaproject.tables.GameTable;
import org.example.sheikh_jackson_javaproject.utils.NodeConsts;
import static org.example.sheikh_jackson_javaproject.utils.NodeConsts.tabTitle;

/**
 * Tab for statistics of games in library using a pie chart.
 * Design Choices:
 * - Uses dynamic aggregation (no extra DB queries)
 * - Simple animation improves UX
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class StatisticsTab extends Tab {

    private final GameTable gt = GameTable.getInstance();
    private final PieChart chart = new PieChart();

    /**
     * Constructs StatisticsTab UI.
     */
    public StatisticsTab() {
        setGraphic(tabTitle("Statistics"));

        VBox container = NodeConsts.vBox();
        chart.setAnimated(true);

        ComboBox<String> selector = new ComboBox<>();
        selector.getItems().addAll("Platform", "Developer");
        selector.setValue("Platform");
        selector.setPrefWidth(NodeConsts.FIELD_WIDTH);
        selector.getStyleClass().add("form-input");

        updateChart("Platform");
        playAnimation();

        selector.setOnAction(e -> {
            updateChart(selector.getValue());
            playAnimation();
        });
        container.getChildren().addAll(selector, chart);
        setContent(container);
    }

    /**
     * Updates pie chart data.
     *
     * @param type grouping type (Platform or Developer)
     */
    private void updateChart(String type) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();

        for (Game g : gt.getAllGames()) {
            String key = type.equals("Developer") ? g.getDeveloper() : g.getPlatform();
            int index = names.indexOf(key);

            if (index == -1) {
                names.add(key);
                counts.add(1);

            }
            else {
                counts.set(index, counts.get(index) + 1);
            }
        }
        int total = gt.getAllGames().size();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        for (int i = 0; i < names.size(); i++) {
            double percent = ((double) counts.get(i) / total) * 100;

            String label = String.format("%s (%.1f%%)",
                    names.get(i),
                    percent
            );
            data.add(new PieChart.Data(label, counts.get(i)));
        }
        chart.setData(data);
        chart.setTitle("Games by " + type);
        chart.setLabelsVisible(true);
    }

    /**
     * Plays chart animation.
     */
    private void playAnimation() {
        chart.setScaleX(0);
        chart.setScaleY(0);

        ScaleTransition st = new ScaleTransition(Duration.seconds(1.5), chart);
        st.setToX(1);
        st.setToY(1);
        st.play();
    }
}
