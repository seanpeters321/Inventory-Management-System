package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import main.java.References;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * <b>Manages Main.fxml</b>
 * <p>
 * Displays the Order, Inventory, and Users pages on a TabPane.
 * Allows for the user to logout of the account, which returns them to the Login page.
 *
 * <b>If the user is an employee, the Users page will be hidden</b>
 *
 * @author Sean Peters
 */
public class MainController extends LoginController implements Initializable {
    // Scene FX:ID's
    @FXML
    protected TabPane tabPane;
    @FXML
    protected Tab orderTab, inventoryTab, usersTab;

    /**
     * <b>Executes when Main.fxml is initialized</b>
     * <p>
     * Disables and hides the User tab if the current user is an Employee.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (user.isEmployee) {
            usersTab.setDisable(true);
            usersTab.setText("");
        }
    }

    /**
     * <b>Handles the logout event</b>
     * <p>
     * Returns the user to the login page.
     *
     * @param event
     * @throws IOException
     */
    public void logout(ActionEvent event) throws IOException {
        References.LOGIN.goTo();
    }
}
