package main.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.util.Duration;
import main.java.References;
import main.java.Stock;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;


/**
 * Manages Order.fxml and its variables: client name, order date, selected items
 */
public class OrderPageController extends MainController implements Initializable {
    ObservableList<String> orderList;
    // Scene FX:ID's
    @FXML
    private DatePicker orderDate;
    @FXML
    private JFXTextField orderQty, orderName;
    @FXML
    private JFXComboBox orderLength, orderWidth, orderDiameter, orderType, orderMetal; //Width is an optional field which only applies to square/rectangular/flat/block stock
    @FXML
    private JFXButton orderAdd, orderConfirm;
    @FXML
    private Label orderAlert;
    @FXML
    private JFXListView<String> order, orderSelect;

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
        orderSelect.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Populates the ComboBoxes
        orderLength.getItems().addAll("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0");
        orderWidth.getItems().addAll("0.2", "0.4", "0.4", "0.6", "0.8", "1.0");
        orderDiameter.getItems().addAll("0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0");
        orderType.getItems().addAll("Bar", "Sheet", "Block");
        orderMetal.getItems().addAll("Aluminum", "Bronze", "Copper", "Steel", "Stainless Steel", "Titanium");

        Scanner fr = null;
        //FileNotFoundException try/catch statement for the inventory text file.
        try {
            fr = new Scanner(new FileReader(INVENTORY));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Creates ObservableList that stores all items in the inventory text file.
        ObservableList<String> list = FXCollections.observableArrayList();

        //Searches for items, in the inventory text file, line by line.
        while (fr.hasNext()) {
            //Splits the items in inventory by "-".
            String string = fr.next();
            String[] output = string.split("-");

            //Separates the item into five objects.
            String resultName = output[1];
            String resultType = output[2];
            String resultDim = output[3];
            int resultQty = Integer.parseInt(output[4]);
            double resultCost = Double.parseDouble(output[5]);
            //Creates formatted text for item.
            String dim = resultDim + " in";
            String item = resultName + " " + resultType;
            String name = resultQty + "| " + item + " (" + dim + ")";

            //Adds the item to the ObservableList
            list.add(name);
        }
        fr.close();

        orderSelect.setItems(list);
    }

    /**
     * Handles <b>Add to Order Button</b>
     *
     * @param event
     */
    public void addToOrder(ActionEvent event) throws FileNotFoundException {
        try {
            String Name;

            //Gets the quantity and selected items from the ComboBoxes
            String quantity = orderQty.getText();
            String length = orderLength.getSelectionModel().getSelectedItem().toString();
            String width = orderWidth.getSelectionModel().getSelectedItem().toString();
            String diameter = orderDiameter.getSelectionModel().getSelectedItem().toString();
            String name = orderType.getSelectionModel().getSelectedItem().toString();
            String metal = orderMetal.getSelectionModel().getSelectedItem().toString();

            Stock stock;

            if (quantity.length() == 0) {
                throw new NullPointerException();
            }

            //Creates stock object with given parameters
            if (width != null)
                stock = new Stock(name, length, diameter, metal);
            else
                stock = new Stock(name, length, diameter, metal, width);

            //generated ID to search for in inventory.txt
            stock.genID();


            //Searches for Stock input by user
            Scanner fr = new Scanner(new FileReader(INVENTORY));
            while (fr.hasNext()) {
                String string;
                Stock stck;
                string = fr.next();
                String[] output = string.split("-");

                String resultID = output[0];


            }
            fr.close();

            //Generates formatted text.
            Name = quantity + "| " + metal + " " + name + " (" + length + "in x " + diameter + "in)";

            //Prints text
            System.out.println(Name);

            //Adds item to order ViewList.
            order.getItems().addAll(Name);

            orderAlert.setOpacity(0);
            orderAlert.setText("");

            //Clears all item selection parameters
            orderQty.clear();
            orderType.getSelectionModel().clearSelection();
            orderMetal.getSelectionModel().clearSelection();
            orderWidth.getSelectionModel().clearSelection();
            orderLength.getSelectionModel().clearSelection();
            orderDiameter.getSelectionModel().clearSelection();
        }

        //Prints alert if a NullPointerException is found.
        catch (NullPointerException e) {
            e.printStackTrace();

            orderAlert.setText("Please Fill in All Values");

            //Animation handling
            final Timeline tl = new Timeline();
            KeyValue keyValue = new KeyValue(orderAlert.opacityProperty(), 1, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            tl.setCycleCount(1);
            tl.setAutoReverse(true);
            tl.getKeyFrames().addAll(keyFrame);
            tl.play();
        }
    }

    /**
     * Adds selected items from the orderSelect list to the order list after button is pressed.
     *
     * @param event
     */
    public void orderSelectAdd(ActionEvent event) {
        ObservableList<String> list = orderSelect.getSelectionModel().getSelectedItems();
        this.order.getItems().addAll(list);

    }

    /**
     * Handles <b>Confirm Order Button</b>.
     *
     * @param event
     */
    public void orderConfirm(ActionEvent event) throws IOException {
        //String verify, putData;
        ObservableList<String> items;

        //Gets local date and time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dt = dtf.format(now);


        //Gets parameters from the order
        String name = orderName.getText();
        String price = "$PLACEHOLDER";

        //Title for the Invoice; file name.
        String title = "Invoice " + name;

        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./outputs/invoices", title)));


        items = order.getItems();

        //Generate invoice

        writer.write("Invoice\t\t\tMetals Inc.\n\n" + "Client: " + name + "\tDate and Time: " + dt + "\n\nPurchased Items:");
        for (String item : items) {
            writer.newLine();
            writer.write("\t" + item);
        }
        writer.write("\nTotal Cost: " + price);
        writer.close();

        //Clears out name, date, and order parameters.
        orderName.clear();
        orderDate.getEditor().clear();
        order.getItems().clear();
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
