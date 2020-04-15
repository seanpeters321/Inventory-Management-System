package main.java.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.java.FileSearcher;
import main.java.References;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;


/**
 * Manages MainAdmin.fxml
 *
 * @author Sean
 */
public class MainController extends LoginController implements Initializable {
    // Scene FX:ID's
    @FXML
    private JFXButton orderButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Changes scene to Login.fxml
     *
     * @param event
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException {
        References.LOGIN.goTo();
    }

    /**
     * Changes scene to InventorySheetEmployee.fxml
     *
     * @param event
     * @throws IOException
     */
    public void goToInventorySheet(ActionEvent event) throws IOException {
        if (user.isAdmin)
            References.INVENTORY_SHEET_ADMIN.goTo();
        else if (user.isEmployee)
            References.INVENTORY_SHEET_EMPLOYEE.goTo();
    }

    /**
     * Changes scene to OrderPage.fxml
     *
     * @param event
     * @throws IOException
     */
    public void goToOrderPage(ActionEvent event) throws IOException {
        References.ORDERS_PAGE.goTo();
    }

    public void goToUsersPage(ActionEvent event) throws IOException {
        References.USERS_PAGE.goTo();
    }

    /**
     * Close handler
     *
     * @param event
     */
    public void closeHandler(ActionEvent event) {
        Platform.exit();
    }


}
