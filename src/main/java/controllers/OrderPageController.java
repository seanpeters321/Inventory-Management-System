package main.java.controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import main.java.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;


/**
 * Manages Order.fxml and its variables: client name, order date, selected items
 */
public class OrderPageController extends MainController implements Initializable {
    protected static ObservableList<String> orderFinal = FXCollections.observableArrayList();
    protected static String name;
    // Scene FX:ID's
    @FXML
    protected JFXTextField orderName;
    ObservableList<String> orderList;
    @FXML
    private Label orderAlert;
    @FXML
    private JFXListView<String> order, orderSelect, invoiceViewer;
    private ObservableList<Stock> inventory, cart;

    public OrderPageController() throws FileNotFoundException {
    }

    /**
     * Populates ComboBoxes and orderSelect upon loading of the scene.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Allows for multiple selected items in the orderSelect ListView.
        orderSelect.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        FileSearcher fileSearcher = new FileSearcher();
        ObservableList invoices = FXCollections.observableArrayList();
        ObservableList<String> files = FXCollections.observableArrayList();
        fileSearcher.recursiveFind(Paths.get("./outputs/invoices"), invoices::addAll);
        for(Object invoice: invoices){
            File file = new File(invoice.toString());
            files.add(file.getName());
        }
        invoiceViewer.setItems(files);



        Scanner fr = null;
        //FileNotFoundException try/catch statement for the inventory text file.
        try {
            fr = new Scanner(new FileReader(INVENTORY));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Creates ObservableList that stores all items in the inventory text file.
        ObservableList<String> list = FXCollections.observableArrayList();
        ObservableList<Stock> items = FXCollections.observableArrayList();
        FileEditor fileEditor = new FileEditor();
        try {
            items = fileEditor.getInventory();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        inventory = items;
        for (Stock item : items) {
            if (Integer.parseInt(item.getQuantity()) > 0) {
                Formatter formatter = new Formatter();
                String stock = item.toString();
                stock = formatter.stockFormatter(stock);
                list.add(stock);
            }
        }
        orderSelect.setItems(list);

    }


    public void selectInvoice(MouseEvent click) throws IOException {
        if(click.getClickCount() == 2){
            String filepath = invoiceViewer.getSelectionModel().getSelectedItem();
            filepath = "./outputs/invoices/" + filepath;
            File file = new File(filepath);
            Desktop.getDesktop().open(file);
        }
    }

    /**
     * Adds selected items from the orderSelect list to the order list after button is pressed.
     *
     * @param event
     */


    public void orderSelectAdd(ActionEvent event) throws FileNotFoundException {
        ObservableList<String> list = orderSelect.getSelectionModel().getSelectedItems();

        this.order.getItems().addAll(list);
        this.orderFinal.addAll(list);
    }


    /**
     * Handles <b>Confirm Order Button</b>.
     *
     * @param event
     */
    public void orderConfirm(ActionEvent event) throws IOException {
        name = orderName.getText();
        References.CONFIRM_ORDER_PAGE.popOut();

        //Clears out name, date, and order parameters.
        orderName.clear();
        order.getItems().clear();
        orderFinal.clear();
        References.ORDERS_PAGE.refresh();
    }

    /**
     * Returns to main menu.
     *
     * @param event
     * @throws IOException
     */
    public void goBack(javafx.event.ActionEvent event) throws IOException {
        if (user.isAdmin) {
            References.MAIN_ADMIN.goTo();
        } else if (user.isEmployee) {
            References.MAIN_EMPLOYEE.goTo();
        }
    }
}
