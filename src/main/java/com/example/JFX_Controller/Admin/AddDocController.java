package com.example.JFX_Controller.Admin;

import javafx.fxml.FXML;
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
        docPane.getChildren().remove(addDocRoot);
        addDocRoot = null;
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
