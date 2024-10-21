package com.example.Handlers;

import javafx.scene.control.Alert;

public class ErrorHandler {

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}