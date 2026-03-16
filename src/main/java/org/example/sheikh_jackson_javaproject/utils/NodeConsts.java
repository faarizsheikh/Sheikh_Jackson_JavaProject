// NodeConsts.java:

package org.example.sheikh_jackson_javaproject.utils;

import java.util.*;
import javafx.animation.ScaleTransition;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sheikh_jackson_javaproject.HelloApplication;

public class NodeConsts {

    public static final double BTN_HEIGHT = 60;
    public static final double FIELD_WIDTH = 450;
    public static final double SCENE_WIDTH = 1699;
    public static final double SCENE_HEIGHT = 989;

    private NodeConsts() {}

    public static BorderPane root(Stage stage) {
        BorderPane root = new BorderPane();
        root.prefWidthProperty().bind(stage.widthProperty());
        root.prefHeightProperty().bind(stage.heightProperty());
        return root;
    }

    public static GridPane gP() {
        GridPane gP = new GridPane();
        gP.setAlignment(Pos.CENTER);
        gP.setHgap(20);
        gP.setVgap(20);
        gP.setPadding(new Insets(40));
        return gP;
    }

    public static VBox vBox() {
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50));
        return container;
    }

    public static Button button(String text, String style) {
        Button btn = new Button(text);
        btn.getStyleClass().addAll("btn", style);
        btn.setPrefWidth(FIELD_WIDTH);
        btn.setPrefHeight(BTN_HEIGHT);
        btnHoverAnimation(btn);
        return btn;
    }

    public static void btnHoverAnimation(Button btn) {
        btn.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        btn.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });
    }

    public static Label formLabel(String text) {
        Label lbl = new Label(text);
        lbl.getStyleClass().add("form-lbl");
        return lbl;
    }

    public static Label tableLabel(String text, double width) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(width);
        lbl.getStyleClass().add("table-lbl");
        return lbl;
    }

    public static Text tabTitle(String text) {
        Text txt = new Text(text);
        txt.getStyleClass().add("tab-title");
        return txt;
    }

    public static double columnWidth(int i) {
        return switch (i) {
            case 0, 3 -> 75; // ID, YEAR
            case 1, 4 -> 350; // TITLE
            case 2 -> 205; // DEVELOPER
            case 5 -> 150; // GENRE & PLATFORM
            default -> 100; // Fallback
        };
    }

    public static ImageView gameImage(String url) {
        ImageView iv = new ImageView();
        iv.setFitHeight(350);
        iv.setFitWidth(350);
        iv.setPreserveRatio(true);

        Image defaultImg = new Image(
                Objects.requireNonNull(NodeConsts.class.getResourceAsStream(
                        "/org/example/sheikh_jackson_javaproject/assets/default.png"
                ))
        );

        try {
            if (url == null || url.trim().isEmpty()) {
                iv.setImage(defaultImg);

            } else {
                Image img = new Image(url, true);

                img.errorProperty().addListener((o, a, b) -> {
                    if (b) iv.setImage(defaultImg);
                });
                iv.setImage(img);
            }

        } catch (Exception e) {
            iv.setImage(defaultImg);
        }
        return iv;
    }

    public static MenuBar mainMenu() {

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        Menu creditsMenu = new Menu("Credits");

        MenuItem exitItem = new MenuItem("Exit");
        MenuItem aboutItem = new MenuItem("Developers");
        MenuItem resItem = new MenuItem("Used Resources");

        fileMenu.getItems().add(exitItem);
        creditsMenu.getItems().addAll(aboutItem, resItem);

        menuBar.getMenus().addAll(fileMenu, creditsMenu);

        exitItem.setOnAction(e -> showExitConfirmation());
        aboutItem.setOnAction(e -> devCredits());
        resItem.setOnAction(e -> resourceCredits());

        return menuBar;
    }

    public static void alert(Alert.AlertType type, String title, String header, Node... nodes) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);

        VBox box = new VBox(5);
        box.getChildren().addAll(nodes);
        alert.getDialogPane().setContent(box);

        applyCSS(alert);

        alert.getDialogPane().getButtonTypes().forEach(bt -> {
            Button b = (Button) alert.getDialogPane().lookupButton(bt);
            btnHoverAnimation(b);
        });

        alert.showAndWait();
    }

    public static void showExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("EXITING SYSTEM");

        VBox box = new VBox(10);

        applyCSS(alert);

        Label askConfirm = new Label("Are you sure you want to exit the program?");
        Label sideNote = new Label("New changes will be saved.");

        askConfirm.getStyleClass().add("ask-confirm");
        sideNote.getStyleClass().add("side-note");

        box.getChildren().addAll(askConfirm, sideNote);
        alert.getDialogPane().setContent(box);

        alert.getDialogPane().getButtonTypes().forEach(bt -> {
            Button b = (Button) alert.getDialogPane().lookupButton(bt);
            btnHoverAnimation(b);
        });

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) System.exit(0);
    }

    public static void devCredits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Developer Credits");
        alert.setHeaderText("DEVELOPERS");

        VBox box = new VBox(10);

        applyCSS(alert);

        Label teamLbl = new Label("Team Name:");
        Text teamTxt = new Text("NODE of Noobs");
        Label projLeadLbl = new Label("Project Lead:");
        Text projLeadTxt = new Text("Faariz Sheikh (Current), Yazan Al-Umari (Formerly)");
        Label collabLbl = new Label("Collaborator(s):");
        Text collabTxt = new Text("Isaac Jackson");
        Text thx_note = new Text("THANKS FOR TEACHING US, CÂI FILIAULT! :)");

        box.getChildren().addAll(teamLbl, teamTxt, projLeadLbl, projLeadTxt, collabLbl, collabTxt, thx_note);

        box.getChildren().forEach(node -> {
            if (node instanceof Text) {
                VBox.setMargin(node, new Insets(0, 0, 25, 0));
                node.getStyleClass().add("credit-txt");
            }
            else {
                node.getStyleClass().add("credit-lbl");
            }
        });
        alert.getDialogPane().setContent(box);

        alert.getDialogPane().getButtonTypes().forEach(bt -> {
            Button b = (Button) alert.getDialogPane().lookupButton(bt);
            btnHoverAnimation(b);
        });

        alert.showAndWait();
    }

    public static void resourceCredits() {
        String items = """
               • CSS
               • Database
               • File I/O
               • IntelliJ IDEA (IDE)
               • Java (Procedual & OO) Programming
               • JavaFX (JFX / Java Effects)
               • PhpMyAdmin
               • SQL
               """;

        Label resList = new Label(items);
        resList.getStyleClass().add("res-list");
        alert(Alert.AlertType.INFORMATION, "Resources Credits", "RESOURCES", resList);
    }

    public static void applyCSS(Scene scene) {
        scene.getStylesheets().add(
                Objects.requireNonNull(NodeConsts.class.getResource(
                        "/org/example/sheikh_jackson_javaproject/assets/style.css"
                )).toExternalForm()
        );
    }

    public static void applyCSS(Alert alert) {
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(HelloApplication.class.getResource(
                                "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                        .toExternalForm()
        );
    }
}
