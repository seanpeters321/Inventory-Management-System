package main;

import gui.LoginController;
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
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginController cont = new LoginController();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
            Scene scene = new Scene(root, 500, 400);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/icons/Ingot 12.png")));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
