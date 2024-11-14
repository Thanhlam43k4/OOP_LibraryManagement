package com.example.JFX_Controller.Admin;

import java.sql.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReturnCopyRowController {
    @FXML private Label docId;
    @FXML private Label title;
    @FXML private Label isbn;
    @FXML private Label dueDate;

    public void setInfo(int docId, String title, String isbn, Date dueDate) {
        this.docId.setText(String.valueOf(docId));
        this.isbn.setText(isbn);
        //this.title.setText(title);
        try {
            this.dueDate.setText(dueDate.toString());            
        } catch (Exception e) {
            System.out.println("no dueDate in returnCopyRow!");
        }
    }
}
