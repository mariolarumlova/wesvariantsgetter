package controller;

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
        String name = firstUsage ? "Configuration" : "MainWindow";
        Scene scene = getScene(language, name);
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (IOException | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.getMessage());
        }
    }

    public static Scene getScene(String language, String name) throws IOException {
        int[] sceneSize = GuiHandler.getInstance().getSceneSize();
        int sceneWidth = sceneSize[0];
        int sceneHeight = sceneSize[1];

        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("labels_" + language));
        Parent root = loader.load(RunApp.class.getResourceAsStream("/view/" + name + ".fxml"));
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add(RunApp.class.getClassLoader().getResource("\\bootstrap3.css").toExternalForm());

        return scene;
    }
}
