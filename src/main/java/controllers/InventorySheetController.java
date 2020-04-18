package main.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import main.java.FileEditor;
import main.java.Formatter;
import main.java.References;
import main.java.Stock;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * <b>Handles InventorySheet.fxml</b>
 * <p>
 * Displays the inventory by reading the inventory text document and scanning for important values.
 * Allows for adding and subtracting of the quantity value of the stock,
 * which can be accomplished either by directly changing the value in the cell, or using the add and minus buttons)
 * <p>
 * To generate a new stock, the user must input the prompted values.
 * Once generated, a new line the inventory text document will be added containing all important information.
 * <p>
 * To delete stock, the user must select a row from the TableView and select the <i>Trash Can</i> button,
 * which opens a confirmation box.
 * This will delete the line in the inventory text document,
 * which means that there is no way to recover the deleted stock.
 *
 * <b>Employee can only view the TableView, while an Administrator can edit, delete and generate stock</b>
 *
 * @author Sean Peters
 */
public class InventorySheetController extends MainController implements Initializable {

    Formatter formatter;
    FileEditor fileEditor = new FileEditor();
    // Scene FX:ID's
    @FXML
    private TableView<Stock> tableView;
    @FXML
    private TableColumn<Stock, String> nameColumn;
    @FXML
    private TableColumn<Stock, String> idColumn;
    @FXML
    private TableColumn<Stock, String> quantityColumn;
    @FXML
    private TableColumn<Stock, Double> priceColumn;
    @FXML
    private TableColumn<Stock, String> dimColumn;
    @FXML
    private JFXButton addStock, plusButton, minusButton, deleteButton;
    @FXML
    private Label errorLabel;
    @FXML
    private JFXComboBox metalBox, typeBox;
    @FXML
    private JFXTextField dimensions, quantity, price;



