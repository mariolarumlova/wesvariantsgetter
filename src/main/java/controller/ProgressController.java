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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            String resourcesPath = PreferencesManager.getInstance().getPreference("resources_path", String.class);
            String scriptName = PreferencesManager.getInstance().getPreference("script_name", String.class);
            String path = resourcesPath + scriptName;
            String[] cmd = {"sh", path};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.setText(textArea.getText() + "\n" + line);
            }
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
}
