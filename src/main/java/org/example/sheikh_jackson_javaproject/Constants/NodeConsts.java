// NodeConsts.java:

package org.example.sheikh_jackson_javaproject.Constants;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class NodeConsts {
    public static Text createTabTitle(String text) {
        Text txt = new Text(text);
        txt.getStyleClass().add("tab-title");
        return txt;
    }
}