package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import main.Stock;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Manages Order.fxml and its variables: client name, order date, selected items
 */
public class OrderPageController implements Initializable {
    @FXML
    private DatePicker orderDate;
    @FXML
    private JFXTextField orderName, orderType;
    @FXML
    private JFXComboBox orderQty, orderLength, orderWidth, orderDiameter; //Width is an optional field which only applies to square/rectangular/flat/block stock
    @FXML
    private JFXButton orderAdd, orderConfirm;
    @FXML
    private Label orderAlert;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Handles <b>Add to Order Button</b>
     * @param event
     */
    public void addToOrder(ActionEvent event) throws FileNotFoundException {
        ArrayList<Stock> order = new ArrayList<Stock>();
        Stock stock = new Stock();

        String quantity = orderQty.getAccessibleText();
        String length = orderLength.getAccessibleText();
        String width = orderWidth.getAccessibleText();
        String diameter = orderDiameter.getAccessibleText();





        //Searches for Stock input by user
        Scanner fr = new Scanner(new FileReader("./src/resources/txt/inventory.txt"));
        while (fr.hasNext()) {
            String string;
            Stock stck;
            string = fr.next();
            String[] output = string.split("-");

            String resultID = output[0];


        }
        fr.close();
    }

    /**
     * Handles <b>Confirm Order Button</b>
     * @param event
     */
    public void orderConfirm(ActionEvent event){
        String date = orderDate.getAccessibleText();
        String name = orderName.getText();

    }
}
