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

/**
 * Controller class responsible for handling the add document functionality
 * by interacting with the user interface and searching documents through the API.
 * This controller manages the search functionality with suggestions and integrates with the admin panel.
 */
public class AddDocApiController implements Initializable {

    @FXML private TextField searchField;
    @FXML private ListView<HBox> suggestList;
    @FXML private StackPane addDocRoot;

    private Timeline searchTimeline;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Initializes the controller by setting up the search field listener.
     * The suggest list visibility is initially hidden.
     *
     * @param location  The location used to resolve relative paths for the root object, or {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if no localization is needed.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        suggestList.setVisible(false);
        searchFieldListener();
    }

    /**
     * Cancels the add document operation and removes the current document pane.
     *
     * @param event The ActionEvent triggered by the cancel button.
     */
    @FXML
    void cancelAddDoc(ActionEvent event) {
        AdminController.instance.docPane.getChildren().remove(addDocRoot);
        addDocRoot = null;
    }

    /**
     * Placeholder method for a button click action to perform a search.
     * Currently unused as the search is handled automatically when text is entered.
     *
     * @param event The ActionEvent triggered by the search button.
     */
    @FXML
    void goSearch(ActionEvent event) {
        // This could be extended to handle search on button click if needed.
    }

    /**
     * Sets up the listener for the search field to trigger the search functionality.
     * The search will be initiated with a delay after the user stops typing.
     */
    private void searchFieldListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // Hide the suggestions list when the text field is empty
                suggestList.setVisible(false);
            } else {
                // If the search text changes, stop the previous timeline and create a new one with a 1-second delay
                if (searchTimeline != null) {
                    searchTimeline.stop();
                }
                searchTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> handleSearch()));
                searchTimeline.playFromStart();
            }
        });
    }

    /**
     * Handles the search operation by submitting the query to the background thread for execution.
     */
    private void handleSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            suggestList.setItems(FXCollections.observableArrayList());
            suggestList.setVisible(false);
            return;
        }

        // Run the search in a background thread
        executorService.submit(() -> searchBooks(query));
    }

    /**
     * Searches for books using the given query and updates the suggestions list.
     * This method runs in a background thread to avoid blocking the UI.
     *
     * @param query The search query entered by the user.
     */
    private void searchBooks(String query) {
        try {
            // Call the API service to search for documents
            List<Document> documents = ApiService.searchBooks(query);

            // Create a list of HBox items based on the search results
            ObservableList<HBox> suggestions = FXCollections.observableArrayList();
            for (Document document : documents) {
                suggestions.add(createItem(document));
            }

            // Update the UI with the search results on the JavaFX Application thread
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

    /**
     * Creates an HBox item for displaying a document's information in the suggestions list.
     *
     * @param doc The document to be displayed.
     * @return A populated HBox containing the document's information.
     */
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
