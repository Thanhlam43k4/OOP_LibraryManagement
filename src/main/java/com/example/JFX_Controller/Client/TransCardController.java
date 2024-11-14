package com.example.JFX_Controller.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.Date;

// Controller của từng thẻ Doc đang mượn ở tab MyDoc
public class TransCardController {
    @FXML
    private ImageView docCover;
    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label genre;
    @FXML
    private Label returnDate;

    @FXML
    void goReadDoc(ActionEvent event) {

    }

    @FXML
    void returnDoc(ActionEvent event) {

    }

    // set thông tin cho các UI element
    public void setInfo(int id, String imageUrl, String title, String author, String genre, Date returnDate) {
        try {
            this.docCover.setImage(new Image(imageUrl));
        } catch (Exception e) {
            System.err.println("docElement coverURL invalid!");
        }
        this.title.setText(title);
        this.author.setText(author);
        this.genre.setText(genre);
        this.returnDate.setText(returnDate.toString());
    }
}
