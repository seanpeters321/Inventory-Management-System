package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * <b>Enum that stores all filepath's</b>
 * <p>
 * Contains methods that allow for the user to traverse between requested pages.
 *
 * @author Sean Peters
 */
public enum References {
    //fxml filepath's
    MAIN("main/resources/fxml/MainPage.fxml", "Main"),
    LOGIN("./main/resources/fxml/Login.fxml", "Login"),
    INVENTORY_SHEET("main/resources/fxml/InventorySheet.fxml", "Inventory Sheet"),
    ORDERS_PAGE("./main/resources/fxml/OrderPage.fxml", "Orders"),
    USERS_PAGE("./main/resources/fxml/UsersPage.fxml", "Users"),
    CONFIRM_ORDER_PAGE("./main/resources/fxml/ConfirmOrder.fxml", "Confirm Order"),
    CONFIRM_BOX("./main/resources/fxml/ConfirmBox.fxml", "Confirm Box"),

    //txt filepath's
    ACCOUNTS("./src/main/resources/txt/accounts.txt", "accounts"),
    INVENTORY("./src/main/resources/txt/inventory.txt", "inventory");

    private final String filepath;
    private final String title;

    References(String filepath, String title) {
        this.filepath = filepath;
        this.title = title;
    }

    public String getFilepath() {
        return filepath;
    }


    // Methods

    /**
     * Changes the scene to the requested fxml file.
     *
     * @throws IOException
     */
    public void goTo() throws IOException {
        Parent page = FXMLLoader.load(getClass().getClassLoader().getResource(filepath));
        Scene scene;
        Stage window = Main.primaryStage;
        if (filepath == LOGIN.filepath) {
            scene = new Scene(page, 500, 400);
        } else {
            scene = new Scene(page, 1281, 793);
        }
        window.setScene(scene);
        window.centerOnScreen();
        window.show();
    }

    /**
     * Creates a new pop-up window for ConfirmBox and ConfirmOrder pages.
     *
     * @throws IOException
     */
    public void popOut() throws IOException {
        Parent page = FXMLLoader.load(getClass().getClassLoader().getResource(filepath));
        Scene scene = new Scene(page);
        Stage window = new Stage();
        window.setResizable(false);
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.centerOnScreen();
        window.setScene(scene);
        window.showAndWait();
    }
}
