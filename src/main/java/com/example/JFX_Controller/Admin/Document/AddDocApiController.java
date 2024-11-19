package com.example.JFX_Controller.Admin.Document;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import com.example.JFX_Controller.Admin.AdminController;

import javafx.event.ActionEvent;

public class AddDocApiController {

    @FXML
    private StackPane addDocRoot;

    @FXML
    private TextField searchField;

    @FXML
    void cancelAddDoc(ActionEvent event) {
        AdminController.instance.docPane.getChildren().remove(addDocRoot);
        addDocRoot = null;
    }

    @FXML
    void goSearch(ActionEvent event) {

    }

}