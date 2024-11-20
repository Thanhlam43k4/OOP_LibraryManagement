package com.example.JFX_Controller.Admin.Document;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import com.example.Model.Document;

import java.io.IOException;

import com.example.Handlers.ImageLoader;
import com.example.JFX_Controller.Admin.AdminController;

import javafx.event.ActionEvent;

public class ApiDocCardController {
    @FXML private ImageView docCover;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label isbn;

    private Document doc;

    @FXML
    void addDocToLib(ActionEvent event) {
        loadAddDoc();
    }

    private void loadAddDoc() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/AddDoc.fxml"));
            Parent addDocPane = loader.load();

            AddDocController addDocController = loader.getController();
            addDocController.setInfo(doc);

            AdminController.instance.docPane.getChildren().add(addDocPane);
            // chỉnh stretch
            AnchorPane.setBottomAnchor(addDocPane, 0.0);
            AnchorPane.setLeftAnchor(addDocPane, 0.0);
            AnchorPane.setRightAnchor(addDocPane, 0.0);
            AnchorPane.setTopAnchor(addDocPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInfo(Document doc) {
        this.doc = doc;
        try {
            Image image = ImageLoader.loadImage(doc.getUrlImage());
            this.docCover.setImage(image);
        } catch (Exception e) {
            System.err.println("docElement coverURL invalid! when add DocElementNode");
        }
        this.title.setText(doc.getTitle());
        this.author.setText(doc.getAuthor());
        this.isbn.setText(doc.getISBN());
    }
}