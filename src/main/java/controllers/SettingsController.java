package main.java.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.java.Main;
import main.java.References;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SettingsController extends Main implements Initializable {

    @FXML
    JFXComboBox themeBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        themeBox.getItems().addAll("Light", "Dark");
    }

    public void apply(ActionEvent event) throws IOException {
        String selectedTheme = themeBox.getSelectionModel().getSelectedItem().toString();
        if(selectedTheme.contentEquals("Light")){
            isDark = false;
        }
        else if(selectedTheme.contentEquals("Dark")){
            isDark = true;
        }
        References.SETTINGS.refresh(event);
        References.MAIN.goTo();
    }
}
