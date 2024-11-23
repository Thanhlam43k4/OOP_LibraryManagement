package com.example.JFX_Controller.Client;

import java.net.URL;
import java.util.ResourceBundle;

//#region Lib
import com.example.Handlers.Notify;
import com.example.Model.Copies;
import com.example.Service.DocumentService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import com.example.JFX_Controller.Controller;
import com.example.Model.Document;
import com.example.Service.SessionManager;
import com.example.Service.TransactionService;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

//#endregion

public class DocInfoController extends Controller implements Initializable {
    @FXML private Label userName;
    @FXML private ImageView docCover;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label description;
    @FXML private Label stateText;
    
    @FXML private Button borrowBut;
    @FXML private ScrollPane docScroll;
    private Parent root;

    private int docId;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBorrowBut();
    }
    @FXML
    void backMain(ActionEvent event) {
        backToClient();
    }
    @FXML
    void borrowDoc(ActionEvent event) {
        int userId = SessionManager.getInstance().getLoggedInUser().getId();
        Copies copyDoc = DocumentService.instance.getAvailCopies(docId);
        if(copyDoc == null){
            Notify.showAlert(Alert.AlertType.ERROR, "Error when borrow Book", "This Book isn't having available copies now!!! Please choose another Book");
            return;
        }else{
            TransactionService.instance.borrowBook(userId,copyDoc.getCopyISBN());
            //ui
            ClientController.instance.addDocElementNodes();
            borrowBut.setDisable(true);
            stateText.setVisible(true);
        }
    }
    @FXML
    void signOut(ActionEvent event) {
        SessionManager.getInstance().clearSession();
        ClientController.instance.clearNode();
        loadScene("Login.fxml");
    }

    private void backToClient() {
        Scene scene = docScroll.getScene();
        AnchorPane clientRoot = (AnchorPane) scene.getRoot();
    
        Timeline timeline = new Timeline();
        KeyValue kvOut = new KeyValue(root.translateXProperty(), scene.getWidth(), Interpolator.EASE_IN);
        KeyFrame kfOut = new KeyFrame(Duration.seconds(0.35), kvOut);
        timeline.getKeyFrames().add(kfOut);

        timeline.setOnFinished(e -> {
            clientRoot.getChildren().remove(root);
        });        
        timeline.play();
    }
    public void setInfo(Document doc, Parent root, Image coverImage) {
        this.root = root;
        docId = doc.getDocumentId();
        docCover.setImage(coverImage);
        this.userName.setText(SessionManager.getInstance().getLoggedInUser().getUsername());
        this.title.setText(doc.getTitle().toUpperCase());
        this.author.setText(doc.getAuthor());
        description.setText(doc.getDescription());
        // query description here
        // check borrow
        setBorrowBut();
    }
    private void setBorrowBut() {
        // query transaction to find if doc borrwed by user then disable button and show text
        
    }
}
