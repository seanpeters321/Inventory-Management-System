package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Stock;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
    private JFXTextField orderQty,orderName;
    @FXML
    private JFXComboBox orderLength, orderWidth, orderDiameter, orderType, orderMetal; //Width is an optional field which only applies to square/rectangular/flat/block stock
    @FXML
    private JFXButton orderAdd, orderConfirm;
    @FXML
    private Label orderAlert;
    @FXML
    private ListView order;

    private Object NullPointerException;

    public OrderPageController() throws FileNotFoundException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderLength.getItems().addAll("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0");
        orderWidth.getItems().addAll("0.2", "0.4", "0.4", "0.6", "0.8", "1.0");
        orderDiameter.getItems().addAll("0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0");
        orderType.getItems().addAll("Bar", "Sheet", "Block");
        orderMetal.getItems().addAll("Aluminum", "Bronze", "Copper", "Steel", "Stainless Steel", "Titanium");
    }

    /**
     * Handles <b>Add to Order Button</b>
     *
     * @param event
     */
    public void addToOrder(ActionEvent event) throws FileNotFoundException {
        try {
                ArrayList < Stock > order = new ArrayList<Stock>();
        String id, Name;
        String quantity = orderQty.getText();
        String length = orderLength.getSelectionModel().getSelectedItem().toString();
        String width = orderWidth.getSelectionModel().getSelectedItem().toString();
        String diameter = orderDiameter.getSelectionModel().getSelectedItem().toString();
        String name = orderType.getSelectionModel().getSelectedItem().toString();
        String metal = orderMetal.getSelectionModel().getSelectedItem().toString();
        Stock stock;
        if(quantity.length() == 0){
            throw new NullPointerException();

        }
        if (width != null)
            stock = new Stock(name, length, diameter, metal);
        else
            stock = new Stock(name, length, diameter, metal, width);
        //generated ID to search for in inventory.txt
        //stock.genID();


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
        Name = quantity + "| " + metal + " " + name + " (" + length + "in x " + diameter + "in)";
        System.out.println(Name);
        this.order.getItems().addAll(Name);
        orderAlert.setOpacity(0);
        orderAlert.setText("");
     }catch(NullPointerException e){
            orderAlert.setText("Please Fill in All Values");
            final Timeline tl = new Timeline();
            KeyValue keyValue  = new KeyValue(orderAlert.opacityProperty(), 1, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            tl.setCycleCount(1);
            tl.setAutoReverse(true);
            tl.getKeyFrames().addAll(keyFrame);
            tl.play();
        }
    }

    /**
     * Handles <b>Confirm Order Button</b>
     *
     * @param event
     */
    public void orderConfirm(ActionEvent event) throws IOException {
        ObservableList<String> items;
        FileWriter writer = new FileWriter("cart.txt");
        items = order.getSelectionModel().getSelectedItems();
        for(String item: items){
            writer.write(item);
        }

    }

    public void goBack(javafx.event.ActionEvent event) throws IOException {
        //grabs scene source
        Parent page = FXMLLoader.load(getClass().getResource("/gui/Main.fxml"));
        Scene npage = new Scene(page);
        //grabs main stage object
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(npage);
        window.setMaximized(true);
        window.show();
    }
}
