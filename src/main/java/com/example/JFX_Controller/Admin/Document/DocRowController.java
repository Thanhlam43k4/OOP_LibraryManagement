package com.example.JFX_Controller.Admin.Document;

import java.io.IOException;
import java.util.List;

import com.example.Handlers.ImageLoader;
import com.example.Handlers.Notify;
import com.example.JFX_Controller.Admin.AdminController;
import com.example.Model.Copies;
import com.example.Model.Document;
import com.example.Service.DocumentService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Controller for managing each document row in the Document Manager tab.
 * This class handles the display and modification of document details,
 * showing copies, and deleting documents.
 */
public class DocRowController {

    @FXML private ImageView docImage;  // Image view to display the document's image
    @FXML private Label title;         // Label to display the document's title
    @FXML private Label author;        // Label to display the document's author
    @FXML private Label genre;         // Label to display the document's genre
    @FXML private Label amount;        // Label to display the document's available amount

    private Node root;  // The root node for this document row
    private Document doc;  // The document being managed by this row

    /**
     * Displays all copies of the document in a separate pane when clicked.
     * This method is called when the user clicks on the 'showAllCopy' area.
     *
     * @param event the MouseEvent triggered by clicking the "show all copies" button.
     */
    @FXML
    void showAllCopy(MouseEvent event) {
        addCopyNodes();  // Load and display the copies of the document
    }

    /**
     * Opens the document modification pane when the 'modify' button is clicked.
     * This method loads the document modification interface and allows editing.
     *
     * @param event the ActionEvent triggered by clicking the "modify" button.
     */
    @FXML
    void openDocModify(ActionEvent event) {
        loadDocModify();  // Load the modify document interface
    }

    /**
     * Deletes the document if it is not part of an active transaction.
     * If the document is in use, a notification is shown.
     * Otherwise, the document is deleted from the UI and the backend.
     *
     * @param event the ActionEvent triggered by clicking the "delete" button.
     */
    @FXML
    void deleteDoc(ActionEvent event) {

        // Check if the document is available for deletion
        if (DocumentService.instance.isDocAvailable(doc.getISBN())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Document cannot be deleted because it is part of an active transaction!");
            return;
        }

        // Remove document from the UI and delete it from the service
        AdminController.docList.remove(this.root);
        DocumentService.instance.deleteDocument(doc.getDocumentId());
        Notify.showAlert(Alert.AlertType.INFORMATION, "Notification", "Document deleted successfully!");
    }

    /**
     * Sets the information for the document row, including displaying the document's image,
     * title, author, genre, and the number of copies available.
     *
     * @param d the Document object containing the document's data.
     * @param root the root node of the document row in the UI.
     */
    public void setInfo(Document d, Node root) {
        doc = d;
        Image image = ImageLoader.loadImage(doc.getUrlImage());  // Load the document image
        this.docImage.setImage(image);  // Set the document image in the image view
        this.title.setText(d.getTitle());  // Set the document title
        this.author.setText(d.getAuthor());  // Set the document author
        this.genre.setText(d.getGenre());  // Set the document genre
        this.amount.setText(String.valueOf(d.getNumberCopy()));  // Set the available number of copies
        this.root = root;  // Set the root node of the document row
    }

    /**
     * Updates the information in the document row when the document details are modified.
     * This method is used to refresh the displayed information.
     *
     * @param d the updated Document object with new information.
     */
    public void modifyInfo(Document d) {
        doc = d;
        Image image = ImageLoader.loadImage(doc.getUrlImage());  // Reload the updated document image
        this.docImage.setImage(image);  // Set the updated image
        this.title.setText(d.getTitle());  // Update the title
        this.author.setText(d.getAuthor());  // Update the author
        this.genre.setText(d.getGenre());  // Update the genre
    }

    /**
     * Loads and displays all copies of the document in a separate pane.
     * This method fetches all copies of the document from the service and displays them.
     */
    void addCopyNodes() {
        AdminController.instance.copiesDocPane.setVisible(true);  // Make the copies pane visible
        AdminController.instance.docCopyVbox.getChildren().clear();  // Clear existing copy rows
        List<Copies> copies = DocumentService.instance.getAllCopies(doc.getDocumentId());  // Fetch all copies of the document
        AdminController.instance.docCopyVbox.setPrefHeight(copies.size() * 70);  // Adjust the height of the copy pane

        // Load and display each copy
        for (Copies c : copies) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/DocCopyRow.fxml"));
                Node node = loader.load();  // Load the copy row UI

                // Set the information for the copy row
                DocCopyController docCopyController = loader.getController();
                docCopyController.setInfo(c);
                AdminController.instance.docCopyVbox.getChildren().add(node);  // Add the copy row to the UI
            } catch (IOException e) {
                e.printStackTrace();  // Handle the exception if the copy row UI fails to load
            }
        }
    }

    /**
     * Loads the document modification interface in a separate pane.
     * This method is triggered when the user decides to modify the document.
     */
    void loadDocModify() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/ModifyDoc.fxml"));
            Parent modifyDocPane = loader.load();  // Load the modify document UI

            // Add the modify pane to the admin pane
            AdminController.instance.docPane.getChildren().add(modifyDocPane);
            AnchorPane.setBottomAnchor(modifyDocPane, 0.0);
            AnchorPane.setLeftAnchor(modifyDocPane, 0.0);
            AnchorPane.setRightAnchor(modifyDocPane, 0.0);
            AnchorPane.setTopAnchor(modifyDocPane, 0.0);

            // Set the document information in the modify pane
            DocModifyController modifyController = loader.getController();
            modifyController.setInfo(doc, this);
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception if the modify pane fails to load
        }
    }
}
