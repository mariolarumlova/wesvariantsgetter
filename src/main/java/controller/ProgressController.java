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

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgressController implements Initializable {

    @FXML
    Button button;
    @FXML
    TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        execute();
    }

    public void execute() {
        Process p;
        try {
            String[] cmd = getCommand();
            if (cmd != null) {
                ProcessBuilder pb = new ProcessBuilder(cmd);
                pb.redirectErrorStream(true);
                pb.directory(new File(PreferencesManager.getInstance().getPreference("analysis_path", String.class)));
                //prepareEnvironment(pb.environment());
                p = pb.start();
                p.waitFor();
                BufferedReader rd = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while((line = rd.readLine()) != null) {
                    textArea.setText(textArea.getText() + "\n" + line);
                    System.out.println(line + "\n");
                }
                rd.close();
            } else System.out.println("Nie udało się utworzyć komendy");
        } catch (PreferencesManager.IncorrectKeyException | PreferencesManager.UnsupportedTypeException | IOException | InterruptedException e) {
            GuiHandler.getInstance().showWindow(e.toString());
            e.printStackTrace();
            textArea.setText("blabablabl\nlshfs\nldgjhjk\nljdhgjk\ndjghk\nldjhgdk\ndkjhgkd\ndjdkj\ndkjhd\ndkjhgd\nldg" +
                    "duhgjnd\nlfjghdgbk\ndkfjhgergbjk\ndfhgdhgi\nkdjhgkdgh\nkdfjhgdg\nkdjhgkd\nkdjhgd\nkhgdgh\ndjfkghd" +
                    "djhkjf\nldnbkjdf\nndjgndkkg\ndjgkdjbg\ndkjgbdkhb\nkdjgkjf\ndbgkd\nkdjgbdkj\ndkjbndk\njdkg\nldjg\n");
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
    
    public String[] getCommand() throws PreferencesManager.UnsupportedTypeException, IOException, PreferencesManager.IncorrectKeyException {
        String resourcesPath = PreferencesManager.getInstance().getPreference("scripts_path", String.class);
        String minicondaPath = PreferencesManager.getInstance().getPreference("miniconda3", String.class);
        String environmentName = PreferencesManager.getInstance().getPreference("env_name", String.class);
        String analysisPath = PreferencesManager.getInstance().getPreference("analysis_path", String.class);
        String programsPath = PreferencesManager.getInstance().getPreference("programs_path", String.class);
        
        String scriptName = PreferencesManager.getInstance().getPreference("script_name", String.class);
        String path = resourcesPath + scriptName;
        String[] cmd = null;
        if ("installing.sh".equals(scriptName)) {
            cmd = new String[] {"sh", path, minicondaPath, environmentName, resourcesPath, programsPath};
        } else if ("analyse.sh".equals(scriptName)) {
            cmd = new String[] {"sh", path, analysisPath, environmentName};
            
        }
//        } else if ("indexing.sh".equals(scriptName)) {
//            cmd = new String[] {"sh", path, analysisPath, environmentName};
//        }
        return cmd;
    }
   
}
