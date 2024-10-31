package com.example.JFX_Controller.Admin;
//#region Lib
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.JFX_Controller.Controller;
import com.example.Model.Client;
import com.example.Model.Document;
import com.example.Model.Transaction;
import com.example.Service.DocumentService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
//#endregion

public class AdminController extends Controller implements Initializable {
    @FXML private Label userName;
    @FXML private TextField searchField;
    // Tab button
    @FXML private HBox docsBut;
    @FXML private HBox usersBut;
    @FXML private HBox tranBut;
    // Docs
    @FXML private AnchorPane docPane;
    @FXML private StackPane copiesDocPane;
    @FXML private VBox docCopyVbox;
    @FXML private VBox docVBox;
    // Users
    @FXML private AnchorPane userPane;
    @FXML private VBox userVBox;
    // Transaction
    @FXML private AnchorPane tranPane;
    @FXML private TableView<Transaction> tranTable;

    private static List<Node> docList = new ArrayList<>();
    private static List<Node> userList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userList.isEmpty()) {
            addUserNodes();
            addDocNodes();
        }
        setPane(docPane, docsBut);
        
        setVBox(userVBox, userList);
        setVBox(docVBox, docList);
        setTranTable();
    }
    //#region event handle
    @FXML
    void docsTab(MouseEvent event)       { setPane(docPane, docsBut); }
    @FXML
    void usersTab(MouseEvent event)      { setPane(userPane, usersBut); }
    @FXML
    void transTab(MouseEvent event)      { setPane(tranPane, tranBut); }
    @FXML
    void showSetting(ActionEvent event)  { }
    @FXML
    void goToProfile(ActionEvent event)  { loadScene("Profile.fxml"); }
    @FXML
    void signOut(ActionEvent event)      { loadScene("Login.fxml"); }
    @FXML
    void closeDocCopy(ActionEvent event) { copiesDocPane.setVisible(false); }
    @FXML
    void openAddDoc(ActionEvent event)   { loadAddDoc(); }
    @FXML
    void openAddUser(ActionEvent event)  { loadAddUser(); }
    //#endregion
    
    //#region fe_func
    // bật/tắt Pane
    private void setPane(AnchorPane pane, HBox tabBut) {
        AnchorPane anchorPane = (AnchorPane) pane.getParent();
        for (Node child : anchorPane.getChildren()) {
            child.setVisible(false);
        }
        pane.setVisible(true);
        setStyleTabButton(tabBut);
    }
    private void setStyleTabButton(HBox selectedBut) {
        docsBut.getStyleClass().clear();
        usersBut.getStyleClass().clear();
        tranBut.getStyleClass().clear();

        docsBut.getStyleClass().add("hbox-style");
        usersBut.getStyleClass().add("hbox-style");
        tranBut.getStyleClass().add("hbox-style");

        selectedBut.getStyleClass().add("hbox-style-selected");
    }
    
    // load Data
    private void addUserNodes() {
        userList.clear();
        for (int i = 0; i < 10; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/UserRow.fxml"));
                Node userNode = loader.load();
                // AnchorPane pane = (AnchorPane) bookNode;
                // pane.getHeight();
                UserRowController userRowController = loader.getController();
                userRowController.setInfo(new Client("123@", "tanh", "01234", 12), userPane);
                userList.add(userNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void addDocNodes() {
        docList.clear();
        List<Document> docs = DocumentService.instance.getAllDocument();
        for (Document d : docs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/DocRow.fxml"));
                Node docNode = loader.load();
                DocRowController docRowController = (DocRowController) loader.getController();
                docRowController.setInfo(d, copiesDocPane, docCopyVbox);
                docList.add(docNode);
            } catch (IOException e) {
                e.printStackTrace();
            }   
        }
    }

    // thêm data vào UI
    private void setVBox(VBox vbox, List<Node> list) {
        //vbox.getChildren().clear();
        vbox.setPrefHeight(list.size() * 70 + 70);
        for (Node node : list) {
            vbox.getChildren().add(node);
        }
    }
    private void setTranTable() {
        TableColumn<Transaction, ?> tranIdCol = tranTable.getColumns().get(0); // Cột đầu tiên
        TableColumn<Transaction, ?> userIdCol = tranTable.getColumns().get(1); // Cột thứ hai
        TableColumn<Transaction, ?> docIdCol = tranTable.getColumns().get(2);
        TableColumn<Transaction, ?> borrowedDateCol = tranTable.getColumns().get(3);
        TableColumn<Transaction, ?> dueDateCol = tranTable.getColumns().get(4);
    
        tranIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        docIdCol.setCellValueFactory(new PropertyValueFactory<>("documentId"));
        borrowedDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    
        ObservableList<Transaction> books = FXCollections.observableArrayList(
            new Transaction(1, 2),
            new Transaction(2, 5),
            new Transaction(8, 7)
        );

        tranTable.setItems(books);
    }
    
    // mở pane add document và add user
    private void loadAddDoc() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/AddDoc.fxml"));
            Parent addDocPane = loader.load();

            docPane.getChildren().add(addDocPane);
            // chỉnh stretch
            AnchorPane.setBottomAnchor(addDocPane, 0.0);
            AnchorPane.setLeftAnchor(addDocPane, 0.0);
            AnchorPane.setRightAnchor(addDocPane, 0.0);
            AnchorPane.setTopAnchor(addDocPane, 0.0);
            
            AddDocController addDocController = loader.getController();
            addDocController.setInfo(docPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadAddUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/AddUser.fxml"));
            Parent addUserPane = loader.load();

            userPane.getChildren().add(addUserPane);
            // chỉnh stretch
            AnchorPane.setBottomAnchor(addUserPane, 0.0);
            AnchorPane.setLeftAnchor(addUserPane, 0.0);
            AnchorPane.setRightAnchor(addUserPane, 0.0);
            AnchorPane.setTopAnchor(addUserPane, 0.0);
            
            AddUserController addUserController = loader.getController();
            addUserController.setInfo(userPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //#endregion
}