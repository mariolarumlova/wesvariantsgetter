package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Config;
import tools.GuiHandler;
import tools.PreferencesManager;
import tools.PropertiesGetter;
import tools.YamlParser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    String propFileName;
    List<String> formatsFa;
    List<String> formatsFq;

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
    @FXML
    Button analysisDestButton;
    @FXML
    Button genomeDestButton;
    @FXML
    Button tumorForwardButton;
    @FXML
    Button tumorReverseButton;
    @FXML
    Button normalForwardButton;
    @FXML
    Button normalReverseButton;
    @FXML
    CheckBox prepareGenomeCheckBox;
    @FXML
    CheckBox removeDuplicatesCheckBox;
    @FXML
    TextField analysisDestTextField;
    @FXML
    TextField genomeDestTextField;
    @FXML
    TextField minPhredScoreTextField;
    @FXML
    TextField tumorForwardTextField;
    @FXML
    TextField tumorReverseTextField;
    @FXML
    TextField normalForwardTextField;
    @FXML
    TextField normalReverseTextField;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            propFileName = "labels_" + PreferencesManager.getInstance().getPreference("language", String.class);
            GuiHandler.getInstance().setComboBox(startPointComboBox, "startPointComboBox");
            GuiHandler.getInstance().setComboBox(endPointComboBox, "endPointComboBox");
            GuiHandler.getInstance().setComboBox(mapperComboBox, "mapperComboBox");
            GuiHandler.getInstance().setComboBox(variantCallerComboBox, "variantCallerComboBox");

            analysisDestTextField.setText(PreferencesManager.getInstance().getPreference("analysis_path", String.class));
            genomeDestTextField.setText(PreferencesManager.getInstance().getPreference("genome_path", String.class));
            minPhredScoreTextField.setText(PreferencesManager.getInstance().getPreference("min_phred_score", String.class));

            prepareGenomeCheckBox.setSelected(PreferencesManager.getInstance().getPreference("prepare_genome", Boolean.class));
            prepareGenomeCheckBox.setDisable(!prepareGenomeCheckBox.isSelected());
            removeDuplicatesCheckBox.setSelected(PreferencesManager.getInstance().getPreference("remove_duplicates", Boolean.class));

            formatsFa = new ArrayList<>();
            formatsFa.add("fasta");
            formatsFa.add("fa");

            formatsFq = new ArrayList<>();
            formatsFq.add("fastq");
            formatsFq.add("fq");

        } catch (IOException | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            e.printStackTrace();
            GuiHandler.getInstance().showWindow(e.getMessage());
        }
    }


    public void onGenomeDestButtonPressed(ActionEvent actionEvent) {
        System.out.println("genome dest");
        try {
            String path = GuiHandler.getInstance().chooseFilePath(formatsFa);
            genomeDestTextField.setText(path);
            prepareGenomeCheckBox.setDisable(false);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            e.printStackTrace();
        }
    }

    public void onAnalysisDestButtonPressed(ActionEvent actionEvent) {
        System.out.println("analysis dest");
    }

    public void onTumorForwardButtonPressed(ActionEvent actionEvent) {
        System.out.println("tumor fwd");
        try {
            String path = GuiHandler.getInstance().chooseFilePath(formatsFq);
            tumorForwardTextField.setText(path);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            e.printStackTrace();
        }
    }

    public void onTumorReverseButtonPressed(ActionEvent actionEvent) {
        System.out.println("tumor rev");
        try {
            String path = GuiHandler.getInstance().chooseFilePath(formatsFq);
            tumorReverseTextField.setText(path);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            e.printStackTrace();
        }
    }

    public void onNormalForwardButtonPressed(ActionEvent actionEvent) {
        System.out.println("normal fwd");
        try {
            String path = GuiHandler.getInstance().chooseFilePath(formatsFq);
            normalForwardTextField.setText(path);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            e.printStackTrace();
        }
    }

    public void onNormalReverseButtonPressed(ActionEvent actionEvent) {
        System.out.println("normal rev");
        try {
            String path = GuiHandler.getInstance().chooseFilePath(formatsFq);
            normalReverseTextField.setText(path);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            e.printStackTrace();
        }
    }

    public void onAnalyseButtonPressed(ActionEvent actionEvent) {
        System.out.println("ANALYSE!");
        try {
            Config config = makeConfigFile();
            if (config != null) {
                    YamlParser.write(config, PreferencesManager.getInstance().getPreference("analysis_path", String.class) + "testConfig.yaml");
                    //TODO: Kopiowanie Snakefile do analysis_path, może być w metodzie write
                    Stage stage = (Stage) analyseButton.getScene().getWindow();
                    Scene scene = RunApp.getScene( "AnalysisProgress");
                    stage.setScene(scene);
                    stage.show();
            } else {
                GuiHandler.getInstance().showWindow(PropertiesGetter.getValue(propFileName, "configMakingError"));
            }
        } catch (IOException | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.getMessage());
            e.printStackTrace();
        }
    }

    public Config makeConfigFile() throws IOException, PreferencesManager.IncorrectKeyException, PreferencesManager.UnsupportedTypeException {
        Config config = new Config();
        //TODO: Walidacja pól i utworzenie obiektu
        return config;
    }
}
