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
import team15.SQLHelpers.*;
import team15.SQLToTable;
import team15.models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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
    @FXML
    Label outOfStockLabel;
    @FXML
    TextField priceField;
    @FXML
    CheckBox cashBox;
    @FXML
    Label discountLabel;
    @FXML
    CheckBox payLaterBox;
    @FXML
    Label finalPriceLabel;
    @FXML
    DatePicker refundDateField;
    @FXML
    TextField refundBlankIDField;
    @FXML
    TextField refundFirstNameField;
    @FXML
    TextField refundLastNameField;
    @FXML
    TableView salesRecordTableView;

    private long refundBlankID = 0;

    private long selectedSalesRecordID = -1;
    private SalesRecord selectedSalesRecord;

    private Boolean cashPayment = false;
    private boolean payLater = false;
    private String paymentType = "Debit/Credit";
    private BankCardDetails paymentInfo;

    private Boolean customerFound;
    private CustomerAccount currentCustomer;
    private int currentBlank = 0;

    private ArrayList<Integer> flightIDs = new ArrayList<Integer>();
    private final double discount = 0;


    // ========================================= REFUNDS ============================================== //

    // ----- Editing blank ID ------ //
    @FXML
    public void refundBlankIDEdited() {
        if (refundBlankIDField.getText() != "") {
            refundBlankID = Long.parseLong(refundBlankIDField.getText());
        } else {
            refundBlankID = 0;
        }
    }

    // ----- Searching for Sales Record ----- //
    @FXML
    public void searchSalesRecordPressed() {
        updateSalesRecordTable();
    }

    // ---- Pressing Refund Button ---- //
    @FXML
    public void refundButtonPressed() throws SQLException {
        if (selectedSalesRecordID != -1) {
            RefundRecordSQLHelper.createRefund(selectedSalesRecordID, selectedSalesRecord.getCommission());
            CustomerAccountSQLHelper.returnCommission(selectedSalesRecord);
            System.out.println("Refund Successful");
            updateSalesRecordTable();
        }
    }


    // ======================================== SELLING BLANKS ====================================== //

    // ----- OPEN SELL BLANKS ----- //
    @FXML
    public void openSellBlanks() {
        updateFlightTable();
    }

    // ----- Searching For The Customer ----- //
    @FXML
    public void searchButtonPressed() {
        if (CustomerAccountSQLHelper.checkCustomer(FirstNameField.getText(), LastNameField.getText(), DobField.getValue())) {
            System.out.println("Found");

            // ----- retrieve Customer ----- //
            currentCustomer = CustomerAccountSQLHelper.getCustomer(FirstNameField.getText(), LastNameField.getText(), DobField.getValue());
            customerFound = true;
            customerIDLabel.setText(String.valueOf(currentCustomer.getCustomerID()));
            customerStatus.setText(currentCustomer.getStatus());

            // ----- retrieve Customer payment details ----- //
            paymentInfo = BankCardDetailsSQLHelper.getBankCardDetails(currentCustomer.getCustomerID());
            bankNameField.setText(paymentInfo.getBank());
            accountNumberField.setText(String.valueOf(paymentInfo.getAccountNumber()));
            sortCodeField.setText(String.valueOf(paymentInfo.getSortCode()));

        } else {
            System.out.println("NOT Found");
            customerFound = false;
        }
    }

    // ----- Selecting Blank Type ----- //
    @FXML
    public void blankTypeSelected() {
        // ----- blank is in stock ----- //
        if (BlankSQLHelper.checkBlankTypeStock(Application.getActiveUser().getStaffID(), (int) blankTypeSelection.getValue())) {
            outOfStockLabel.setText("");
            currentBlank = (int) blankTypeSelection.getValue();
        } else {
            outOfStockLabel.setText("OUT OF STOCK");
            currentBlank = 0;
        }
        updateFlightTable();
    }

    // ----- Searching For Flights ----- //
    private void updateFlightTable() {
        flightsTableView.getItems().clear();
        if (currentBlank != 0 && currentBlank != 452 && currentBlank != 451) {
            reassignColumns();
            try (Connection connection = DatabaseConnector.connect()) {
                LocalDate departureDate;
                if (departureDateField.getValue() == null) {
                    departureDate = LocalDate.now();
                    System.out.println(Timestamp.valueOf(departureDate.atStartOfDay()));
                } else {
                    departureDate = departureDateField.getValue();
                }
                ResultSet rs = FlightSQLHelper.getFlightPath(startingAirportField.getText(), destinationAirportField.getText(), departureDate, currentBlank, connection);

                // ----- convert rs to list (444)----- //
                if (currentBlank == 444) {
                    ArrayList<FlightPath444> data = new ArrayList<>();
                    if (rs != null) {
                        while (rs.next()) {
                            data.add(new FlightPath444(rs));
                            // ---- turn list into observable list ----- //
                            ObservableList dataList = FXCollections.observableArrayList(data);

                            // ---- display table view ---- //
                            flightsTableView.setItems(dataList);
                        }
                    }
                } else if (currentBlank == 440) {
                    ArrayList<FlightPath440> data = new ArrayList<>();
                    if (rs != null) {
                        while (rs.next()) {
                            data.add(new FlightPath440(rs));
                            // ---- turn list into observable list ----- //
                            ObservableList dataList = FXCollections.observableArrayList(data);

                            // ---- display table view ---- //
                            flightsTableView.setItems(dataList);
                        }
                    }
                } else if (currentBlank == 201) {
                    ArrayList<FlightPath201> data = new ArrayList<>();
                    if (rs != null) {
                        while (rs.next()) {
                            data.add(new FlightPath201(rs));
                            // ---- turn list into observable list ----- //
                            ObservableList dataList = FXCollections.observableArrayList(data);

                            // ---- display table view ---- //
                            flightsTableView.setItems(dataList);
                        }
                    }
                } else if (currentBlank == 101) {
                    ArrayList<FlightPath101> data = new ArrayList<>();
                    if (rs != null) {
                        while (rs.next()) {
                            data.add(new FlightPath101(rs));
                            // ---- turn list into observable list ----- //
                            ObservableList dataList = FXCollections.observableArrayList(data);

                            // ---- display table view ---- //
                            flightsTableView.setItems(dataList);
                        }
                    }
                }


            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    public void startingAirportEdited() {
        updateFlightTable();
    }

    @FXML
    public void destinationAirportEdited() {
        updateFlightTable();
    }

    @FXML
    public void dateSelected() {
        updateFlightTable();
    }

    // ----- Cash payment selected -----/
    @FXML
    public void cashPressed() {
        if (cashPayment) {
            cashPayment = false;
        } else {
            cashPayment = true;
            payLater = false;
            payLaterBox.setSelected(false);
        }
        changePaymentType();
    }

    // ---- Pay Later Selected ----- //
    @FXML
    public void payLaterPressed() {
        if (bankNameField.getText() != null && accountNumberField.getText() != null && sortCodeField.getText() != null) {
            if (payLater) {
                payLater = false;
            } else {
                payLater = true;
                cashBox.setSelected(false);
                cashPayment = false;
            }
        }
        changePaymentType();
    }

    // ---- Price Set ----- //
    @FXML
    public void priceChanged() {
        finalPriceLabel.setText(String.valueOf(Double.parseDouble(priceField.getText()) - discount));
    }


    // ---------- PRESSED SELL BLANKS ---------- //
    @FXML
    public void sellBlankPressed() {
        // ----- Check if conditions met ----- //
        if (checkForEmptySellBlankFields() && checkPaymentInfo() && checkValidFlightPicked()) {

            // ---- retrieve the blank ----- //
            Blank blankForSale = BlankSQLHelper.getBlankForSale(Application.getActiveUser().getStaffID(), currentBlank);

            // ---- create Sales Record ---- //
            long blankID = blankForSale.getBlankID();
            int customerID = -1;
            if (customerFound) {
                customerID = currentCustomer.getCustomerID();
            }
            int staffID = Application.getActiveUser().getStaffID();
            double localPrice = Double.parseDouble(finalPriceLabel.getText());
            double discount = this.discount;
            double conversionRate = TravelAgentSQLHelper.getTravelAgent(Application.getActiveUser().getTravelAgentCode()).getUSDConversionRate();
            conversionRate = Math.round(conversionRate * 100.0) / 100.0;
            double usdPrice = localPrice * conversionRate;
            usdPrice = Math.round(usdPrice * 100.0) / 100.0;
            double commission = usdPrice * TravelAgentContractSQLHelper.getCommissionRate(Application.getActiveUser().getTravelAgentCode(), String.valueOf(currentBlank));
            commission = Math.round(commission * 100.0) / 100.0;
            double taxRate = 0;
            String bank = "";
            long accountNumber = 0;
            long sortcode = 0;
            if (!cashPayment) {
                bank = bankNameField.getText();
                accountNumber = Long.parseLong(accountNumberField.getText());
                sortcode = Long.parseLong(sortCodeField.getText());
            }
            String customerFirstName = FirstNameField.getText();
            String customerLastName = LastNameField.getText();

            // --------------- ADD SALES RECORD TO DATABASE ------------- //
            SalesRecordSQLHelper.createNewRecord(blankID, customerID, staffID, localPrice,
                    discount, conversionRate, usdPrice, commission,
                    taxRate, paymentType, bank, accountNumber, sortcode,
                    customerFirstName, customerLastName);
        }
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
        startingAirportColumn.setCellValueFactory(new PropertyValueFactory<FlightPath444, String>("StartingAirport"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<FlightPath444, Timestamp>("Departure"));
        leg1Column.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg1"));
        leg2Column.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg2"));
        leg3Column.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg3"));
        leg4Column.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg4"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<FlightPath444, String>("Destination"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<FlightPath444, Timestamp>("Arrival"));

        // ----- Combo Box ----- //
        blankTypeSelection.getItems().addAll(444, 420, 201, 101, 451, 452);


        // ====================== GET FLIGHT IDS FROM FLIGHT =================== //
        flightsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                flightIDs = new ArrayList<Integer>();
                if (flightsTableView.getSelectionModel().getSelectedItem() != null) {
                    if (currentBlank == 444) {
                        FlightPath444 flight = (FlightPath444) flightsTableView.getSelectionModel().getSelectedItem();
                        flightIDs.add(flight.getFlightLeg1());
                        flightIDs.add(flight.getFlightLeg2());
                        flightIDs.add(flight.getFlightLeg3());
                        flightIDs.add(flight.getFlightLeg4());
                    } else if (currentBlank == 440) {
                        FlightPath440 flight = (FlightPath440) flightsTableView.getSelectionModel().getSelectedItem();
                        flightIDs.add(flight.getFlightLeg1());
                        flightIDs.add(flight.getFlightLeg2());
                    } else if (currentBlank == 201) {
                        FlightPath201 flight = (FlightPath201) flightsTableView.getSelectionModel().getSelectedItem();
                        flightIDs.add(flight.getFlightLeg1());
                        flightIDs.add(flight.getFlightLeg2());
                    } else if (currentBlank == 101) {
                        FlightPath101 flight = (FlightPath101) flightsTableView.getSelectionModel().getSelectedItem();
                        flightIDs.add(flight.getFlightLeg1());
                    }
                }
                System.out.println(flightIDs);
            }
        });

        // ====================== Entering the price =================== //
        priceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    priceField.setText(oldValue);
                }
                if (newValue.length() > 15) {
                    priceField.setText(oldValue);
                }
            }
        });

        accountNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    accountNumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 10) {
                    accountNumberField.setText(oldValue);
                }
            }
        });

        sortCodeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    sortCodeField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 6) {
                    sortCodeField.setText(oldValue);
                }
            }
        });

        refundBlankIDField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    refundBlankIDField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 11) {
                    refundBlankIDField.setText(oldValue);
                }
            }
        });

        salesRecordTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (salesRecordTableView.getSelectionModel().getSelectedItem() != null) {
                    selectedSalesRecord = (SalesRecord) salesRecordTableView.getSelectionModel().getSelectedItem();
                    selectedSalesRecordID = selectedSalesRecord.getRecordID();
                } else {
                    selectedSalesRecordID = -1;
                    selectedSalesRecord = null;
                }
                System.out.println(selectedSalesRecordID);
            }
        });
    }

    private void reassignColumns() {
        flightsTableView.getColumns().clear();
        TableColumn column1 = new TableColumn<>();
        TableColumn column2 = new TableColumn<>();
        TableColumn column3 = new TableColumn<>();
        TableColumn column4 = new TableColumn<>();
        TableColumn column5 = new TableColumn<>();
        TableColumn column6 = new TableColumn<>();
        TableColumn column7 = new TableColumn<>();
        TableColumn column8 = new TableColumn<>();

        switch (currentBlank) {
            case 444:
                column1.setText("StartingAirport");
                column1.setCellValueFactory(new PropertyValueFactory<FlightPath444, String>("StartingAirport"));

                column2.setText("Departure");
                column2.setCellValueFactory(new PropertyValueFactory<FlightPath444, Timestamp>("Departure"));

                column3.setText("FlightLeg1");
                column3.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg1"));

                column4.setText("FlightLeg2");
                column4.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg2"));

                column5.setText("FlightLeg3");
                column5.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg3"));

                column6.setText("FlightLeg3");
                column6.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg4"));

                column7.setText("Destination");
                column7.setCellValueFactory(new PropertyValueFactory<FlightPath444, String>("Destination"));

                column8.setText("Arrival");
                column8.setCellValueFactory(new PropertyValueFactory<FlightPath444, Timestamp>("Arrival"));

                flightsTableView.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8);
                break;

            case 420:
            case 201:
                column1.setText("StartingAirport");
                column1.setCellValueFactory(new PropertyValueFactory<FlightPath444, String>("StartingAirport"));

                column2.setText("Departure");
                column2.setCellValueFactory(new PropertyValueFactory<FlightPath444, Timestamp>("Departure"));

                column3.setText("FlightLeg1");
                column3.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg1"));

                column4.setText("FlightLeg2");
                column4.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg2"));

                column5.setText("Destination");
                column5.setCellValueFactory(new PropertyValueFactory<FlightPath444, String>("Destination"));

                column6.setText("Arrival");
                column6.setCellValueFactory(new PropertyValueFactory<FlightPath444, Timestamp>("Arrival"));

                flightsTableView.getColumns().addAll(column1, column2, column3, column4, column5, column6);
                break;

            case 101:
                column1.setText("StartingAirport");
                column1.setCellValueFactory(new PropertyValueFactory<FlightPath444, String>("StartingAirport"));

                column2.setText("Departure");
                column2.setCellValueFactory(new PropertyValueFactory<FlightPath444, Timestamp>("Departure"));

                column3.setText("FlightLeg1");
                column3.setCellValueFactory(new PropertyValueFactory<FlightPath444, Integer>("FlightLeg1"));

                column4.setText("Destination");
                column4.setCellValueFactory(new PropertyValueFactory<FlightPath444, String>("Destination"));

                column5.setText("Arrival");
                column5.setCellValueFactory(new PropertyValueFactory<FlightPath444, Timestamp>("Arrival"));

                flightsTableView.getColumns().addAll(column1, column2, column3, column4, column5);
                break;

        }
    }

    private boolean checkForEmptySellBlankFields() {
        if (FirstNameField.getText() != "" && LastNameField.getText() != "" &&
                checkPaymentInfo() && currentBlank != 0 && priceField.getText() != "") {
            return true;
        } else {
            System.out.println("Missing Fields");
            return false;
        }
    }

    private boolean checkPaymentInfo() {
        if (cashPayment) {
            return true;
        } else return bankNameField.getText() != "" && accountNumberField.getText() != "" && sortCodeField.getText() != "";
    }

    private boolean checkValidFlightPicked() {
        // ----- if a flight blank has been selected ----- //
        if ((currentBlank == 444) || currentBlank == 440 || currentBlank == 201 || currentBlank == 101) {
            // ---- check that advisor has selected a flight ----- //
            if (!flightIDs.isEmpty()) {
                return true;
            }
        }
        // ---- check if it is a MCO blank ---- //
        return currentBlank == 452 || currentBlank == 451;
    }

    private void changePaymentType() {
        if (cashPayment && !payLater) {
            paymentType = "Cash";
        } else if (!cashPayment && payLater) {
            paymentType = "Pay Later";
        } else if (!cashPayment && !payLater) {
            paymentType = "Debit/Credit";
        }
        System.out.println(paymentType);
    }

    private void updateSalesRecordTable() {
        try (Connection connection = DatabaseConnector.connect()) {
            Date date;
            if (refundDateField.getValue() == null) {
                date = null;
            } else {
                date = Date.valueOf(refundDateField.getValue());
            }
            if (SalesRecordSQLHelper.checkSalesRecord(refundBlankID, date, refundFirstNameField.getText(), refundLastNameField.getText())) {
                System.out.println("set found");

                // ----- Retrieve rs from SalesRecordSQLHelper ----- //
                ResultSet rs = SalesRecordSQLHelper.getSalesRecords(refundBlankID, date, refundFirstNameField.getText(), refundLastNameField.getText(), connection);

                // ----- convert rs to list ----- //
                ArrayList<SalesRecord> data = new ArrayList<>();
                if (!(rs == null)) {
                    while (rs.next()) {
                        data.add(new SalesRecord(rs));
                        // ---- turn list into observable list ----- //
                        ObservableList dataList = FXCollections.observableArrayList(data);

                        // ---- display table view ---- //
                        SQLToTable.fillTableView(salesRecordTableView, rs);
                        salesRecordTableView.setItems(dataList);
                    }
                }
            } else {
                salesRecordTableView.getItems().clear();
                System.out.println("set not found");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
