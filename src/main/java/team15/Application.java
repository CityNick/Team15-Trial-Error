package team15;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import team15.models.StaffAccount;

import java.io.IOException;

public class Application extends javafx.application.Application {

    private static Stage activeStage;
    private static StaffAccount activeUser;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        activeStage = stage;
        activeStage.setTitle("AirVia Login Page");
        activeStage.setScene(scene);
        activeStage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    public static void changeToScene(String fxml){
    }

    public static StaffAccount getActiveUser(){return activeUser;}
    public static void setActiveUser(StaffAccount user) {activeUser = user;}



}