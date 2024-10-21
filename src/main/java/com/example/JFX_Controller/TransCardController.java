package com.example.JFX_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.Date;

public class TransCardController {
    @FXML private ImageView docCover;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label returnDate;

    private int id;

    @FXML
    void goReadDoc(ActionEvent event) {

    }
    @FXML
    void returnDoc(ActionEvent event) {

    }

    // set thông tin cho các UI element
    public void setInfo(int id, String imageUrl, String title, String author, String genre, Date returnDate) {
        this.id = id;
        this.docCover.setImage(new Image(imageUrl));
        this.title.setText(title);
        this.author.setText(author);
        this.genre.setText(genre);
        this.returnDate.setText("Expired: " + returnDate.toString());
    }
}
