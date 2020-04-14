package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public enum References implements Navigator {
    //fxml filepath's
    MAIN_ADMIN("./main/resources/fxml/MainAdmin.fxml"),
    MAIN_EMPLOYEE("./main/resources/fxml/MainEmployee.fxml"),
    LOGIN("./main/resources/fxml/Login.fxml"),
    INVENTORY_SHEET_ADMIN("./main/resources/fxml/InventorySheetAdmin.fxml"),
    INVENTORY_SHEET_EMPLOYEE("./main/resources/fxml/InventorySheetEmployee.fxml"),
    ORDERS_PAGE("./main/resources/fxml/OrderPage.fxml"),
    USERS_PAGE("./main/resources/fxml/UsersPage.fxml"),

    //txt filepath's
    ACCOUNTS("./src/main/resources/txt/accounts.txt"),
    INVENTORY("./src/main/resources/txt/inventory.txt"),
    ORDER_HISTORY("./src/main/resources/txt/orderHistory");

    private final String filepath;

    References(String filepath) {
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public void goTo() throws IOException {
        Parent page = FXMLLoader.load(getClass().getClassLoader().getResource(filepath));
        Scene scene = new Scene(page);
        Stage window = Main.primaryStage;
        window.setScene(scene);
        window.setMaximized(true);
        if (filepath == LOGIN.filepath) {
            window.centerOnScreen();
        }

        window.show();
    }

    public void refresh() throws IOException {
        Parent page = FXMLLoader.load(getClass().getClassLoader().getResource(filepath));
        Scene scene = new Scene(page);
        Main.primaryStage.setScene(scene);
    }
}
