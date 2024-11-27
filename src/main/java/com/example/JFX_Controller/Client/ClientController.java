package com.example.JFX_Controller.Client;

//#region Lib
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.MainUI;
import com.example.JFX_Controller.Controller;
import com.example.JFX_Controller.ProfileController;
import com.example.Model.Document;
import com.example.Model.Transaction;
import com.example.Service.DocumentService;
import com.example.Service.SessionManager;
import com.example.Service.TransactionService;
import com.example.Handlers.ExtraFunction;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
//#endregion
import javafx.scene.control.TextField;

public class ClientController extends Controller implements Initializable{
//UI
    @FXML public AnchorPane root;
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
    private static final int cardWidth = 240; // Chiều rộng phần tử sách + Hgap
    private static final int docElementWidth = 615;
    
    private static ObservableList<Parent> cardList = FXCollections.observableArrayList();
    public static List<Parent> docelementList = new ArrayList<>();
    
    public int currentCol = 0; // số cột hiện tại của grid

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
        searchFieldListener();

        updateGrid.run();
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
        loadProfile();
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
                        int coll = (int) ((double)newWidth - 350)/cardWidth;
                        if(coll != currentCol) {
                            currentCol = coll;
                            updateGrid(browseGrid, cardList, currentCol);
                        }
                    }
                    else {
                        int coll = (int) ((double)newWidth - 350)/docElementWidth;
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
        searchField.setText(null);
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
                Parent bookNode = loader.load();
                
                CardController cardController = loader.getController();
                cardController.setInfo(document);
                cardList.add(bookNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void addDocElementNodes() {
        docelementList.clear();
        List<Transaction> transactions = TransactionService.instance.getTransactionsByUserId(SessionManager.getInstance().getLoggedInUser().getId());
        for (Transaction transaction : transactions) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/DocElement.fxml"));
                Parent bookNode = loader.load();
                String ISBN = ExtraFunction.extractISBN(transaction.getISBN());

                Document docInfo  = DocumentService.instance.getDocumentByISBN(ISBN);
                TransCardController transCardController = loader.getController();
                transCardController.setInfo(docInfo, transaction.getReturnDate(), transaction.getTransactionId());
                docelementList.add(bookNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // thay đổi cột của grid
    public void updateGrid(GridPane grid, List<Parent> list, int colCnt) {
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
    
    private void loadProfile() {
        try {
            // create docinfo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Profile.fxml"));
            Parent profileRoot = loader.load();

            ProfileController profileController = loader.getController();
            profileController.setInfo(root, profileRoot);

            // fix docinfo size
            AnchorPane.setBottomAnchor(profileRoot, 0.0);
            AnchorPane.setLeftAnchor(profileRoot, 0.0);
            AnchorPane.setRightAnchor(profileRoot, 0.0);
            AnchorPane.setTopAnchor(profileRoot, 0.0);

            // set docinfo position
            ClientController.instance.root.getChildren().add(profileRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearNode() {
        docelementList.clear();
        cardList.clear();
    }

    FilteredList<Parent> docFilterList = new FilteredList<>(cardList, s -> true);
    private void searchFieldListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            docFilterList.setPredicate(parent -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả nếu không có gì được nhập
                }
                String lowerCaseFilter = newValue.toLowerCase();

                try {
                    // Lấy Label trong Parent và kiểm tra text
                    Label label = (Label) parent.lookup("#name");
                    if (label != null) {
                        return label.getText().toLowerCase().contains(lowerCaseFilter);
                    } else {
                        return false; // Trả về false nếu không tìm thấy Label
                    }
                } catch (NullPointerException e) {
                    return false; // Bạn có thể thay đổi giá trị trả về nếu cần
                }
            });
            updateGrid.run();
        });
    }

    Runnable updateGrid = () -> {
        browseGrid.getChildren().clear(); // Xóa kết quả cũ
        int column = 0, row = 0;
        int colCnt = (int) (browseScroll.getWidth())/cardWidth;
        // Thêm các mục phù hợp vào GridPane
        for (Parent item : docFilterList) {
            browseGrid.add(item, column, row);

            column++;
            if (column >= colCnt) { // Hiển thị tối đa 3 cột mỗi hàng
                column = 0;
                row++;
            }
        }
    };
    //#endregion
}