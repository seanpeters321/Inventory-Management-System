package gui;

import javafx.application.Preloader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import main.Stock;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Manages InventorySheet.fxml
 *
 * @author Sean Peters
 */
public class InventorySheetController implements Initializable {

    @FXML
    private TableView<Stock> tableView;
    @FXML
    private TableColumn<Stock, String> nameColumn;
    @FXML
    private TableColumn<Stock, String> idColumn;
    @FXML
    private TableColumn<Stock, Integer> quantityColumn;
    @FXML
    private TableColumn<Stock, Double> priceColumn;

    /**
     * Allows for the Name cell to be edited
     *
     * @param edittedCell
     */
    public void changeNameCellEvent(@SuppressWarnings("rawtypes") CellEditEvent edittedCell) throws IOException {
        String filePath = ".src/resources/txt/inventory.txt";
        Scanner scanner = new Scanner(new FileReader(filePath));
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String string;
            while ((string = bufferedReader.readLine()) != null) {

                String str = fileReader.toString();
                String[] output = string.split("-");
            }
        Stock stockSelected = tableView.getSelectionModel().getSelectedItem();
        String newname = edittedCell.getNewValue().toString();
        stockSelected.setName(newname);
    }

    /**
     * Allows for the ID cell to be edited
     *
     * @param edittedCell
     */
    public void changeIDCellEvent(@SuppressWarnings("rawtypes") CellEditEvent edittedCell) {
        Stock stockSelected = tableView.getSelectionModel().getSelectedItem();
        stockSelected.setID(edittedCell.getNewValue().toString());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("ID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Stock, Double>("price"));

        try {
            tableView.setItems(getStock());
        } catch (IOException e) {
            e.printStackTrace();
        }

        tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    /**
     * Adds items to the Inventory List
     *
     * @return Stock ObservableList
     * @throws IOException
     * @see ObservableList
     * @see Stock
     */
    public ObservableList<Stock> getStock() throws IOException {
        ObservableList<Stock> stock = FXCollections.observableArrayList();

        Scanner fr = new Scanner(new FileReader("./src/resources/txt/inventory.txt"));
        while (fr.hasNext()) {
            String string;
            Stock stck;
            string = fr.next();
            String[] output = string.split("-");

            String resultID = output[0];
            String resultName = output[1];
            int resultQnty = Integer.parseInt(output[2]);
            double resultCost = Double.parseDouble(output[3]);
            stck = new Stock(resultID, resultName, resultQnty, resultCost);
            stock.add(stck);
        }
        fr.close();
        return stock;
    }

    /**
     * Returns to Main page
     *
     * @param event Back button being pressed
     * @throws IOException If Main.fxml cannot be found
     */
    public void goBack(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("/gui/Main.fxml"));
        Scene npage = new Scene(page);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(npage);
        window.setFullScreen(true);
        window.show();
    }

    /**
     * Refreshes the chart to show changes to inventory.txt
     *
     * @param event Refresh button being pressed
     * @throws IOException If InventorySheet.fxml cannot be found
     */
    public void refresh(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("/gui/InventorySheet.fxml"));
        Scene npage = new Scene(page);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(npage);
        window.setFullScreen(true);
        window.show();
    }
}
