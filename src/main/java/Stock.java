package main.java;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.Random;


/**
 * Represents a stock in inventory which is identified with an ID. This ID represents the important characteristics of the Stock object so that the object can be reference in the inventory.txt file.
 *
 * @author Sean Peters
 */
public class Stock {
    private SimpleStringProperty name, ID, length, diameter, width, type, dimensions, item, quantity, finalQuantity = new SimpleStringProperty("");
    private SimpleStringProperty fullName;
    private SimpleDoubleProperty price, totalPrice;


    /**
     * @param ID
     * @param name
     * @param quantity
     * @param price
     */
    public Stock(String ID, String name, String quantity, double price, String dimensions) {
        this.name = new SimpleStringProperty(name);
        this.ID = new SimpleStringProperty(ID);
        this.quantity = new SimpleStringProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.dimensions = new SimpleStringProperty(dimensions);
    }

    public Stock(String ID, String name, String quantity, double price) {
        this.name = new SimpleStringProperty(name);
        this.ID = new SimpleStringProperty(ID);
        this.quantity = new SimpleStringProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
    }

    public Stock(String name, String length, String diameter, String type) {
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

    public Stock(String id, String metal, String type, String dimensions, String quantity, double price) {
        this.ID = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(metal);
        this.type = new SimpleStringProperty(type);
        this.dimensions = new SimpleStringProperty(dimensions);
        this.quantity = new SimpleStringProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.fullName = new SimpleStringProperty(metal + " " + type + " [" + dimensions.replace("x", " x ") + "]");
        this.totalPrice = new SimpleDoubleProperty(price);
    }

    public Stock(String metal, String type, String dimensions, String quantity, double price){
        this.name = new SimpleStringProperty(metal);
        this.type = new SimpleStringProperty(type);
        this.dimensions = new SimpleStringProperty(dimensions);
        this.quantity = new SimpleStringProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.ID = new SimpleStringProperty(genID(metal, type));
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(name.get() + " " + type.get());
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public SimpleStringProperty IDProperty() {
        return ID;
    }

    public String getDimensions() {
        return dimensions.get();
    }

    public void setDimensions(String dimensions) {
        this.dimensions.set(dimensions);
    }

    public SimpleStringProperty dimensionsProperty() {
        return dimensions;
    }

    public String getItem() {
        return item.get();
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public SimpleStringProperty itemProperty() {
        return item;
    }

    public String getQuantity() {
        return quantity.get();
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public double getPrice() {
        return price.get();
    }

    public String getType(){return type.get();}

    public void setPrice(double price) {
        this.price.set(price);
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public SimpleDoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public SimpleStringProperty finalQuantityProperty() {
        return finalQuantity;
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
    public Stock toStock(String id, String name, String quantity, double cost, String dimensions, String type) throws IOException {
        Stock stock = new Stock(id, name, quantity, cost, dimensions);
        return stock;
    }

    /**
     * Creates a string based of of stock.
     *
     * @return
     */
    public String toString() {
        String stock = "";
        stock = ID.get() + " " + name.get() + " " + type.get() + " " + dimensions.get() + " " + quantity.get() + " " + price.intValue();
        return stock;
    }


    /**
     * Generates ID based off of metal type, length, width, and height.
     * Used to reference inventory.txt and get price of item and its quantity in stock.
     */
    public String genID(String name, String type) {
        Random random = new Random();

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
            case "StainlessSteel":
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
            case "Shaft":
                ID += "40";
                break;
            case "Cylinder":
                ID += "50";
                break;
        }


        ID += Integer.toString(random.nextInt((999999 - 100000) + 1) + 100000);
        return ID;
    }


}
