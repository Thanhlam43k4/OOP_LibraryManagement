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

public class DocModifyController {

    @FXML private TextField title;
    @FXML private TextField author;
    @FXML private TextField genre;
    @FXML private TextField isbn;
    @FXML private TextArea description;
    @FXML private TextField imageUrl;

    @FXML private StackPane docModifyRoot;

    private int docId;
    private DocRowController docRowController;
    @FXML
    void cancelModifyDoc(ActionEvent event) {
        AdminController.instance.docPane.getChildren().remove(this.docModifyRoot);
        this.docModifyRoot = null;
    }
    @FXML
    void saveModifyDoc(ActionEvent event) {
        if(validate()){
            Document modifyDoc = new Document(title.getText(),
                    author.getText(),
                    genre.getText(),
                    description.getText(),
                    imageUrl.getText());
            modifyDoc.setDocumentId(docId);
            modifyDoc.setDescription(description.getText());

            DocumentService.instance.updateDocument(modifyDoc);
            docRowController.modifyInfo(modifyDoc); // update ui
            Notify.showAlert(Alert.AlertType.INFORMATION, "Nofication", "Document has been modified!");
            // tắt Pane
            AdminController.instance.docPane.getChildren().remove(this.docModifyRoot);
            this.docModifyRoot = null;
        }else {
            return;
        }

    }

    private boolean validate() {
        // Kiểm tra từng trường đầu vào
        if (!Validate.isValidTitle(title.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Tilte invalid!");
            return false;
        }

        if (!Validate.isValidAuthor(author.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Author invalid!");
            return false;
        }

        if (!Validate.isValidGenre(genre.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Genre invalid!");
            return false;
        }

        if (Validate.isValidISBN(isbn.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "ISBN invalid!");
            return false;
        }

        if (!Validate.isValidTitle(description.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Desciption invalid!");
            return false;
        }
        return true;
    }

    public void setInfo(Document d, DocRowController docRow) {
        docId = d.getDocumentId();
        this.title.setText(d.getTitle());
        this.author.setText(d.getAuthor());
        this.genre.setText(d.getGenre());
        this.description.setText(d.getDescription());
        this.imageUrl.setText(d.getUrlImage());
        this.isbn.setText(d.getISBN());
        this.docRowController = docRow;
    }
}