package com.example.JFX_Controller.Admin.Document;

import com.example.Model.Copies;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DocCopyController {

    @FXML private Label docId;
    @FXML private Label title;
    @FXML private Label isbn;
    @FXML private FontAwesomeIconView iconState;
    @FXML private Label state;

    public void setInfo(Copies c) {
        this.docId.setText(String.valueOf(c.getDocumentId()));
        this.title.setText(c.getTitle());
        this.isbn.setText(c.getCopyISBN());
        this.state.setText(c.getStatus());
        if(c.getStatus().equals("Available")) {
            state.setStyle("-fx-text-fill: #317c47;");
            iconState.setStyle("-fx-fill: #317c47;");
        }
        else {
            state.setStyle("-fx-text-fill: #9d3838;");
            iconState.setStyle("-fx-fill: #9d3838;");
        }
    }
}
