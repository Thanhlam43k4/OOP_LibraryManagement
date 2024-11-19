package com.example.JFX_Controller.Admin.Document;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import com.example.JFX_Controller.Admin.AdminController;

import javafx.event.ActionEvent;

public class AddDocApiController {

    @FXML private StackPane addDocRoot;
    @FXML private TextField searchField;
    @FXML private ListView<Parent> suggestList;

    @FXML
    void cancelAddDoc(ActionEvent event) {
        AdminController.instance.docPane.getChildren().remove(addDocRoot);
        addDocRoot = null;
    }

    @FXML
    void goSearch(ActionEvent event) {
        for (int i = 0; i < 4; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/ApiDocCard.fxml"));
                Parent docNode = loader.load();
                suggestList.getItems().add(docNode);
            } catch (IOException e) {
                e.printStackTrace();
            }   
        }
    }

}