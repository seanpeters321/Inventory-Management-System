package main;

import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleStringProperty username, password, type;

    public User(String username, String password, String type){
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.type = new SimpleStringProperty(type);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
