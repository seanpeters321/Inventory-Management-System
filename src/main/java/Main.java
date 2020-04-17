package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * <b>The main class of this program</b>
 *
 * @author Sean Peters
 * @version 1.7.3, 04/16/2020
 */
public class Main extends Application {
    public static Animations animations = new Animations();
    // Inherited Variables
    public static User user;
    public static Stage primaryStage;
    public static boolean decision;
    // Filepath's
    public final String INVENTORY = References.INVENTORY.getFilepath();
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
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/main/resources/icons/Ingot 12.png")));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
