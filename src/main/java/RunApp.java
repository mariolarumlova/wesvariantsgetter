import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class RunApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        primaryStage.setTitle("WES pipeline ver 1.0");
//        PropertiesGetter propertiesGetter = new PropertiesGetter();
//        Boolean firstUsage = Boolean.parseBoolean(propertiesGetter.getValue("firstUsage"));
        Boolean firstUsage = PreferencesManager.getInstance().getPreference("firstUsage", Boolean.class);
        Scene scene = getScene(firstUsage);
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (IOException | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            e.printStackTrace();
        }
    }

    public Scene getScene(Boolean firstUsage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("application"));
        Parent root;
        if (firstUsage) {
            root = loader.load(getClass().getResourceAsStream("\\view\\Configuration.fxml"));
        } else {
            root = loader.load(getClass().getResourceAsStream("\\view\\MainWindow.fxml"));
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("\\bootstrap3.css").toExternalForm());
        return scene;
    }
}
