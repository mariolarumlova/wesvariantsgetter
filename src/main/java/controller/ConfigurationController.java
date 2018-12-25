package controller;

import javafx.event.ActionEvent;
import settings.PreferencesManager;

public class ConfigurationController {

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
            PreferencesManager.getInstance().setPreference("firstUsage", false, Boolean.class);
        } catch (PreferencesManager.UnsupportedTypeException e) {
            e.printStackTrace();
        }
    }
}
