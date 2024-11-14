package com.example.JFX_Controller.Client;

//#region Lib
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;

import com.example.JFX_Controller.Controller;
import com.example.Model.Document;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//#endregion

// Controller của từng thẻ Doc trong tab Browse
public class CardController extends Controller {
    @FXML
    private VBox card;
    @FXML
    private ImageView docCover;
    @FXML
    private Label name;
    @FXML
    private Label genre;

    private int id;
    private String imageUrl;

    boolean canLoad = true;

    @FXML
    void selectCard(MouseEvent event) {
        if (canLoad) {
            loadDocInfo();
            canLoad = false;
        }
    }

    // add DocInfo in Client with animation
    private void loadDocInfo() {
        try {
            // create docinfo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/DocInfo.fxml"));
            Parent docinfoRoot = loader.load();
            Scene scene = card.getScene();

            DocInfoController docInfoController = loader.getController();
            docInfoController.setInfo(id, imageUrl, name.getText(), genre.getText());

            // fix docinfo size
            AnchorPane pane = (AnchorPane) docinfoRoot;
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setTopAnchor(pane, 0.0);

            // set docinfo position
            docinfoRoot.translateXProperty().set(scene.getWidth());
            AnchorPane clientRoot = (AnchorPane) scene.getRoot();
            clientRoot.getChildren().add(docinfoRoot);

            // setup animation
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(docinfoRoot.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.35), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(e -> {
                canLoad = true;
            });
            timeline.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInfo(Document doc) {
        try {
            docCover.setImage(new Image(doc.getUrlImage()));
        } catch (Exception e) {
            System.err.println("card coverURL invalid! when add CardNode");
        }
        this.id = doc.getDocumentId();
        this.imageUrl = doc.getUrlImage();
        this.name.setText(doc.getTitle());
        this.genre.setText(doc.getGenre());
    }
}
