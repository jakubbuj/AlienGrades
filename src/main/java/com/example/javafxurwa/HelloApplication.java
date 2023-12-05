package com.example.javafxurwa;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage mainStage) throws IOException {
        HelloController.switchToMainScene(mainStage);

        mainStage.setResizable(false);


    }

    public static void main(String[] args) {
        launch(args);
    }
}





