package team15.controllers;

import javafx.fxml.FXML;
import team15.Application;

import java.io.IOException;

public class TravelAdvisorController {

    @FXML
    public void logoutPressed() throws IOException {
        Application.changeToScene("login.fxml");     // - returns to login page
        Application.setActiveUser(null);                  // - deletes current active user
    }
}
