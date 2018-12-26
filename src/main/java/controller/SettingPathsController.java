package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tools.GuiHandler;
import tools.PreferencesManager;
import tools.PropertiesGetter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingPathsController implements Initializable {

    @FXML
    TextField minicondaPathTextField;
    @FXML
    TextField programsPathTextField;
    @FXML
    TextField envNameTextField;
    @FXML
    Button minicondaPathButton;
    @FXML
    Button programsPathButton;
    @FXML
    Button saveButton;
    @FXML
    Button languageButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image imageLanguage = new Image(getClass().getResourceAsStream("/images/language.png"));
            languageButton.setGraphic(new ImageView(imageLanguage));
            Image imageAccept = new Image(getClass().getResourceAsStream("/images/ok.png"));
            saveButton.setGraphic(new ImageView(imageAccept));

            minicondaPathTextField.setText(PreferencesManager.getInstance().getPreference("miniconda3", String.class));
            programsPathTextField.setText(PreferencesManager.getInstance().getPreference("programs_path", String.class));
            envNameTextField.setText(PreferencesManager.getInstance().getPreference("env_name", String.class));
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }


    public void onMinicondaPathButtonPressed(ActionEvent actionEvent) {
        try {
            minicondaPathTextField.setText(GuiHandler.getInstance().chooseDirectoryPath());
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onProgramsPathButtonPressed(ActionEvent actionEvent) {
        try {
            programsPathTextField.setText(GuiHandler.getInstance().chooseDirectoryPath());
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onSaveButtonPressed(ActionEvent actionEvent) {
        try {
            String envName = envNameTextField.getText();
            if (envName.toLowerCase().matches("[a-z]+")) {
                PreferencesManager.getInstance().setPreference("env_name", envName, String.class);
                PreferencesManager.getInstance().setPreference("miniconda3", minicondaPathTextField.getText(), String.class);
                PreferencesManager.getInstance().setPreference("programs_path", programsPathTextField.getText(), String.class);
                PreferencesManager.getInstance().setPreference("firstUsage", false, Boolean.class);
                Stage stage = (Stage) saveButton.getScene().getWindow();
                Scene scene = RunApp.getScene( "MainWindow");
                stage.setScene(scene);
                stage.show();
            } else throw new IncorrectEnvironmentNameException();
        } catch (Exception | PreferencesManager.IncorrectKeyException | IncorrectEnvironmentNameException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onLanguageButtonPressed(ActionEvent actionEvent) {
        try {
            String oldLanguage = PreferencesManager.getInstance().getPreference("language", String.class);
            String newLanguage = oldLanguage.equals("pl") ? "en" : "pl";
            PreferencesManager.getInstance().setPreference("language", newLanguage, String.class);

            Stage stage = (Stage) languageButton.getScene().getWindow();
            Scene scene = RunApp.getScene( "SettingPaths");
            stage.setScene(scene);
            stage.show();
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    private class IncorrectEnvironmentNameException extends Throwable {
        @Override
        public String toString() {
            try {
                String propFileName = "labels_" + PropertiesGetter.getValue("application", "language");
                return PropertiesGetter.getValue(propFileName, "incorrectEnvironmentNameException");
            } catch (IOException e) {
                return "IncorrectEnvironmentNameException{}";
            }
        }
    }
}
