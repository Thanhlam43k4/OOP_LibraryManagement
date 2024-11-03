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

public class MainUI extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        //Create databse connection
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Failed to establish a database connection.");
            return;
        }
        // Create Scene
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Login.fxml"));
        Parent root = loader.load();
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Image/logo.jpg"))); // Đảm bảo đường dẫn đúng
        primaryStage.getIcons().add(logoImage); // Thêm biểu tượng vào cửa sổ

        Scene scene = new Scene(root, 1400, 1000);
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