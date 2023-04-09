package team15.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import team15.Application;
import team15.DatabaseConnector;
import team15.SQLHelpers.BankCardDetailsSQLHelper;
import team15.SQLHelpers.BlankSQLHelper;
import team15.SQLHelpers.CustomerAccountSQLHelper;
import team15.SQLHelpers.FlightSQLHelper;
import team15.SQLToTable;
import team15.models.BankCardDetails;
import team15.models.CustomerAccount;
import team15.models.FlightPath;
import team15.models.StaffBlanks;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TravelAdvisorController implements Initializable {

    @FXML
    TextField FirstNameField;
    @FXML
    TextField LastNameField;
    @FXML
    DatePicker DobField;
    @FXML
    Label customerIDLabel;
    @FXML
    Label customerStatus;
    @FXML
    TextField bankNameField;
    @FXML
    TextField accountNumberField;
    @FXML
    TextField sortCodeField;
    @FXML
    TableView flightsTableView;
    @FXML
    TextField startingAirportField;
    @FXML
    TextField destinationAirportField;
    @FXML
    DatePicker departureDateField;
    @FXML
    TableColumn startingAirportColumn;
    @FXML
    TableColumn departureColumn;
    @FXML
    TableColumn leg1Column;
    @FXML
    TableColumn leg2Column;
    @FXML
    TableColumn leg3Column;
    @FXML
    TableColumn leg4Column;
    @FXML
    TableColumn destinationColumn;
    @FXML
    TableColumn arrivalColumn;
    @FXML
    ComboBox blankTypeSelection;

    private BankCardDetails paymentInfo;

    private Boolean customerFound;
    private CustomerAccount currentCustomer;





    // ========================== SELLING BLANKS ========================== //

    // ----- OPEN SELL BLANKS ----- //
    @FXML
    public void openSellBlanks(){
        updateFlightTable();
    }

    // ----- Searching For The Customer ----- //
    @FXML
    public void searchButtonPressed(){
        if (CustomerAccountSQLHelper.checkCustomer(FirstNameField.getText(),LastNameField.getText(),DobField.getValue())) {
            System.out.println("Found");

            // ----- retrieve Customer ----- //
            currentCustomer = CustomerAccountSQLHelper.getCustomer(FirstNameField.getText(),LastNameField.getText(),DobField.getValue());
            customerFound = true;
            customerIDLabel.setText(String.valueOf(currentCustomer.getCustomerID()));
            customerStatus.setText(currentCustomer.getStatus());

            // ----- retrieve Customer payment details ----- //
            paymentInfo = BankCardDetailsSQLHelper.getBankCardDetails(currentCustomer.getCustomerID());
            bankNameField.setText(paymentInfo.getBank());
            accountNumberField.setText(String.valueOf(paymentInfo.getAccountNumber()));
            sortCodeField.setText(String.valueOf(paymentInfo.getSortCode()));

        }
        else{
            System.out.println("NOT Found");
            customerFound = false;
        }
    }

    // ----- Selecting Blank Type ----- //
    @FXML
    public void blankTypeSelected


    // ----- Searching For Flights ----- //
    public void updateFlightTable(){
        flightsTableView.getItems().clear();
        try (Connection connection = DatabaseConnector.connect()) {
            LocalDate departureDate;
            if (departureDateField.getValue() == null){departureDate = LocalDate.now();
            System.out.println(Timestamp.valueOf(departureDate.atStartOfDay()));}
            else{departureDate = departureDateField.getValue();}
            ResultSet rs = FlightSQLHelper.getFlightPath(startingAirportField.getText(),destinationAirportField.getText(), departureDate, connection);

            // ----- convert rs to list ----- //
            ArrayList<FlightPath> data = new ArrayList<>();
            if (rs != null) {
                while (rs.next()) {
                    data.add(new FlightPath(rs));
                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    //SQLToTable.fillTableView(flightsTableView, rs);
                    flightsTableView.setItems(dataList);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    @FXML
    public void startingAirportEdited(){
        updateFlightTable();
    }
    @FXML
    public void destinationAirportEdited(){
        updateFlightTable();
    }



    // ============================= LOGOUT ============================== //
    @FXML
    public void logoutPressed() throws IOException {
        Application.changeToScene("login.fxml");     // - returns to login page
        Application.setActiveUser(null);                  // - deletes current active user
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // ----- Column Set Up ----- //
        startingAirportColumn.setCellValueFactory(new PropertyValueFactory<FlightPath,String>("StartingAirport"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<FlightPath,Timestamp>("Departure"));
        leg1Column.setCellValueFactory(new PropertyValueFactory<FlightPath, Integer>("FlightLeg1"));
        leg2Column.setCellValueFactory(new PropertyValueFactory<FlightPath, Integer>("FlightLeg2"));
        leg3Column.setCellValueFactory(new PropertyValueFactory<FlightPath, Integer>("FlightLeg3"));
        leg4Column.setCellValueFactory(new PropertyValueFactory<FlightPath, Integer>("FlightLeg4"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<FlightPath,String>("Destination"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<FlightPath,Timestamp>("Arrival"));

        // ----- Combo Box ----- //
        blankTypeSelection.getItems().addAll(444,420,201,101,451,452);
    }
}
