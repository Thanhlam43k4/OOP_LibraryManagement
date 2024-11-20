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
import com.example.Service.SessionManager;
import com.example.Service.TransactionService;
import com.example.Handlers.ExtraFunction;
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
import javafx.scene.control.TextField;

public class ClientController extends Controller implements Initializable{
//UI
    @FXML private Label userName;
    @FXML private TextField searchField;
    // Tab button
    @FXML private HBox browseBut;
    @FXML private HBox mydocBut;
    // Browse Tab
    @FXML private ScrollPane browseScroll;
    @FXML private GridPane browseGrid;
    // MyDoc Tab
    @FXML private ScrollPane mydocScroll;
    @FXML public GridPane mydocGrid;

//Prop
    private static final int cardWidth = 200; // Chiều rộng phần tử sách + Hgap
    private static final int docElementWidth = 495;
    
    private static List<Node> cardList = new ArrayList<>();
    private static List<Node> docelementList = new ArrayList<>();
    
    private int currentCol = 0; // số cột hiện tại của grid

    public static ClientController instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;

        userName.setText(SessionManager.getInstance().getLoggedInUser().getUsername());
        if(cardList.isEmpty()) {
            System.out.println("Add client node");
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
    void profile(ActionEvent event) { 
        loadScene("Profile.fxml"); 
    }
    @FXML
    void signOut(ActionEvent event) { 
        loadScene("Login.fxml"); 
        clearNode(); 
        SessionManager.getInstance().clearSession();
    }
    //#region fe_func

    // catch SceneWidth change
    private void widthListener() {
        // bắt scene mới
        browseBut.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.widthProperty().addListener((obss, oldWidth, newWidth) -> {
                    if(browseScroll.getParent().isVisible()) {
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
        browseScroll.getParent().setVisible(isActive);
        browseBut.getStyleClass().clear();
        if(!isActive) {
            browseBut.getStyleClass().add("second-hbox-style");
            return;
        }
        browseBut.getStyleClass().add("second-hbox-style-selected");
        int colCnt = (int) (browseScroll.getWidth())/cardWidth;
        updateGrid(browseGrid, cardList, colCnt);
    }
    private void setMyDoc(boolean isActive) {
        mydocScroll.getParent().setVisible(isActive);        
        mydocBut.getStyleClass().clear();
        if(!isActive) {
            mydocBut.getStyleClass().add("second-hbox-style");
            return;
        }
        mydocBut.getStyleClass().add("second-hbox-style-selected");
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
                cardController.setInfo(document);
                cardList.add(bookNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void addDocElementNodes() {
        docelementList.clear();
        List<Transaction> transactions = TransactionService.instance.getTransactionsByUserId(SessionManager.getInstance().getLoggedInUser().getId());
        for (Transaction transaction : transactions) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/DocElement.fxml"));
                Node bookNode = loader.load();
                String ISBN = ExtraFunction.extractISBN(transaction.getISBN());

                Document docInfo  = DocumentService.instance.getDocumentByISBN(ISBN);
                TransCardController transCardController = loader.getController();
                transCardController.setInfo(docInfo, transaction.getReturnDate());
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
    
    private void clearNode() {
        docelementList.clear();
        cardList.clear();
    }
    //#endregion
}