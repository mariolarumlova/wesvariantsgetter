package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tools.PreferencesManager;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationController implements Initializable {

    @FXML
    Button firstUsageInstallButton;
    @FXML
    Button firstUsageSetupButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image imageInstall = new Image(getClass().getResourceAsStream("/images/download.png"));
        firstUsageInstallButton.setGraphic(new ImageView(imageInstall));
        Image imageBrowse = new Image(getClass().getResourceAsStream("/images/browse.png"));
        firstUsageSetupButton.setGraphic(new ImageView(imageBrowse));
    }

    public void onInstallButtonPressed(ActionEvent actionEvent) {
        //TODO: Create a method
        try {
            PreferencesManager.getInstance().setPreference("firstUsage", false, Boolean.class);
        } catch (PreferencesManager.UnsupportedTypeException e) {
            e.printStackTrace();
        }
    }

    public void onSetupButtonPressed(ActionEvent actionEvent) {
        //TODO: Create a method
        try {
            //te poniżej trzeba tu ustawić
//        programs_path = programsPath
//        miniconda3 = miniconda3Path
//        env_name = ngs
            PreferencesManager.getInstance().setPreference("firstUsage", false, Boolean.class);
        } catch (PreferencesManager.UnsupportedTypeException e) {
            e.printStackTrace();
        }
    }


}
