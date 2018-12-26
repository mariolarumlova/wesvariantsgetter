package tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiHandler {

    private static volatile GuiHandler instance;

    private GuiHandler() {}

    public static GuiHandler getInstance() {
        if (instance == null) {
            synchronized (GuiHandler.class) {
                if (instance == null) {
                    instance = new GuiHandler();
                }
            }
        }
        return instance;
    }

    public int[] getSceneSize() {
        int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
        int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

        int sceneWidth = 0;
        int sceneHeight = 0;
        if (screenWidth <= 800 && screenHeight <= 600) {
            sceneWidth = 700;
            sceneHeight = 350;
        } else if (screenWidth <= 1280 && screenHeight <= 768) {
            sceneWidth = 900;
            sceneHeight = 450;
        } else if (screenWidth <= 1920 && screenHeight <= 1080) {
            sceneWidth = 1100;
            sceneHeight = 650;
        }

        int[] out = {sceneWidth, sceneHeight};

        return out;
    }

    public void showWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public void setComboBox(ComboBox comboBox, String name) throws IOException, PreferencesManager.UnsupportedTypeException, PreferencesManager.IncorrectKeyException {
        String propFileName = "labels_" + PreferencesManager.getInstance().getPreference("language", String.class);
        ObservableList<String> options =
                FXCollections.observableArrayList(PropertiesGetter.getValue(propFileName, name).split("#"));
        comboBox.setItems(options);
        comboBox.getSelectionModel().selectFirst();
    }

    public String chooseFilePath(List<String> extensions) throws PreferencesManager.IncorrectKeyException, IOException, PreferencesManager.UnsupportedTypeException {
        String propFileName = "labels_" + PreferencesManager.getInstance().getPreference("language", String.class);
        FileChooser fileChooser = new FileChooser();
        Stage chooseSource = new Stage();
        fileChooser.setTitle(PropertiesGetter.getValue(propFileName, "chooseDirectoryLabel"));
        fileChooser.setInitialDirectory(new File(PreferencesManager.getInstance().getPreference("analysis_path", String.class)));
        List<FileChooser.ExtensionFilter> extFilters = new ArrayList<>();
        for (String ext: extensions) {
            extFilters.add(new FileChooser.ExtensionFilter(ext.toUpperCase(), "*." + ext.toLowerCase()));
        }
        fileChooser.getExtensionFilters().addAll(extFilters);
        File file = fileChooser.showOpenDialog(chooseSource);
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            showWindow(PropertiesGetter.getValue(propFileName, "chooseDirectoryError"));
            return "";
        }
    }

    public String chooseDirectoryPath() throws PreferencesManager.IncorrectKeyException, IOException, PreferencesManager.UnsupportedTypeException {
        String propFileName = "labels_" + PreferencesManager.getInstance().getPreference("language", String.class);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        directoryChooser.setTitle(PropertiesGetter.getValue(propFileName, "chooseDirectoryLabel"));
        directoryChooser.setInitialDirectory(new File(PreferencesManager.getInstance().getPreference("analysis_path", String.class)));
        File file = directoryChooser.showDialog(stage);

        if (file != null) {
            //TODO: Sprawdź, czy w linuxie jest na pewno "/"
            String delimiter = "\\";
            return file.getAbsolutePath() + delimiter;
        } else {
            showWindow(PropertiesGetter.getValue(propFileName, "chooseDirectoryError"));
            return "";
        }
    }
}
