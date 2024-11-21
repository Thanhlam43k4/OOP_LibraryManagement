package com.example.Handlers;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoader {

    public static Image loadImage(String urlString) {
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            // Tạo URL và mở kết nối
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setConnectTimeout(5000); // Thiết lập timeout kết nối
            connection.setReadTimeout(5000);    // Thiết lập timeout đọc dữ liệu
            connection.connect();

            // Kiểm tra mã phản hồi
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Đọc dữ liệu ảnh từ InputStream
                inputStream = connection.getInputStream();
                return new Image(inputStream);
            } else {
                System.err.println("Failed to fetch image. HTTP Response Code: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng InputStream và connection nếu có
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (connection != null) {
                    connection.disconnect(); // Đảm bảo ngắt kết nối khi xong
                }
            } catch (Exception e) {
                System.err.println("Failed to close InputStream or connection: " + e.getMessage());
            }
        }
        return null; // Trả về null nếu không tải được ảnh
    }
}
