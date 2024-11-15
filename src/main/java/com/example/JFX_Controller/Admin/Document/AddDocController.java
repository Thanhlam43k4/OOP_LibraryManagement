package com.example.JFX_Controller.Admin.Document;

import java.io.IOException;

import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.JFX_Controller.Admin.AdminController;
import com.example.Model.Document;
import com.example.Service.DocumentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;

public class AddDocController {
    @FXML private TextField title;
    @FXML private TextField author;
    @FXML private TextField genre;
    @FXML private TextField quantity;
    @FXML private TextField isbn;
    @FXML private TextField imageUrl;

    @FXML private StackPane addDocRoot;

    private AdminController adminController;

    @FXML
    void addDoc(ActionEvent event) {
        String title_input = title.getText();
        String author_input = author.getText();
        String genre_input = genre.getText();
        String quantity_input = quantity.getText();
        String isbn_input = isbn.getText();
        String imageUrl_input = imageUrl.getText();

        // Kiểm tra từng trường đầu vào
        if (!Validate.isValidTitle(title_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Tilte invalid!");
            return;
        }

        if (!Validate.isValidAuthor(author_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Author invalid!");
            return;
        }

        if (!Validate.isValidGenre(genre_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Genre invalid!");
            return;
        }

        if (!Validate.isValidQuantity(quantity_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Quantity invalid!");
            return;
        }

        if (!Validate.isValidISBN(isbn_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "ISBN invalid!");
            return;
        }

        // Nếu tất cả các trường hợp đều hợp lệ, thêm tài liệu
        int quantity = Integer.parseInt(quantity_input); // Chuyển đổi số lượng thành số nguyên
        Document doc = new Document(title_input, author_input, genre_input, quantity, isbn_input, imageUrl_input);
        DocumentService.instance.addDocument(doc);
        doc.setDocumentId(DocumentService.instance.getDocumentByISBN(isbn_input).getDocumentId());
        // update ui
        addDocNode(doc);
        adminController.docPane.getChildren().remove(addDocRoot);
        addDocRoot = null;
        // Thông báo thành công
        Notify.showAlert(Alert.AlertType.INFORMATION, "Nofication", "Add Document sucess!");
    }

    @FXML
    void cancelAddDoc(ActionEvent event) {
        adminController.docPane.getChildren().remove(addDocRoot);
        addDocRoot = null;
    }

    private void addDocNode(Document doc) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/DocRow.fxml"));
            Node docNode = loader.load();
            DocRowController docRowController = (DocRowController) loader.getController();
            docRowController.setInfo(doc, adminController, docNode);
            AdminController.docList.add(docNode);
            adminController.docVBox.setPrefHeight(AdminController.docList.size() * 70 + 70);        
            adminController.docVBox.getChildren().add(docNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInfo(AdminController adminController) {
        this.adminController = adminController;
    }
}
