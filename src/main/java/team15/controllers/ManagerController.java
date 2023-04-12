package team15.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import team15.Application;
import team15.DatabaseConnector;
import team15.PopupController;
import team15.SQLHelpers.*;
import team15.SQLToTable;
import team15.models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
    Label exchangeRateLabel;
    @FXML
    Label fixedDiscountLabel;
    @FXML
    TableView discountTableView;
    @FXML
    TextField discountField;
    @FXML
    TextField thresholdField;
    @FXML
    TextField fixedDiscountField;
    @FXML
    TextField exchangeRateField;



    private int selectedStaffID;
    private int selectedCustomerID;
    private StaffBlanks staffBlanks;
    private CustomerAccount customerAccount;
    private Boolean isValued = false;
    private FlexibleDiscount flexibleDiscount;

    @FXML
    public void logoutPressed() throws IOException {
        Application.changeToScene("login.fxml");     // - returns to login page
        Application.setActiveUser(null);                  // - deletes current active user
    }

    // ============================== USD & DISCOUNT ===================================== //

    // ----- Opened USD & Discounts ----- //
    @FXML
    public void openedUSDAndDiscount() throws SQLException {
        TravelAgent travelAgent = TravelAgentSQLHelper.getTravelAgent(Application.getActiveUser().getTravelAgentCode());
        double exchangeRate = travelAgent.getUSDConversionRate();
        exchangeRate = Math.round(exchangeRate * 100.0) / 100.0;
        exchangeRateLabel.setText("1 Unit = $ "+ String.valueOf(exchangeRate));

        double fixedDiscount = FixedDiscountSQLHelper.getFixedDiscount();
        fixedDiscount = Math.round(fixedDiscount * 100.0) / 100.0;
        fixedDiscountLabel.setText("Fixed Discount: "+fixedDiscount+"%");

        updateFlexibleDiscountTable();
    }


    // ----- Save Exchange Rate Pressed ----- //
    @FXML
    public void saveExchangeRatePressed() throws SQLException {
        if (!exchangeRateField.getText().equals("")){
            double conversionRate = Double.parseDouble(exchangeRateField.getText());
            conversionRate = Math.round(conversionRate * 100.0) / 100.0;
            TravelAgentSQLHelper.newExchangeRate(conversionRate);

            TravelAgent travelAgent = TravelAgentSQLHelper.getTravelAgent(Application.getActiveUser().getTravelAgentCode());
            double exchangeRate = travelAgent.getUSDConversionRate();
            exchangeRate = Math.round(exchangeRate * 100.0) / 100.0;
            exchangeRateLabel.setText("1 Unit = $ "+ String.valueOf(exchangeRate));
            exchangeRateField.setText("");
        }
    }

    // ----- Save Fixed ExchangeRate ------ //
    @FXML
    public void changeFixedDiscountPressed() throws SQLException {
        if (!fixedDiscountField.getText().equals("")){
            double fixedDiscount = Double.parseDouble(fixedDiscountField.getText());
            fixedDiscount = Math.round(fixedDiscount * 100.0) / 100.0;
            FixedDiscountSQLHelper.newFixedDiscount(fixedDiscount);

            fixedDiscount = FixedDiscountSQLHelper.getFixedDiscount();
            fixedDiscount = Math.round(fixedDiscount * 100.0) / 100.0;
            fixedDiscountLabel.setText("Fixed Discount: "+fixedDiscount+"%");
            fixedDiscountField.setText("");
        }
    }

    // ----- Deleting A Flexible Discount ----- //
    @FXML
    public void deleteFlexibleDiscountPressed() throws SQLException{
        if (flexibleDiscount != null){
            FlexibleDiscountSQLHelper.removeFlexibleDiscount(flexibleDiscount);
            thresholdField.setText("");
            discountField.setText("");

            updateFlexibleDiscountTable();
        }
    }

    // ----- Adding A New Flexible Discount ----- //
    @FXML
    public void addNewFlexibleDiscountPressed() throws SQLException {

        double threshold = Double.parseDouble(thresholdField.getText());
        threshold = Math.round(threshold * 100.0) / 100.0;

        double discount = Double.parseDouble(discountField.getText());
        discount = Math.round(discount * 100.0) / 100.0;

        if (!FlexibleDiscountSQLHelper.checkFlexibleDiscountExists(threshold, discount)){
            FlexibleDiscountSQLHelper.addNewFlexibleDiscount(threshold, discount);
            thresholdField.setText("");
            discountField.setText("");

            updateFlexibleDiscountTable();
        }
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
                    GlobalInterlineController.setTravelAgentCode(Application.getActiveUser().getTravelAgentCode());
                    PopupController.displayPopup("GlobalInterlineSales.fxml");

                }
                // ----- individual interline -----//
                else{
                    IndividualInterlineController.setStartDate(Date.valueOf(startDatePicker.getValue()));
                    IndividualInterlineController.setEndDate(Date.valueOf(endDatePicker.getValue()));
                    IndividualInterlineController.setTravelAgentCode(Application.getActiveUser().getTravelAgentCode());
                    PopupController.displayPopup("IndividualInterlineSales.fxml");
                }
            }
            // --------- DOMESTIC SALES ---------- //
            else if (reportTypeBox.getValue().equals("Domestic Sales")){

                // ----- global domestic ----- //
                if (rangeBox.getValue().equals("Global")){
                    GlobalDomesticController.setStartDate(Date.valueOf(startDatePicker.getValue()));
                    GlobalDomesticController.setEndDate(Date.valueOf(endDatePicker.getValue()));
                    GlobalDomesticController.setTravelAgentCode(Application.getActiveUser().getTravelAgentCode());
                    PopupController.displayPopup("GlobalDomesticSales.fxml");
                }
                // ----- individual domestic -----//
                else{
                    IndividualDomesticController.setStartDate(Date.valueOf(startDatePicker.getValue()));
                    IndividualDomesticController.setEndDate(Date.valueOf(endDatePicker.getValue()));
                    IndividualDomesticController.setTravelAgentCode(Application.getActiveUser().getTravelAgentCode());
                    PopupController.displayPopup("IndividualDomesticSales.fxml");
                }
            }
            // -------- STOCK TURNOVER -------- //
            else if (reportTypeBox.getValue().equals("Stock Turnover")) {
                StockTurnoverController.setStartDate(Date.valueOf(startDatePicker.getValue()));
                StockTurnoverController.setEndDate(Date.valueOf(endDatePicker.getValue()));
                PopupController.displayPopup("TicketStockTurnover.fxml");
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

    private void updateFlexibleDiscountTable() throws SQLException {
        discountTableView.getColumns().clear();
        TableColumn column1 = new TableColumn<>();
        TableColumn column2 = new TableColumn<>();

        column1.setText("Threshold");
        column1.setCellValueFactory(new PropertyValueFactory<FlexibleDiscount, String>("Threshold"));

        column2.setText("Discount (%)");
        column2.setCellValueFactory(new PropertyValueFactory<FlexibleDiscount, Timestamp>("Percentage"));

        discountTableView.getColumns().addAll(column1, column2);

        discountTableView.getItems().clear();
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = FlexibleDiscountSQLHelper.getFlexibleDiscounts(connection);

            ArrayList<FlexibleDiscount> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {
                    FlexibleDiscount flexibleDiscount = new FlexibleDiscount(rs);

                    data.add(flexibleDiscount);
                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    discountTableView.setItems(dataList);
                }

            }
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

        // ---- opens on USD Rate ------ //
        try {
            openedUSDAndDiscount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

        exchangeRateField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    exchangeRateField.setText(oldValue);
                }
                if (newValue.length() > 15) {
                    exchangeRateField.setText(oldValue);
                }
            }
        });

        thresholdField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    thresholdField.setText(oldValue);
                }
                if (newValue.length() > 15) {
                    thresholdField.setText(oldValue);
                }
            }
        });

        discountField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    discountField.setText(oldValue);
                }
                if (newValue.length() > 15) {
                    discountField.setText(oldValue);
                }
            }
        });
        fixedDiscountField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    fixedDiscountField.setText(oldValue);
                }
                if (newValue.length() > 15) {
                    fixedDiscountField.setText(oldValue);
                }
            }
        });

        discountTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (discountTableView.getSelectionModel().getSelectedItem() != null) {
                    flexibleDiscount = (FlexibleDiscount) discountTableView.getSelectionModel().getSelectedItem();
                }
                else{
                    flexibleDiscount = null;
                }
            }
        });

    }


}
