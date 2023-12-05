module com.example.javafxurwa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxurwa to javafx.fxml;
    exports com.example.javafxurwa;
}