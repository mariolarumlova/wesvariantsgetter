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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
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
        //setDefaultPaths();
        String name = firstUsage ? "Configuration" : "MainWindow";
        Scene scene = getScene(name);
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (Exception | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
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
        //scene.getStylesheets().add(RunApp.class.getClassLoader().getResource("\\bootstrap3.css").toExternalForm());

        return scene;
    }

    public void setDefaultPaths() throws PreferencesManager.UnsupportedTypeException, URISyntaxException, IOException {
        
//        File tmpDir = new File(System.getProperty("tmp.dir"));
//        
//        File mainDir = new File(tmpDir, "xdir");
//        mainDir.mkdir();
//        
//        mainDir.exists();
//        mainDir.isDirectory();
//        
//        //metoda ktora skopiuje do mainDir rules i scrips z jara

//        resource = RunApp.class.getClassLoader().getResource("rules/annotate.smk");
//        file = Paths.get(resource.toURI()).toFile();
        File file = getFile("rules/annotate.smk");
        String rulesPath = file.getParent();
        PreferencesManager.getInstance().setPreference("rules_path", rulesPath, String.class);
        
//        URL resource = RunApp.class.getClassLoader().getResource("config_example.yaml");
//        System.out.println(resource.toURI().toString());
//        File file = Paths.get(resource.toURI()).toFile();
        file = getFile("config_example.yaml");
        String resourcesPath = file.getParent();
        PreferencesManager.getInstance().setPreference("resources_path", resourcesPath, String.class);


    }
    
    private File getFile(String resourcesFileName) throws URISyntaxException, IOException {
        URI uri = RunApp.class.getClassLoader().getResource(resourcesFileName).toURI();

        if("jar".equals(uri.getScheme())){
            for (FileSystemProvider provider: FileSystemProvider.installedProviders()) {
                if (provider.getScheme().equalsIgnoreCase("jar")) {
                    try {
                        provider.getFileSystem(uri);
                    } catch (FileSystemNotFoundException e) {
                        // in this case we need to initialize it first:
                        provider.newFileSystem(uri, Collections.emptyMap());
                    }
                }
            }
        }
        return Paths.get(uri).toFile();
//        java.lang.UnsupportedOperationException
//	at com.sun.nio.zipfs.ZipPath.toFile(ZipPath.java:597)
//	at controller.RunApp.getFile(RunApp.java:104)
//	at controller.RunApp.setDefaultPaths(RunApp.java:75)
//	at controller.RunApp.start(RunApp.java:35)
//	at com.sun.javafx.application.LauncherImpl.lambda$launchApplication1$161(LauncherImpl.java:863)
//	at com.sun.javafx.application.PlatformImpl.lambda$runAndWait$174(PlatformImpl.java:326)
//	at com.sun.javafx.application.PlatformImpl.lambda$null$172(PlatformImpl.java:295)
//	at java.security.AccessController.doPrivileged(Native Method)
//	at com.sun.javafx.application.PlatformImpl.lambda$runLater$173(PlatformImpl.java:294)
//	at com.sun.glass.ui.InvokeLaterDispatcher$Future.run(InvokeLaterDispatcher.java:95)
//	at com.sun.glass.ui.gtk.GtkApplication._runLoop(Native Method)
//	at com.sun.glass.ui.gtk.GtkApplication.lambda$null$48(GtkApplication.java:139)
//	at java.lang.Thread.run(Thread.java:748)

    }
}
