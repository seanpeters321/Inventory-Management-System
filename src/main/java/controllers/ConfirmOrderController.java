package main.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import main.java.Animations;
import main.java.FileEditor;
import main.java.Formatter;
import main.java.Stock;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


/**
 * <b>Handles ConfirmOrder.fxml</b>
 * <p>
 * Disables interaction in primary window.
 * <p>
 * Displays a TableView which shows the user the items selected on the OrderPage.
 * The quantityColumn is blank when initialized, and needs to be filled in before confirming the order.
 * The priceColumn will change its values depending on the input value of the quantityColumn,
 * this displays the total cost of the specific item in each row.
 * <p>
 * Underneath the TableView, the total cost of all items is displayed.
 * It is responsive and will change if the quantityColumn is edited.
 * <p>
 * Pressing the Confirm Order button will close the window and allow for interaction in primary window.
 *
 * @author Sean Peters
 */
public class ConfirmOrderController extends OrderPageController implements Initializable {
    @FXML
    private TableView<Stock> confirmView;
    @FXML
    private TableColumn<Stock, String> idColumn;
    @FXML
    private TableColumn<Stock, String> nameColumn;
    @FXML
    private TableColumn<Stock, Double> priceColumn;
    @FXML
    private TableColumn<Stock, String> inStockColumn;
    @FXML
    private TableColumn<Stock, String> quantityColumn;
    @FXML
    private Label priceLabel, errorLabel;

    /**
     * <b>Executes when ConfirmOrder.fxml is initialized</b>
     * <p>
     * Populates the TableView confirmView and Label priceLabel (The generated number assumes each item has quantity of one).
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double totalPrice = 0;
        FileEditor fileEditor = new FileEditor();
        ObservableList<String> list = this.orderFinal;
        ObservableList<Stock> items = FXCollections.observableArrayList();
        for (String item : list) {
            String price;
            Stock stock = null;
            item = StringUtils.substringBetween(item, "(", ")");
            try {
                stock = fileEditor.getStock(item);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            totalPrice += stock.totalPriceProperty().get();
            items.add(stock);
        }

        confirmView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        idColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("fullName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Stock, Double>("totalPrice"));
        inStockColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("quantity"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("finalQuantity"));

        confirmView.setItems(items);
        confirmView.setEditable(true);
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        String totalCost = Double.toString(totalPrice);
        priceLabel.setText("$" + totalCost);
    }

    /**
     * <b>Handles the CellEditEvent of the quantityColumn</b>
     * <p>
     * When the user inputs and confirms a value in a cell in the quantityColumn, the new value will be reflected in the TableView
     * and will be used to calculate the priceColumn values and priceLabel value.
     * Does not allow the user to input a value below zero or above the in stock amount (value in the inStockColumn)
     *
     * @param editedCell
     * @throws IOException
     */
    public void changeQuantityCellEditEvent(TableColumn.CellEditEvent editedCell) throws IOException {
        FileEditor fileEditor = new FileEditor();
        int quantity = Integer.parseInt(editedCell.getNewValue().toString());
        Stock stock = (Stock) editedCell.getRowValue();
        int max = Integer.parseInt(stock.getQuantity());

        if (quantity <= max && quantity > 0) {
            stock.finalQuantityProperty().set(Integer.toString(quantity));
            stock.totalPriceProperty().set(Double.parseDouble(Double.toString(stock.totalPriceProperty().get() * Double.parseDouble(stock.finalQuantityProperty().get()))));
            editedCell.getTableView().refresh();

            double totalPrice = 0;

            ObservableList<Stock> stck = editedCell.getTableView().getItems();
            for (Stock item : stck) {
                totalPrice += item.totalPriceProperty().get();
            }
            priceLabel.setText("$" + totalPrice);
        } else {
            Animations a = new Animations();
            a.errorMessage("Cannot Go Above In Stock Quantity", errorLabel);
            editedCell.getTableView().refresh();
        }
    }

    /**
     * <b>Handles the Confirm button</b>
     * <p>
     * Will generate an invoice based off the data in the TableView, which will be stored in the invoices folder in the outputs directory.
     * Will subtract all stock confirmed from the inventory text document.
     * Once executed, the window will close.
     *
     * @param event
     * @throws IOException
     */
    public void confirmOrder(ActionEvent event) throws IOException {
        Formatter formatter = new Formatter();
        FileEditor fileEditor = new FileEditor();

        //String verify, putData;
        ObservableList<Stock> items = confirmView.getItems();

        //Gets local date and time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dt = dtf.format(now);

        //Gets parameters from the order
        String name = this.name;
        String price = priceLabel.getText();

        //Title for the Invoice; file name.
        String title = "Invoice " + name;

        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./outputs/invoices", title)));


        //Generate invoice
        writer.write("Invoice\t\t\tMetals Inc.\n\n" + "Client: " + name + "\nDate and Time: " + dt + "\n\nPurchased Items:");
        for (Stock item : items) {
            writer.newLine();
            writer.write("\t" + item.finalQuantityProperty().get() + " |" + formatter.stockFormatter(item.toString()));
        }
        writer.write("\nTotal Cost: " + price);
        writer.close();

        for (Stock item : items) {
            String oldRow = item.toString().replace(" ", "-");
            String newQuantity = Integer.toString(Integer.parseInt(item.getQuantity()) - Integer.parseInt(item.finalQuantityProperty().get()));
            item.setQuantity(newQuantity);
            String newRow = item.toString().replace(" ", "-");
            ;
            fileEditor.modifyFile(INVENTORY, oldRow, newRow);
            fileEditor.clearEmptyLines(INVENTORY);
        }

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
}
