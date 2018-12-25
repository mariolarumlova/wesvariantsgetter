import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import settings.PreferencesManager;
import settings.PropertiesGetter;

import java.io.IOException;
import java.util.ResourceBundle;

public class RunApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        primaryStage.setTitle("WES pipeline ver " + PropertiesGetter.getValue("application", "version"));
//        Boolean firstUsage = Boolean.parseBoolean(settings.PropertiesGetter.getValue("firstUsage"));
        Boolean firstUsage = PreferencesManager.getInstance().getPreference("firstUsage", Boolean.class);
        String language = PreferencesManager.getInstance().getPreference("language", String.class);
        Scene scene = getScene(firstUsage, language);
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (IOException | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            e.printStackTrace();
        }
    }

    public Scene getScene(Boolean firstUsage, String language) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("labels_" + language));
        String path = firstUsage ? "\\view\\Configuration.fxml" : "\\view\\MainWindow.fxml";
        Parent root = loader.load(getClass().getResourceAsStream(path));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("\\bootstrap3.css").toExternalForm());
        return scene;
    }
}
