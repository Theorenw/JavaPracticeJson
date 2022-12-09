module com.example.javapracticejson {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.javapracticejson to javafx.fxml, com.google.gson;
    exports com.example.javapracticejson;
}