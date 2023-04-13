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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AdminController implements Initializable {

    private int selectedStaffID = 0;
    private File backupFile;
    private File restoreFile;
    private TravelAgentContract selectedContract;
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
    @FXML
    private TextField travelAgentCodeContractField;
    @FXML
    private TextField Field444;
    @FXML
    private TextField Field440;
    @FXML
    private TextField Field420;
    @FXML
    private TextField Field451;
    @FXML
    private TextField Field452;
    @FXML
    private TextField Field201;
    @FXML
    private TextField Field101;



    // ================================ BACKUP ================================= //

    /**
     * selectFilePressed Subroutine
     * @param e records when button is pressed
     * gives the ADMIN the ability to select a file for backup
     */
    // ----- ADMIN tries to select a file to save ----- //

    @FXML
    public void selectFilePressed(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();                                                                    // - FileChooser Object made
        fileChooser.setTitle("Select or enter a file");                                                                 // - FileChooser Title
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL", "*.sql"));      // - filters for .sql files
        backupFile = fileChooser.showSaveDialog(((Button) e.getSource()).getScene().getWindow());                       // - assigns the file to backup
        selectFile.setText(backupFile.getName());                                                                       // - updates GUI to show file name
    }

    /**
     * backupPressed Subroutine
     * @param event
     * @throws IOException
     * Executes backup and notifies ADMIN of the result
     * Displays any errors if it fails
     */
    // ----- ADMIN Presses the backup button ----- //
    @FXML
    private void backupPressed(ActionEvent event) throws IOException {

        // ----- Ensures ADMIN has selected a file before backing up ----- //
        if (backupFile != null) {

            // ---- CMD to run mysqldump.exe ----- //
            String[] backupCommand = new String[]{"mysqldump", "--skip-column-statistics", "--databases", "in2018g15",
                    "-hsmcse-stuproj00.city.ac.uk", "-P3306", "-uin2018g15_a", "-p3mo2KgVO",
                    "-r" + backupFile.getAbsolutePath()};

            // ---- Executes CMD ----- //
            Process process = Runtime.getRuntime().exec(backupCommand);

            try {
                // ---- Backup Process Successful
                process.waitFor();                           // - pauses application while executing
                backupState.setText("Backup Successful");    // - updates GUI to show ADMIN Backup was successful

                // ---- Backup Process Unsuccessful
            } catch (InterruptedException e) {               // - exception occured so process did not work
                backupState.setText("Backup Unsuccessful");  // - updates GUI to show ADMIN Backup was unsuccessful
                throw new RuntimeException(e);               // - throws error
            }

            // ----- Debug Assist Tools ----- //
            System.out.println(process.getOutputStream().toString());  // - outputs to terminal
            System.out.println(process.getErrorStream().toString());   // - outputs error to terminal
        } else {

            // ---- Resets GUI Display ----- //
            backupFile = null;                                    // - backup file set to null default
            backupState.setText("Please Enter A Save File");      // - GUI updates to reflect change
        }

    }

    // =============================== Restore ================================= //

    /**
     * selectRestoreFile Subroutine
     * @param e records when button is pressed
     * Gives the ADMIN the ability to select a file for restoration
     */
    @FXML
    // ----- ADMIN Presses Select Restore File ----- //
    private void selectedRestoreFile(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();                                                                    // - FileChooser Object made
        fileChooser.setTitle("Select or enter a file");                                                                 // - FileChooser Title
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL", "*.sql"));      // - filters for .sql files
        restoreFile = fileChooser.showOpenDialog(((Button) e.getSource()).getScene().getWindow());                      // - assigns file to restore
        restoreFileButton.setText(restoreFile.getName());                                                               // - Update GUI to show file name
    }

    /**
     * backupPressed Subroutine
     * @throws IOException
     * Executes restore and notifies ADMIN of the result
     * Displays any errors if it fails
     */
    @FXML
    private void restorePressed() throws IOException {

        // ----- Checks if a file has been selected before attempting to restore ----- //
        if (restoreFile != null) {

            // ---- CMD to run mysql.exe in order to restore the database ----- //
            String[] restoreCmd = new String[]{"mysql", "-hsmcse-stuproj00.city.ac.uk", "-P3306", "-uin2018g15_a", "-p3mo2KgVO", "in2018g15"};

            ProcessBuilder processBuilder = new ProcessBuilder(restoreCmd);                  // - creates a process builder object using CMD
            processBuilder.redirectInput(ProcessBuilder.Redirect.from(restoreFile));         // - creates restore file
            Process process = processBuilder.start();                                        // - executes restoration on target restore file
            try {
                // ----- Restoration Successful ----- //
                process.waitFor();                                                           // - causes application to pause while executing
                restoreState.setText("Restore Successful");                                  // - updates GUI to inform ADMIN restoration was successful

                // ----- Restoration Unsuccessful ----- //
            } catch (InterruptedException e) {                         // - Error occurs
                restoreState.setText("Restore Unsuccessful");          // - Updates GUI to inform ADMIN backup was unsuccessful
                throw new RuntimeException(e);                         // - throws error
            }

            // ----- File not chosen before executing ----- //
        } else {
            restoreState.setText("Please Enter A Save File To Restore");   // - update GUI to ask ADMIN to select a file to restore
        }
    }


    // ================================ LOGOUT ==================================//

    /**
     * logoutPressed Subroutine
     * @throws IOException
     * responsible for taking user back to the login page when logging out
     * sets activeUser variable to null (nothing) = no one logged in
     */
    // ----- Logout Button IS Pressed ----- //
    @FXML
    public void logoutPressed() throws IOException {
        Application.changeToScene("login.fxml");     // - returns to login page
        Application.setActiveUser(null);                  // - deletes current active user
    }

    // ========================== MAINTAIN BLANKS TAB ======================= //

    /**
     * openMaintainBlanks Subroutine
     * executed when user switches to the 'Maintain Blanks' Tab
     * executes updateBlanksTable() to update TableView
     */
    // ----- Open Maintain Blanks Tab ------ //
    @FXML
    public void openMaintainBlanks() {
        updateBlanksTable();
    }

    // ----- User enters a Travel Agent Code ----- //

    /**
     * searchTravelAgentBlanksEdited Subroutine
     * executed when user edits the contents pof the search field
     * executes updateBlanksTable() to update TableView based on new search
     */
    @FXML
    public void searchTravelAgentBlanksEdited() {
        updateBlanksTable();
    }

    /**
     * manageStockPressed() Subroutine
     * @throws IOException
     * executed when manager presses the corresponding button
     * utilises Class PopupController, and it's method 'displayPopup(@param)'
     * Takes ADMIN to the blankStockPopup fxml page
     */
    // ----- Admin Presses Manage Stock ----- //
    @FXML
    public void manageStockPressed() throws IOException {
        // ----- Uses Popup manager to create a Popup ----- //
        PopupController.displayPopup("BlankStockPopup.fxml");    // - Opens BlankStockPopup page
        updateBlanksTable();
    }


    // ========================= VIEW CONTRACTS TAB ========================= //

    /**
     * openviewContracts Subroutine
     * executed when ADMIN opens the contract 'View Contracts' Tab
     */
    // ----- Open View Contracts Tab ------ //
    @FXML
    public void openViewContracts() {
        updateContractTable();
    }

    /**
     * searchedContractsEdited Subroutine
     * executed when the user enters a value into the search bar
     */
    // ----- User enters a value into the search bar ----- //
    @FXML
    public void searchContractEdited() {
        updateContractTable();
    }

    // ----- Admin Pressed The Delete Button (-) ----- //
    @FXML
    public void deleteContractPressed() throws SQLException {
        if (selectedContract != null){
            TravelAgentContractSQLHelper.deleteContract(selectedContract);
            updateContractTable();
        }
    }

    // ----- Admin Presses Create Button (+) ----- //
    @FXML
    public void createContractPressed() throws SQLException {
        if(!travelAgentCodeContractField.getText().equals("") && !Field444.getText().equals("") && !Field440.getText().equals("") &&
                !Field420.getText().equals("") && !Field451.getText().equals("") && !Field452.getText().equals("") &&
                !Field201.getText().equals("") && !Field101.getText().equals("")){

            // ----- Assigns Values into their data type values ----- //
            int travelAgentCode = Integer.parseInt(travelAgentCodeContractField.getText());
            double commission444 = Double.parseDouble(Field444.getText());
            double commission440 = Double.parseDouble(Field440.getText());
            double commission420 = Double.parseDouble(Field420.getText());
            double commission451 = Double.parseDouble(Field451.getText());
            double commission452 = Double.parseDouble(Field452.getText());
            double commission201 = Double.parseDouble(Field201.getText());
            double commission101 = Double.parseDouble(Field101.getText());

            if (!TravelAgentContractSQLHelper.checkContractExists(travelAgentCode, commission444, commission440, commission420,
                    commission451, commission452,commission201,commission101)){

                // ----- Creates a Travel Agent Contract ----- //
                TravelAgentContractSQLHelper.createTravelAgentContract(travelAgentCode, commission444, commission440, commission420,
                        commission451, commission452,commission201,commission101);

                // ----- Update Table ----- //
                updateContractTable();
            }
        }
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

        travelAgentContractTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (travelAgentContractTableView.getSelectionModel().getSelectedItem() != null) {
                    selectedContract = (TravelAgentContract) travelAgentContractTableView.getSelectionModel().getSelectedItem();
                    System.out.println(selectedContract.getTravelAgentCode());
                } else {
                    selectedContract = null;
                }
            }
        });

        travelAgentCodeContractField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    travelAgentCodeContractField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 3) {
                    travelAgentCodeContractField.setText(oldValue);
                }
            }
        });

        // -- Ensures you can only input numbers
    }


}
