package com.example.JFX_Controller.Client;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.example.Model.Document;

import javafx.event.ActionEvent;

public class ReadDocController {

    @FXML private Label content;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private ImageView docCover;

    private Parent root;
    @FXML
    void backToMain(ActionEvent event) {
        ClientController.instance.root.getChildren().remove(root);
        root = null;
    }

    public void setInfo(Parent root, Document doc, Image coverImage) {
        this.root = root;
        try {
            docCover.setImage(coverImage);
        } catch (Exception e) {
            System.err.println("can't load docCover_url in ReadDocController");
        }
        title.setText(doc.getTitle().toUpperCase());
        author.setText(doc.getAuthor());
        genre.setText(doc.getGenre());
    }
}