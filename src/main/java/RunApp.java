import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import settings.GuiHandler;
import settings.PreferencesManager;
import settings.PropertiesGetter;

import java.io.IOException;

public class RunApp extends Application {

    private static FXMLLoader loader;

    private static volatile RunApp instance;

    private RunApp() {}

    public static RunApp getInstance() {
        if (instance == null) {
            synchronized (RunApp.class) {
                if (instance == null) {
                    instance = new RunApp();
                }
            }
        }
        return instance;
    }

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
        Scene scene = GuiHandler.getInstance().getScene(loader, language, name);
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
}
