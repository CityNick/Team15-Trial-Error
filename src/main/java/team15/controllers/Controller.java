package team15.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import team15.StaffAccountSQL;

public class Controller {
    @FXML
    private TextField staffID_field;
    @FXML
    private PasswordField password_field;

    @FXML
    public void on_login_pressed() {
        System.out.println("pressed login");
        StaffAccountSQL.checkAccount(Long.parseLong(staffID_field.getText()),password_field.getText());
    }
}