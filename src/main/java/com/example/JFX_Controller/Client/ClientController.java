package com.example.JFX_Controller.Client;

//#region Lib
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.MainUI;
import com.example.JFX_Controller.Controller;
import com.example.Model.Document;
import com.example.Model.Transaction;
import com.example.Service.DocumentService;
import com.example.Service.TransactionService;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
//#endregion

public class ClientController extends Controller implements Initializable{
//UI
    @FXML private Label userName;
    // Tab button
    @FXML private HBox browseBut;
    @FXML private HBox mydocBut;
    // Browse Tab
    @FXML private ScrollPane browseScroll;
    @FXML private GridPane browseGrid;
    // MyDoc Tab
    @FXML private ScrollPane mydocScroll;
    @FXML private GridPane mydocGrid;

//Prop
    private static final int cardWidth = 185; // Chiều rộng phần tử sách + Hgap
    private static final int docElementWidth = 515;
    
    private static List<Node> cardList = new ArrayList<>();
    private static List<Node> docelementList = new ArrayList<>();
    
    private int currentCol = 0; // số cột hiện tại của grid

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(cardList.isEmpty()) {
            addCardNodes();
            addDocElementNodes();
        }

        setBrowse(true);
        setMyDoc(false);
        
        widthListener(); 
    }

    @FXML
    void browseTab(MouseEvent event) {
        setBrowse(true);
        setMyDoc(false);
    }
    @FXML
    void mydocTab(MouseEvent event) {
        setBrowse(false);
        setMyDoc(true);
    }
    @FXML
    void showSetting(ActionEvent event) { }
    @FXML
    void profile(ActionEvent event) { loadScene("Profile.fxml"); }
    @FXML
    void signOut(ActionEvent event) { loadScene("Login.fxml"); }
    //#region fe_func

    // catch SceneWidth change
    private void widthListener() {
        // bắt scene mới
        browseBut.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.widthProperty().addListener((obss, oldWidth, newWidth) -> {
                    if(browseScroll.isVisible()) {
                        int coll = (int) ((double)newWidth - 223)/cardWidth;
                        if(coll != currentCol) {
                            currentCol = coll;
                            updateGrid(browseGrid, cardList, currentCol);
                        }
                    }
                    else {
                        int coll = (int) ((double)newWidth - 223)/docElementWidth;
                        if(coll != currentCol) {
                            currentCol = coll;
                            updateGrid(mydocGrid, docelementList, currentCol);
                        }
                    }
                });        
            }
        });
    }

    // bật/tắt Pane
    private void setBrowse(boolean isActive) {
        browseScroll.setVisible(isActive);
        browseBut.getStyleClass().clear();
        if(!isActive) {
            browseBut.getStyleClass().add("hbox-style");
            return;
        }
        browseBut.getStyleClass().add("hbox-style-selected");
        int colCnt = (int) (MainUI.primaryStage.getScene().getWidth() - 223)/cardWidth;
        updateGrid(browseGrid, cardList, colCnt);
    }
    private void setMyDoc(boolean isActive) {
        mydocScroll.setVisible(isActive);        
        mydocBut.getStyleClass().clear();
        if(!isActive) {
            mydocBut.getStyleClass().add("hbox-style");
            return;
        }
        mydocBut.getStyleClass().add("hbox-style-selected");
        int colCnt = (int) (MainUI.primaryStage.getScene().getWidth() - 223)/docElementWidth;
        updateGrid(mydocGrid, docelementList, colCnt);
    }

    // tạo list
    private void addCardNodes() {
        cardList.clear();
        List<Document> docs = DocumentService.instance.getAllDocument();
        for (Document document : docs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Card.fxml"));
                Node bookNode = loader.load();
                
                CardController cardController = loader.getController();
                cardController.setInfo(document.getDocumentId(), "/DocCover_url/1586s.jpg", document.getTitle(), document.getGenre());
                cardList.add(bookNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void addDocElementNodes() {
        docelementList.clear();
        List<Transaction> transactions = TransactionService.instance.getTransactionsByUserId(2);
        for (Transaction transaction : transactions) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/DocElement.fxml"));
                Node bookNode = loader.load();
                
                Document docInfo = DocumentService.instance.getDocumentById(transaction.getDocumentId());
                TransCardController transCardController = loader.getController();
                transCardController.setInfo(transaction.getDocumentId(), "/DocCover_url/1586s.jpg", docInfo.getTitle(), "Bill Gate" ,docInfo.getGenre(), transaction.getReturnDate());
                docelementList.add(bookNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // thay đổi cột của grid
    private void updateGrid(GridPane grid, List<Node> list, int colCnt) {
        grid.getChildren().clear();
        int row = 0, col = 0;
        for (Node node : list) {
            grid.add(node, col, row);
            col++;
            if (col == colCnt) {
                col = 0;
                row++;
            }
        }
    }
    //#endregion
}