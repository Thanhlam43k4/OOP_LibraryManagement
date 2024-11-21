package com.example.JFX_Controller.Admin;
//#region Lib
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.example.JFX_Controller.Controller;
import com.example.JFX_Controller.Admin.Document.DocRowController;
import com.example.JFX_Controller.Admin.User.UserRowController;
import com.example.Model.Client;
import com.example.Model.Document;
import com.example.Model.Transaction;
import com.example.Service.DocumentService;
import com.example.Service.SessionManager;
import com.example.Service.TransactionService;
import com.example.Service.UserService;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
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
//#endregion

public class AdminController extends Controller implements Initializable {
    @FXML private Label userName;
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> searchChoice;
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
    @FXML private ListView<Parent> transListView;

    private String[] docSearchOp = {"title", "author", "genre"};
    private String[] userSearchOp = {"userName", "email", "phone"};
    private String[] transSearchOp = {"userId", "docISBN", "returnDate"};
    private String[] currentSearchOp = docSearchOp;
    public static ObservableList<Parent> docList = FXCollections.observableArrayList(); 
    private static ObservableList<Parent> userList = FXCollections.observableArrayList(); 
    public static ObservableList<Parent> transList = FXCollections.observableArrayList(); 
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
        setPane(docPane, docsBut, docFilterList, docSearchOp);
        
        docListView.setItems(docFilterList);
        userListView.setItems(userFilterList);
        transListView.setItems(transFilterList);
    }
    //#region event handle
    @FXML
    void docsTab(MouseEvent event)       { setPane(docPane, docsBut, docFilterList, docSearchOp); }
    @FXML
    void usersTab(MouseEvent event)      { setPane(userPane, usersBut, userFilterList, userSearchOp); }
    @FXML
    void transTab(MouseEvent event)      { setPane(tranPane, tranBut, transFilterList, transSearchOp); }
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

    //#endregion
    
    //#region fe_func
    // bật/tắt Pane
    private void setPane(Parent pane, HBox tabBut, FilteredList<Parent> filterList, String[] searchOption) {
        Pane anchorPane = (Pane) pane.getParent();
        searchField.setText(null);
        searchChoice.getItems().removeAll(currentSearchOp);
        currentSearchOp = searchOption;
        searchChoice.getItems().addAll(currentSearchOp);
        searchChoice.setValue(currentSearchOp[0]);
        searchFieldListener(filterList);
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
                Parent transNode = loader.load();
                TransRowController transRowController = (TransRowController) loader.getController();
                transRowController.setInfo(t);
                transList.add(transNode);
            } catch (IOException e) {
                e.printStackTrace();
            }   
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
 
    ChangeListener<String> currentListener = null;
    FilteredList<Parent> docFilterList = new FilteredList<>(docList, s -> true);
    FilteredList<Parent> userFilterList = new FilteredList<>(userList, s -> true);
    FilteredList<Parent> transFilterList = new FilteredList<>(transList, s -> true);
    
    // thay đổi listener
    private void searchFieldListener(FilteredList<Parent> filterList) {
        if(currentListener != null) searchField.textProperty().removeListener(currentListener);

        currentListener = (observable, oldValue, newValue) -> {
            filterList.setPredicate(parent -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả nếu không có gì được nhập
                }
                String lowerCaseFilter = newValue.toLowerCase();

                try {
                    // Lấy Label trong Parent và kiểm tra text
                    Label label = (Label) parent.lookup("#" + searchChoice.getValue());
                    if (label != null) {
                        return label.getText().toLowerCase().contains(lowerCaseFilter);
                    } else {
                        return false; // Trả về false nếu không tìm thấy Label
                    }
                } catch (NullPointerException e) {
                    // Nếu gặp NullPointerException, trả về false mà không làm gián đoạn ứng dụng
                    return false; // Bạn có thể thay đổi giá trị trả về nếu cần
                }
            });
        };
        searchField.textProperty().addListener(currentListener);
    }
}