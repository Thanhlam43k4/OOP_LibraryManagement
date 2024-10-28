package com.example.JFX_Controller.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DocCopyController {

    @FXML private Label docId;
    @FXML private Label title;
    @FXML private Label isbn;
    @FXML private Label state;

    public void setInfo(int docId, String title, String isbn, String state) {
        this.docId.setText(String.valueOf(docId));
        this.title.setText(title);
        this.isbn.setText(isbn);
        this.state.setText(state);
    }
}
