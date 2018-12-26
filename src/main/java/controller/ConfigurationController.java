package controller;

import javafx.event.ActionEvent;
import tools.PreferencesManager;

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
