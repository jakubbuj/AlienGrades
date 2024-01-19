module com.example.javafxurwa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxproject to javafx.fxml;
    exports com.example.javafxproject;
}