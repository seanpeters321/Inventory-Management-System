package main.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import main.java.FileEditor;
import main.java.Main;
import main.java.References;
import main.java.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Manages Login.fxml
 *
 * @author Sean Peters
 */
public class LoginController extends Main implements Initializable {

    // Scene FX:ID's
    @FXML
    private JFXTextField txtUser;
    @FXML
    private JFXPasswordField txtPass;
    @FXML
    private Label statusLabel;
    @FXML
    private JFXButton loginBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Adds shadow effect to button
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.ROSYBROWN);
        dropShadow.setHeight(5);
        dropShadow.setWidth(5);
        dropShadow.setRadius(5);
    }

    /**
     * Checks if the given username and password match an existing user and logs
     * them into this system
     *
     * @param event
     * @throws IOException
     */
    public void Login(ActionEvent event) throws IOException {
        try {
            // Checks for username and password match
            FileEditor file = new FileEditor();
            String name = txtUser.getText();
            String pass = txtPass.getText();
            User user = file.userIdentifier(name, pass);

            //compares the username and password and returns a boolean
            if (user != null && user.isAdmin) {
                this.user = user;
                statusLabel.setText("Login Success");
                References.MAIN_ADMIN.goTo();
            } else if (user != null && user.isEmployee) {
                this.user = user;
                statusLabel.setText("Login Success");
                References.MAIN_EMPLOYEE.goTo();
            } else {
                animations.errorMessage("Invalid Username and/or Password", statusLabel);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
