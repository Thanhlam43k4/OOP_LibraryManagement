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

    }

    private void searchFieldListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // Khi TextField trống, ListView tắt
                suggestList.setVisible(false);
            } 
            else {
                searchTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> handleSearch()));
                searchTimeline.playFromStart();
            }
        });
    }
    private void handleSearch() {
        try {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                suggestList.setItems(FXCollections.observableArrayList());
                return;
            }
            if (query.isEmpty()) {
                suggestList.setVisible(false);
                return;
            }
            executorService.submit(() -> searchBooks(query));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // query api và addnode vào listview
    private void searchBooks(String query) {
        try {
            // Gọi API để tìm kiếm sách
            List<Document> documents = ApiService.searchBooks(query);

            ObservableList<HBox> suggestions = FXCollections.observableArrayList();
            for (Document document : documents) {
                suggestions.add(createItem(document));
            }
            Platform.runLater(() -> {
                suggestList.setItems(suggestions);
                suggestList.setVisible(!suggestions.isEmpty());
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