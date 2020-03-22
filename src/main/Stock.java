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
    private SimpleStringProperty name, ID, length, diameter, width, type, dimensions, item;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty price;

    /**
     * @param ID
     * @param name
     * @param quantity
     * @param price
     */
    public Stock(String ID, String name, int quantity, double price, String dimensions, String type) {
        this.name = new SimpleStringProperty(name);
        this.ID = new SimpleStringProperty(ID);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.dimensions = new SimpleStringProperty(dimensions);
        this.type = new SimpleStringProperty(type);
    }

    public Stock(String ID, String item, int quantity, double price, String dimensions) {
        this.item = new SimpleStringProperty(item);
        this.ID = new SimpleStringProperty(ID);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.dimensions = new SimpleStringProperty(dimensions);
    }

    public Stock(String name, String length, String type, String diameter) {
        this.name = new SimpleStringProperty(name);
        this.length = new SimpleStringProperty(length);
        this.diameter = new SimpleStringProperty(diameter);
        this.type = new SimpleStringProperty(type);
    }

    public Stock(String name, String length, String diameter, String type, String width) {
        this.name = new SimpleStringProperty(name);
        this.length = new SimpleStringProperty(length);
        this.diameter = new SimpleStringProperty(diameter);
        this.type = new SimpleStringProperty(type);
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
    public Stock toStock(String id, String name, int quantity, double cost, String dimensions, String type) throws IOException {
        Stock stock = new Stock(id, name, quantity, cost, dimensions, type);
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
    public void setPrice(Double price) {this.price = new SimpleDoubleProperty(price);}

    public String getDimensions(){return dimensions.get();}

    public void setDimensions(String dimensions){this.dimensions = new SimpleStringProperty(dimensions);}

    public String getType(){return type.get();}

    public void setType(String type){this.type = new SimpleStringProperty(type);}

    public String getItem(){return item.get();}

    public void setItem(String item){this.item = new SimpleStringProperty(item);}


    /**
     * Generates ID based off of metal type, length, width, and height.
     * Used to reference inventory.txt and get price of item and its quantity in stock.
     */
    public String genID() {
        String name = this.name.toString();
        String type = this.type.toString();
        String length = this.length.toString();
        String diameter = this.diameter.toString();
        String width = this.width.toString();
        String ID = null;

        switch (name) {
            case "Aluminum":
                ID = "1";
                break;
            case "Bronze":
                ID = "2";
                break;
            case "Copper":
                ID = "3";
                break;
            case "Steel":
                ID = "4";
                break;
            case "Stainless Steel":
                ID = "5";
                break;
            case "Titanium":
                ID = "6";
                break;
        }

        switch (type) {
            case "Bar":
                ID += "10";
                break;
            case "Sheet":
                ID += "20";
                break;
            case "Block":
                ID += "30";
                break;
        }

        ID += length + diameter + width;
        return ID;
    }


}
