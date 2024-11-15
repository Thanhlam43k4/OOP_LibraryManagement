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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class ApiService {
    private static final Gson gson = new Gson();

    public static List<Document> searchBooks(String query) throws Exception {
        BufferedReader in = getBufferedReader(query);
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
        JsonArray documentsArray = jsonResponse.getAsJsonArray("books");
        List<Document> documents = new ArrayList<>();
        if (documentsArray == null) {
            System.out.println("No results found for your search query: " + query);
            return new ArrayList<>(); // Hoặc trả về một danh sách trống
        }
        for (JsonElement documentElement : documentsArray) {
            JsonObject documentObject = documentElement.getAsJsonObject();
            Document document = new Document(
                    documentObject.get("title").getAsString(),
                    documentObject.get("authors").getAsString(),
                    documentObject.get("image").getAsString(),
                    documentObject.get("url").getAsString()
            );
            documents.add(document);
        }
        return documents;
    }

    private static BufferedReader getBufferedReader(String query) throws IOException {
        String urlString = "https://www.dbooks.org/api/search/" + query + "?limit=5";
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");  // Thêm dòng này
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("HTTP GET Request Failed with Error code : " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        return in;
    }
}
