package com.example.JFX_Controller.Admin.Document;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.JFX_Controller.Admin.AdminController;
import com.example.Model.Document;
import com.example.Service.DocumentService;

import javafx.event.ActionEvent;

/**
 * Controller for modifying the details of an existing document in the Admin panel.
 * This class handles the functionality to update document information, validate
 * input fields, and update the UI accordingly.
 */
public class DocModifyController {

    @FXML private TextField title;
    @FXML private TextField author;
    @FXML private TextField genre;
    @FXML private TextField isbn;
    @FXML private TextArea description;
    @FXML private TextField imageUrl;

    @FXML private StackPane docModifyRoot;

    private int docId;  // Stores the ID of the document to be modified
    private DocRowController docRowController;  // Controller for the document row in the UI

    /**
     * Cancels the modification of the document and removes the modification pane.
     * This method is called when the user clicks the cancel button.
     *
     * @param event the ActionEvent triggered by clicking the cancel button.
     */
    @FXML
    void cancelModifyDoc(ActionEvent event) {
        AdminController.instance.docPane.getChildren().remove(this.docModifyRoot);  // Remove the modify pane
        this.docModifyRoot = null;
    }

    /**
     * Saves the modified document information after validating the input fields.
     * This method is called when the user clicks the save button.
     *
     * @param event the ActionEvent triggered by clicking the save button.
     */
    @FXML
    void saveModifyDoc(ActionEvent event) {
        if(validate()) {
            // Create a new Document object with the updated information
            Document modifyDoc = new Document(
                    docId,
                    title.getText(),
                    author.getText(),
                    imageUrl.getText(),
                    genre.getText(),
                    description.getText());

            // Update the document in the service
            DocumentService.instance.updateDocument(modifyDoc);

            // Update the UI to reflect the changes
            docRowController.modifyInfo(modifyDoc);

            // Show a success notification
            Notify.showAlert(Alert.AlertType.INFORMATION, "Notification", "Document has been modified!");

            // Close the modification pane
            AdminController.instance.docPane.getChildren().remove(this.docModifyRoot);
            this.docModifyRoot = null;
        } else {
            return;  // If validation fails, return without saving
        }
    }

    /**
     * Validates the input fields for the document modification.
     * Checks if all fields contain valid data.
     *
     * @return true if all fields are valid, false otherwise.
     */
    private boolean validate() {
        // Validate each input field and show an error if invalid
        if (Validate.isValidTitle(title.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Title is invalid!");
            return false;
        }

        if (Validate.isValidAuthor(author.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Author is invalid!");
            return false;
        }

        if (Validate.isValidGenre(genre.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Genre is invalid!");
            return false;
        }

        if (Validate.isValidTitle(description.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Description is invalid!");
            return false;
        }

        return true;  // All fields are valid
    }

    /**
     * Sets the information of the document to be modified in the input fields.
     * This method is called when the user opens the modify document pane.
     *
     * @param d the document whose information will be displayed in the input fields.
     * @param docRow the controller of the document row in the UI to be updated after saving the modification.
     */
    public void setInfo(Document d, DocRowController docRow) {
        docId = d.getDocumentId();  // Set document ID
        this.title.setText(d.getTitle());  // Set document title
        this.author.setText(d.getAuthor());  // Set document author
        this.genre.setText(d.getGenre());  // Set document genre
        this.isbn.setText(d.getISBN());  // Set document ISBN
        this.description.setText(d.getDescription());  // Set document description
        this.imageUrl.setText(d.getUrlImage());  // Set document image URL
        this.docRowController = docRow;  // Set the document row controller
    }
}
