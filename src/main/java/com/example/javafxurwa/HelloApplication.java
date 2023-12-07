package com.example.javafxurwa;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage mainStage) throws IOException {
        HelloController.switchToMainScene(mainStage);
        // Creation of method switchToMainScene() was in order to implement buttons that go back to the main screen
        // without creating a new stage while start(Stage stage) was being called,
        // which would've had a stage.show() run every time

        mainStage.setResizable(false);

    }

    public static void main(String[] args) {
        launch(args);
    }
}





