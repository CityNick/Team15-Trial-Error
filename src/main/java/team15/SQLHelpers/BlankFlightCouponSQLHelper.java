package team15.SQLHelpers;

import team15.Application;
import team15.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class BlankFlightCouponSQLHelper {
    public static void addFlights(long blankID, ArrayList<Integer> flightIDs) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {
            for (int counter = 0; counter < flightIDs.size(); counter++){
                PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO BlankFlightCoupon (BlankID, FlightID) VALUES (?,?) ");

                stmt.setLong(1, blankID);
                stmt.setInt(2, flightIDs.get(counter));
                stmt.executeUpdate();
            }

        }
    }
}
