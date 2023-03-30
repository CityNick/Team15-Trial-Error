package team15.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    public void on_login_pressed() {
        System.out.println("pressed login");
    }
}