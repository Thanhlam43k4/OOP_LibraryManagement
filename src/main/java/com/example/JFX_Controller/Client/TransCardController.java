package com.example.JFX_Controller.Client;

import com.example.MainUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.Date;

import com.example.Handlers.ImageLoader;
import com.example.Handlers.Notify;
import com.example.Model.Document;
import com.example.Service.SessionManager;
import com.example.Service.TransactionService;

import static com.example.JFX_Controller.Client.ClientController.docElementWidth;

// Controller của từng thẻ Doc đang mượn ở tab MyDoc
public class TransCardController {
    @FXML private ImageView docCover;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label returnDate;

    @FXML private HBox root;

    private Document doc;
    private Image coverImage;

    String ISBN;
    @FXML
    void goReadDoc(ActionEvent event) {
        try {
            // create docinfo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/ReadDoc.fxml"));
            Parent readDocRoot = loader.load();

            ReadDocController readDocController = loader.getController();
            readDocController.setInfo(readDocRoot, doc, coverImage);

            // fix docinfo size
            //AnchorPane pane = (AnchorPane) docinfoRoot;
            AnchorPane.setBottomAnchor(readDocRoot, 0.0);
            AnchorPane.setLeftAnchor(readDocRoot, 0.0);
            AnchorPane.setRightAnchor(readDocRoot, 0.0);
            AnchorPane.setTopAnchor(readDocRoot, 0.0);

            // set docinfo position
            ClientController.instance.root.getChildren().add(readDocRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void returnDoc(ActionEvent event) {
        int userId = SessionManager.getInstance().getLoggedInUser().getId();
        TransactionService.instance.returnBook(userId, ISBN);
        //ui

        ClientController.docelementList.remove(root);
        GridPane mydocGrid = ClientController.instance.mydocGrid;
        int colCnt = (int) (MainUI.primaryStage.getScene().getWidth() - 350)/docElementWidth;

        ClientController.instance.updateGrid(mydocGrid, ClientController.docelementList, colCnt);
        Notify.showAlert(Alert.AlertType.INFORMATION, "Nofication", "Return Document sucess!");
    }

    // set thông tin cho các UI element
    public void setInfo(Document doc, Date returnDate, String ISBN) {
        this.doc = doc;
        try {
            Image image = ImageLoader.loadImage(doc.getUrlImage());
            coverImage = image;
            docCover.setImage(image);
        } catch (Exception e) {
            System.err.println("docElement coverURL invalid! when add DocElementNode");
        }
        this.ISBN = ISBN;
        this.title.setText(doc.getTitle());
        this.author.setText(doc.getAuthor());
        this.genre.setText(doc.getGenre());
        this.returnDate.setText(returnDate.toString());
    }
}
