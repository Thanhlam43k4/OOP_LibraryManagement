package com.example.JFX_Controller.Admin;
//#region Lib
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.JFX_Controller.Controller;
import com.example.JFX_Controller.Admin.Document.DocRowController;
import com.example.JFX_Controller.Admin.User.UserRowController;
import com.example.Model.Client;
import com.example.Model.Document;
import com.example.Model.Transaction;
import com.example.Service.ApiService;
import com.example.Service.DocumentService;
import com.example.Service.SessionManager;
import com.example.Service.TransactionService;
import com.example.Service.UserService;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//#endregion

public class AdminController extends Controller implements Initializable {
    @FXML private Label userName;
    @FXML private TextField searchField;
    @FXML private ListView<String> suggestionsListView;
    // Tab button
    @FXML private HBox docsBut;
    @FXML private HBox usersBut;
    @FXML private HBox tranBut;
    // Docs
    @FXML public StackPane docPane;
    @FXML public StackPane copiesDocPane;
    @FXML public VBox docCopyVbox;
    @FXML public ListView<Parent> docListView;
    // Users
    @FXML public StackPane userPane;
    @FXML public StackPane returnDocPane;
    @FXML public VBox returnDocVbox;
    @FXML private ListView<Parent> userListView;
    // Transaction
    @FXML private AnchorPane tranPane;
    @FXML public VBox transVBox;

    private Timeline searchTimeline;
    public static ObservableList<Parent> docList = FXCollections.observableArrayList(); 
    private static ObservableList<Parent> userList = FXCollections.observableArrayList(); 
    public static List<Node> transList = new ArrayList<>();
    // singleton
    public static AdminController instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;

        userName.setText(SessionManager.getInstance().getLoggedInUser().getUsername());
        if (userList.isEmpty()) {
            System.out.println("Add admin node");
            addUserNodes();
            addDocNodes();
            addTranscNodes();
        }
        setupSearchFieldListener();
        setPane(docPane, docsBut);
        
        docListView.setItems(docList);
        userListView.setItems(userList);
        setVBox(transVBox, transList);
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
    void signOut(ActionEvent event)      { loadScene("Login.fxml"); 
                                           clearNode();
                                           SessionManager.getInstance().clearSession();}
    @FXML
    void closeDocCopy(ActionEvent event) { copiesDocPane.setVisible(false); }
    @FXML
    void closeReturnDoc(ActionEvent event) { returnDocPane.setVisible(false); }
    @FXML
    void openAddDoc(ActionEvent event)   { loadAddDoc("AddDoc.fxml"); }
    @FXML
    void openAddDocApi(ActionEvent event) { loadAddDoc("AddDocAPI.fxml"); }
    @FXML
    void openAddUser(ActionEvent event)  { loadAddUser(); }
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(); // ExecutorService với 1 luồng

    //#endregion
    
    //#region fe_func
    // bật/tắt Pane
    private void setPane(Parent pane, HBox tabBut) {
        Pane anchorPane = (Pane) pane.getParent();
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
    
    // load fxml Data
    private void addUserNodes() {
        userList.clear();
        List<Client> clients = UserService.instance.getAllClients();
        for (Client c : clients) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/UserRow.fxml"));
                Parent userNode = loader.load();
                UserRowController userRowController = loader.getController();
                userRowController.setInfo(c);
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
                Parent docNode = loader.load();
                DocRowController docRowController = (DocRowController) loader.getController();
                docRowController.setInfo(d, docNode);
                docList.add(docNode);
            } catch (IOException e) {
                e.printStackTrace();
            }   
        }
    }
    public void addTranscNodes() {
        transList.clear();
        List<Transaction> trans = TransactionService.instance.getAllTransaction();
        for (Transaction t : trans) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/TranRow.fxml"));
                Node transNode = loader.load();
                TransRowController transRowController = (TransRowController) loader.getController();
                transRowController.setInfo(t);
                transList.add(transNode);
            } catch (IOException e) {
                e.printStackTrace();
            }   
        }
    }

    // thêm data vào UI
    public void setVBox(VBox vbox, List<Node> list) {
        // clear vbox trừ node đầu tiên
        if (vbox.getChildren().size() > 1) {
            vbox.getChildren().subList(1, vbox.getChildren().size()).clear();
        }
        vbox.setPrefHeight(list.size() * 70 + 70);
        for (Node node : list) {
            vbox.getChildren().add(node);
        }
    }
    
    // mở pane add document và add user
    private void loadAddDoc(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/" + fxml));
            Parent addDocPane = loader.load();

            docPane.getChildren().add(addDocPane);
            // chỉnh stretch
            AnchorPane.setBottomAnchor(addDocPane, 0.0);
            AnchorPane.setLeftAnchor(addDocPane, 0.0);
            AnchorPane.setRightAnchor(addDocPane, 0.0);
            AnchorPane.setTopAnchor(addDocPane, 0.0);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // giải phóng
    private void clearNode() {
        instance = null;
        docList.clear();
        userList.clear();
        transList.clear();
    }


    // catch find textfield change
    private void setupSearchFieldListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Hủy Timeline trước đó nếu có
            if (searchTimeline != null) {
                searchTimeline.stop();
            }
            // Nếu trường tìm kiếm không trống
            if (!newValue.trim().isEmpty()) {
                // Tạo một mới Timeline với 2 giây trì hoãn
                searchTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> handleSearch()));
                searchTimeline.playFromStart(); // Bắt đầu chạy Timeline
            } else {
                suggestionsListView.getParent().setVisible(false); // Ẩn ListView nếu trường tìm kiếm trống
            }
        });
    } 
    @FXML
    private void handleSearch() {
        try {
            if (searchField == null || suggestionsListView == null) {
                System.err.println("searchField or suggestionList is null!");
                return;
            }

            String query = searchField.getText().trim();

            if (query.isEmpty()) {
                suggestionsListView.getParent().setVisible(false);
                return;
            }
            executorService.submit(() -> searchBooks(query));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void searchBooks(String query) {
        try {
            // Gọi API để tìm kiếm sách
            List<Document> documents = ApiService.searchBooks(query);

            ObservableList<String> suggestions = FXCollections.observableArrayList();
            for (Document document : documents) {
                suggestions.add(document.getTitle());
            }
            Platform.runLater(() -> {
                suggestionsListView.setItems(suggestions);
                suggestionsListView.getParent().setVisible(!suggestions.isEmpty());
            });

            suggestionsListView.setOnMouseClicked(event -> {
                String selectedDocument = suggestionsListView.getSelectionModel().getSelectedItem();
                if (selectedDocument != null) {
                    System.out.println(selectedDocument);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void shutdown() {
        executorService.shutdown(); // Đảm bảo dừng ExecutorService khi không còn sử dụng
    }
    //#endregion
}