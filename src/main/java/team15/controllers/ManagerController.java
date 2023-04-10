package team15.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import team15.Application;
import team15.DatabaseConnector;
import team15.PopupManager;
import team15.SQLHelpers.BlankSQLHelper;
import team15.SQLToTable;
import team15.models.StaffAccount;
import team15.models.StaffBlanks;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {

    private int selectedStaffID;
    private StaffBlanks staffBlanks;
    @FXML
    TableView staffBlanksView;
    @FXML
    TextField searchStaffBlanksField;
    @FXML
    ComboBox reportTypeBox;
    @FXML
    ComboBox rangeBox;

    @FXML
    public void logoutPressed() throws IOException {
        Application.changeToScene("login.fxml");     // - returns to login page
        Application.setActiveUser(null);                  // - deletes current active user
    }


    // ================================ ASSIGNS BLANKS TAB =============================== //

    // ----- Open Assign Blanks Tab ----- //
    @FXML
    public void assignBlanksOpened(){
        updateStaffBlanksTable();
    }

    // ----- Press Assign Blanks ---- //
    @FXML
    public void assignBlanksPressed() throws IOException {
        if (selectedStaffID != 0){
            AssignStaffBlankPopupController.setStaffBlanks(staffBlanks);
            AssignStaffBlankPopupController.setTravelAgentCode(Application.getActiveUser().getTravelAgentCode());
            PopupManager.displayPopup("AllocateStaffBlanksPopup.fxml");
        }
    }

    // ---- edited search staff blanks ----- //
    @FXML
    public void searchStaffBlanksEdited(){
        updateStaffBlanksTable();
    }







    public void updateStaffBlanksTable(){
        staffBlanksView.getItems().clear();
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = BlankSQLHelper.staffBlankCount(searchStaffBlanksField.getText(), Application.getActiveUser().getTravelAgentCode(),connection);

            // ----- convert rs to list ----- //
            ArrayList<StaffBlanks> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {
                    data.add(new StaffBlanks(rs));
                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    SQLToTable.fillTableView(staffBlanksView, rs);
                    staffBlanksView.setItems(dataList);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // ----- Ensures Users enter a valid input ----- //
        searchStaffBlanksField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    searchStaffBlanksField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 3) {
                    searchStaffBlanksField.setText(oldValue);
                }
            }
        });

        staffBlanksView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(staffBlanksView.getSelectionModel().getSelectedItem() != null)
                {
                    staffBlanks = (StaffBlanks) staffBlanksView.getSelectionModel().getSelectedItem();
                    selectedStaffID = staffBlanks.getStaffID();
                    System.out.println("Selected StaffID: " + selectedStaffID);
                }
                else{
                    selectedStaffID = 0;
                    System.out.println("Selected StaffID: " + selectedStaffID);
                }
            }
        });

        reportTypeBox.getItems().addAll("Stock Turnover", "Interline Sales", "Domestic Sales");

        rangeBox.getItems().addAll("Global", "Individual");
    }


}
