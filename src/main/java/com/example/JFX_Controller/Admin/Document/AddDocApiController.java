package com.example.JFX_Controller.Admin.Document;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.JFX_Controller.Admin.AdminController;
import com.example.Model.Document;
import com.example.Service.ApiService;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class AddDocApiController implements Initializable {

    @FXML private TextField searchField;
    @FXML private ListView<HBox> suggestList;

    @FXML private StackPane addDocRoot;

    private Timeline searchTimeline;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        suggestList.setVisible(false);
        searchFieldListener();
    }

    @FXML
    void cancelAddDoc(ActionEvent event) {
        AdminController.instance.docPane.getChildren().remove(addDocRoot);
        addDocRoot = null;
    }

    @FXML
    void goSearch(ActionEvent event) {
        // This could be extended to handle search on button click if needed.
    }

    private void searchFieldListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // Khi TextField trống, ListView tắt
                suggestList.setVisible(false);
            } else {
                // Nếu có sự thay đổi trong nội dung, hủy timeline cũ
                if (searchTimeline != null) {
                    searchTimeline.stop();
                }
                // Tạo một timeline mới với thời gian trì hoãn
                searchTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> handleSearch()));
                searchTimeline.playFromStart();
            }
        });
    }

    private void handleSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            suggestList.setItems(FXCollections.observableArrayList());
            suggestList.setVisible(false);
            return;
        }

        // Chạy tìm kiếm trong một luồng nền
        executorService.submit(() -> searchBooks(query));
    }

    private void searchBooks(String query) {
        try {
            // Gọi API để tìm kiếm tài liệu
            List<Document> documents = ApiService.searchBooks(query);

            // Tạo danh sách các item HBox từ kết quả tìm kiếm
            ObservableList<HBox> suggestions = FXCollections.observableArrayList();
            for (Document document : documents) {
                suggestions.add(createItem(document));
            }

            // Cập nhật giao diện người dùng trong JavaFX thread
            Platform.runLater(() -> {
                if (suggestions.isEmpty()) {
                    suggestList.setVisible(false);
                } else {
                    suggestList.setItems(suggestions);
                    suggestList.setVisible(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox createItem(Document doc) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/ApiDocCard.fxml"));
            HBox node = loader.load();
            ApiDocCardController apiDocCardController = loader.getController();
            apiDocCardController.setInfo(doc);
            return node;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
