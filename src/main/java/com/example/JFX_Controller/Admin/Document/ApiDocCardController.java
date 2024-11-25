package com.example.JFX_Controller.Admin.Document;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import com.example.Model.Document;

import java.io.IOException;

import com.example.Handlers.ImageLoader;
import com.example.JFX_Controller.Admin.AdminController;

import javafx.event.ActionEvent;

/**
 * Controller for handling the document card display in the Admin panel.
 * This class manages the document details display and the functionality
 * to add the document to the library when triggered by the user.
 */
public class ApiDocCardController {

    @FXML private ImageView docCover;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label isbn;

    private Document doc;

    /**
     * Handles the event when the user clicks to add the document to the library.
     * It loads the 'Add Document' form and populates it with the document details.
     *
     * @param event the action event triggered when the "Add Document" button is clicked.
     */
    @FXML
    void addDocToLib(ActionEvent event) {
        loadAddDoc();
    }

    /**
     * Loads the 'Add Document' form and sets the document details in the form for editing.
     * The form is then added to the Admin panel's document pane.
     */
    private void loadAddDoc() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/AddDoc.fxml"));
            Parent addDocPane = loader.load();

            // Get the controller for AddDoc.fxml and pass the document details
            AddDocController addDocController = loader.getController();
            addDocController.setInfo(doc);

            // Add the form to the Admin panel
            AdminController.instance.docPane.getChildren().add(addDocPane);

            // Stretch the form to fill the container
            AnchorPane.setBottomAnchor(addDocPane, 0.0);
            AnchorPane.setLeftAnchor(addDocPane, 0.0);
            AnchorPane.setRightAnchor(addDocPane, 0.0);
            AnchorPane.setTopAnchor(addDocPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the document details in the document card for display.
     * This method is called to populate the UI with the information of a specific document.
     *
     * @param doc the document whose details are to be displayed on the card.
     */
    public void setInfo(Document doc) {
        this.doc = doc;

        try {
            // Load the document cover image
            Image image = ImageLoader.loadImage(doc.getUrlImage());
            this.docCover.setImage(image);
        } catch (Exception e) {
            System.err.println("Document cover URL is invalid when adding DocElementNode.");
        }

        // Set the document title, author, and ISBN
        this.title.setText(doc.getTitle());
        this.author.setText(doc.getAuthor());
        this.isbn.setText(doc.getISBN());
    }
}
