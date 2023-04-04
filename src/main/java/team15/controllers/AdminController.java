package team15.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import team15.Application;
import team15.DatabaseConnector;
import team15.SQLHelpers.StaffAccountSQL;
import team15.SQLToTable;
import team15.models.StaffAccount;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;


public class AdminController {
    @FXML
    private TextField searchStaffField;
    @FXML
    private TableView staffTableView;

    // ============================ LOGOUT ===============================//
    // ----- Logout Button IS Pressed ----- //
    @FXML
    public void logoutPressed() throws IOException {
        Application.changeToScene("login.fxml");     // - returns to login page
        Application.setActiveUser(null);                  // - deletes current active user
    }


    // =========================== VIEW STAFF =========================== //

    // ----- Opened to View Staff Tab ----- //
    @FXML
    public void openStaffTab(){
        searchStaffEdited();
    }

    // ----- User enters a value into the search bar ----- //
    @FXML
    public void searchStaffEdited() {
        try (Connection connection = DatabaseConnector.connect()) {
            System.out.println(searchStaffField.getText());

            if (StaffAccountSQL.checkStaffAccount(searchStaffField.getText())) {
                System.out.println("set found");

                // ----- Retrieve rs from StaffSQLAccount ----- //
                ResultSet rs = StaffAccountSQL.getResultSet(searchStaffField.getText(), connection);

                // ----- convert rs to list ----- //
                ArrayList<StaffAccount> data = new ArrayList<>();
                if (!(rs == null)) {
                    while (rs.next()) {
                        data.add(new StaffAccount(rs));
                        // ---- turn list into observable list ----- //
                        ObservableList dataList = FXCollections.observableArrayList(data);

                        // ---- display table view ---- //
                        SQLToTable.fillTableView(staffTableView, rs);
                        staffTableView.setItems(dataList);
                    }
                }
            } else {
                System.out.println("set not found");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


}
