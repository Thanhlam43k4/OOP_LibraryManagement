package com.example.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.Model.Document;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The ApiService class provides methods to interact with an external API
 * to search for books and retrieve details about them.
 *
 * <p>This class uses a third-party API to fetch books based on a query string
 * and returns a list of Document objects representing the books found.</p>
 */
public class ApiService {

    // The Gson object is used for parsing JSON responses.
    private static final Gson gson = new Gson();

    /**
     * Searches for books based on the provided query and returns a list of Document objects.
     *
     * @param query The search term used to query the book API.
     * @return A list of Document objects representing the books found.
     * @throws IOException If an input or output error occurs while reading the API response.
     */
    public static List<Document> searchBooks(String query) throws IOException {
        // Get the raw JSON response from the API based on the search query.
        String jsonResponse = getJsonResponse(query);

        // Parse the JSON response and return the list of Document objects.
        return parseBooks(jsonResponse);
    }

    /**
     * Sends a GET request to the API and retrieves the raw JSON response as a String.
     *
     * @param query The search term used to query the book API.
     * @return The raw JSON response from the API.
     * @throws IOException If an input or output error occurs during the HTTP request.
     */
    public static String getJsonResponse(String query) throws IOException {
        String urlString = "https://www.dbooks.org/api/search/" + query + "?limit=6";
        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getResponseCode() == HttpURLConnection.HTTP_OK
                        ? connection.getInputStream()
                        : connection.getErrorStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    /**
     * Parses the JSON response from the API and extracts the book information into a list of Document objects.
     *
     * @param jsonResponse The raw JSON response from the API.
     * @return A list of Document objects representing the books found.
     */
    public static List<Document> parseBooks(String jsonResponse) {
        // Parse the JSON response into a JsonObject.
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray documentsArray = jsonObject.getAsJsonArray("books");

        // Prepare a list to store the Document objects.
        List<Document> documents = new ArrayList<>();

        // If there are no books found, print a message and return an empty list.
        if (documentsArray == null || documentsArray.isEmpty()) {
            System.out.println("No results found.");
            return documents; // Return an empty list if no books were found.
        }

        // Loop through the books array and add each book's details to the list of Document objects.
        for (int i = 0; i < Math.min(documentsArray.size(), 6); i++) { // Limit to 6 results.
            JsonObject documentObject = documentsArray.get(i).getAsJsonObject();
            documents.add(new Document(
                    documentObject.get("title").getAsString(),
                    documentObject.get("authors").getAsString(),
                    documentObject.get("id").getAsString(),
                    documentObject.get("subtitle").getAsString(),
                    documentObject.get("image").getAsString()
            ));
        }

        // Return the list of Document objects.
        return documents;
    }
}
