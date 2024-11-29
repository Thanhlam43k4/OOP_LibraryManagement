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
import com.example.Handlers.ImageLoader;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//#endregion


/**
 * Controller class for managing Card UI components.
 * Responsible for displaying card information and handling user interactions.
 */
public class CardController extends Controller {

    @FXML
    private VBox card;

    @FXML
    private ImageView docCover;

    @FXML
    private Label name;

    @FXML
    private Label genre;

    private Document doc;
    private Image coverImage;

    private boolean canLoad = true;

    /**
     * Handles the selection of a card.
     * If loading is allowed, it will load the document information.
     *
     * @param event The mouse event triggered by clicking the card.
     */
    @FXML
    private void selectCard(MouseEvent event) {
        if (canLoad) {
            loadDocInfo();
            canLoad = false;
        }
    }

    /**
     * Loads the document information and adds it to the client with animation.
     * The animation slides the new view into position.
     */
    private void loadDocInfo() {
        try {
            // Load DocInfo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/DocInfo.fxml"));
            Parent docInfoRoot = loader.load();
            Scene scene = card.getScene();

            // Configure DocInfoController
            DocInfoController docInfoController = loader.getController();
            docInfoController.setInfo(doc, docInfoRoot, coverImage);

            // Fix DocInfo size and anchors
            AnchorPane pane = (AnchorPane) docInfoRoot;
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setTopAnchor(pane, 0.0);

            // Set initial position for animation
            docInfoRoot.translateXProperty().set(scene.getWidth());
            AnchorPane clientRoot = (AnchorPane) scene.getRoot();
            clientRoot.getChildren().add(docInfoRoot);

            // Setup slide-in animation
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(docInfoRoot.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.35), kv);
            timeline.getKeyFrames().add(kf);

            timeline.setOnFinished(e -> canLoad = true);
            timeline.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the information of the card, including document details and cover image.
     *
     * @param doc The document to display in the card.
     */
    public void setInfo(Document doc) {
        this.doc = doc;
        try {
            Image image = ImageLoader.loadImage(doc.getUrlImage());
            coverImage = image;
            docCover.setImage(image);
        } catch (Exception e) {
            System.err.println("Card cover URL invalid when adding CardNode.");
        }
        this.name.setText(doc.getTitle());
        this.genre.setText(doc.getGenre());
    }
}