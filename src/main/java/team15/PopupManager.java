package team15;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PopupManager {

    private static Stage activeStage;

    public static void displayPopup(String fxml) throws IOException {
        activeStage = new Stage();
        activeStage.initOwner(Application.getActiveStage());
        activeStage.initModality(Modality.WINDOW_MODAL);
        activeStage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        activeStage.setScene(scene);
        activeStage.show();
    }

    public static void closePopup() {
        activeStage.close();
    }
}
