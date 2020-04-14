import javafx.application.Application;
import javafx.stage.Stage;
import main.java.FileEditor;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Sean\\IdeaProjects\\Metels Inc. Inventory Management System\\tests\\main\\hello";
        FileEditor fileEditor = new FileEditor();
        fileEditor.modifyFile(filePath, "goodbye", "hello");

    }
}
