package com.example;

//#region Lib
import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

import com.example.Database.DatabaseConnection;
import com.example.Service.DocumentService;
import com.example.Service.TransactionService;
import com.example.Service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
//#endregion

/**
 * MainUI class for launching the Book Management Application.
 * It initializes the database connection, services, and UI components.
 */
public class MainUI extends Application {

    // The primary stage for the application.
    public static Stage primaryStage;

    /**
     * Entry point for the JavaFX application.
     * Sets up the application window and initiates services.
     *
     * @param stage the primary stage for this application
     * @throws IOException if there is an error loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Create a database connection
        Connection con = DatabaseConnection.getConnection();

        // If the connection fails, exit the application
        if (con == null) {
            System.out.println("Failed to establish a database connection.");
            return;
        }

        // Initialize services with the database connection
        createServices(con);

        // Set up the primary stage (main window) of the application
        primaryStage = stage;

        // Load the FXML layout for the Login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Login.fxml"));
        Parent root = loader.load();

        // Set the application icon
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Image/logo.jpg"))); // Ensure the path is correct
        primaryStage.getIcons().add(logoImage);

        // Set up the scene and window properties
        Scene scene = new Scene(root, 1700, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Book Management Application");

        // Show the primary stage (application window)
        primaryStage.show();
    }

    /**
     * Creates and initializes service instances with the provided database connection.
     *
     * @param con the active database connection to be used by services
     */
    void createServices(Connection con) {
        // Initializing service instances with singleton pattern
        UserService.instance = new UserService(con);
        DocumentService.instance = new DocumentService(con);
        TransactionService.instance = new TransactionService(con);
    }

    /**
     * The main entry point for launching the JavaFX application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
