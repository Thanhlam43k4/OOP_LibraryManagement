package com.example.JFX_Controller.Admin.Document;

import java.io.IOException;

import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.JFX_Controller.Admin.AdminController;
import com.example.Model.Document;
import com.example.Service.DocumentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;

/**
 * Controller for managing the addition of documents in the Admin panel.
 * Handles the user input, validation, and submission of document data.
 */
public class AddDocController {

    @FXML private TextField title;
    @FXML private TextField author;
    @FXML private TextField genre;
    @FXML private TextField quantity;
    @FXML private TextField isbn;
    @FXML private TextArea description;
    @FXML private TextField imageUrl;

    @FXML private StackPane addDocRoot;

    /**
     * Handles the event when the user submits the form to add a new document.
     * It validates the input fields and if valid, it adds the document to the system.
     *
     * @param event the action event triggered when the "Add Document" button is clicked.
     */
    @FXML
    void addDoc(ActionEvent event) {
        String title_input = title.getText();
        String author_input = author.getText();
        String genre_input = genre.getText();
        String quantity_input = quantity.getText();
        String isbn_input = isbn.getText();
        String description_input = description.getText();
        String imageUrl_input = imageUrl.getText();

        // Validate input fields
        if (Validate.isValidTitle(title_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Title invalid!");
            return;
        }

        if (Validate.isValidAuthor(author_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Author invalid!");
            return;
        }

        if (Validate.isValidGenre(genre_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Genre invalid!");
            return;
        }

        if (!Validate.isValidQuantity(quantity_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Quantity invalid!");
            return;
        }

        if (Validate.isValidISBN(isbn_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "ISBN invalid!");
            return;
        }

        if (Validate.isValidTitle(description_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Description invalid!");
            return;
        }

        // Convert quantity to integer and add document if valid
        int quantity = Integer.parseInt(quantity_input);
        Document doc = new Document(title_input, author_input, genre_input, quantity, isbn_input, imageUrl_input);
        doc.setDescription(description_input);
        DocumentService.instance.addDocument(doc);
        doc.setDocumentId(DocumentService.instance.getDocumentByISBN(isbn_input).getDocumentId());

        // Update UI
        addDocNode(doc);

        // Show success notification
        Notify.showAlert(Alert.AlertType.INFORMATION, "Notification", "Add Document Success!");
        AdminController.instance.docPane.getChildren().remove(addDocRoot);
        addDocRoot = null;
    }

    /**
     * Handles the event when the user cancels the document addition form.
     * Closes the add document form and removes it from the UI.
     *
     * @param event the action event triggered when the "Cancel" button is clicked.
     */
    @FXML
    void cancelAddDoc(ActionEvent event) {
        AdminController.instance.docPane.getChildren().remove(addDocRoot);
        addDocRoot = null;
    }

    /**
     * Adds the newly created document node to the document list in the Admin panel.
     *
     * @param doc the document to be added.
     */
    private void addDocNode(Document doc) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/DocRow.fxml"));
            Parent docNode = loader.load();
            DocRowController docRowController = (DocRowController) loader.getController();
            docRowController.setInfo(doc, docNode);
            AdminController.docList.add(docNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the input fields to display the information of a document.
     * This method is used to populate the fields when editing an existing document.
     *
     * @param d the document whose information is to be set in the fields.
     */
    public void setInfo(Document d) {
        this.title.setText(d.getTitle());
        this.author.setText(d.getAuthor());
        this.genre.setText(d.getGenre());
        this.isbn.setText(d.getISBN());
        this.description.setText(d.getDescription());
        this.imageUrl.setText(d.getUrlImage());
    }
}
