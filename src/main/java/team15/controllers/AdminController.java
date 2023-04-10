package team15.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import team15.Application;
import team15.DatabaseConnector;
import team15.PopupController;
import team15.SQLHelpers.BlankSQLHelper;
import team15.SQLHelpers.StaffAccountSQLHelper;
import team15.SQLHelpers.TravelAgentContractSQLHelper;
import team15.SQLToTable;
import team15.models.Blank;
import team15.models.StaffAccount;
import team15.models.TravelAgentContract;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AdminController implements Initializable {

    private int selectedStaffID = 0;
    private File backupFile;
    private File restoreFile;
    @FXML
    private TextField searchStaffField;
    @FXML
    private TextField searchTravelAgentBlanksField;
    @FXML
    private TextField searchTravelAgentContractField;
    @FXML
    private TableView staffTableView;
    @FXML
    private TableView travelAgentBlanksTableView;
    @FXML
    private TableView travelAgentContractTableView;
    @FXML
    private Button selectFile;
    @FXML
    private Button restoreFileButton;
    @FXML
    private Label backupState;
    @FXML
    private Label restoreState;


    // ================================ BACKUP ================================= //
    @FXML
    public void selectFilePressed(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select or enter a file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL", "*.sql"));
        backupFile = fileChooser.showSaveDialog(((Button) e.getSource()).getScene().getWindow());
        selectFile.setText(backupFile.getName());
    }

    @FXML
    private void backupPressed(ActionEvent event) throws IOException {
        if (backupFile != null) {
            String[] backupCommand = new String[]{"mysqldump", "--skip-column-statistics", "--databases", "in2018g15",
                    "-hsmcse-stuproj00.city.ac.uk", "-P3306", "-uin2018g15_a", "-p3mo2KgVO",
                    "-r" + backupFile.getAbsolutePath()};

            Process process = Runtime.getRuntime().exec(backupCommand);
            try {
                process.waitFor();
                backupState.setText("Backup Successful");
            } catch (InterruptedException e) {
                backupState.setText("Backup Unsuccessful");
                throw new RuntimeException(e);
            }
            System.out.println(process.getOutputStream().toString());
            System.out.println(process.getErrorStream().toString());
        } else {
            backupState.setText("Please Enter A Save File");
        }

    }

    // =============================== Restore ================================= //
    @FXML
    private void selectedRestoreFile(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select or enter a file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL", "*.sql"));
        restoreFile = fileChooser.showOpenDialog(((Button) e.getSource()).getScene().getWindow());
        restoreFileButton.setText(restoreFile.getName());
    }

    @FXML
    private void restorePressed() throws IOException {
        if (restoreFile != null) {
            String[] restoreCmd = new String[]{"mysql", "-hsmcse-stuproj00.city.ac.uk", "-P3306", "-uin2018g15_a", "-p3mo2KgVO", "in2018g15"};
            ProcessBuilder processBuilder = new ProcessBuilder(restoreCmd);
            processBuilder.redirectInput(ProcessBuilder.Redirect.from(restoreFile));

            Process process = processBuilder.start();
            try {
                process.waitFor();
                restoreState.setText("Restore Successful");
            } catch (InterruptedException e) {
                restoreState.setText("Restore Unsuccessful");
                throw new RuntimeException(e);
            }
        } else {
            restoreState.setText("Please Enter A Save File To Restore");
        }
    }


    // ================================ LOGOUT ==================================//
    // ----- Logout Button IS Pressed ----- //
    @FXML
    public void logoutPressed() throws IOException {
        Application.changeToScene("login.fxml");     // - returns to login page
        Application.setActiveUser(null);                  // - deletes current active user
    }

    // ========================== MAINTAIN BLANKS TAB ======================= //

    // ----- Open Maintain Blanks Tab ------ //
    @FXML
    public void openMaintainBlanks() {
        updateBlanksTable();
    }

    // ----- User enters a Travel Agent Code ----- //
    @FXML
    public void searchTravelAgentBlanksEdited() {
        updateBlanksTable();
    }


    // ----- Admin Presses Manage Stock ----- //
    @FXML
    public void manageStockPressed() throws IOException {
        // ----- Uses Popup manager to create a Popup ----- //
        PopupController.displayPopup("BlankStockPopup.fxml");
        updateBlanksTable();
    }


    // ========================= VIEW CONTRACTS TAB ========================= //

    // ----- Open View Contracts Tab ------ //
    @FXML
    public void openViewContracts() {
        updateContractTable();
    }

    // ----- User enters a value into the search bar ----- //
    @FXML
    public void searchContractEdited() {
        updateContractTable();
    }


    // =========================== VIEW STAFF TAB =========================== //

    // ----- Opened View Staff Tab ----- //
    @FXML
    public void openStaffTab() {
        updateStaffTable();
    }

    // ----- User enters a value into the search bar ----- //
    @FXML
    public void searchStaffEdited() {
        updateStaffTable();
    }

    // ----- User presses + button to create a new staff Member ----- //
    @FXML
    public void createStaff() throws IOException {
        PopupController.displayPopup("ViewStaffPopup.fxml");
        updateStaffTable();
    }

    @FXML
    public void deleteStaff() {
        if (selectedStaffID != 0) {
            StaffAccountSQLHelper.deleteStaff(selectedStaffID);
            updateStaffTable();
            selectedStaffID = 0;
        }
    }


    public void updateBlanksTable() {
        travelAgentBlanksTableView.getItems().clear();
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs = BlankSQLHelper.getTravelAgentBlankResultSet(searchTravelAgentBlanksField.getText(), connection);

            // ----- convert rs to list ----- //
            ArrayList<Blank> data = new ArrayList<>();
            if (!(rs == null)) {
                while (rs.next()) {
                    data.add(new Blank(rs));
                    // ---- turn list into observable list ----- //
                    ObservableList dataList = FXCollections.observableArrayList(data);

                    // ---- display table view ---- //
                    SQLToTable.fillTableView(travelAgentBlanksTableView, rs);
                    travelAgentBlanksTableView.setItems(dataList);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void updateStaffTable() {
        try (Connection connection = DatabaseConnector.connect()) {
            System.out.println(searchStaffField.getText());

            if (StaffAccountSQLHelper.checkStaffAccount(searchStaffField.getText())) {
                System.out.println("set found");

                // ----- Retrieve rs from StaffSQLAccount ----- //
                ResultSet rs = StaffAccountSQLHelper.getResultSet(searchStaffField.getText(), connection);

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
                staffTableView.getItems().clear();
                System.out.println("set not found");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateContractTable() {
        try (Connection connection = DatabaseConnector.connect()) {
            System.out.println(searchTravelAgentContractField.getText());

            if (TravelAgentContractSQLHelper.checkTravelAgentContract(searchTravelAgentContractField.getText())) {
                System.out.println("set found");

                // ----- Retrieve rs from TravelAgentSQL ----- //
                ResultSet rs = TravelAgentContractSQLHelper.getTravelAgentContractResultSet(searchTravelAgentContractField.getText(), connection);

                // ----- convert rs to list ----- //
                ArrayList<TravelAgentContract> data = new ArrayList<>();
                if (!(rs == null)) {
                    while (rs.next()) {
                        data.add(new TravelAgentContract(rs));
                        // ---- turn list into observable list ----- //
                        ObservableList dataList = FXCollections.observableArrayList(data);

                        // ---- display table view ---- //
                        SQLToTable.fillTableView(travelAgentContractTableView, rs);
                        travelAgentContractTableView.setItems(dataList);
                    }
                }
            } else {
                travelAgentContractTableView.getItems().clear();
                System.out.println("set not found");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // ----- Ensures Users enter a valid input ----- //
        searchTravelAgentBlanksField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    searchTravelAgentBlanksField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 3) {
                    searchTravelAgentBlanksField.setText(oldValue);
                }
            }
        });
        searchTravelAgentContractField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    searchTravelAgentContractField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 3) {
                    searchTravelAgentContractField.setText(oldValue);
                }
            }
        });

        staffTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (staffTableView.getSelectionModel().getSelectedItem() != null) {
                    StaffAccount staff = (StaffAccount) staffTableView.getSelectionModel().getSelectedItem();
                    selectedStaffID = staff.getStaffID();
                    System.out.println("Selected StaffID: " + selectedStaffID);
                } else {
                    selectedStaffID = 0;
                    System.out.println("Selected StaffID: " + selectedStaffID);
                }
            }
        });
    }


}
