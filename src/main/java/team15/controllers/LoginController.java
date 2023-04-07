package team15.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import team15.Application;
import team15.SQLHelpers.StaffAccountSQLHelper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField staffIDField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorField;

    // ====================== Login =========================== //
    @FXML
    public void on_login_pressed() throws IOException {

        // ---------- Login Successful ---------- //
        if (StaffAccountSQLHelper.checkAccountLogin(Long.parseLong(staffIDField.getText()),passwordField.getText())){

            // ---- Check User's Role ---- //
            switch (Application.getActiveUser().getRole()){
                // - Administrator - //
                case "Administrator":
                    Application.changeToScene("Admin.fxml");
                    break;

                // - Travel Advisor - //
                case "Travel Advisor":
                    Application.changeToScene("TravelAdvisor.fxml");
                    break;

                // - Office Manager - //
                case "Manager":
                    Application.changeToScene("OfficeManager.fxml");
                    break;

            }
        }

        // ---------- Login Unsuccessful ---------- //
        else{
            errorField.setText("Error: Could Not Connect");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staffIDField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    staffIDField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 15) {
                    staffIDField.setText(oldValue);
                }
            }
        });
    }
}