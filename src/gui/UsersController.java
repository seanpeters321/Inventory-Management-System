package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.FileEditor;
import main.User;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UsersController implements Initializable {
    @FXML
    JFXTextField username;
    @FXML
    JFXTextField password;
    @FXML
    JFXRadioButton employee;
    @FXML
    JFXRadioButton admin;
    @FXML
    JFXButton addUserButton;
    @FXML
    TableView<User> usersTable;
    @FXML
    TableColumn<User, String> namesCol;
    @FXML
    TableColumn<User, String> passwordsCol;
    @FXML
    TableColumn<User, String> typesCol;



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        namesCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordsCol.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        typesCol.setCellValueFactory(new PropertyValueFactory<User, String>("type"));

        try{
            usersTable.setItems(getUser());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ObservableList<User> getUser() throws IOException{
        String filePath = "C:\\Users\\Sean\\IdeaProjects\\Metels Inc. Inventory Management System\\src\\resources\\txt\\accounts.txt";
        ObservableList<User> user = FXCollections.observableArrayList();
        User account;
        Scanner scan = new Scanner(new FileReader(filePath));

        while(scan.hasNext()){
            String string = scan.next();

            String[] output = string.split("-");

            String username = output[0];
            String password = output[1];
            String type = output[2];

            account = new User(username, password, type);
            user.add(account);
        }
        scan.close();
        return user;
    }

    public void addUser(ActionEvent event) throws IOException{
        try{
            String account;
            String username = this.username.getText();
            String password = this.password.getText();
            String type = "";
            if(admin.isSelected())
                type = "admin";
            else if (employee.isSelected())
                type = "employee";

            account = username + "-" + password + "-" + type;

            FileEditor fileEditor = new FileEditor();
            fileEditor.addToFile("C:\\Users\\Sean\\IdeaProjects\\Metels Inc. Inventory Management System\\src\\resources\\txt\\accounts.txt", account);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        Parent page = FXMLLoader.load(getClass().getClassLoader().getResource("\\gui/UsersPage.fxml"));
        Scene npage = new Scene(page);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(npage);
        window.show();
    }

    public void goBack(ActionEvent event) throws IOException {
        //grabs scene source
        Parent page = FXMLLoader.load(getClass().getResource("/gui/MainAdmin.fxml"));
        Scene npage = new Scene(page);
        //grabs main stage object
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(npage);
        window.setMaximized(true);
        window.show();
    }
}
