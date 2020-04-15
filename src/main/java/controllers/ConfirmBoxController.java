package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import main.java.Main;


public class ConfirmBoxController extends Main {

    public void confirm(ActionEvent event){
        decision = true;
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void deny(ActionEvent event){
        decision = false;
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
}
