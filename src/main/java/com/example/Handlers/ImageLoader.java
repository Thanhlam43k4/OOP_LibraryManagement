package com.example.Handlers;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A utility class for loading images from a remote URL.
 * It establishes a connection to the given URL, fetches the image data,
 * and returns it as a JavaFX Image object.
 */
public class ImageLoader {

    /**
     * Loads an image from a remote URL and returns it as a JavaFX Image object.
     * If the image cannot be loaded due to an error or a failed HTTP request,
     * this method returns null.
     *
     * @param urlString The URL of the image to be loaded.
     * @return A JavaFX Image object containing the loaded image, or null if
     *         the image could not be loaded.
     */
    public static Image loadImage(String urlString) {
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            // Create a URL object from the provided URL string and open a connection
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setConnectTimeout(5000); // Set connection timeout
            connection.setReadTimeout(5000);    // Set read timeout
            connection.connect();

            // Check the HTTP response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the image data from InputStream and return it as an Image object
                inputStream = connection.getInputStream();
                return new Image(inputStream);
            } else {
                System.err.println("Failed to fetch image. HTTP Response Code: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the InputStream and disconnect the connection in the finally block
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (connection != null) {
                    connection.disconnect(); // Ensure the connection is closed when done
                }
            } catch (Exception e) {
                System.err.println("Failed to close InputStream or connection: " + e.getMessage());
            }
        }
        return null; // Return null if the image cannot be loaded
    }
}
