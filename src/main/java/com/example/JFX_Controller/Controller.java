package com.example.JFX_Controller;

import java.io.IOException;

import com.example.MainUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class Controller {
    protected void loadScene(String fxmlFile) {
        double width = MainUI.primaryStage.getScene().getWidth();
        double height = MainUI.primaryStage.getScene().getHeight();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            MainUI.primaryStage.setScene(scene);
            MainUI.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
