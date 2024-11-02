package com.example.JFX_Controller.Admin;

import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.Model.Document;
import com.example.Service.DocumentService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    @FXML private AnchorPane docPane;

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
            Notify.showAlert(Alert.AlertType.ERROR, "Lỗi", "Tiêu đề không hợp lệ!");
            return;
        }

        if (!Validate.isValidAuthor(author_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Lỗi", "Tác giả không hợp lệ!");
            return;
        }

        if (!Validate.isValidGenre(genre_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Lỗi", "Thể loại không hợp lệ!");
            return;
        }

        if (!Validate.isValidQuantity(quantity_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Lỗi", "Số lượng phải là số dương!");
            return;
        }

        if (!Validate.isValidISBN(isbn_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Lỗi", "ISBN không hợp lệ!");
            return;
        }

        // Nếu tất cả các trường hợp đều hợp lệ, thêm tài liệu
        int quantity = Integer.parseInt(quantity_input); // Chuyển đổi số lượng thành số nguyên
        Document doc = new Document(title_input, author_input, genre_input, quantity, isbn_input, imageUrl_input);
        DocumentService.instance.addDocument(doc);

        // Thông báo thành công
        Notify.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thêm sách thành công!");
    }

    @FXML
    void cancelAddDoc(ActionEvent event) {
        docPane.getChildren().remove(addDocRoot);
        addDocRoot = null;
    }

    public void setInfo(AnchorPane docPane) {
        this.docPane = docPane;
    }
}
