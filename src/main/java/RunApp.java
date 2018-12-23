import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RunApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplikacja");
        Scene scene = new Scene(new Label("This is a test label"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
