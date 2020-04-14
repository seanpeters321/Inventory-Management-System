package main.java.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import main.java.FileEditor;
import main.java.Formatter;
import main.java.References;
import main.java.Stock;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


/**
 * Manages InventorySheetEmployee.fxml
 *
 * @author Sean Peters
 */
public class InventorySheetController extends MainController implements Initializable {

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

    Formatter formatter;
    FileEditor fileEditor = new FileEditor();

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
    public void changeQuantityCellEvent( CellEditEvent<Stock, String> editedCell) throws IOException {
        int quantity = Integer.parseInt(editedCell.getNewValue().toString());
        if(quantity < 0){
            errorLabel.setOpacity(0);
            errorLabel.setText("Cannot Go Below Zero");
            final Timeline tl = new Timeline();
            KeyValue keyValue = new KeyValue(errorLabel.opacityProperty(), 1, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            tl.setCycleCount(1);
            tl.setAutoReverse(true);
            tl.getKeyFrames().addAll(keyFrame);
            tl.play();
            tableView.refresh();
        }else {
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
            tableView.setItems(getStock());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (user.isAdmin)
            tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    /**
     * Grabs items from the inventory.txt file and converts them to be read and added to an ObservableList. This list in then implemented in the table view.
     *
     * @return Stock ObservableList
     * @throws IOException
     * @see ObservableList
     * @see Stock
     */
    public ObservableList<Stock> getStock() throws IOException {
        ObservableList<Stock> stock = FXCollections.observableArrayList();

        Scanner fr = new Scanner(new FileReader(INVENTORY));
        while (fr.hasNext()) {
            String string;
            Stock stck;
            string = fr.next();
            String[] output = string.split("-");

            String resultID = output[0];
            String resultName = output[1];

            String resultType = output[2];

            String resultDim = output[3];
            String dim = resultDim + " in";

            String item = resultName + " " + resultType;

            String resultQnty = output[4];

            double resultCost = Double.parseDouble(output[5]);

            stck = new Stock(resultID, item, resultQnty, resultCost, dim);
            stock.add(stck);
        }
        fr.close();
        return stock;
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
        }catch(NullPointerException e){
            errorLabel.setOpacity(0);
            errorLabel.setText("Please Select A Stock");
            final Timeline tl = new Timeline();
            KeyValue keyValue = new KeyValue(errorLabel.opacityProperty(), 1, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            tl.setCycleCount(1);
            tl.setAutoReverse(true);
            tl.getKeyFrames().addAll(keyFrame);
            tl.play();
        }
    }

    public void setSubStock(ActionEvent event) throws IOException {
        try{
        FileEditor fileEditor = new FileEditor();
        String q = tableView.getSelectionModel().getSelectedItem().toString();
        q = q.replaceAll(" ", "-");
        q = q.replaceAll("-in-", "-");
        String[] output = q.split("-");
        int change = Integer.parseInt(output[4]) - 1;
        if (change < 0) {
            errorLabel.setOpacity(0);
            errorLabel.setText("Cannot Go Below Zero");
            final Timeline tl = new Timeline();
            KeyValue keyValue = new KeyValue(errorLabel.opacityProperty(), 1, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            tl.setCycleCount(1);
            tl.setAutoReverse(true);
            tl.getKeyFrames().addAll(keyFrame);
            tl.play();
        } else {
            String newQ = Integer.toString(change);
            String newLine = output[0] + "-" + output[1] + "-" + output[2] + "-" + output[3] + "-" + newQ + "-" + output[5];
            fileEditor.modifyFile(INVENTORY, q, newLine);
            References.INVENTORY_SHEET_ADMIN.refresh();
            errorLabel.setOpacity(0);
        }
        }catch(NullPointerException e){
            errorLabel.setOpacity(0);
            errorLabel.setText("Please Select A Stock");
            final Timeline tl = new Timeline();
            KeyValue keyValue = new KeyValue(errorLabel.opacityProperty(), 1, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            tl.setCycleCount(1);
            tl.setAutoReverse(true);
            tl.getKeyFrames().addAll(keyFrame);
            tl.play();
        }
    }
}
