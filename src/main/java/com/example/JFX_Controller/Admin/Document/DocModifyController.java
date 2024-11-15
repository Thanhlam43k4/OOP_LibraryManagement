package com.example.JFX_Controller.Admin.Document;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import com.example.Handlers.Notify;
import com.example.Model.Document;
import com.example.Service.DocumentService;

import javafx.event.ActionEvent;

public class DocModifyController {

    @FXML private TextField title;
    @FXML private TextField author;
    @FXML private TextField genre;
    @FXML private TextField isbn;
    @FXML private TextArea description;
    @FXML private TextField imageUrl;

    @FXML private StackPane docModifyRoot;
    @FXML private AnchorPane docPane;

    private int docId;
    private DocRowController docRowController;
    @FXML
    void cancelModifyDoc(ActionEvent event) {
        docPane.getChildren().remove(this.docModifyRoot);
        this.docModifyRoot = null;
    }

    @FXML
    void saveModifyDoc(ActionEvent event) {
        Document modifyDoc = new Document(title.getText(), 
                                          author.getText(), 
                                          genre.getText(), 
                                          0,
                                          isbn.getText(), 
                                          imageUrl.getText());
        modifyDoc.setDocumentId(docId);
        modifyDoc.setDescription(description.getText());
        DocumentService.instance.updateDocument(modifyDoc);
        docRowController.modifyInfo(modifyDoc); // update ui
        Notify.showAlert(Alert.AlertType.INFORMATION, "Nofication", "Document has been modified!");

        docPane.getChildren().remove(this.docModifyRoot);
        this.docModifyRoot = null;
    }

    public void setInfo(Document d, AnchorPane docPane, DocRowController docRow) {
        docId = d.getDocumentId();
        this.title.setText(d.getTitle());
        this.author.setText(d.getAuthor());
        this.genre.setText(d.getGenre());
        this.description.setText(d.getDescription());
        this.imageUrl.setText(d.getUrlImage());
        this.isbn.setText(d.getISBN());
        this.docPane = docPane;
        this.docRowController = docRow;
    }
}