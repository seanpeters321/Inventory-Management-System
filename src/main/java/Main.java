package main.java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.controllers.InventorySheetController;

import java.io.IOException;


/**
 * <b>The main class of this program</b>
 *
 * @author Sean Peters
 * @version 1.7.3, 04/16/2020
 */
public class Main extends Application {
    /**
     * Iteration of the Animation class
     */
    public static Animations animations = new Animations();
    // Inherited Variables
    /**
     * User object that represents the current logged in user
     */
    public static User user;
    /**
     * The primary stage that the program is displayed
     */
    public static Stage primaryStage;
    /**
     * Stores a confirmation value for the ConfirmBox
     */
    public static boolean decision, isDark = false;
    // Filepath's
    /**
     * Quick reference to the inventory text document
     */
    public final String INVENTORY = References.INVENTORY.getFilepath();
    /**
     * Quick reference to the accounts text document
     */
    public final String ACCOUNTS = References.ACCOUNTS.getFilepath();

    /**
     * <b>Launches the program</b>
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    /**
     * <b>Sets up the GUI</b>
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            this.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/Login.fxml"));
            Scene scene = new Scene(root, 500, 400);
            if(isDark == true){
                scene.getStylesheets().add(References.DARK_THEME.getFilepath());
            }else {
                scene.getStylesheets().add(References.LIGHT_THEME.getFilepath());
            }
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/main/resources/icons/Ingot 12.png")));
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.DECORATED);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
