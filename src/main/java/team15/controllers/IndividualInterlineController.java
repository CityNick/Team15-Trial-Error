package team15.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import team15.Application;
import team15.DatabaseConnector;
import team15.SQLHelpers.ReportSQLHelper;
import team15.SQLHelpers.StaffAccountSQLHelper;
import team15.models.Report;
import team15.models.StaffAccount;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IndividualInterlineController implements Initializable {
    @FXML
    private TableView salesTableView;
    @FXML
    private Label reportPeriodLabel;
    @FXML
    private Label travelAgentLabel;
    @FXML
    private Label blanksSoldLabel;
    @FXML
    private Label numPaymentsMadeLabel;
    @FXML
    private Label paymentsValueLabel;
    @FXML
    private Label numPaymentsPendingLabel;
    @FXML
    private Label pendingValueLabel;
    @FXML
    private Label totalCommissionLabel;
    @FXML
    private TableColumn staffIDColumn;
    @FXML
    private ComboBox staffBox;



    private static Date startDate;
    private static Date endDate;

    private static int travelAgentCode;


    private static int blanksSold=0;

    private static int numPaymentsMade=0;
    private static double paymentsValue=0;

    private static int numPaymentsPending=0;
    private static double pendingValue=0;
    private static double totalCommission=0;

    private static int staffID = -1;

    @FXML
    public void staffIDSelected() throws SQLException {
        resetValues();
        staffID = (int) staffBox.getValue();
        fillInTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createReportColumns();
        try {
            fillInTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            addStaffIDs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void createReportColumns(){
        TableColumn column1 = new TableColumn<>();
        TableColumn column2 = new TableColumn<>();
        TableColumn column3 = new TableColumn<>();
        TableColumn column4 = new TableColumn<>();
        TableColumn column5 = new TableColumn<>();
        TableColumn column6 = new TableColumn<>();
        TableColumn column7 = new TableColumn<>();
        TableColumn column8 = new TableColumn<>();
        TableColumn column9 = new TableColumn<>();


        column1.setText("StaffID");
        column1.setCellValueFactory(new PropertyValueFactory<Report, Integer>("StaffID"));

        column2.setText("BlankID");
        column2.setCellValueFactory(new PropertyValueFactory<Report, Long>("BlankID"));

        column3.setText("Local Price");
        column3.setCellValueFactory(new PropertyValueFactory<Report, Double>("LocalPrice"));

        column4.setText("USD Price ($)");
        column4.setCellValueFactory(new PropertyValueFactory<Report, Double>("USDPrice"));

        column5.setText("Discount");
        column5.setCellValueFactory(new PropertyValueFactory<Report, Double>("Discount"));

        column6.setText("Tax");
        column6.setCellValueFactory(new PropertyValueFactory<Report, Double>("Tax"));

        column7.setText("Payment Type");
        column7.setCellValueFactory(new PropertyValueFactory<Report, Integer>("PaymentType"));

        column8.setText("Commission ($)");
        column8.setCellValueFactory(new PropertyValueFactory<Report, Double>("Commission"));

        column9.setText("Date");
        column9.setCellValueFactory(new PropertyValueFactory<Report, Date>("Date"));

        salesTableView.getColumns().addAll(column1,column2,column3, column4,column5,column6,column7,column8,column9);
    }


    private void fillInTable() throws SQLException {
        salesTableView.getItems().clear();
        salesTableView.getColumns().clear();
        createReportColumns();
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = ReportSQLHelper.getIndividualInterlineReport(connection, startDate, endDate, staffID);

            ArrayList<Report> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {
                    blanksSold += 1;
                    Report report = new Report(rs);

                    if (report.getPaymentType().equals("Pay Later")){
                        numPaymentsPending += 1;
                        pendingValue += report.getUSDPrice();
                    }
                    else{
                        numPaymentsMade += 1;
                        paymentsValue += report.getUSDPrice();
                    }
                    totalCommission += report.getCommission();

                    data.add(report);
                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    salesTableView.setItems(dataList);
                }

            }
        }
        reportPeriodLabel.setText(String.valueOf(startDate) + " to " + String.valueOf(endDate));
        travelAgentLabel.setText(String.valueOf(travelAgentCode));
        blanksSoldLabel.setText(String.valueOf(blanksSold));
        numPaymentsMadeLabel.setText(String.valueOf(numPaymentsMade));
        paymentsValueLabel.setText("$ "+String.valueOf(paymentsValue));
        numPaymentsPendingLabel.setText(String.valueOf(numPaymentsPending));
        pendingValueLabel.setText("$ "+String.valueOf(pendingValue));
        totalCommission = Math.round(totalCommission * 100.0) / 100.0;
        totalCommissionLabel.setText("$ "+String.valueOf(totalCommission));
    }


    public static Date getStartDate() {
        return startDate;
    }

    public static void setStartDate(Date startDate) {
        IndividualInterlineController.startDate = startDate;
    }

    public static Date getEndDate() {
        return endDate;
    }

    public static void setEndDate(Date endDate) {
        IndividualInterlineController.endDate = endDate;
    }

    public static int getTravelAgentCode() {
        return travelAgentCode;
    }

    public static void setTravelAgentCode(int travelAgentCode) {
        IndividualInterlineController.travelAgentCode = travelAgentCode;
    }

    public static int getStaffID() {
        return staffID;
    }

    public static void setStaffID(int staffID) {
        IndividualInterlineController.staffID = staffID;
    }

    private void addStaffIDs() throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = StaffAccountSQLHelper.getStaffIDs(connection, Application.getActiveUser().getTravelAgentCode());

            if (!(rs == null)) {
                while (rs.next()) {
                    StaffAccount staff = new StaffAccount(rs);
                    staffBox.getItems().add(staff.getStaffID());
                }

            }
        }
    }

    private void resetValues(){
        blanksSold = 0;
        numPaymentsMade = 0;
        paymentsValue = 0;
        numPaymentsPending = 0;
        pendingValue = 0;
        totalCommission = 0;
    }
}
