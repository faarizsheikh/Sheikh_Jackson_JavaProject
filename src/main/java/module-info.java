module org.example.sheikh_jackson_javaproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sheikh_jackson_javaproject to javafx.fxml;
    exports org.example.sheikh_jackson_javaproject;
}