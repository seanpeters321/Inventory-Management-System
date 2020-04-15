package main.java.controllers;

import com.jfoenix.controls.JFXButton;
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
 * Manages InventorySheetEmployee.fxml
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
    private JFXButton addStock;
    @FXML
    private JFXButton subStock;
    @FXML
    private Label errorLabel;

    /**
     * Allows for the Name cell to be edited
     *
     * @param editedCell
     */
    public void changeNameCellEvent(CellEditEvent<Stock, String> editedCell) throws IOException {
        formatter = new Formatter();
        formatter.formatStockFromCellEdit(editedCell);
        fileEditor.modifyFile(INVENTORY, formatter.oldRow, formatter.newRow);
        fileEditor.clearEmptyLines(INVENTORY);
        References.INVENTORY_SHEET_ADMIN.refresh();
    }

    /**
     * Allows for the Quantity cell to be edited
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
            References.INVENTORY_SHEET_ADMIN.refresh();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //Sets up columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("ID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Stock, Double>("price"));
        dimColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("dimensions"));

        try {
            tableView.setItems(fileEditor.getInventory());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (user.isAdmin)
            tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    /**
     * Returns to Main page
     *
     * @param event Back button being pressed
     * @throws IOException If MainAdmin.fxml cannot be found
     */
    public void goBack(ActionEvent event) throws IOException {
        if (user.isAdmin)
            References.MAIN_ADMIN.goTo();
        else if (user.isEmployee)
            References.MAIN_EMPLOYEE.goTo();
    }

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
            References.INVENTORY_SHEET_ADMIN.refresh();
        } catch (NullPointerException e) {
            animations.errorMessage("Please Select A Stock", errorLabel);
        }
    }


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
                References.INVENTORY_SHEET_ADMIN.refresh();
                errorLabel.setOpacity(0);
            }
        } catch (NullPointerException e) {
            animations.errorMessage("Please Select A Stock", errorLabel);
        }
    }
}
