package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tools.GuiHandler;
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
        try {
            //TODO: Odpalanie skryptu instalacyjnego
            Stage stage = (Stage) firstUsageInstallButton.getScene().getWindow();
            Scene scene = RunApp.getScene( "Progress");
            stage.setScene(scene);
            stage.show();
            PreferencesManager.getInstance().setPreference("firstUsage", false, Boolean.class);
        } catch (Exception | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onSetupButtonPressed(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) firstUsageSetupButton.getScene().getWindow();
            Scene scene = RunApp.getScene( "SettingPaths");
            stage.setScene(scene);
            stage.show();
        } catch (Exception | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }


}
