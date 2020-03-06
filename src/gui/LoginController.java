package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.FileEditor;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;

/**
 * Manages Login.fxml
 *
 * @author Sean Peters
 */
public class LoginController implements Initializable {

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
       // loginBtn.setEffect(dropShadow);
    }
    /**
     * Checks if the given username and password match an existing user and logs
     * them into this system
     *
     * @param event
     * @throws IOException
     */
    public void Login(ActionEvent event) throws IOException {
        // Checks for username and password match
        FileEditor file = new FileEditor();
        String user, pass;
        user = txtUser.getText();
        pass = txtPass.getText();
        boolean match = file.userpassMatch(user, pass);
        //compares the username and password and returns a boolean
        if (match) {
            statusLabel.setText("Login Success");
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();
            Parent page = FXMLLoader.load(getClass().getResource("/gui/Main.fxml"));
            Scene npage = new Scene(page, width, height);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(npage);
            window.setFullScreen(true);
            window.show();
        } else {
            //Fade in animation for the error text for 300ms
            statusLabel.setOpacity(0);
            statusLabel.setText("Invalid Username and/or Password");
            final Timeline tl = new Timeline();
            KeyValue keyValue  = new KeyValue(statusLabel.opacityProperty(), 1, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            tl.setCycleCount(1);
            tl.setAutoReverse(true);
            tl.getKeyFrames().addAll(keyFrame);
            tl.play();
        }
    }
}
