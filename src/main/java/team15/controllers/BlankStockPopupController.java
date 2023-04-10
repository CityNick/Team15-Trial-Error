package team15.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import team15.SQLHelpers.BlankSQLHelper;
import team15.SQLHelpers.TravelAgentSQLHelper;

import java.net.URL;
import java.util.ResourceBundle;

public class BlankStockPopupController implements Initializable {

    private int travelAgentCode = 0;
    private int change444;
    private int change440;
    private int change420;

    private int change451;
    private int change452;

    private int change201;
    private int change101;

    @FXML
    private Button makeChangesButton;

    @FXML
    private TextField travelAgentCodeField;
    @FXML
    private Label travelAgentCodeDisplay;


    @FXML
    private Label fieldFor444;
    @FXML
    private Label changeTo444;

    @FXML
    private Label fieldFor440;
    @FXML
    private Label changeTo440;
    @FXML
    private Label fieldFor420;
    @FXML
    private Label changeTo420;
    @FXML
    private Label fieldFor451;
    @FXML
    private Label changeTo451;
    @FXML
    private Label fieldFor452;
    @FXML
    private Label changeTo452;
    @FXML
    private Label fieldFor201;
    @FXML
    private Label changeTo201;
    @FXML
    private Label fieldFor101;
    @FXML
    private Label changeTo101;


    // ============================== ADMIN SEARCHES FOR TRAVEL AGENT =========================== //
    @FXML
    public void TravelAgentCodeFieldEdited() {

        // ----- If The Travel Agent Exists ----- //
        if ((travelAgentCodeField.getText() != null) && (TravelAgentSQLHelper.checkTravelAgent(travelAgentCodeField.getText()))) {
            travelAgentCodeDisplay.setText(travelAgentCodeField.getText());
            travelAgentCode = Integer.parseInt(travelAgentCodeField.getText());
            updateStock();

        } else {
            travelAgentCodeDisplay.setText("N/A");
            travelAgentCode = 0;
            updateStock();
        }
    }


    // ============================= ADMIN CHANGES STOCK FOR 444 ============================== //

    // ----- ADMIN ADDS STOCK ----- //
    @FXML
    public void addTo444() {
        String s;
        change444 += 1;
        if (change444 >= 0) {
            s = "+" + change444;
        } else {
            s = String.valueOf(change444);
        }
        changeTo444.setText(s);
    }

    // ----- ADMIN REDUCES STOCK ----- //
    @FXML
    public void sub444() {
        String s;
        change444 -= 1;
        if (change444 >= 0) {
            s = "+" + change444;
        } else {
            s = String.valueOf(change444);
        }
        changeTo444.setText(s);
    }

    // ============================= ADMIN CHANGES STOCK FOR 440 ============================== //

    // ----- ADMIN ADDS STOCK ----- //
    @FXML
    public void addTo440() {
        String s;
        change440 += 1;
        if (change440 >= 0) {
            s = "+" + change440;
        } else {
            s = String.valueOf(change440);
        }
        changeTo440.setText(s);
    }

    // ----- ADMIN REDUCES STOCK ----- //
    @FXML
    public void sub440() {
        String s;
        change440 -= 1;
        if (change440 >= 0) {
            s = "+" + change440;
        } else {
            s = String.valueOf(change440);
        }
        changeTo440.setText(s);
    }


    // ============================= ADMIN CHANGES STOCK FOR 440 ============================== //

    // ----- ADMIN ADDS STOCK ----- //
    @FXML
    public void addTo420() {
        String s;
        change420 += 1;
        if (change420 >= 0) {
            s = "+" + change420;
        } else {
            s = String.valueOf(change420);
        }
        changeTo420.setText(s);
    }

    // ----- ADMIN REDUCES STOCK ----- //
    @FXML
    public void sub420() {
        String s;
        change420 -= 1;
        if (change420 >= 0) {
            s = "+" + change420;
        } else {
            s = String.valueOf(change420);
        }
        changeTo420.setText(s);
    }


    // ============================= ADMIN CHANGES STOCK FOR 451 ============================== //

    // ----- ADMIN ADDS STOCK ----- //
    @FXML
    public void addTo451() {
        String s;
        change451 += 1;
        if (change451 >= 0) {
            s = "+" + change451;
        } else {
            s = String.valueOf(change451);
        }
        changeTo451.setText(s);
    }

    // ----- ADMIN REDUCES STOCK ----- //
    @FXML
    public void sub451() {
        String s;
        change451 -= 1;
        if (change451 >= 0) {
            s = "+" + change451;
        } else {
            s = String.valueOf(change451);
        }
        changeTo451.setText(s);
    }


    // ============================= ADMIN CHANGES STOCK FOR 452 ============================== //

