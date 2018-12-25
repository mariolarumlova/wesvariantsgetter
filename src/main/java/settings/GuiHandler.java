package settings;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.ResourceBundle;

public class GuiHandler {

    private static volatile GuiHandler instance;

    private GuiHandler() {}

    public static GuiHandler getInstance() {
        if (instance == null) {
            synchronized (GuiHandler.class) {
                if (instance == null) {
                    instance = new GuiHandler();
                }
            }
        }
        return instance;
    }

    public int[] getSceneSize() {
        int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
        int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

        int sceneWidth = 0;
        int sceneHeight = 0;
        if (screenWidth <= 800 && screenHeight <= 600) {
            sceneWidth = 600;
            sceneHeight = 350;
        } else if (screenWidth <= 1280 && screenHeight <= 768) {
            sceneWidth = 800;
            sceneHeight = 450;
        } else if (screenWidth <= 1920 && screenHeight <= 1080) {
            sceneWidth = 1000;
            sceneHeight = 650;
        }

        int[] out = {sceneWidth, sceneHeight};

        return out;
    }

    public void showWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public Scene getScene(FXMLLoader loader, String language, String name) throws IOException {
        int[] sceneSize = getSceneSize();
        int sceneWidth = sceneSize[0];
        int sceneHeight = sceneSize[1];

        loader.setResources(ResourceBundle.getBundle("labels_" + language));
        Parent root = loader.load(getClass().getResourceAsStream("\\view\\" + name + ".fxml"));
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("\\bootstrap3.css").toExternalForm());

        return scene;
    }
}
