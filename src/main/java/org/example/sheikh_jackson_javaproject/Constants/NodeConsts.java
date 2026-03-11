// NodeConsts.java:

package org.example.sheikh_jackson_javaproject.Constants;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class NodeConsts {
    public static Text createTabTitle(String text) {
        Text t = new Text(text);
        t.getStyleClass().add("tab-title");
        return t;
    }
}