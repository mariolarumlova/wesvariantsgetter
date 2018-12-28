package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tools.GuiHandler;
import tools.PreferencesManager;
import tools.PropertiesGetter;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.*;

public class RunApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        primaryStage.setTitle("WES pipeline ver " + PropertiesGetter.getValue("application", "version"));
        Boolean firstUsage = PreferencesManager.getInstance().getPreference("firstUsage", Boolean.class);
        copyResourcesAndSetDefaultPaths();
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

        return scene;
    }

    public void copyResourcesAndSetDefaultPaths() throws PreferencesManager.UnsupportedTypeException, Exception {

        String[] rulesNamesArray = new String[] {"annotate.smk", "call_bcftools.smk", "call_fasd_somatic.smk",
                "call_snv_sniffer.smk", "map_bowtie2.smk", "map_bwa.smk",
                "mark_duplicates.smk", "prepare_genome.smk", "quality_filtering.smk",
                "realign.smk", "remove_adapters.smk", "sort.smk"};
        List<String> rulesNames = Arrays.asList(rulesNamesArray);
        String rulesPath = "";
        for (String resourceName : rulesNames) {
            rulesPath = exportResource(resourceName, "rules");
        }
        PreferencesManager.getInstance().setPreference("rules_path", rulesPath, String.class);

        String[] scriptsNamesArray = new String[] {"analyse.sh", "indexing.sh", "installing.sh"};
        List<String> scriptsNames = Arrays.asList(scriptsNamesArray);
        String scriptsPath = "";
        for (String resourceName : scriptsNames) {
            scriptsPath = exportResource(resourceName, "scripts");
        }
        PreferencesManager.getInstance().setPreference("scripts_path", scriptsPath, String.class);

        exportResource("environment.yaml", null);
        String resourcesPath = exportResource("Snakefile", null);
        PreferencesManager.getInstance().setPreference("resources_path", resourcesPath, String.class);

    }

    /**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName ie.: "/SmartLibrary.dll"
     * @return The path to the exported resource
     * @throws Exception
     */
    public String exportResource(String resourceName, String subdirectory) throws Exception {

        String delimiter = "/";
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        File dir = null;
        String resourceRelPath = subdirectory!= null ? subdirectory + "/" + resourceName : resourceName;
        try {
            stream = RunApp.class.getClassLoader().getResourceAsStream(resourceRelPath);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }
            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(RunApp.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            if (subdirectory != null) {
                dir = new File(jarFolder, delimiter + subdirectory);
                dir.mkdir();
            }
            File file = new File(jarFolder + delimiter + resourceRelPath);
            file.createNewFile();
            resStreamOut = new FileOutputStream(jarFolder + delimiter + resourceRelPath, false);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return subdirectory!= null ? jarFolder + delimiter + subdirectory + delimiter : jarFolder + delimiter;
    }

}
