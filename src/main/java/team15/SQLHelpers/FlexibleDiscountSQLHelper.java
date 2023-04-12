package team15.SQLHelpers;

import team15.Application;
import team15.DatabaseConnector;
import team15.models.FixedDiscount;
import team15.models.FlexibleDiscount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static team15.DatabaseConnector.connection;

public class FlexibleDiscountSQLHelper {

    public static ResultSet getFlexibleDiscounts(Connection connection) throws SQLException {

            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM FlexibleDiscount WHERE TravelAgentCode = ? ");
            stmt.setInt(1, Application.getActiveUser().getTravelAgentCode());
            ResultSet rs = stmt.executeQuery();

            return rs;
    }

    public static void removeFlexibleDiscount(FlexibleDiscount flexibleDiscount) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM FlexibleDiscount WHERE TravelAgentCode = ? AND Threshold = ? AND Percentage = ?  ");
            stmt.setInt(1, Application.getActiveUser().getTravelAgentCode());
            stmt.setDouble(2, flexibleDiscount.getThreshold());
            stmt.setDouble(3, flexibleDiscount.getPercentage());
            stmt.executeUpdate();
        }
    }

    public static boolean checkFlexibleDiscountExists(double threshold, double discount) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM FlexibleDiscount WHERE TravelAgentCode = ? AND Threshold = ? AND Percentage = ?  ");

            stmt.setInt(1, Application.getActiveUser().getTravelAgentCode());
            stmt.setDouble(2, threshold);
            stmt.setDouble(3, discount);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()){
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static void addNewFlexibleDiscount(double threshold, double discount) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO FlexibleDiscount (TravelAgentCode, Threshold, Percentage) VALUES (?,?,?) ");
            stmt.setInt(1, Application.getActiveUser().getTravelAgentCode());
            stmt.setDouble(2, threshold);
            stmt.setDouble(3, discount);
            stmt.executeUpdate();
        }
    }

    public static double calculateDiscount(double expenditure, double basePrice) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM FlexibleDiscount WHERE TravelAgentCode = ? AND Threshold <= ? ORDER BY Threshold DESC ");


            stmt.setInt(1, Application.getActiveUser().getTravelAgentCode());
            stmt.setDouble(2, expenditure);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()){
                return 0;
            }
            else{
                FlexibleDiscount flexibleDiscount = new FlexibleDiscount(rs);
                double discount = basePrice * (flexibleDiscount.getPercentage() / 100);
                return discount;
            }
        }
    }
}