    /**
     * <b>Executes when InventorySheet is initialized.</b>
     * <p>
     * Initially populates the TableView and sets its SelectionModel to SINGLE.
     * If the use is an Employee, the add, minus, and <i>Trash Can</i> buttons will be disabled and hidden,
     * as well as the generate stock section becoming disabled.
     *
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //Sets up columns
        setUpTableView();
        if (user.isAdmin) {
            metalBox.getItems().addAll("Copper", "Steel", "StainlessSteel", "Aluminum", "Bronze", "Titanium");
            typeBox.getItems().addAll("Shaft", "Block", "Cylinder", "Sheet", "Bar");
        }
        try {
            tableView.setItems(fileEditor.getInventory());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (user.isAdmin)
            tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        if (user.isEmployee) {
            metalBox.setDisable(true);
            typeBox.setDisable(true);
            dimensions.setDisable(true);
            quantity.setDisable(true);
            price.setDisable(true);
            addStock.setDisable(true);
            plusButton.setDisable(true);
            plusButton.setOpacity(0);
            minusButton.setDisable(true);
            minusButton.setOpacity(0);
            deleteButton.setDisable(true);
            deleteButton.setOpacity(0);
        }
    }

    /**
     * Populates the TableView.
     */
    private void setUpTableView() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("ID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Stock, Double>("price"));
        dimColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("dimensions"));
    }


    /**
     * <b>Handles the CellEditEvent of the nameColumn</b>
     * <p>
     * Pushes the changes to the inventory text document,
     * then refreshes the TableView to reflect these changes.
     *
     * @param editedCell
     */
    public void changeNameCellEvent(CellEditEvent<Stock, String> editedCell) throws IOException {
        formatter = new Formatter();
        formatter.formatStockFromCellEdit(editedCell);
        fileEditor.modifyFile(INVENTORY, formatter.oldRow, formatter.newRow);
        fileEditor.clearEmptyLines(INVENTORY);
        refreshTable();
    }

    /**
     * <b>Handles the CellEditEvent of the quantityColumn</b>
     * <p>
     * Pushes the changes to the inventory text document,
     * then refreshes the TableView to reflect these changes.
     *
     * @param editedCell
     */
    public void changeQuantityCellEvent(CellEditEvent<Stock, String> editedCell) throws IOException {
        int quantity = Integer.parseInt(editedCell.getNewValue().toString());
        if (quantity < 0) {
            animations.errorMessage("Cannot Go Below Zero", errorLabel);
        } else {
            formatter = new Formatter();
            formatter.formatStockFromCellEdit(editedCell);
            fileEditor.modifyFile(INVENTORY, formatter.oldRow, formatter.newRow);
            fileEditor.clearEmptyLines(INVENTORY);
            refreshTable();
        }
    }

    /**
     * <b>Handles the delete stock event</b>
     * <p>
     * When the <i>Trash Can</i> button is selected, the TableView's SelectionModel will be searched
     * to isolate the selected item. A confirmation box will then be prompted. If confirmed, the stock will be deleted
     * in the inventory text document.
     * <p>
     * There is no way to recover the deleted stock.
     *
     * @param event
     * @throws IOException
     */
    public void deleteStock(ActionEvent event) throws IOException {
        if (tableView.getSelectionModel().getSelectedItems().isEmpty() == false) {
            FileEditor fileEditor = new FileEditor();
            References.CONFIRM_BOX.popOut();
            ObservableList<Stock> toDelete = FXCollections.observableArrayList();
            if (decision == true) {
                toDelete = tableView.getSelectionModel().getSelectedItems();
                for (Stock item : toDelete) {
                    String string = item.toString().replace(" ", "-");
                    fileEditor.modifyFile(INVENTORY, string, "");
                    fileEditor.clearEmptyLines(INVENTORY);
                }
            }
        }
        refreshTable();
    }

    /**
     * <b>Handles the generate stock event</b>
     * <p>
     * Grabs the selected items from the JFXComboBoxes and the input values for the JFXTextFields.
     * With this data, a Stock object will be created which will generated an ID.
     * The new stock will be added to the inventory text document and the TableView will be refreshed to reflect the change.
     *
     * @param event
     * @throws IOException
     */
    public void genStock(ActionEvent event) throws IOException {
        FileEditor fileEditor = new FileEditor();
        String metal = metalBox.getSelectionModel().getSelectedItem().toString();
        String type = typeBox.getSelectionModel().getSelectedItem().toString();
        String dim = dimensions.getText();
        String quantity = this.quantity.getText();
        double price = Double.parseDouble(this.price.getText());

        Stock stock = new Stock(metal, type, dim, quantity, price);
        String toAdd = stock.toString().replace(" ", "-");
        fileEditor.addToFile(INVENTORY, toAdd);
        metalBox.getSelectionModel().clearSelection();
        typeBox.getSelectionModel().clearSelection();
        dimensions.clear();
        this.quantity.clear();
        this.price.clear();
        refreshTable();
    }

    /**
     * Refreshes the TableView
     */
    public void refreshTable() {
        setUpTableView();
        try {
            tableView.setItems(fileEditor.getInventory());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <b>Handles the subtract stock quantity event</b>
     * <p>
     * Gets the selected item from the SelectionModel and subtracts one from the quantity value
     * on the inventory text document. The TableView is then refreshed to reflect the change.
     *
     * @param event
     * @throws IOException
     */
    public void setAddStock(ActionEvent event) throws IOException {
        try {
            FileEditor fileEditor = new FileEditor();
            String q = tableView.getSelectionModel().getSelectedItem().toString();
            q = q.replaceAll(" ", "-");
            q = q.replaceAll("-in-", "-");
            String[] output = q.split("-");
            int change = Integer.parseInt(output[4]) + 1;
            String newQ = Integer.toString(change);
            String newLine = output[0] + "-" + output[1] + "-" + output[2] + "-" + output[3] + "-" + newQ + "-" + output[5];
            fileEditor.modifyFile(INVENTORY, q, newLine);
            refreshTable();

        } catch (NullPointerException e) {
            animations.errorMessage("Please Select A Stock", errorLabel);
        }
    }

    /**
     * <b>Handles the add stock quantity event</b>
     * <p>
     * Gets the selected item from the SelectionModel and adds one to the quantity value
     * on the inventory text document. The TableView is then refreshed to reflect the change.
     *
     * @param event
     * @throws IOException
     */
    public void setSubStock(ActionEvent event) throws IOException {
        try {
            FileEditor fileEditor = new FileEditor();
            String q = tableView.getSelectionModel().getSelectedItem().toString();
            q = q.replaceAll(" ", "-");
            q = q.replaceAll("-in-", "-");
            String[] output = q.split("-");
            int change = Integer.parseInt(output[4]) - 1;
            if (change < 0) {
                animations.errorMessage("Cannot Go Below Zero", errorLabel);
            } else {
                String newQ = Integer.toString(change);
                String newLine = output[0] + "-" + output[1] + "-" + output[2] + "-" + output[3] + "-" + newQ + "-" + output[5];
                fileEditor.modifyFile(INVENTORY, q, newLine);
                refreshTable();
                errorLabel.setOpacity(0);
            }
        } catch (NullPointerException e) {
            animations.errorMessage("Please Select A Stock", errorLabel);
        }
    }
}
