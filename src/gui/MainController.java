package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Manages Main.fxml
 *
 * @author Sean
 */
public class MainController implements Initializable {
    @FXML
    private JFXButton orderButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    /**
     * Changes scene to Login.fxml
     *
     * @param event
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
        Scene npage = new Scene(page);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(npage);
        window.setFullScreen(false);
        window.show();
    }

    /**
     * Changes scene to InventorySheet.fxml
     *
     * @param event
     * @throws IOException
     */
    public void goToInventorySheet(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("/gui/InventorySheet.fxml"));
        Scene npage = new Scene(page);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(npage);
        window.setMaximized(true);
        window.show();
    }

    /**
     * Changes scene to OrderPage.fxml
     * @param event
     * @throws IOException
     */
    public void goToOrderPage(ActionEvent event) throws IOException{
        Parent page = FXMLLoader.load(getClass().getResource("/gui/OrderPage.fxml"));
        Scene npage = new Scene(page);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(npage);
        window.setMaximized(true);
        window.show();
    }

    /**
     * Close handler
     * @param event
     */
    public void closeHandler(ActionEvent event) {
        Platform.exit();
    }


}
