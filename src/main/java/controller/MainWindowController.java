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
import java.util.*;

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
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }


    public void onGenomeDestButtonPressed(ActionEvent actionEvent) {
        System.out.println("genome dest");
        try {
            String path = GuiHandler.getInstance().chooseFilePath(formatsFa);
            genomeDestTextField.setText(path);
            prepareGenomeCheckBox.setDisable(false);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onAnalysisDestButtonPressed(ActionEvent actionEvent) {
        System.out.println("analysis dest");
        try {
            String path = GuiHandler.getInstance().chooseDirectoryPath();
            analysisDestTextField.setText(path);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onTumorForwardButtonPressed(ActionEvent actionEvent) {
        System.out.println("tumor fwd");
        try {
            String path = GuiHandler.getInstance().chooseFilePath(formatsFq);
            tumorForwardTextField.setText(path);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onTumorReverseButtonPressed(ActionEvent actionEvent) {
        System.out.println("tumor rev");
        try {
            String path = GuiHandler.getInstance().chooseFilePath(formatsFq);
            tumorReverseTextField.setText(path);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onNormalForwardButtonPressed(ActionEvent actionEvent) {
        System.out.println("normal fwd");
        try {
            String path = GuiHandler.getInstance().chooseFilePath(formatsFq);
            normalForwardTextField.setText(path);
        } catch (IOException | PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onNormalReverseButtonPressed(ActionEvent actionEvent) {
        System.out.println("normal rev");
        try {
            String path = GuiHandler.getInstance().chooseFilePath(formatsFq);
            normalReverseTextField.setText(path);
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
                    YamlParser.write(config, PreferencesManager.getInstance().getPreference("analysis_path", String.class) + "testConfig.yaml");
                    //TODO: Kopiowanie Snakefile do analysis_path, może być w metodzie write w YamlParserze
                    Stage stage = (Stage) analyseButton.getScene().getWindow();
                    Scene scene = RunApp.getScene( "AnalysisProgress");
                    stage.setScene(scene);
                    stage.show();
            } else {
                GuiHandler.getInstance().showWindow(PropertiesGetter.getValue(propFileName, "configMakingError"));
            }
        } catch (Exception | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public Config makeConfigFile() throws IOException, PreferencesManager.IncorrectKeyException, PreferencesManager.UnsupportedTypeException {
        Map<String, Object> samples = parseSamplePaths();
        Map<String, Object> genome = parseGenomePath();
        Map<String, Object> rules = parseRulesSettings();
        if (samples!=null && genome!=null && rules!=null)
            return new Config(samples, genome, rules);
        else
            return null;
    }

    public Map<String, Object> parseSamplePaths() {
        Map<String, Object> out = null;

        boolean allPathsSelected = tumorForwardTextField.getText() != null &&
                tumorReverseTextField.getText() != null &&
                normalForwardTextField.getText() != null &&
                normalReverseTextField.getText() != null;
        System.out.println("all selected " + allPathsSelected);
        if (allPathsSelected()) {
            String tumorForwardWhole = tumorForwardTextField.getText();
            String[] tumorForwardArray = tumorForwardWhole.split("\\.");
            String tumorForwardPath = tumorForwardArray[tumorForwardArray.length-2];
            String tumorForwardExt = "." + tumorForwardArray[tumorForwardArray.length-1];

            String tumorReverseWhole = tumorReverseTextField.getText();
            String[] tumorReverseArray = tumorReverseWhole.split("\\.");
            String tumorReversePath = tumorReverseArray[tumorReverseArray.length-2];
            String tumorReverseExt = "." + tumorReverseArray[tumorReverseArray.length-1];

            String normalForwardWhole = normalForwardTextField.getText();
            String[] normalForwardArray = normalForwardWhole.split("\\.");
            String normalForwardPath = normalForwardArray[normalForwardArray.length-2];
            String normalForwardExt = "." + normalForwardArray[normalForwardArray.length-1];

            String normalReverseWhole = normalReverseTextField.getText();
            String[] normalReverseArray = normalReverseWhole.split("\\.");
            String normalReversePath = normalReverseArray[normalReverseArray.length-2];
            String normalReverseExt = "." + normalReverseArray[normalReverseArray.length-1];

            boolean equalExtensions = tumorForwardExt.equals(tumorReverseExt) &&
                                        tumorForwardExt.equals(normalForwardExt) &&
                                        tumorForwardExt.equals(normalReverseExt);
            System.out.println("equal ext " + equalExtensions);
            if (equalExtensions) {
                tumorForwardArray = tumorForwardWhole.split("\\\\|/|\\.");
                tumorReverseArray = tumorReverseWhole.split("\\\\|/|\\.");
                normalForwardArray = normalForwardWhole.split("\\\\|/|\\.");
                normalReverseArray = normalReverseWhole.split("\\\\|/|\\.");

                // usuwanie 1 i 2 z nazw
                int indexTemp = tumorForwardArray.length - 1;
                String lastElementTemp = tumorForwardArray[indexTemp];
                int index = lastElementTemp.length() - 1;
                tumorForwardArray[indexTemp - 1] = lastElementTemp.substring(0, index);
                String tumorForwardSimplifiedPath = Arrays.toString(tumorForwardArray);

                indexTemp = tumorReverseArray.length - 1;
                lastElementTemp = tumorReverseArray[indexTemp];
                tumorReverseArray[indexTemp - 1] = lastElementTemp.substring(0, index);
                String tumorReverseSimplifiedPath = Arrays.toString(tumorForwardArray);

                indexTemp = normalForwardArray.length - 1;
                lastElementTemp = normalForwardArray[indexTemp];
                normalForwardArray[indexTemp - 1] = lastElementTemp.substring(0, index);
                String normalForwardSimplifiedPath = Arrays.toString(tumorForwardArray);


                indexTemp = normalReverseArray.length - 1;
                lastElementTemp = normalReverseArray[indexTemp];
                normalReverseArray[indexTemp - 1] = lastElementTemp.substring(0, index);
                String normalReverseSimplifiedPath = Arrays.toString(tumorForwardArray);

                boolean pairsMatch = tumorForwardSimplifiedPath.equals(tumorReverseSimplifiedPath) &&
                                normalForwardSimplifiedPath.equals(normalReverseSimplifiedPath);
                System.out.println("pairs match " + pairsMatch);

                if (pairsMatch) {
                    out = new HashMap<>();
                    out.put("ext", tumorForwardExt);

                }
            }

        }

        return out;
    }

    public Map<String, Object> parseGenomePath() {
        Map<String, Object> out = null;

        if (genomeDestTextField.getText() != null) {
            String genomeWhole = genomeDestTextField.getText();
            String[] genomeArray = genomeWhole.split("\\.");
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
        Map<String, Object> out = null;
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

        out = new HashMap<>();
        out.put("path", PreferencesManager.getInstance().getPreference("rules_path", String.class));
        out.put("prepare_genome", prepareGenomeCheckBox.isSelected());
        out.put("removing_adapters", removingAdapters);
        out.put("quality_filtering", qualityFiltering);
        out.put("min_phred_score", minPhredScore);
        out.put("mapper", mapper);
        out.put("remove_duplicates", removeDuplicatesCheckBox.isSelected());
        out.put("variant_caller", variantCaller);
        out.put("annotate", annotate);

        return out;
    }

    public boolean allPathsSelected() {
        return tumorForwardTextField.getText() != null &&
                tumorReverseTextField.getText() != null &&
                normalForwardTextField.getText() != null &&
                normalReverseTextField.getText() != null;
    }
}
