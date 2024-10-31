package com.example.JFX_Controller.Admin;

import java.io.IOException;

import com.example.Model.Document;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

// Controller của từng hàng trong tab DocManager
public class DocRowController {
    @FXML private Label docId;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label amount;

    @FXML private StackPane copiesDocPane;
    @FXML private VBox docCopyVbox;
    
    private Document doc;

    @FXML
    void showAllCopy(MouseEvent event) {
        addCopyNodes();
    }
    @FXML
    void openDocModify(ActionEvent event) {
        loadDocModify();
    }
    @FXML
    void deleteDoc(ActionEvent event) {

    }

    public void setInfo(Document d, StackPane copiesDocPane, VBox docCopyVBox) {
        doc = d;
        this.docId.setText(String.valueOf(d.getDocumentId()));
        this.title.setText(d.getTitle());
        this.author.setText(d.getAuthor());
        this.genre.setText(d.getGenre());
        this.amount.setText(String.valueOf(d.getNumberCopy()));
        this.copiesDocPane = copiesDocPane;
        this.docCopyVbox = docCopyVBox;
    }

    // có thể tối ưu sau
    void addCopyNodes() {
        copiesDocPane.setVisible(true);
        docCopyVbox.getChildren().clear();
        docCopyVbox.setPrefHeight(8 * 70);
        for (int i=0; i<8; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/DocCopyRow.fxml"));
                Node node = loader.load();
                docCopyVbox.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }   
        }
    }
    
    // mở pane chỉnh sửa Doc
    void loadDocModify() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/ModifyDoc.fxml"));
            Parent modifyDocPane = loader.load();

            // lấy docPane của admin
            AnchorPane docPane = (AnchorPane) copiesDocPane.getParent();
            docPane.getChildren().add(modifyDocPane);
            AnchorPane.setBottomAnchor(modifyDocPane, 0.0);
            AnchorPane.setLeftAnchor(modifyDocPane, 0.0);
            AnchorPane.setRightAnchor(modifyDocPane, 0.0);
            AnchorPane.setTopAnchor(modifyDocPane, 0.0);
            
            DocModifyController modifyController = loader.getController();
            modifyController.setInfo(doc, docPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
