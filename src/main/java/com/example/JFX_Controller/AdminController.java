package com.example.JFX_Controller;
//#region Lib
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
//#endregion

public class AdminController extends Controller implements Initializable {
    @FXML private TextField searchField;
    @FXML private HBox docsBut;
    @FXML private HBox usersBut;
    // Docs
    @FXML private StackPane addDocPane;
    @FXML private AnchorPane docPane;
    @FXML private VBox docVBox;
    // Users
    @FXML private AnchorPane userPane;
    @FXML private VBox userVBox;

    private static List<Node> docList = new ArrayList<>();
    private static List<Node> userList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userList.isEmpty()) {
            addNodes(userList, "UserElement.fxml");
            addNodes(docList, "DocElement2.fxml");
        }
        setUser(true);
        setDoc(false);
        setVBox(userVBox, userList);
        setVBox(docVBox, docList);
    }

    @FXML
    void docsTab(MouseEvent event) {
        setDoc(true);
        setUser(false);
    }
    @FXML
    void usersTab(MouseEvent event) {
        setDoc(false);
        setUser(true);
    }
    @FXML
    void showSetting(ActionEvent event) {

    }
    @FXML
    void goToProfile(ActionEvent event) {
        loadScene("Profile.fxml");
    }
    @FXML
    void signOut(ActionEvent event) {
        loadScene("Login.fxml");
    }
    @FXML
    void openAddDoc(ActionEvent event) {
        addDocPane.setVisible(true);
    }
    @FXML
    void cancelAddDoc(ActionEvent event) {
        addDocPane.setVisible(false);
    }

    //#region fe_func
    // bật/tắt Pane
    private void setDoc(boolean isActive) {
        docPane.setVisible(isActive);
        docsBut.getStyleClass().clear();
        if(!isActive) {
            docsBut.getStyleClass().add("hbox-style");
            return;
        }
        docsBut.getStyleClass().add("hbox-style-selected");
        // int colCnt = (int) (MainUI.primaryStage.getScene().getWidth() - 223)/cardWidth;
        // updateGrid(browseGrid, cardList, colCnt);
    }
    private void setUser(boolean isActive) {
        userPane.setVisible(isActive);
        usersBut.getStyleClass().clear();
        if(!isActive) {
            usersBut.getStyleClass().add("hbox-style");
            return;
        }
        usersBut.getStyleClass().add("hbox-style-selected");
        // int colCnt = (int) (MainUI.primaryStage.getScene().getWidth() - 223)/cardWidth;
        // updateGrid(browseGrid, cardList, colCnt);
    }
    
    // tạo list
    private void addNodes(List<Node> UIlist, String fxml) {
        UIlist.clear();
        for (int i = 0; i < 10; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/" + fxml));
                Node bookNode = loader.load();
                UIlist.add(bookNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // cập nhật VBox
    private void setVBox(VBox vbox, List<Node> list) {
        //vbox.getChildren().clear();
        vbox.setPrefHeight(list.size() * 70 + 70);
        for (Node node : list) {
            vbox.getChildren().add(node);
        }
    }
    //#endregion
}