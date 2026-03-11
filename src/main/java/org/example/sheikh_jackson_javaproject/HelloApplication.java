// HelloApplication.java:

package org.example.sheikh_jackson_javaproject;

import javafx.scene.text.Text;
import org.example.sheikh_jackson_javaproject.database.*;
import org.example.sheikh_jackson_javaproject.tabs.*;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMaximized(true);
        BorderPane root = new BorderPane();

        // Bind layout to stage dimensions
        root.prefWidthProperty().bind(stage.widthProperty());
        root.prefHeightProperty().bind(stage.heightProperty());

        // --- MENU BAR SETUP ---
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu creditsMenu = new Menu("Credits");

        MenuItem exitItem = new MenuItem("Exit");
        MenuItem aboutItem = new MenuItem("Developers");
        MenuItem resItem = new MenuItem("Used Resources");

        fileMenu.getItems().add(exitItem);
        creditsMenu.getItems().addAll(aboutItem, resItem);
        menuBar.getMenus().addAll(fileMenu, creditsMenu);

        // Actions
        exitItem.setOnAction(e -> showExitConfirmation(stage));
        aboutItem.setOnAction(e -> devCredits());
        resItem.setOnAction(e -> resourceCredits());

        // Handle the Window "X" button
        stage.setOnCloseRequest(e -> {
            e.consume();
            showExitConfirmation(stage);
        });

        // --- TAB PANE SETUP (BIG & CENTERED) ---
        TabPane pane = new TabPane();

        // Header Dimensions
        pane.setTabMinHeight(50);
        pane.setTabMaxHeight(50);
        pane.setTabMinWidth(300);
        pane.setTabMaxWidth(300);
        pane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Initialize Tabs with your "Bigger" styling
        AddItemTab addItemTab = new AddItemTab();
        DeleteItemTab deleteItemTab = new DeleteItemTab();
        ViewItemTab viewItemTab = new ViewItemTab();
        UpdateItemTab updateItemTab = new UpdateItemTab();
        StatisticsTab statisticsTab = new StatisticsTab();

        addItemTab.getStyleClass().addAll("tab-box", "add-tab");
        viewItemTab.getStyleClass().addAll("tab-box", "view-tab");
        updateItemTab.getStyleClass().addAll("tab-box","update-tab");
        deleteItemTab.getStyleClass().addAll("tab-box", "delete-tab");
        statisticsTab.getStyleClass().addAll("tab-box", "stats-tab");

        pane.getTabs().addAll(addItemTab, viewItemTab, updateItemTab, deleteItemTab, statisticsTab);

        // --- FINAL ASSEMBLY ---
        root.setTop(menuBar);
        root.setCenter(pane);

        Database db = Database.getInstance();
        Scene scene = new Scene(root, 1699, 989);

        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/org/example/sheikh_jackson_javaproject/assets/style.css")).toExternalForm()
        );

        stage.setTitle("Game Library Management System!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Confirms before closing the application
     */
    private void showExitConfirmation(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("EXITING SYSTEM");

        VBox box = new VBox(10);

        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource(
                        "/org/example/sheikh_jackson_javaproject/assets/style.css"
                )).toExternalForm()
        );

        Text askConfirm = new Text("Are you sure you want to exit the program?");
        Text sideNote = new Text("New changes will be saved.");

        askConfirm.getStyleClass().add("ask-confirm");
        sideNote.getStyleClass().add("side-note");

        box.getChildren().addAll(
                askConfirm,
                sideNote
        );

        alert.getDialogPane().getStyleClass().addAll("alert", "exit");
        alert.getDialogPane().setContent(box);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
    
    private void devCredits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Developers Credits");
        alert.setHeaderText("DEVELOPERS");

        VBox box = new VBox(5);

        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource(
                        "/org/example/sheikh_jackson_javaproject/assets/style.css"
                )).toExternalForm()
        );

        Label collabTitle = new Label("\nCollaborator(s):");
        Label projectLeadTitle = new Label("Project Lead:");
        Label teamNameTitle = new Label("Team Name:");

        Text collabsTxt = new Text("Isaac Jackson\n\n");
        Text course = new Text("Java III | Computer Programming (T850)\n");

        Text projectLead = new Text(
                """
                 Faariz Sheikh (Current),
                 Yazan Al-Umari (Formerly)"""
        );
        Text teamName = new Text("NODE of Noobs\n");
        Text thanks = new Text("THANKS FOR TEACHING US, CÂI FILIAULT! :)");

        collabTitle.getStyleClass().add("credit-label");
        projectLeadTitle.getStyleClass().add("credit-label");
        teamNameTitle.getStyleClass().add("credit-label");

        box.getChildren().addAll(
                teamNameTitle, teamName,
                projectLeadTitle, projectLead,
                collabTitle, collabsTxt,
                course,
                thanks
        );

        alert.getDialogPane().setContent(box);
        alert.showAndWait();
    }

    private void resourceCredits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resources Credits");
        alert.setHeaderText("RESOURCES");

        VBox box = new VBox(5);

        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource(
                        "/org/example/sheikh_jackson_javaproject/assets/style.css"
                )).toExternalForm()
        );

        Text resList = new Text(
             """
             • CSS
             • JavaFX Animations
             • Database
             • File I/O
             • IntelliJ IDEA (IDE)
             • Java
             • JavaFX (JFX / Java Effects)
             • PhpMyAdmin"""
        );

        resList.getStyleClass().add("res-list");

        box.getChildren().addAll(resList);

        alert.getDialogPane().setContent(box);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch();
    }
}