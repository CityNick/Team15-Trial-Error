package team15.SQLHelpers;

import team15.Application;
import team15.DatabaseConnector;
import team15.models.FixedDiscount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FixedDiscountSQLHelper {

    public static double getFixedDiscount() throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {

            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM FixedDiscount WHERE TravelAgentCode = ? ");
            stmt.setInt(1, Application.getActiveUser().getTravelAgentCode());
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return 0;
            }
            else{
                FixedDiscount fixedDiscount = new FixedDiscount(rs);
                return fixedDiscount.getFixedDiscount();
            }
        }
    }

    public static void newFixedDiscount(double fixedDiscount) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {

            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE FixedDiscount SET FixedDiscount = ? WHERE TravelAgentCode = ? ");
            stmt.setDouble(1, fixedDiscount);
            stmt.setInt(2, Application.getActiveUser().getTravelAgentCode());
            stmt.executeUpdate();
        }
    }
}
