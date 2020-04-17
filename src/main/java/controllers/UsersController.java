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
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;


/**
 * <b>Manages UsersPage.fxml</b>
 * <p>
 * Displays all users in a TableView which shows the username, password and type.
 *
 * <b>Only accessible by administrators</b>
 *
 * @author Sean Peters
 */
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
    @FXML
    JFXButton deleteButton;

    /**
     * <b>Executes when UsersPage.fxml is initialized</b>
     * <p>
     * Populates the TableView and changes its SelectionModel to MULTIPLE
     *
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        usersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setupTableView();
    }

    /**
     * Populates the TableView
     */
    private void setupTableView() {
        namesCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordsCol.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        typesCol.setCellValueFactory(new PropertyValueFactory<User, String>("type"));

        try {
            usersTable.setItems(getUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the users from the accounts text document
     *
     * @return ObservableList which contains all the users in accounts.txt
     * @throws IOException
     */
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

    /**
     * <b>Handles the add user event</b>
     * <p>
     * Grabs the data from the input areas to create a new user.
     * A User object is generated and a new line is added to the accounts document.
     * Refreshes the TableView to reflect the changes.
     *
     * @param event
     * @throws IOException
     */
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
        refreshTable();
    }

    /**
     * <b>Handles the remove user event</b>
     * <p>
     * Removes the user in the SelectionModel and prompts a confirm box.
     * If confirmed the user will be deleted and the TableView will refreshed to reflect the change.
     * The deleted user cannot be recovered.
     *
     * @param event
     * @throws IOException
     * @throws NoSuchElementException
     */
    public void removeUser(ActionEvent event) throws IOException, NoSuchElementException {
        ObservableList<User> users = usersTable.getSelectionModel().getSelectedItems();
        if (!users.isEmpty()) {
            References.CONFIRM_BOX.popOut();
            if (decision == true) {
                FileEditor fileEditor = new FileEditor();
                try {
                    for (User user : users) {
                        fileEditor.modifyFile(ACCOUNTS, user.toString(), "");
                        fileEditor.clearEmptyLines(ACCOUNTS);
                        refreshTable();
                    }
                } catch (NoSuchElementException e) {
                }
            }
        }
    }

    /**
     * Refreshes the TableView.
     */
    public void refreshTable() {
        setupTableView();
    }
}
