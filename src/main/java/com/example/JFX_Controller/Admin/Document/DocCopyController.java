package com.example.JFX_Controller.Admin.Document;

import com.example.Model.Copies;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for displaying the information of a document copy in the Admin panel.
 * This class is responsible for showing the details of a document copy including
 * its ID, title, ISBN, and status.
 */
public class DocCopyController {

    @FXML private Label docId;
    @FXML private Label title;
    @FXML private Label isbn;
    @FXML private FontAwesomeIconView iconState;
    @FXML private Label state;

    /**
     * Sets the information of a document copy in the corresponding UI elements.
     * This method populates the document copy's ID, title, ISBN, and its current
     * status (e.g., Available or Unavailable). It also changes the appearance of
     * the state text and icon based on the document copy's availability.
     *
     * @param c the document copy whose details are to be displayed.
     */
    public void setInfo(Copies c) {
        this.docId.setText(String.valueOf(c.getDocumentId()));  // Set document ID
        this.title.setText(c.getTitle());  // Set document title
        this.isbn.setText(c.getCopyIsbn());  // Set document ISBN
        this.state.setText(c.getStatus());  // Set document copy status

        // Change the text and icon colors based on the availability of the document copy
        if(c.getStatus().equals("Available")) {
            state.setStyle("-fx-text-fill: #317c47;");  // Green text for available state
            iconState.setStyle("-fx-fill: #317c47;");  // Green icon for available state
        } else {
            state.setStyle("-fx-text-fill: #9d3838;");  // Red text for unavailable state
            iconState.setStyle("-fx-fill: #9d3838;");  // Red icon for unavailable state
        }
    }
}
