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
//        Boolean firstUsage = Boolean.parseBoolean(tools.PropertiesGetter.getValue("firstUsage"));
        Boolean firstUsage = PreferencesManager.getInstance().getPreference("firstUsage", Boolean.class);
        setDefaultPaths();
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

    public void setDefaultPaths() throws PreferencesManager.UnsupportedTypeException, Exception {
        

//        
//        //metoda ktora skopiuje do mainDir rules i scrips z jara

//        URL resource = RunApp.class.getClassLoader().getResource("rules/annotate.smk");
//        File file = Paths.get(resource.toURI()).toFile();
//        String rulesPath = file.getParent();
        String[] rulesNamesArray = new String[] {"rules/annotate.smk", "rules/call_bcftools.smk", "rules/call_fasd_somatic.smk",
                "rules/call_snv_sniffer.smk", "rules/map_bowtie2.smk", "rules/map_bwa.smk",
                "rules/mark_duplicates.smk", "rules/prepare_genome.smk", "rules/quality_filtering.smk",
                "rules/realign.smk", "rules/remove_adapters.smk", "rules/sort.smk"};
        List<String> rulesNames = Arrays.asList(rulesNamesArray);
        List<String> rulesPaths = new ArrayList<>();
        //String rulesPath = exportResource("rules/annotate.smk");
        for (String resourceName : rulesNames) {
            rulesPaths.add(exportResource(resourceName));
        }
        System.out.println("Rules :\n" + rulesPaths.toString());
        //PreferencesManager.getInstance().setPreference("rules_path", rulesPath, String.class);
        
//        resource = RunApp.class.getClassLoader().getResource("/config_example.yaml");
//        System.out.println(resource.toURI().toString());
//        file = Paths.get(resource.toURI()).toFile();
//        String resourcesPath = file.getParent();


//        String resourcesPath = exportResource("/config_example.yaml");
//        PreferencesManager.getInstance().setPreference("resources_path", resourcesPath, String.class);


    }

    /**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName ie.: "/SmartLibrary.dll"
     * @return The path to the exported resource
     * @throws Exception
     */
    public String exportResource(String resourceName) throws Exception {
//        File tmpDir = new File(System.getProperty("tmp.dir"));
//
//        File mainDir = new File(tmpDir, "xdir");
//        mainDir.mkdir();
//        if (mainDir.exists()) {
//            if (mainDir.isDirectory()) {
//
//            }
//        }

        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        File dir = null;
        try {
            stream = RunApp.class.getClassLoader().getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(RunApp.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            dir = new File(jarFolder, "\\rules");
            dir.mkdir();
            File file = new File(jarFolder + "\\" + resourceName);
            file.createNewFile();
            resStreamOut = new FileOutputStream(jarFolder + "\\" + resourceName, false);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        }
//        } finally {
//            //stream.close();
//            //resStreamOut.close();
//        }

        return jarFolder + resourceName;
    }

}
