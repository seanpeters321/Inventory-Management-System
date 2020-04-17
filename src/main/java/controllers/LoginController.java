package main.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.java.FileEditor;
import main.java.Main;
import main.java.References;
import main.java.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * <b>Handles Login.fxml</b>
 * <p>
 * Prompts the user for a username and password.
 * Based off of the user's input values,
 * the user type will be determined.
 * The User object will be stored on the Main class so that all classes can access the current user type.
 * <p>
 * After logging in the scene will change to the MainPage.
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
    }

    /**
     * <b>Handles the login event</b>
     * <p>
     * Checks if the given username and password match an existing user and logs
     * them into this system.
     * A user object will be generated with the matched user and be stored in the Main class.
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
                References.MAIN.goTo();
            } else if (user != null && user.isEmployee) {
                this.user = user;
                statusLabel.setText("Login Success");
                References.MAIN.goTo();
            } else {
                animations.errorMessage("Invalid Username and/or Password", statusLabel);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
