package com.example.Handlers;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.util.Objects;

public class Notify {

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(Notify.class.getResource("/CSS_Setup/alert.css")).toExternalForm()
        );
        alert.setContentText(message);
        alert.show();
    }
}