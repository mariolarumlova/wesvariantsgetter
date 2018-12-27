package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Config;
import org.apache.commons.io.FileUtils;
import tools.GuiHandler;
import tools.PreferencesManager;
import tools.PropertiesGetter;
import tools.YamlParser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class MainWindowController implements Initializable {

    String propFileName;
    List<String> formatsFa;
    List<String> formatsFq;

    String tumorForwardWhole;
    String tumorReverseWhole;
    String normalForwardWhole;
    String normalReverseWhole;

    @FXML
    Button changeConfigurationButton;
    @FXML
    Button languageButton;
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
            setDefaultValueStartPointComboBox();
            GuiHandler.getInstance().setComboBox(endPointComboBox, "endPointComboBox");
            setDefaultValueEndPointComboBox();
            GuiHandler.getInstance().setComboBox(mapperComboBox, "mapperComboBox");
            setDefaultValueMapperComboBox();
            GuiHandler.getInstance().setComboBox(variantCallerComboBox, "variantCallerComboBox");
            setDefaultValueVariantCallerComboBox();

            analysisDestTextField.setText(PreferencesManager.getInstance().getPreference("analysis_path", String.class));
            genomeDestTextField.setText(PreferencesManager.getInstance().getPreference("genome_path", String.class));
            minPhredScoreTextField.setText(PreferencesManager.getInstance().getPreference("min_phred_score", String.class));

            prepareGenomeCheckBox.setSelected(PreferencesManager.getInstance().getPreference("prepare_genome", Boolean.class));
            prepareGenomeCheckBox.setDisable(!prepareGenomeCheckBox.isSelected());
            removeDuplicatesCheckBox.setSelected(PreferencesManager.getInstance().getPreference("remove_duplicates", Boolean.class));

            Image imageSettings = new Image(getClass().getResourceAsStream("/images/settings.png"));
            changeConfigurationButton.setGraphic(new ImageView(imageSettings));
            Image imageLanguage = new Image(getClass().getResourceAsStream("/images/language.png"));
            languageButton.setGraphic(new ImageView(imageLanguage));
            Image imageAccept = new Image(getClass().getResourceAsStream("/images/ok.png"));
            analyseButton.setGraphic(new ImageView(imageAccept));

            formatsFa = new ArrayList<>();
            formatsFa.add("fasta");
            formatsFa.add("fa");

            formatsFq = new ArrayList<>();
            formatsFq.add("fastq");
            formatsFq.add("fq");

        } catch (IOException | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onChangeConfigurationButtonPressed(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) changeConfigurationButton.getScene().getWindow();
            Scene scene = RunApp.getScene( "Configuration");
            stage.setScene(scene);
            stage.show();
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onLanguageButtonPressed(ActionEvent actionEvent) {
        try {
            String oldLanguage = PreferencesManager.getInstance().getPreference("language", String.class);
            String newLanguage = oldLanguage.equals("pl") ? "en" : "pl";
            PreferencesManager.getInstance().setPreference("language", newLanguage, String.class);

            Stage stage = (Stage) changeConfigurationButton.getScene().getWindow();
            Scene scene = RunApp.getScene( "MainWindow");
            stage.setScene(scene);
            stage.show();
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onGenomeDestButtonPressed(ActionEvent actionEvent) {
        System.out.println("genome dest");
        try {
            genomeDestTextField.setText(GuiHandler.getInstance().chooseFilePath(formatsFa));
            prepareGenomeCheckBox.setDisable(false);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onAnalysisDestButtonPressed(ActionEvent actionEvent) {
        System.out.println("analysis dest");
        try {
            analysisDestTextField.setText(GuiHandler.getInstance().chooseDirectoryPath());
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onTumorForwardButtonPressed(ActionEvent actionEvent) {
        System.out.println("tumor fwd");
        try {
            tumorForwardTextField.setText(GuiHandler.getInstance().chooseFilePath(formatsFq));
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onTumorReverseButtonPressed(ActionEvent actionEvent) {
        System.out.println("tumor rev");
        try {
            tumorReverseTextField.setText(GuiHandler.getInstance().chooseFilePath(formatsFq));
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onNormalForwardButtonPressed(ActionEvent actionEvent) {
        System.out.println("normal fwd");
        try {
            normalForwardTextField.setText(GuiHandler.getInstance().chooseFilePath(formatsFq));
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onNormalReverseButtonPressed(ActionEvent actionEvent) {
        System.out.println("normal rev");
        try {
            normalReverseTextField.setText(GuiHandler.getInstance().chooseFilePath(formatsFq));
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onAnalyseButtonPressed(ActionEvent actionEvent) {
        System.out.println("ANALYSE!");
        try {
            Config config = makeConfigFile();
            if (config != null) {
                    PreferencesManager.getInstance().setPreference("analysis_path", analysisDestTextField.getText(), String.class);
                    writeConfigFileAndCopySnakeFileToAnalysisFolder(config);
                    //TODO: Odpalanie skryptu uruchamiającego analizę
                    PreferencesManager.getInstance().setPreference("script_name", "analyse.sh", String.class);
                    Stage stage = (Stage) analyseButton.getScene().getWindow();
                    Scene scene = RunApp.getScene( "Progress");
                    stage.setScene(scene);
                    stage.show();
            } else {
                GuiHandler.getInstance().showWindow(PropertiesGetter.getValue(propFileName, "configMakingError"));
            }
        } catch (IncorrectPathException | Exception | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    private void setDefaultValueStartPointComboBox() throws PreferencesManager.IncorrectKeyException, IOException, PreferencesManager.UnsupportedTypeException {
        boolean removingAdapters = PreferencesManager.getInstance().getPreference("removing_adapters", Boolean.class);
        boolean qualityFiltering = PreferencesManager.getInstance().getPreference("quality_filtering", Boolean.class);
        if (removingAdapters) {
            startPointComboBox.getSelectionModel().select(0);
        } else if (qualityFiltering) {
            startPointComboBox.getSelectionModel().select(1);
        } else {
            startPointComboBox.getSelectionModel().select(2);
        }
    }

    private void setDefaultValueEndPointComboBox() throws PreferencesManager.IncorrectKeyException, IOException, PreferencesManager.UnsupportedTypeException {
        boolean annotate = PreferencesManager.getInstance().getPreference("annotate", Boolean.class);
        if (annotate) {
            endPointComboBox.getSelectionModel().select(1);
        } else {
            endPointComboBox.getSelectionModel().select(0);
        }
    }

    private void setDefaultValueMapperComboBox() throws PreferencesManager.IncorrectKeyException, IOException, PreferencesManager.UnsupportedTypeException {
        String mapper = PreferencesManager.getInstance().getPreference("mapper", String.class);
        List<String> options = Arrays.asList(PropertiesGetter.getValue(propFileName, "mapperComboBox").split("#"));
        if (mapper != null && options != null) {
            for (String option : options) {
                if (mapper.equalsIgnoreCase(option)) {
                    mapperComboBox.getSelectionModel().select(option);
                    break;
                }
            }
        }
    }

    private void setDefaultValueVariantCallerComboBox() throws PreferencesManager.IncorrectKeyException, IOException, PreferencesManager.UnsupportedTypeException {
        String variantCaller = PreferencesManager.getInstance().getPreference("variant_caller", String.class);
        List<String> options = Arrays.asList(PropertiesGetter.getValue(propFileName, "variantCallerComboBox").split("#"));
        if (variantCaller != null && options != null) {
            for (String option : options) {
                if (variantCaller.equalsIgnoreCase(option)) {
                    variantCallerComboBox.getSelectionModel().select(option);
                    break;
                }
            }
        }
    }

    public Config makeConfigFile() throws IOException, PreferencesManager.IncorrectKeyException, PreferencesManager.UnsupportedTypeException, IncorrectPathException {
        Map<String, Object> samples = parseSamplePaths();
        Map<String, Object> genome = parseGenomePath();
        Map<String, Object> rules = parseRulesSettings();
        if (samples!=null && genome!=null && rules!=null)
            return new Config(samples, genome, rules);
        else
            return null;
    }

    public Map<String, Object> parseSamplePaths() throws IncorrectPathException {
        Map<String, Object> out = null;

        if (allPathsSelected()) {
            String extension = equalExtensions();
            if (extension != null) {
                String path = equalPaths();
                if (path != null) {
                    String[] ids = pairsMatch();
                    if (pairsMatch() != null) {
                        out = new HashMap<>();
                        out.put("ext", extension);
                        out.put("path", path);
                        out.put("ids", ids);
                    }
                }
            }

        }

        return out;
    }

    public Map<String, Object> parseGenomePath() throws IncorrectPathException {
        Map<String, Object> out = null;

        if (genomeDestTextField.getText() != null) {
            String genomeWhole = genomeDestTextField.getText();
            String[] genomeArray = genomeWhole.split("\\.");
            if (genomeArray.length<2) throw new IncorrectPathException();
            String path = genomeArray[genomeArray.length-2];
            String ext = "." + genomeArray[genomeArray.length-1];
            genomeArray = genomeWhole.split("\\\\|/|\\.");
            String id = genomeArray[genomeArray.length-2];

            out = new HashMap<>();
            out.put("id", id);
            out.put("ext", ext);
            out.put("path", path);
        }

        return out;
    }

    public Map<String, Object> parseRulesSettings() throws NumberFormatException, PreferencesManager.IncorrectKeyException, IOException, PreferencesManager.UnsupportedTypeException {
        Integer minPhredScore = Integer.parseInt(minPhredScoreTextField.getText().replaceAll(" ", ""));
        int selectedStartPoint = startPointComboBox.getSelectionModel().getSelectedIndex();
        boolean removingAdapters, qualityFiltering;
        switch (selectedStartPoint)  {
            case 0:  removingAdapters = true;
                qualityFiltering = true;
                break;
            case 1:  removingAdapters = false;
                qualityFiltering = true;
                break;
            default: removingAdapters = false;
                qualityFiltering = false;
                break;
        }
        int selectedEndPoint = endPointComboBox.getSelectionModel().getSelectedIndex();
        boolean annotate;
        switch (selectedEndPoint)  {
            case 0:  annotate = false;
                break;
            default: annotate = true;
                break;
        }
        String mapper = mapperComboBox.getSelectionModel().getSelectedItem().toString().toLowerCase();
        String variantCaller = variantCallerComboBox.getSelectionModel().getSelectedItem().toString().toLowerCase();

        return getRulesSettingsMap(removingAdapters, qualityFiltering, minPhredScore, mapper, variantCaller, annotate);
    }

    private boolean allPathsSelected() {
        tumorForwardWhole = tumorForwardTextField.getText();
        tumorReverseWhole = tumorReverseTextField.getText();
        normalForwardWhole = normalForwardTextField.getText();
        normalReverseWhole = normalReverseTextField.getText();

        return tumorForwardWhole != null &&
                tumorReverseWhole != null &&
                normalForwardWhole != null &&
                normalReverseWhole != null;
    }

    private String equalExtensions() {
        String[] tumorForwardArray = tumorForwardWhole.split("\\.");
        String tumorForwardExt = "." + tumorForwardArray[tumorForwardArray.length-1];

        String[] tumorReverseArray = tumorReverseWhole.split("\\.");
        String tumorReverseExt = "." + tumorReverseArray[tumorReverseArray.length-1];

        String[] normalForwardArray = normalForwardWhole.split("\\.");
        String normalForwardExt = "." + normalForwardArray[normalForwardArray.length-1];

        String[] normalReverseArray = normalReverseWhole.split("\\.");
        String normalReverseExt = "." + normalReverseArray[normalReverseArray.length-1];

        boolean equalExtensions = tumorForwardExt.equals(tumorReverseExt) &&
                tumorForwardExt.equals(normalForwardExt) &&
                tumorForwardExt.equals(normalReverseExt);

        return equalExtensions ? tumorForwardExt : null;
    }

    private String equalPaths() throws IncorrectPathException {
        String delimiter = getDelimiter();
        if (delimiter == null) {
            throw new IncorrectPathException();
        } else {
            String[] tumorForwardArray = tumorForwardWhole.split(delimiter + "|\\.");
            String[] tumorReverseArray = tumorReverseWhole.split(delimiter + "|\\.");
            String[] normalForwardArray = normalForwardWhole.split(delimiter + "|\\.");
            String[] normalReverseArray = normalReverseWhole.split(delimiter + "|\\.");

            //pozostawianie samej ścieżki do pliku
            tumorForwardArray = Arrays.copyOfRange(tumorForwardArray, 0, tumorForwardArray.length - 3);
            tumorReverseArray = Arrays.copyOfRange(tumorReverseArray, 0, tumorReverseArray.length - 3);
            normalForwardArray = Arrays.copyOfRange(normalForwardArray, 0, normalForwardArray.length - 3);
            normalReverseArray = Arrays.copyOfRange(normalReverseArray, 0, normalReverseArray.length - 3);

            //odtworzenie ścieżki poprzez dodanie delimitera
            String tumorForwardPath = String.join(delimiter, tumorForwardArray);
            String tumorReversePath = String.join(delimiter, tumorReverseArray);
            String normalForwardPath = String.join(delimiter, normalForwardArray);
            String normalReversePath = String.join(delimiter, normalReverseArray);

            boolean equalPaths = tumorForwardPath.equals(tumorReversePath) &&
                    tumorForwardPath.equals(normalForwardPath) &&
                    tumorForwardPath.equals(normalReversePath);

            return equalPaths ? tumorForwardPath : null;
        }
    }

    private String getDelimiter() {
        String tempString = tumorForwardWhole.split("\\.")[0];
        List<String> delimiters = Arrays.asList(new String[] {"\\\\", "/"});
        String[] tempArray;
        String delimiter = null;
        for (String del : delimiters) {
            tempArray = tempString.split(del);
            if (tempArray.length>1) {
                delimiter = del;
                break;
            }
        }
        return delimiter;
    }

    private String[] pairsMatch() {
        String[] tumorForwardArray = tumorForwardWhole.split("\\\\|/|\\.");
        String[] tumorReverseArray = tumorReverseWhole.split("\\\\|/|\\.");
        String[] normalForwardArray = normalForwardWhole.split("\\\\|/|\\.");
        String[] normalReverseArray = normalReverseWhole.split("\\\\|/|\\.");

        String tumorForwardId = tumorForwardArray[tumorForwardArray.length-2];
        tumorForwardId = tumorForwardId.substring(0, tumorForwardId.length()-2);
        String tumorReverseId = tumorReverseArray[tumorReverseArray.length-2];
        tumorReverseId = tumorReverseId.substring(0, tumorReverseId.length()-2);
        String normalForwardId = normalForwardArray[normalForwardArray.length-2];
        normalForwardId = normalForwardId.substring(0, normalForwardId.length()-2);
        String normalReverseId = normalReverseArray[normalReverseArray.length-2];
        normalReverseId = normalReverseId.substring(0, normalReverseId.length()-2);

        boolean pairsMatch = tumorForwardId.equals(tumorReverseId) &&
                normalForwardId.equals(normalReverseId);

        return pairsMatch ? new String[]{tumorForwardId, normalForwardId} : null;
    }

    private Map<String, Object> getRulesSettingsMap(Boolean removingAdapters, Boolean qualityFiltering, Integer minPhredScore,
                                                    String mapper, String variantCaller, Boolean annotate) throws PreferencesManager.IncorrectKeyException, IOException, PreferencesManager.UnsupportedTypeException {
        Map<String, Object> out = new HashMap<>();
        out.put("path", PreferencesManager.getInstance().getPreference("rules_path", String.class));
        out.put("prepare_genome", prepareGenomeCheckBox.isSelected());
        PreferencesManager.getInstance().setPreference("prepare_genome", prepareGenomeCheckBox.isSelected(), Boolean.class);
        out.put("removing_adapters", removingAdapters);
        PreferencesManager.getInstance().setPreference("removing_adapters", removingAdapters, Boolean.class);
        out.put("quality_filtering", qualityFiltering);
        PreferencesManager.getInstance().setPreference("quality_filtering", qualityFiltering, Boolean.class);
        out.put("min_phred_score", minPhredScore);
        PreferencesManager.getInstance().setPreference("min_phred_score", minPhredScore, Integer.class);
        out.put("mapper", mapper);
        PreferencesManager.getInstance().setPreference("mapper", mapper, String.class);
        out.put("remove_duplicates", removeDuplicatesCheckBox.isSelected());
        PreferencesManager.getInstance().setPreference("remove_duplicates", removeDuplicatesCheckBox.isSelected(), Boolean.class);
        out.put("variant_caller", variantCaller);
        PreferencesManager.getInstance().setPreference("variant_caller", variantCaller, String.class);
        out.put("annotate", annotate);
        PreferencesManager.getInstance().setPreference("annotate", annotate, Boolean.class);
        return out;
    }


    private void writeConfigFileAndCopySnakeFileToAnalysisFolder(Config config) throws IOException, PreferencesManager.UnsupportedTypeException, PreferencesManager.IncorrectKeyException, URISyntaxException {
        String analysisPath = PreferencesManager.getInstance().getPreference("analysis_path", String.class);
        //YamlParser.write(config, analysisPath + "config.yaml");
        File source = new File(PreferencesManager.getInstance().getPreference("resources_path", String.class) + "Snakefile");
        File dest = new File(analysisPath + "Snakefile");
        FileUtils.copyFile(source, dest);
    }

    private class IncorrectPathException extends Throwable {
        @Override
        public String toString() {
            try {
                return PropertiesGetter.getValue(propFileName, "incorrectPathException");
            } catch (IOException e) {
                return "IncorrectPathException()";
            }
        }
    }
}
