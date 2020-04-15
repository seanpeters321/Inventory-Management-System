package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * The main class of this program
 *
 * @author Sean Peters
 * @version 1.0.0, 02/23/2020
 */
public class Main extends Application {
    public static Animations animations = new Animations();
    // Inherited Variables
    public static User user;
    public static Stage primaryStage;
    // Filepath's
    public final String INVENTORY = References.INVENTORY.getFilepath();
    public final String ACCOUNTS = References.ACCOUNTS.getFilepath();
    public final String ORDER_HISTORY = References.ORDER_HISTORY.getFilepath();

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        FileEditor fileEditor = new FileEditor();
        fileEditor.getInventory();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            this.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/Login.fxml"));
            Scene scene = new Scene(root, 500, 400);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/main/resources/icons/Ingot 12.png")));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(Stage window) {
        window.close();
    }

}
