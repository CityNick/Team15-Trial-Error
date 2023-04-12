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
import team15.SQLHelpers.ReportSQLHelper;
import team15.models.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockTurnoverController implements Initializable {

    @FXML
    TableView agentStockTableView;
    @FXML
    TableView subAgentTableView;
    @FXML
    TableView assignTableView;
    @FXML
    TableView usedTableView;
    @FXML
    TableView remainingTableView;
    @FXML
    Label periodLabel;
    @FXML
    Label travelAgentLabel;


    private static Date startDate;
    private static Date endDate;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            createAgentStockTable();
            createSubAgentTable();
            createAssignTable();
            createUsedTable();
            createRemainingTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        periodLabel.setText(String.valueOf(startDate) + " To " + String.valueOf(endDate));
        travelAgentLabel.setText(String.valueOf(Application.getActiveUser().getTravelAgentCode()));
    }

    private void createAgentStockTable() throws SQLException {
        agentStockTableView.getColumns().clear();
        TableColumn column1 = new TableColumn<>();
        TableColumn column2 = new TableColumn<>();

        column1.setText("Type");
        column1.setCellValueFactory(new PropertyValueFactory<AgentStock, Integer>("BlankType"));

        column2.setText("Count");
        column2.setCellValueFactory(new PropertyValueFactory<AgentStock, Integer>("Count"));

        agentStockTableView.getColumns().addAll(column1,column2);

        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = ReportSQLHelper.getAgentStock(connection, startDate, endDate);

            ArrayList<AgentStock> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {

                    AgentStock agentStock = new AgentStock(rs);
                    data.add(agentStock);

                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    agentStockTableView.setItems(dataList);
                }
            }
        }
    }

    private void createSubAgentTable() throws SQLException {
        subAgentTableView.getColumns().clear();
        TableColumn column1 = new TableColumn<>();
        TableColumn column2 = new TableColumn<>();
        TableColumn column3 = new TableColumn<>();

        column1.setText("StaffID");
        column1.setCellValueFactory(new PropertyValueFactory<UsedStock, Integer>("StaffID"));

        column2.setText("Type");
        column2.setCellValueFactory(new PropertyValueFactory<UsedStock, Integer>("BlankType"));

        column3.setText("Count");
        column3.setCellValueFactory(new PropertyValueFactory<UsedStock, Integer>("Count"));

        subAgentTableView.getColumns().addAll(column1,column2, column3);

        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = ReportSQLHelper.getSubAgentStock(connection, startDate, endDate);

            ArrayList<UsedStock> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {

                    UsedStock usedStock = new UsedStock(rs);
                    data.add(usedStock);

                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    subAgentTableView.setItems(dataList);
                }
            }
        }
    }

    private void createAssignTable() throws SQLException {
        assignTableView.getColumns().clear();
        TableColumn column1 = new TableColumn<>();
        TableColumn column2 = new TableColumn<>();
        TableColumn column3 = new TableColumn<>();

        column1.setText("StaffID");
        column1.setCellValueFactory(new PropertyValueFactory<AssignStock, Integer>("StaffID"));

        column2.setText("Type");
        column2.setCellValueFactory(new PropertyValueFactory<AssignStock, Integer>("BlankType"));

        column3.setText("Count");
        column3.setCellValueFactory(new PropertyValueFactory<AssignStock, Integer>("Count"));

        assignTableView.getColumns().addAll(column1,column2, column3);

        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = ReportSQLHelper.getAssignStock(connection, startDate, endDate);

            ArrayList<AssignStock> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {

                    AssignStock assignStock = new AssignStock(rs);
                    data.add(assignStock);

                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    assignTableView.setItems(dataList);
                }
            }
        }
    }

    private void createUsedTable() throws SQLException{
        usedTableView.getColumns().clear();
        TableColumn column1 = new TableColumn<>();
        TableColumn column2 = new TableColumn<>();
        TableColumn column3 = new TableColumn<>();

        column1.setText("StaffID");
        column1.setCellValueFactory(new PropertyValueFactory<UsedStock, Integer>("StaffID"));

        column2.setText("Type");
        column2.setCellValueFactory(new PropertyValueFactory<UsedStock, Integer>("BlankType"));

        column3.setText("Count");
        column3.setCellValueFactory(new PropertyValueFactory<UsedStock, Integer>("Count"));

        usedTableView.getColumns().addAll(column1,column2, column3);

        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = ReportSQLHelper.getUsedStock(connection, startDate, endDate);

            ArrayList<UsedStock> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {

                    UsedStock usedStock = new UsedStock(rs);
                    data.add(usedStock);

                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    usedTableView.setItems(dataList);
                }
            }
        }
    }

    private void createRemainingTable() throws SQLException{
        remainingTableView.getColumns().clear();
        TableColumn column1 = new TableColumn<>();
        TableColumn column2 = new TableColumn<>();
        TableColumn column3 = new TableColumn<>();

        column1.setText("StaffID");
        column1.setCellValueFactory(new PropertyValueFactory<RemainingStock, Integer>("StaffID"));

        column2.setText("Type");
        column2.setCellValueFactory(new PropertyValueFactory<RemainingStock, Integer>("BlankType"));

        column3.setText("Count");
        column3.setCellValueFactory(new PropertyValueFactory<RemainingStock, Integer>("Count"));

        remainingTableView.getColumns().addAll(column1,column2, column3);

        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = ReportSQLHelper.getRemainingStock(connection, startDate, endDate);

            ArrayList<RemainingStock> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {

                    RemainingStock remainingStock = new RemainingStock(rs);
                    data.add(remainingStock);

                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    remainingTableView.setItems(dataList);
                }
            }
        }
    }



    public Date getStartDate() {
        return startDate;
    }

    public static void setStartDate(Date startDate) {
        StockTurnoverController.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public static void setEndDate(Date endDate) {
        StockTurnoverController.endDate = endDate;
    }
}
