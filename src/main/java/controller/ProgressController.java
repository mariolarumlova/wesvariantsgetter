package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tools.GuiHandler;
import tools.PreferencesManager;
import tools.PropertiesGetter;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ProgressController implements Initializable {
    @FXML
    Button button;
    @FXML
    TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String scriptName = PreferencesManager.getInstance().getPreference("script_name", String.class);
            String instructions = getInstructionsForScript(scriptName);
            textArea.setText(instructions);
        } catch (PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException | Exception e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
        }
    }

    public void onButtonPressed(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) button.getScene().getWindow();
            Scene scene = RunApp.getScene("MainWindow");
            stage.setScene(scene);
            stage.show();
        } catch (PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException | IOException e) {
                GuiHandler.getInstance().showWindow(e.toString());
                e.printStackTrace();
            }
    }

    public String getInstructionsForScript(String scriptName) throws IOException, PreferencesManager.UnsupportedTypeException, PreferencesManager.IncorrectKeyException {
        saveScript(scriptName);
        String propFileName = "labels_" + PropertiesGetter.getValue("application", "language");
        String analysisPath = PreferencesManager.getInstance().getPreference("analysis_path", String.class);
        return PropertiesGetter.getValue(propFileName, "openTerminal") + "cd " + analysisPath + "\nchmod +x " + scriptName + "\n./" + scriptName;
    }

    public void saveScript(String scriptName) throws PreferencesManager.IncorrectKeyException, IOException, PreferencesManager.UnsupportedTypeException {

        String analysisPath = PreferencesManager.getInstance().getPreference("analysis_path", String.class);
        File source = new File(PreferencesManager.getInstance().getPreference("scripts_path", String.class) + scriptName);
        File dest = new File(analysisPath + scriptName);
        dest.createNewFile();
        List<String> parameters = Arrays.asList(getParameters(scriptName));

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(source));
        String ln;
        while((ln = br.readLine()) != null)
        {
            int i = 1;
            for (String param : parameters) {
                System.out.println("i = " + i + ", param = " + param);
                String target = "$" + Integer.toString(i);
                ln = ln.replace(target, param);
                i++;
                System.out.println(ln);
            }
            sb.append(ln).append("\n");
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
        bw.write(sb.toString());
        bw.close();
    }
    
    public String[] getParameters(String scriptName) throws PreferencesManager.UnsupportedTypeException, IOException, PreferencesManager.IncorrectKeyException {
        String resourcesPath = PreferencesManager.getInstance().getPreference("scripts_path", String.class);
        String minicondaPath = PreferencesManager.getInstance().getPreference("miniconda3", String.class);
        String environmentName = PreferencesManager.getInstance().getPreference("env_name", String.class);
        String analysisPath = PreferencesManager.getInstance().getPreference("analysis_path", String.class);
        String programsPath = PreferencesManager.getInstance().getPreference("programs_path", String.class);

        String[] parameters = null;
        if ("installing.sh".equals(scriptName)) {
            PreferencesManager.getInstance().setPreference("miniconda3", minicondaPath+"miniconda3", String.class);
            minicondaPath = PreferencesManager.getInstance().getPreference("miniconda3", String.class);
            parameters = new String[] {minicondaPath, environmentName, resourcesPath, programsPath};
        } else if ("analyse.sh".equals(scriptName)) {
            parameters = new String[] {analysisPath, environmentName};
        }
        else if ("analyse_w_indexing.sh".equals(scriptName)) {
            String pathToGenome = PreferencesManager.getInstance().getPreference("path_to_genome", String.class);
            String genomeIndex = PreferencesManager.getInstance().getPreference("genome_index", String.class);
            String genomeExt = PreferencesManager.getInstance().getPreference("genome_ext", String.class);
            parameters = new String[] {analysisPath, environmentName, pathToGenome, genomeIndex, genomeExt};
        }
        return parameters;
    }
   
}
