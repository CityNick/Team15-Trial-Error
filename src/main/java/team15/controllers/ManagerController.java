package team15.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import team15.Application;
import team15.DatabaseConnector;
import team15.PopupController;
import team15.SQLHelpers.BlankSQLHelper;
import team15.SQLHelpers.CustomerAccountSQLHelper;
import team15.SQLToTable;
import team15.models.CustomerAccount;
import team15.models.StaffBlanks;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {

    @FXML
    TableView staffBlanksView;
    @FXML
    TableView customerTableView;
    @FXML
    TextField searchStaffBlanksField;
    @FXML
    ComboBox reportTypeBox;
    @FXML
    ComboBox rangeBox;
    @FXML
    ComboBox discountTypeBox;
    @FXML
    CheckBox valuedBox;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    private int selectedStaffID;
    private int selectedCustomerID;
    private StaffBlanks staffBlanks;
    private CustomerAccount customerAccount;
    private Boolean isValued = false;

    @FXML
    public void logoutPressed() throws IOException {
        Application.changeToScene("login.fxml");     // - returns to login page
        Application.setActiveUser(null);                  // - deletes current active user
    }


    // =============================== GENERATE REPORTS ================================== //

    // ----  Selecting Fields For Reports ---- //
    @FXML
    public void reportTypePicked(){
        if (reportTypeBox.getValue() == "Stock Turnover") {
            rangeBox.getItems().clear();
            rangeBox.getItems().addAll("Global");
            rangeBox.setValue("Global");
        }
        else{
            rangeBox.getItems().clear();
            rangeBox.getItems().addAll("Global", "Individual");
        }
    }

    // ---- Generate Report Button Pressed ---- //
    @FXML
    public void generateReportPressed() throws IOException {
        System.out.println("Report Type = " + reportTypeBox.getValue());
        System.out.println("Range = " + rangeBox.getValue());

        if (reportTypeBox.getValue() != null && rangeBox.getValue() != null && validDatesPicked()) {
            // --------- INTERLINE SALES ---------- //
            if (reportTypeBox.getValue().equals("Interline Sales")){

                // ----- global interline ----- //
                if (rangeBox.getValue().equals("Global")){
                    GlobalInterlineController.setStartDate(Date.valueOf(startDatePicker.getValue()));
                    GlobalInterlineController.setEndDate(Date.valueOf(endDatePicker.getValue()));
                    PopupController.displayPopup("GlobalInterlineSales.fxml");

                }
                // ----- individual interline -----//
                else{
                    PopupController.displayPopup("IndividualInterlineSales.fxml");
                }
            }
            // --------- INTERLINE SALES ---------- //
            else if (reportTypeBox.getValue().equals("Domestic Sales")){

                // ----- global interline ----- //
                if (rangeBox.getValue().equals("Global")){
                    PopupController.displayPopup("GlobalDomesticSales.fxml");
                }
                // ----- individual interline -----//
                else{
                    PopupController.displayPopup("IndividualIDomesticSales.fxml");
                }
            }
        }
        else{
            System.out.println("Invalid fields");
        }
    }




    // ================================ ASSIGNS BLANKS TAB =============================== //

    // ----- Open Assign Blanks Tab ----- //
    @FXML
    public void assignBlanksOpened() {
        updateStaffBlanksTable();
    }

    // ----- Press Assign Blanks ---- //
    @FXML
    public void assignBlanksPressed() throws IOException {
        if (selectedStaffID != 0) {
            AssignStaffBlankPopupController.setStaffBlanks(staffBlanks);
            AssignStaffBlankPopupController.setTravelAgentCode(Application.getActiveUser().getTravelAgentCode());
            PopupController.displayPopup("AllocateStaffBlanksPopup.fxml");
        }
    }

    // ---- edited search staff blanks ----- //
    @FXML
    public void searchStaffBlanksEdited() {
        updateStaffBlanksTable();
    }

    // ================================= VIEW CUSTOMERS =================================== //

    // ----- Open View Customers ----- //
    @FXML
    public void viewCustomerOpened(){
        updateCustomerTable();
    }
    // ---- Checked valued box ----- //
    @FXML
    public void pressedValued(){
        if (isValued == false){
            isValued = true;
        }
        else{
            isValued = false;
            discountTypeBox.setValue(null);
        }
    }

    // ---- Pressed Apply changes ---- //
    @FXML
    public void pressedApplyChanges(){
        if ((isValued == true && discountTypeBox.getValue() != null) || (isValued == false && discountTypeBox.getValue() == null)){
            CustomerAccountSQLHelper.updateCustomerStatus(isValued, (String) discountTypeBox.getValue(), customerAccount.getCustomerID());
            updateCustomerTable();
        }
        else{
            System.out.println("Missing Fields");
        }
    }







    private void updateStaffBlanksTable() {
        staffBlanksView.getItems().clear();
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = BlankSQLHelper.staffBlankCount(searchStaffBlanksField.getText(), Application.getActiveUser().getTravelAgentCode(), connection);

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
            System.out.println(e);
        }
    }

    private void updateCustomerTable(){
        customerTableView.getItems().clear();
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = CustomerAccountSQLHelper.getAllCustomers(connection);

            // ----- convert rs to list ----- //
            ArrayList<CustomerAccount> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {
                    data.add(new CustomerAccount(rs));
                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    SQLToTable.fillTableView(customerTableView, rs);
                    customerTableView.setItems(dataList);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Boolean validDatesPicked(){
        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null){
            int val = startDatePicker.getValue().compareTo(endDatePicker.getValue());
            if ( val <= 0){
                System.out.println("Valid Dates picked");
                return true;
            }
            else{
                System.out.println("Invalid Dates picked");
                return false;
            }
        }
        System.out.println("Invalid Dates picked");
        return false;
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
                if (staffBlanksView.getSelectionModel().getSelectedItem() != null) {
                    staffBlanks = (StaffBlanks) staffBlanksView.getSelectionModel().getSelectedItem();
                    selectedStaffID = staffBlanks.getStaffID();
                    System.out.println("Selected StaffID: " + selectedStaffID);
                } else {
                    selectedStaffID = 0;
                    System.out.println("Selected StaffID: " + selectedStaffID);
                }
            }
        });

        customerTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (customerTableView.getSelectionModel().getSelectedItem() != null) {
                    customerAccount = (CustomerAccount) customerTableView.getSelectionModel().getSelectedItem();
                    selectedCustomerID = customerAccount.getCustomerID();
                    if (Objects.equals(customerAccount.getStatus(), "Valued")){
                        isValued = true;
                        valuedBox.setSelected(true);
                    }
                    else{
                        isValued = false;
                        valuedBox.setSelected(false);
                    }
                    discountTypeBox.setValue(customerAccount.getDiscountType());

                    System.out.println("Selected StaffID: " + selectedCustomerID);
                } else {
                    selectedStaffID = 0;
                    System.out.println("Selected StaffID: " + selectedCustomerID);
                }
            }
        });

        reportTypeBox.getItems().addAll("Stock Turnover", "Interline Sales", "Domestic Sales");

        rangeBox.getItems().addAll("Global", "Individual");
        discountTypeBox.getItems().addAll("Fixed", "Flex");
    }


}
