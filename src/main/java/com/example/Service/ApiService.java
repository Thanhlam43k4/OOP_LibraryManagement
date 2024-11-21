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

public class ApiService {
    private static final Gson gson = new Gson();

    public static List<Document> searchBooks(String query) throws IOException {
        String jsonResponse = getJsonResponse(query); // Lấy phản hồi JSON từ API
        return parseBooks(jsonResponse); // Phân tích dữ liệu và trả về danh sách
    }

    private static String getJsonResponse(String query) throws IOException {
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

    private static List<Document> parseBooks(String jsonResponse) {
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray documentsArray = jsonObject.getAsJsonArray("books");

        List<Document> documents = new ArrayList<>();
        if (documentsArray == null || documentsArray.size() == 0) {
            System.out.println("No results found.");
            return documents; // Trả về danh sách rỗng nếu không có kết quả
        }

        for (int i = 0; i < Math.min(documentsArray.size(), 6); i++) { // Giới hạn tối đa là 6 kết quả
            JsonObject documentObject = documentsArray.get(i).getAsJsonObject();
            documents.add(new Document(
                    documentObject.get("title").getAsString(),
                    documentObject.get("authors").getAsString(),
                    documentObject.get("id").getAsString(),
                    documentObject.get("subtitle").getAsString(),
                    documentObject.get("image").getAsString()
            ));
        }
        return documents;
    }
}
