package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tools.GuiHandler;
import tools.PreferencesManager;
import tools.PropertiesGetter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    String propFileName;

    @FXML
    Button analyseButton;
    @FXML
    ComboBox startPointComboBox;
    @FXML
    ComboBox endPointComboBox;
    @FXML
    ComboBox mapperComboBox;
    @FXML
    ComboBox variantCallerComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            propFileName = "labels_" + PreferencesManager.getInstance().getPreference("language", String.class);
            setComboBox(startPointComboBox, "startPointComboBox");
            setComboBox(endPointComboBox, "endPointComboBox");
            setComboBox(mapperComboBox, "mapperComboBox");
            setComboBox(variantCallerComboBox, "variantCallerComboBox");

        } catch (IOException | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            e.printStackTrace();
            GuiHandler.getInstance().showWindow(e.getMessage());
        }
    }


    public void onGenomeDestButtonPressed(ActionEvent actionEvent) {
        System.out.println("genome dest");
    }

    public void onAnalysisDestButtonPressed(ActionEvent actionEvent) {
        System.out.println("analysis dest");
    }

    public void onTumorForwardButtonPressed(ActionEvent actionEvent) {
        System.out.println("tumor fwd");
    }

    public void onTumorReverseButtonPressed(ActionEvent actionEvent) {
        System.out.println("tumor rev");
    }

    public void onNormalForwardButtonPressed(ActionEvent actionEvent) {
        System.out.println("normal fwd");
    }

    public void onNormalReverseButtonPressed(ActionEvent actionEvent) {
        System.out.println("normal rev");
    }

    public void onAnalyseButtonPressed(ActionEvent actionEvent) {
        System.out.println("ANALYSE!");
        try {
            Stage stage = (Stage) analyseButton.getScene().getWindow();
            Scene scene = RunApp.getScene( "AnalysisProgress");
            stage.setScene(scene);
            stage.show();
        } catch (IOException | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.getMessage());
            e.printStackTrace();
        }
    }

    public void setComboBox(ComboBox comboBox, String name) throws IOException {
        ObservableList<String> options =
                FXCollections.observableArrayList(PropertiesGetter.getValue(propFileName, name).split("#"));
        comboBox.setItems(options);
        comboBox.getSelectionModel().selectFirst();
    }
}
