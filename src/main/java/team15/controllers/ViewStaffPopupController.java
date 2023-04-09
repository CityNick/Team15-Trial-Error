package team15.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import team15.DatabaseConnector;
import team15.SQLHelpers.StaffAccountSQLHelper;
import team15.models.StaffAccount;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewStaffPopupController implements Initializable {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<String> Role;
    @FXML
    private TextField travelAgentCodeField;
    @FXML
    private TextField supervisorIDField;
    @FXML
    private Label creationResultField;

    @FXML public void createPressed() {
        creationResultField.setText("");

        StaffAccount newStaff = new StaffAccount();
        newStaff.setFirstName(firstNameField.getText());
        newStaff.setLastName(lastNameField.getText());
        newStaff.setPassword(passwordField.getText());
        newStaff.setRole(Role.getValue());

        if (travelAgentCodeField.getText() == ""){newStaff.setTravelAgentCode(0);}
        else { newStaff.setTravelAgentCode(Integer.parseInt(travelAgentCodeField.getText()));}

        if (supervisorIDField.getText() == ""){newStaff.setSupervisorID(0);}
        else{newStaff.setSupervisorID(Integer.parseInt(supervisorIDField.getText()));}


        try (Connection connection = DatabaseConnector.connect()){
            if(StaffAccountSQLHelper.createStaffAccount(newStaff, connection)){
                creationResultField.setText("Account Made Successfully");
                firstNameField.clear();
                lastNameField.clear();
                passwordField.clear();
                travelAgentCodeField.clear();
                supervisorIDField.clear();
            }
            else{
                creationResultField.setText("Account Already Exists");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // ----- Ensures Users enter a valid input ----- //
        travelAgentCodeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    travelAgentCodeField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 3) {
                    travelAgentCodeField.setText(oldValue);
                }
            }
        });
        supervisorIDField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    supervisorIDField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 3) {
                    supervisorIDField.setText(oldValue);
                }
            }
        });

        Role.getItems().addAll("Administrator", "Manager", "Travel Advisor");
    }
}
