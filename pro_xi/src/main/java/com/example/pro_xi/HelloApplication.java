package com.example.pro_xi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("stage1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log In!");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            logout(stage);

        });
    }

    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("Welcome to Log out section");
        alert.setContentText("Do you want to log out?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            System.out.println("You're logged out!!!");
            stage.close();

        }

    }

    public static void main(String[] args) {
        launch();
    }
}