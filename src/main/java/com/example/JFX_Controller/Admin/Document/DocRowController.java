package com.example.JFX_Controller.Admin.Document;

import java.io.IOException;
import java.util.List;

import com.example.Model.Copies;
import com.example.Model.Document;
import com.example.Service.DocumentService;

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
        List<Copies> copies = DocumentService.instance.getAllCopies(doc.getDocumentId());
        docCopyVbox.setPrefHeight(copies.size() * 70);
        for (Copies c : copies) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/DocCopyRow.fxml"));
                Node node = loader.load();

                DocCopyController docCopyController = loader.getController();
                docCopyController.setInfo(c);
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
