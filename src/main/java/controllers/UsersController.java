package main.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.FileEditor;
import main.java.References;
import main.java.User;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class UsersController extends MainController implements Initializable {
    // Scene FX:ID's
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
        usersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        namesCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordsCol.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        typesCol.setCellValueFactory(new PropertyValueFactory<User, String>("type"));

        try {
            usersTable.setItems(getUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<User> getUser() throws IOException {
        ObservableList<User> user = FXCollections.observableArrayList();
        User account;
        Scanner scan = new Scanner(new FileReader(ACCOUNTS));

        while (scan.hasNext()) {
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

    public void addUser(ActionEvent event) throws IOException {
        try {
            String account;
            String username = this.username.getText();
            String password = this.password.getText();
            String type = "";
            if (admin.isSelected())
                type = "admin";
            else if (employee.isSelected())
                type = "employee";

            account = username + "-" + password + "-" + type;

            FileEditor fileEditor = new FileEditor();
            fileEditor.addToFile(ACCOUNTS, account);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        References.USERS_PAGE.refresh();
    }

    public void removeUser(ActionEvent event) throws IOException {
        ObservableList<User> users = usersTable.getSelectionModel().getSelectedItems();
        FileEditor fileEditor = new FileEditor();
        for (User user : users) {
            fileEditor.modifyFile(ACCOUNTS, user.toString(), "");
        }
        References.USERS_PAGE.refresh();
    }

    public void goBack(ActionEvent event) throws IOException {
        References.MAIN_ADMIN.goTo();
    }
}
