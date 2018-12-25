package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import settings.PreferencesManager;

import java.io.IOException;

public class MainWindowController {//implements Initializable {

    @FXML
    AnchorPane anchorPane;

    @FXML
    Button analyseButton;

//    @FXML
//    GridPane gridPane;
//
//    Stage stage;
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        Platform.runLater(() -> {
//            do {
//                Scene scene = gridPane.getScene();
//                if (scene != null) {
//                    stage = (Stage) scene.getWindow();
//                }
//            } while (stage == null);
//        });
//    }
//
//    public static void swapView(Parent target, Node node) {
//        GridPane parentVBox = (GridPane) node.getParent();
//        if (parentVBox.getChildren().size() > 1)
//            parentVBox.getChildren().remove(1);
//
//        parentVBox.getChildren().add(target);
//    }

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
            String language = PreferencesManager.getInstance().getPreference("language", String.class);
            Scene scene = RunApp.getScene(language, "AnalysisProgress");
            stage.setScene(scene);
            stage.show();
        } catch (IOException | PreferencesManager.UnsupportedTypeException | PreferencesManager.IncorrectKeyException e) {
            GuiHandler.getInstance().showWindow(e.getMessage());
            e.printStackTrace();
        }
    }


}
