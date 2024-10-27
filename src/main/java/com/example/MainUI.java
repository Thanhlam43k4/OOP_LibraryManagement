package com.example;

//#region Lib
import java.io.IOException;
import java.sql.Connection;

import com.example.Database.DatabaseConnection;

import com.example.Service.DocumentService;
import com.example.Service.TransactionService;
import com.example.Service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//#endregion

public class MainUI extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        // Create databse connection
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Failed to establish a database connection.");
            return;
        }
        // Create service
        createServices(con);

        // Create Scene
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1400, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Book Management Application");
        primaryStage.show();
    }

    void createServices(Connection con) {
        UserService.instance = new UserService(con);
        DocumentService.instance = new DocumentService(con);
        TransactionService.instance = new TransactionService(con);
    }

    public static void main(String[] args) {
        launch(args);
    }
}