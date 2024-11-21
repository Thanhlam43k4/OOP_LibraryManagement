package com.example.JFX_Controller.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.sql.Date;

import com.example.Model.Document;

// Controller của từng thẻ Doc đang mượn ở tab MyDoc
public class TransCardController {
    @FXML private ImageView docCover;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label returnDate;

    @FXML private HBox root;

    @FXML
    void goReadDoc(ActionEvent event) {

    }

    @FXML
    void returnDoc(ActionEvent event) {
        
    }

    // set thông tin cho các UI element
    public void setInfo(Document doc, Date returnDate) {
        try {
            this.docCover.setImage(new Image(doc.getUrlImage()));
        } catch (Exception e) {
            System.err.println("docElement coverURL invalid! when add DocElementNode");
        }
        this.title.setText(doc.getTitle());
        this.author.setText(doc.getAuthor());
        this.genre.setText(doc.getGenre());
        this.returnDate.setText(returnDate.toString());
    }
}
