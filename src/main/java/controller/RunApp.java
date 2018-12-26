package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tools.GuiHandler;
import tools.PreferencesManager;
import tools.PropertiesGetter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class RunApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        primaryStage.setTitle("WES pipeline ver " + PropertiesGetter.getValue("application", "version"));
//        Boolean firstUsage = Boolean.parseBoolean(tools.PropertiesGetter.getValue("firstUsage"));
        Boolean firstUsage = PreferencesManager.getInstance().getPreference("firstUsage", Boolean.class);
        setDefaultPaths();
        String name = firstUsage ? "Configuration" : "MainWindow";
        Scene scene = getScene(name);
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (Exception | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.toString());
        }
    }

    public static Scene getScene(String name) throws IOException, PreferencesManager.UnsupportedTypeException, PreferencesManager.IncorrectKeyException {
        int[] sceneSize = GuiHandler.getInstance().getSceneSize();
        int sceneWidth = sceneSize[0];
        int sceneHeight = sceneSize[1];

        String language = PreferencesManager.getInstance().getPreference("language", String.class);
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("labels_" + language));
        Parent root = loader.load(RunApp.class.getResourceAsStream("/view/" + name + ".fxml"));
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add(RunApp.class.getClassLoader().getResource("\\bootstrap3.css").toExternalForm());

        return scene;
    }

    public void setDefaultPaths() throws PreferencesManager.UnsupportedTypeException, URISyntaxException {
        URL resource = RunApp.class.getClassLoader().getResource("rules/annotate.smk");
        File file = Paths.get(resource.toURI()).toFile();
        String resourcesPath = file.getParent();
        PreferencesManager.getInstance().setPreference("resources_path", resourcesPath, String.class);

        resource = RunApp.class.getClassLoader().getResource("config_example.yaml");
        file = Paths.get(resource.toURI()).toFile();
        String rulesPath = file.getParent();
        PreferencesManager.getInstance().setPreference("rules_path", rulesPath, String.class);
    }
}
