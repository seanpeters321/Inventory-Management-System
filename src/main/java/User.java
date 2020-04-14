package main.java;

import javafx.beans.property.SimpleStringProperty;


public class User {
    public boolean isAdmin = false, isEmployee = false;
    private SimpleStringProperty username, password, type;

    public User(String username, String password, String type) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.type = new SimpleStringProperty(type);
        if (type.contentEquals("admin"))
            isAdmin = true;
        else if (type.contentEquals("employee"))
            isEmployee = true;
    }

    public String toString() {
        String user = this.username.get() + "-" + this.password.get() + "-" + this.type.get();
        return user;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }
}
