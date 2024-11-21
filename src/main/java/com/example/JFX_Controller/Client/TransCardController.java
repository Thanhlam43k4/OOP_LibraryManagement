package com.example.JFX_Controller.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.sql.Date;

import com.example.Handlers.ImageLoader;
import com.example.Handlers.Notify;
import com.example.Model.Document;
import com.example.Service.TransactionService;

// Controller của từng thẻ Doc đang mượn ở tab MyDoc
public class TransCardController {
    @FXML private ImageView docCover;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label returnDate;

    @FXML private HBox root;

    private int tranId;
    @FXML
    void goReadDoc(ActionEvent event) {

    }

    @FXML
    void returnDoc(ActionEvent event) {
        TransactionService.instance.returnBook(tranId);
        //ui
        ClientController.docelementList.remove(root);
        GridPane mydocGrid = ClientController.instance.mydocGrid;
        ClientController.instance.updateGrid(mydocGrid, ClientController.docelementList, ClientController.instance.currentCol);
        Notify.showAlert(Alert.AlertType.INFORMATION, "Nofication", "Return Document sucess!");
    }

    // set thông tin cho các UI element
    public void setInfo(Document doc, Date returnDate, int tranId) {
        try {
            Image image = ImageLoader.loadImage(doc.getUrlImage());
            docCover.setImage(image);
        } catch (Exception e) {
            System.err.println("docElement coverURL invalid! when add DocElementNode");
        }
        this.tranId = tranId;
        this.title.setText(doc.getTitle());
        this.author.setText(doc.getAuthor());
        this.genre.setText(doc.getGenre());
        this.returnDate.setText(returnDate.toString());
    }
}
