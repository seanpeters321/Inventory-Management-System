package main;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;

/**
 * Represents a stock in inventory which is identified with an ID. This ID represents the important characteristics of the Stock object so that the object can be reference in the inventory.txt file.
 *
 * @author Sean Peters
 */
public class Stock {
    private SimpleStringProperty name, ID, length, diameter, width;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty price;

    /**
     * @param ID
     * @param name
     * @param quantity
     * @param price
     */
    public Stock(String ID, String name, int quantity, double price) {
        this.name = new SimpleStringProperty(name);
        this.ID = new SimpleStringProperty(ID);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
    }

    public Stock(String name, String length, String diameter){
        this.name = new SimpleStringProperty(name);
        this.length = new SimpleStringProperty(length);
        this.diameter = new SimpleStringProperty(diameter);
    }

    public Stock(String name, String length, String diameter, String width){
        this.name = new SimpleStringProperty(name);
        this.length = new SimpleStringProperty(length);
        this.diameter = new SimpleStringProperty(diameter);
        this.width = new SimpleStringProperty(width);
    }

    /**
     * Creates Stock object off of ID, name, quantity, and cost.
     *
     * @param id
     * @param name
     * @param quantity
     * @param cost
     * @return
     * @throws IOException
     */
    public Stock toStock(String id, String name, int quantity, double cost) throws IOException {
        Stock stock = new Stock(id, name, quantity, cost);
        return stock;
    }

    /**
     * Creates a string based of of stock.
     *
     * @return
     */
    public String toString() {
        String stock = "";
        stock = ID.get() + " " + name.get() + " " + quantity.intValue() + " " + price.doubleValue();
        return stock;
    }

    /**
     * Gets name.
     *
     * @return
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets name.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    /**
     * Gets ID.
     *
     * @return
     */
    public String getID() {
        return ID.get();
    }

    /**
     * Sets ID.
     *
     * @param ID
     */
    public void setID(String ID) {
        this.ID = new SimpleStringProperty(ID);
    }

    /**
     * Gets quantity.
     *
     * @return
     */
    public int getQuantity() {
        return quantity.get();
    }

    /**
     * Sets quantity
     *
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    /**
     * Gets price.
     *
     * @return
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * Sets price.
     *
     * @param price
     */
    public void setPrice(Double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    /**
     * Generates ID based off of metal type, length, width, and height.
     * Used to reference inventory.txt and get price of item and its quantity in stock.
     */
    public void genID() {
        String name, width, length, diamter, ID;
        String nm = this.name.toString();
        switch(nm){
            case "Aluminum":
                ID = "01";
                break;
            case "Steel":
                ID = "02";
                break;
        }
    }


}
