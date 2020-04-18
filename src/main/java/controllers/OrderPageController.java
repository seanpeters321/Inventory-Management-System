package main.java.controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;


/**
 * Handles OrderPage.fxml
 *
 * Displays all stored invoices and inventory items that are in stock (quantity is greater than zero).
 * Fulfills orders by adding them to a list which then when the Confirm Order button is pressed,
 * opens a confirmation page which prompts the user to clarify the quantity of each stock selected in the JFXListView orderList.
 * After being confirmed the JFXListView orderList is reset and all other JFXListViews are refreshed.
 *
 * <b>Can be accessed equally by Employee and Administrator</b>
 *
 * @author Sean Peters
 */
public class OrderPageController extends MainController implements Initializable {

    // Inherited Objects
    protected static ObservableList<String> orderFinal = FXCollections.observableArrayList();
    protected static String name;
    // Scene FX:ID's
    @FXML
    protected JFXTextField orderName;
    @FXML
    private Label orderAlert;
    @FXML
    private JFXListView<String> orderList, inventoryList, invoiceList;



    /**
     * Executes when OrderPage.fxml is initialized.
     *
     * Populates JFXListViews inventoryList and invoiceList. Sets the selection model of the JFXListView orderList to SINGLE.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets the SelectionModel of the ListView to single.
        inventoryList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        refreshListViews();
    }

    /**
     * Handles MouseEvent when a double-click on an item in the JFXListView invoiceList is executed.
     * It will prompt the user to select how to open the selected invoice text document.
     * The invoice text document can be viewed in any text editor.
     *
     * @param click event fires when a mouse click is detected
     * @throws IOException
     */
    public void openInvoice(MouseEvent click) throws IOException {
        if (click.getClickCount() == 2) {
            String filepath = invoiceList.getSelectionModel().getSelectedItem();
            filepath = "./outputs/invoices/" + filepath + ".txt";
            File file = new File(filepath);
            Desktop.getDesktop().open(file);
        }
    }

    /**
     * Adds the Stock object selected from the JFXListView inventoryList to the JFXListView orderList and ObservableList orderFinal.
     *
     * @param event
     */
    public void orderSelectAdd(ActionEvent event) throws FileNotFoundException {
        ObservableList<String> selectedItems = inventoryList.getSelectionModel().getSelectedItems();
        this.orderList.getItems().addAll(selectedItems);
        this.orderFinal.addAll(selectedItems);
    }

    /**
     * Brings up the ConfirmOrder page only if JFXListView orderList is not empty.
     * After returning from the ConfirmOrder page, the
     *
     * @param event
     */
    public void orderConfirm(ActionEvent event) throws IOException {
        if (!orderName.getText().contentEquals("")) {
            name = orderName.getText();
            References.CONFIRM_ORDER_PAGE.popOut();

            //Clears out name, date, and order parameters.
            orderName.clear();
            orderList.getItems().clear();
            orderFinal.clear();

            refreshListViews();
        } else {
            orderName.setStyle("-fx-prompt-text-fill: #B20020");
        }
    }

    /**
     * Opens the invoices folder in File Explorer.
     *
     * @throws IOException
     */
    public void openFolder() throws IOException {
        Desktop desktop = Desktop.getDesktop();
        File folder = null;
        try {
            folder = new File("./outputs/invoices");
            desktop.open(folder);
        } catch (IllegalArgumentException e) {
            System.out.println("File Not Found");
        }
    }

    /**
     * Refreshes all JFXListViews
     */
    public void refreshListViews() {
        FileSearcher fileSearcher = new FileSearcher();
        ObservableList invoices = FXCollections.observableArrayList();
        ObservableList<String> files = FXCollections.observableArrayList();
        fileSearcher.fileFinder(Paths.get("./outputs/invoices"), invoices::addAll);
        if (!invoices.isEmpty()) {
            for (Object invoice : invoices) {
                File file = new File(invoice.toString().replaceAll(".txt",""));
                files.add(file.getName());
            }
            invoiceList.setItems(files);
        }else{
            invoiceList.getItems().clear();
        }
        ObservableList<String> list = FXCollections.observableArrayList();
        ObservableList<Stock> inventory = FXCollections.observableArrayList();
        FileEditor fileEditor = new FileEditor();
        try {
            inventory = fileEditor.getInventory();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Stock item : inventory) {
            if (Integer.parseInt(item.getQuantity()) > 0) {
                Formatter formatter = new Formatter();
                String stock = item.toString();
                stock = formatter.stockFormatter(stock);
                list.add(stock);
            }
        }
        inventoryList.setItems(list);
    }

    public void clearOrder(ActionEvent event){
        orderList.getItems().clear();

    }
}
