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
 * JavaFX tab for displaying statistical insights of games
 * in the library using a pie chart.
 * This tab dynamically aggregates game data by selected
 * category (Platform or Developer) and visualizes it.
 * Design Choices:
 * Uses in-memory aggregation to avoid additional database queries
 * and applies animation to enhance user experience.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class StatisticsTab extends Tab {

    /**
     * Singleton instance of GameTable used to retrieve
     * game data for statistical analysis.
     */
    private final GameTable gt = GameTable.getInstance();

    /**
     * PieChart used to visualize game distribution
     * based on selected grouping criteria.
     */
    private final PieChart chart = new PieChart();

    /**
     * Constructs the StatisticsTab and initializes UI components,
     * including the selector for grouping and the pie chart.
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
     * Updates the pie chart data based on the selected grouping type.
     * Aggregates game counts by either platform or developer and
     * calculates percentage distribution for visualization.
     *
     * @param type grouping type ("Platform" or "Developer")
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
     * Plays a scale animation on the pie chart to enhance
     * visual presentation when data is updated.
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
