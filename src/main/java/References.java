package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public enum References implements Navigator {
    //fxml filepath's
    MAIN_ADMIN("./main/resources/fxml/MainAdmin.fxml", "Main (Admin)"),
    MAIN_EMPLOYEE("./main/resources/fxml/MainEmployee.fxml", "Main (Employee)"),
    LOGIN("./main/resources/fxml/Login.fxml", "Login"),
    INVENTORY_SHEET_ADMIN("./main/resources/fxml/InventorySheetAdmin.fxml", "Inventory Sheet (Admin)"),
    INVENTORY_SHEET_EMPLOYEE("./main/resources/fxml/InventorySheetEmployee.fxml", "Inventory Sheet (Employee)"),
    ORDERS_PAGE("./main/resources/fxml/OrderPage.fxml", "Orders"),
    USERS_PAGE("./main/resources/fxml/UsersPage.fxml", "Users"),
    CONFIRM_ORDER_PAGE("./main/resources/fxml/ConfirmOrder.fxml", "Confirm Order"),

    //txt filepath's
    ACCOUNTS("./src/main/resources/txt/accounts.txt", "accounts"),
    INVENTORY("./src/main/resources/txt/inventory.txt", "inventory"),
    ORDER_HISTORY("./src/main/resources/txt/orderHistory", "order history");

    private final String filepath;
    private final String title;

    References(String filepath, String title) {
        this.filepath = filepath;
        this.title = title;
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

    public void refresh() throws IOException {
        Parent page = FXMLLoader.load(getClass().getClassLoader().getResource(filepath));
        Scene scene = new Scene(page);
        Main.primaryStage.setScene(scene);
    }
}
