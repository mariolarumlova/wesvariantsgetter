import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import settings.GuiHandler;
import settings.PreferencesManager;
import settings.PropertiesGetter;

import java.io.IOException;
import java.util.ResourceBundle;

public class RunApp extends Application {

    private static FXMLLoader loader;

//    private static volatile RunApp instance;
//
//    private RunApp() {}
//
//    public static RunApp getInstance() {
//        if (instance == null) {
//            synchronized (RunApp.class) {
//                if (instance == null) {
//                    instance = new RunApp();
//                }
//            }
//        }
//        return instance;
//    }

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
        String name = firstUsage ? "Configuration" : "MainWindow";
        loader = new FXMLLoader();
        Scene scene = getScene(loader, language, name);
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (IOException | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.getMessage());
        }
    }

    public static FXMLLoader getLoader() {
        return loader;
    }

    public static void setLoader(FXMLLoader loader) {
        RunApp.loader = loader;
    }

    public Scene getScene(FXMLLoader loader, String language, String name) throws IOException {
        int[] sceneSize = GuiHandler.getInstance().getSceneSize();
        int sceneWidth = sceneSize[0];
        int sceneHeight = sceneSize[1];

        loader.setResources(ResourceBundle.getBundle("labels_" + language));
        Parent root = loader.load(getClass().getResourceAsStream("\\view\\" + name + ".fxml"));
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("\\bootstrap3.css").toExternalForm());

        return scene;
    }
}
