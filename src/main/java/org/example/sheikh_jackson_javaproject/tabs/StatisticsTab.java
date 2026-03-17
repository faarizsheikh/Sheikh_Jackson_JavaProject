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

public class StatisticsTab extends Tab {

    private final GameTable gt = GameTable.getInstance();
    private final PieChart chart = new PieChart();

    public StatisticsTab() {
        setGraphic(tabTitle("Statistics"));

        chart.setAnimated(true);
        VBox container = NodeConsts.vBox();

        ComboBox<String> selector = new ComboBox<>();
        selector.getItems().addAll("Platform", "Developer");
        selector.setValue("Platform");
        selector.setPrefWidth(NodeConsts.FIELD_WIDTH);
        selector.getStyleClass().add("form-input");

        updateChart("Platform");
        playChartAnimation();

        selector.setOnAction(e -> {
            updateChart(selector.getValue());
            playChartAnimation();
        });
        container.getChildren().addAll(selector, chart);
        setContent(container);
    }

    private void playChartAnimation() {
        chart.setScaleX(0);
        chart.setScaleY(0);

        ScaleTransition st = new ScaleTransition(Duration.seconds(1.5), chart);
        st.setToX(1);
        st.setToY(1);
        st.play();
    }

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
}
