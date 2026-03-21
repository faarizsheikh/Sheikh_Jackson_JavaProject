// NodeConsts.java:

package org.example.sheikh_jackson_javaproject.utils;

import java.util.*;
import javafx.animation.ScaleTransition;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sheikh_jackson_javaproject.HelloApplication;

/**
 * Provides reusable UI components and constants.
 * Includes factory methods for creating styled JavaFX nodes such as buttons,
 * layouts, labels, dialogs, menus, and image utilities.
 * Design Choice: Centralizing UI logic ensures consistency,
 * reduces duplication, and simplifies UI management across the application.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class NodeConsts {

    /**
     * Default button height used across the application UI.
     */
    public static final double BTN_HEIGHT = 60;

    /**
     * Default width used for input fields and primary buttons.
     */
    public static final double FIELD_WIDTH = 450;

    /**
     * Default application scene width.
     */
    public static final double SCENE_WIDTH = 1699;

    /**
     * Default application scene height.
     */
    public static final double SCENE_HEIGHT = 989;

    /**
     * Private constructor to prevent instantiation.
     */
    private NodeConsts() {}

    /**
     * Creates a responsive root layout bound to the given stage size.
     *
     * @param stage application window stage
     * @return a BorderPane bound to stage width and height
     */
    public static BorderPane root(Stage stage) {
        BorderPane root = new BorderPane();
        root.prefWidthProperty().bind(stage.widthProperty());
        root.prefHeightProperty().bind(stage.heightProperty());
        return root;
    }

    /**
     * Creates a preconfigured GridPane with center alignment and spacing.
     *
     * @return configured GridPane instance
     */
    public static GridPane gP() {
        GridPane gP = new GridPane();
        gP.setAlignment(Pos.CENTER);
        gP.setHgap(20);
        gP.setVgap(20);
        gP.setPadding(new Insets(40));
        return gP;
    }

    /**
     * Creates a preconfigured VBox container with center alignment.
     *
     * @return configured VBox instance
     */
    public static VBox vBox() {
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50));
        return container;
    }

    /**
     * Creates a styled JavaFX Button with hover animation applied.
     *
     * @param text button text
     * @param style CSS style class
     * @return styled Button instance
     */
    public static Button button(String text, String style) {
        Button btn = new Button(text);
        btn.getStyleClass().addAll("btn", style);
        btn.setPrefWidth(FIELD_WIDTH);
        btn.setPrefHeight(BTN_HEIGHT);
        btnHoverAnimation(btn);
        return btn;
    }

    /**
     * Applies a hover scale animation effect to a JavaFX Button.
     *
     * @param btn the button to animate (must not be null)
     *
     * @apiNote This method modifies mouse event handlers of the button.
     */
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

    /**
     * Creates a styled form label.
     *
     * @param text label text
     * @return styled Label instance
     */
    public static Label formLabel(String text) {
        Label lbl = new Label(text);
        lbl.getStyleClass().add("form-lbl");
        return lbl;
    }

    /**
     * Creates a styled table label with a fixed preferred width.
     *
     * @param text label text
     * @param width preferred width in pixels
     * @return configured Label instance
     */
    public static Label tableLabel(String text, double width) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(width);
        lbl.getStyleClass().add("table-lbl");
        return lbl;
    }

    /**
     * Creates a styled tab title text node.
     *
     * @param text title text
     * @return configured Text node
     */
    public static Text tabTitle(String text) {
        Text txt = new Text(text);
        txt.getStyleClass().add("tab-title");
        return txt;
    }

    /**
     * Returns a predefined column width based on column index mapping.
     *
     * @param i column index matching table schema
     * @return column width in pixels
     */
    public static double columnWidth(int i) {
        return switch (i) {
            case 0, 3 -> 75; // ID, YEAR
            case 1, 4 -> 350; // TITLE
            case 2 -> 205; // DEVELOPER
            case 5 -> 150; // GENRE & PLATFORM
            default -> 100; // Fallback
        };
    }

    /**
     * Loads an image from a URL and applies fixed display sizing.
     * If the URL is null, empty, or invalid, a default fallback image is used.
     *
     * @param url image URL (can be null or invalid)
     * @return ImageView containing loaded or fallback image
     *
     * @implNote Uses asynchronous loading for remote images and falls back
     * to a bundled default image on failure.
     */
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

    /**
     * Creates the main application menu bar with File and Credits menus.
     *
     * @return configured MenuBar instance
     */
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

    /**
     * Displays a styled JavaFX alert dialog with custom content nodes.
     *
     * @param type alert type
     * @param title dialog title
     * @param header dialog header text
     * @param nodes content nodes (must not be null)
     *
     * @apiNote Applies CSS styling and hover effects to dialog buttons.
     */
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

    /**
     * Displays a confirmation dialog before exiting the application.
     * If confirmed, the application will terminate.
     */
    public static void showExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("EXITING SYSTEM");

        VBox box = new VBox(10);

        applyCSS(alert);

        Text askConfirm = new Text("Are you sure you want to exit the program?");
        Text sideNote = new Text("New changes will be saved.");

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

    /**
     * Displays a modal dialog showing developer credits.
     *
     * @apiNote This method creates and displays a JavaFX information dialog.
     */
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

    /**
     * Displays a dialog listing technologies and tools used in the project.
     */
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

        Text resList = new Text(items);
        resList.getStyleClass().add("res-list");
        alert(Alert.AlertType.INFORMATION, "Resources Credits", "RESOURCES", resList);
    }

    /**
     * Validates whether a string exceeds a maximum allowed length.
     *
     * @param value input value (must not be null)
     * @param max maximum allowed length
     * @param fieldName field name used in error message
     * @return true if value exceeds limit, false otherwise
     *
     * @apiNote Displays a warning dialog if validation fails.
     */
    public static boolean exceedsMax(String value, int max, String fieldName) {
        if (value.length() > max) {
            alert(Alert.AlertType.WARNING, "Too Long", "WARNING!",
                    new Text(fieldName + " exceeds max length of " + max)
                    {{ getStyleClass().add("side-note"); }}
            );
            return true;
        }
        return false;
    }

    /**
     * Applies the main stylesheet to a JavaFX Scene.
     *
     * @param scene scene to style
     */
    public static void applyCSS(Scene scene) {
        scene.getStylesheets().add(
                Objects.requireNonNull(NodeConsts.class.getResource(
                        "/org/example/sheikh_jackson_javaproject/assets/style.css"
                )).toExternalForm()
        );
    }

    /**
     * Applies the main stylesheet to an Alert dialog.
     *
     * @param alert alert to style
     */
    public static void applyCSS(Alert alert) {
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(HelloApplication.class.getResource(
                                "/org/example/sheikh_jackson_javaproject/assets/style.css"))
                        .toExternalForm()
        );
    }
}
