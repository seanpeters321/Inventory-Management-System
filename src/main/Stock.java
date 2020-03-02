package main;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;

/**
 * @author Sean Peters
 */
public class Stock {
    private SimpleStringProperty name, ID;
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

    public Stock toStock(String id, String name, int quantity, double cost) throws IOException {
        Stock stock = new Stock(id, name, quantity, cost);
        return stock;
    }

    public String toString() {
        String stock = "";
        stock = ID.get() + " " + name.get() + " " + quantity.intValue() + " " + price.doubleValue();
        return stock;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String ID) {
        this.ID = new SimpleStringProperty(ID);
	}

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(Double price) {
        this.price = new SimpleDoubleProperty(price);
    }


}