    // ----- ADMIN ADDS STOCK ----- //
    @FXML
    public void addTo452() {
        String s;
        change452 += 1;
        if (change452 >= 0) {
            s = "+" + change452;
        } else {
            s = String.valueOf(change452);
        }
        changeTo452.setText(s);
    }

    // ----- ADMIN REDUCES STOCK ----- //
    @FXML
    public void sub452() {
        String s;
        change452 -= 1;
        if (change452 >= 0) {
            s = "+" + change452;
        } else {
            s = String.valueOf(change452);
        }
        changeTo452.setText(s);
    }


    // ============================= ADMIN CHANGES STOCK FOR 201 ============================== //

    // ----- ADMIN ADDS STOCK ----- //
    @FXML
    public void addTo201() {
        String s;
        change201 += 1;
        if (change201 >= 0) {
            s = "+" + change201;
        } else {
            s = String.valueOf(change201);
        }
        changeTo201.setText(s);
    }

    // ----- ADMIN REDUCES STOCK ----- //
    @FXML
    public void sub201() {
        String s;
        change201 -= 1;
        if (change201 >= 0) {
            s = "+" + change201;
        } else {
            s = String.valueOf(change201);
        }
        changeTo201.setText(s);
    }


    // ============================= ADMIN CHANGES STOCK FOR 101 ============================== //

    // ----- ADMIN ADDS STOCK ----- //
    @FXML
    public void addTo101() {
        String s;
        change101 += 1;
        if (change101 >= 0) {
            s = "+" + change101;
        } else {
            s = String.valueOf(change101);
        }
        changeTo101.setText(s);
    }

    // ----- ADMIN REDUCES STOCK ----- //
    @FXML
    public void sub101() {
        String s;
        change101 -= 1;
        if (change101 >= 0) {
            s = "+" + change101;
        } else {
            s = String.valueOf(change101);
        }
        changeTo101.setText(s);
    }


    // ============================ ADMIN SAVES CHANGES TO STOCK =============================== //
    @FXML
    public void makeChangesPressed() {
        if (travelAgentCode != 0) {
            String style = makeChangesButton.getStyle();
            makeChangesButton.setText("Making Changes...");
            makeChangesButton.setStyle("-fx-background-color: #5C5B5A");

            // ---- Add / Remove Stock ----- //
            changeStock(change444, 444);
            changeStock(change440, 440);
            changeStock(change420, 420);
            changeStock(change451, 451);
            changeStock(change452, 452);
            changeStock(change201, 201);
            changeStock(change101, 101);


            // ----- Changes Complete ----- //
            updateStock();
            makeChangesButton.setStyle(style);
            makeChangesButton.setText("Make Changes");
        }
    }


    // ============================== GENERATE / REMOVE STOCK  FUNCTION ======================//
    public void changeStock(int change, int blankType) {

        if (travelAgentCode != 0) {
            // ---- positive change ----- //
            if (change > 0) {
                BlankSQLHelper.stockUpBlanks(travelAgentCode, change, blankType);
            }
            // ----- negative change ----- //
            else if (change < 0) {
                BlankSQLHelper.deleteBlanks(travelAgentCode, change, blankType);
            }
        }
    }


    // ================================== UPDATES DISPLAYED STOCK =========================== //

    public void updateStock() {
        if (travelAgentCodeField.getText() != null && travelAgentCode != 0) {
            int travelAgentCode = Integer.parseInt(travelAgentCodeField.getText());
            fieldFor444.setText(String.valueOf(BlankSQLHelper.countTravelAgentStock(travelAgentCode, 444)));
            fieldFor440.setText(String.valueOf(BlankSQLHelper.countTravelAgentStock(travelAgentCode, 440)));
            fieldFor420.setText(String.valueOf(BlankSQLHelper.countTravelAgentStock(travelAgentCode, 420)));

            fieldFor451.setText(String.valueOf(BlankSQLHelper.countTravelAgentStock(travelAgentCode, 451)));
            fieldFor452.setText(String.valueOf(BlankSQLHelper.countTravelAgentStock(travelAgentCode, 452)));

            fieldFor201.setText(String.valueOf(BlankSQLHelper.countTravelAgentStock(travelAgentCode, 201)));
            fieldFor101.setText(String.valueOf(BlankSQLHelper.countTravelAgentStock(travelAgentCode, 101)));

            change444 = 0;
            changeTo444.setText("+0");

            change440 = 0;
            changeTo440.setText("+0");

            change420 = 0;
            changeTo420.setText("+0");

            change451 = 0;
            changeTo451.setText("+0");

            change452 = 0;
            changeTo452.setText("+0");

            change201 = 0;
            changeTo201.setText("+0");

            change101 = 0;
            changeTo101.setText("+0");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        travelAgentCodeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    travelAgentCodeDisplay.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (newValue.length() > 3) {
                    travelAgentCodeField.setText(oldValue);
                }
            }
        });
    }

}
