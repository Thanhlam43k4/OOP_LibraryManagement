package com.example.Handlers;

import javafx.scene.control.Alert;
import java.util.Objects;

/**
 * A utility class for displaying alerts in the JavaFX application.
 * The class provides a method to show customized alerts with specific titles,
 * messages, and alert types.
 */
public class Notify {

    /**
     * Displays an alert with a specified alert type, title, and message.
     * This method allows customization of the alert's appearance by setting the
     * alert's title and content text. Additionally, it applies a custom stylesheet
     * for styling the alert dialog.
     *
     * @param alertType The type of the alert (e.g., INFORMATION, WARNING, ERROR, etc.).
     * @param title The title of the alert dialog.
     * @param message The message content to be displayed in the alert dialog.
     */
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        // Create a new Alert object with the given alert type
        Alert alert = new Alert(alertType);

        // Set the title of the alert
        alert.setTitle(title);

        // Apply a custom stylesheet to style the alert
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(Notify.class.getResource("/CSS_Setup/alert.css")).toExternalForm()
        );

        // Set the content text (message) of the alert
        alert.setContentText(message);

        // Display the alert to the user
        alert.show();
    }
}
