package controller;

import javafx.event.ActionEvent;

public class MainWindowController {
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
    }
}
