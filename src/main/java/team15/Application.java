package team15;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import team15.models.StaffAccount;

import java.io.IOException;

public class Application extends javafx.application.Application {

    private static Stage activeStage;
    private static StaffAccount activeUser;

    // =============================== Start Up ====================================== //

    /** Start function
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        // ----- Login Page On Startup ----- //
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));     // - retrieves login.fxml file
        Scene scene = new Scene(fxmlLoader.load());                                                    // - converts .fxml into a scene
        activeStage = stage;                                                                           // - sets stage as active stage
        activeStage.setTitle("AirVia Systems");                                                        // - window title set
        activeStage.setScene(scene);                                                                   // - sets the login scene
        activeStage.show();                                                                            // - shows application
    }

    // =============================== Main ====================================== //
    /** Main Method
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }


    // ========================== Scene Changes =================================== //

    /**
     * Scene Change Function
     * @param fxml is a string input that determines the next scene
     * @throws IOException
     */
    public static void changeToScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        activeStage.setScene(scene);
        activeStage.show();
    }


    // ================= activeUser Setter & Getter =================== //
    public static StaffAccount getActiveUser(){return activeUser;}
    public static void setActiveUser(StaffAccount user) {activeUser = user;}



}