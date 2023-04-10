package team15.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import team15.Application;
import team15.DatabaseConnector;
import team15.SQLHelpers.Report;
import team15.SQLHelpers.ReportSQLHelper;


import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GlobalInterlineController implements Initializable {

    @FXML
    private static TableView salesTableView;
    @FXML
    private static Label reportPeriodLabel;
    @FXML
    private static Label travelAgentLabel;
    @FXML
    private static Label blanksSoldLabel;
    @FXML
    private static Label numPaymentsMadeLabel;
    @FXML
    private static Label paymentsValueLabel;
    @FXML
    private static Label numPaymentsPendingLabel;
    @FXML
    private static Label pendingValueLabel;
    @FXML
    private static Label totalCommissionLabel;



    private static Date startDate;
    private static Date endDate;
    private static int travelAgentCode;


    private static int blanksSold;

    private static int numPaymentsMade;
    private static double paymentsValue;

    private static int numPaymentsPending;
    private static double pendingValue;

    private static double totalCommission;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillInTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createReportColumns(){
        salesTableView.getColumns().clear();
        TableColumn column1 = new TableColumn<>();
        TableColumn column2 = new TableColumn<>();
        TableColumn column3 = new TableColumn<>();
        TableColumn column4 = new TableColumn<>();
        TableColumn column5 = new TableColumn<>();
        TableColumn column6 = new TableColumn<>();
        TableColumn column7 = new TableColumn<>();
        TableColumn column8 = new TableColumn<>();


        column1.setText("StaffID");
        column1.setCellValueFactory(new PropertyValueFactory<Report, Integer>("StaffID"));

        column2.setText("BlankID");
        column2.setCellValueFactory(new PropertyValueFactory<Report, Long>("BlankID"));

        column3.setText("Local Price");
        column3.setCellValueFactory(new PropertyValueFactory<Report, Double>("LocalPrice"));

        column4.setText("USD Price ($)");
        column4.setCellValueFactory(new PropertyValueFactory<Report, Double>("USDPrice"));

        column5.setText("Discount");
        column5.setCellValueFactory(new PropertyValueFactory<Report, Integer>("Discount"));

        column6.setText("Tax");
        column6.setCellValueFactory(new PropertyValueFactory<Report, Integer>("Tax"));

        column7.setText("Payment Method");
        column7.setCellValueFactory(new PropertyValueFactory<Report, Integer>("PaymentMethod"));

        column8.setText("Commission ($)");
        column8.setCellValueFactory(new PropertyValueFactory<Report, Integer>("Commission"));
    }

    private void fillInTable() throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = ReportSQLHelper.getGlobalDomesticReport(Application.getActiveUser().getTravelAgentCode(),connection, startDate, endDate);

            ArrayList<Report> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {
                    data.add(new Report(rs));
                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    createReportColumns();
                    salesTableView.setItems(dataList);
                }
            }
        }
    }


    public static Date getStartDate() {
        return startDate;
    }

    public static void setStartDate(Date startDate) {
        GlobalInterlineController.startDate = startDate;
    }

    public static Date getEndDate() {
        return endDate;
    }

    public static void setEndDate(Date endDate) {
        GlobalInterlineController.endDate = endDate;
    }
}
