package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import main.java.Main;


/**
 * <b>Handles ConfirmBox.fxml</b>
 * <p>
 * Disables interaction in primary window.
 * <p>
 * Used to confirm the deletion of an item.
 * Displays two buttons which will then change the value of decision boolean, which is stored in Main.
 * <p>
 * Pressing either button will close the window and allow for interaction in the primary window.
 *
 * @author Sean Peters
 */
public class ConfirmBoxController extends Main {

    /**
     * Sets the value of the boolean decision to true.
     * Closes the window.
     *
     * @param event
     */
    public void confirm(ActionEvent event) {
        decision = true;
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    /**
     * Sets the value of the boolean decision to false.
     * Closes the window.
     *
     * @param event
     */
    public void deny(ActionEvent event) {
        decision = false;
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
}
