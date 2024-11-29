package com.example.JFX_Controller;

import java.io.IOException;

import com.example.MainUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Abstract controller class that provides common functionality for loading FXML scenes.
 * This class is intended to be extended by specific controllers in the application.
 *
 * It handles loading the scene from an FXML file, setting the size of the scene to match
 * the current size of the primary stage, and setting the new scene on the primary stage.
 */
public abstract class Controller {

    /**
     * Loads the specified FXML file and sets it as the current scene on the primary stage.
     * The scene's width and height are set to match the current size of the primary stage.
     *
     * @param fxmlFile The name of the FXML file to load (located in the /Scenes/ directory).
     * @throws IOException If there is an issue loading the FXML file.
     */
    protected void loadScene(String fxmlFile) {
        // Retrieve current width and height of the primary stage's scene
        double width = MainUI.primaryStage.getScene().getWidth();
        double height = MainUI.primaryStage.getScene().getHeight();

        try {
            // Load the FXML file and create the scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);

            // Set the new scene on the primary stage
            MainUI.primaryStage.setScene(scene);
            MainUI.primaryStage.show();
        } catch (IOException e) {
            // Print stack trace if an error occurs while loading the FXML file
            e.printStackTrace();
        }
    }
}
