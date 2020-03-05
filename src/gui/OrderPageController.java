package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

/**
 * Manages Order.fxml and its variables: client name, order date, selected items
 */
public class OrderPageController implements Initializable {
    @FXML
    private JFXDatePicker orderDate;
    @FXML
    private JFXTextField orderName, orderID;
    @FXML
    private JFXComboBox orderQty;
    @FXML
    private JFXButton orderAdd, orderConfirm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void addToOrder(ActionEvent event){

    }

    public void orderConfirm(ActionEvent event){
        orderDate.getAccessibleText();
        orderName.getText();

    }
}
