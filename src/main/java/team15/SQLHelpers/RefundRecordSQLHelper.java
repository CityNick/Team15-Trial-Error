package team15.SQLHelpers;

import team15.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RefundRecordSQLHelper {

    public static void createRefund(long salesRecordID, Double CommissionToRefund) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO RefundRecord (SalesRecordRecordID, CommissionToRefund) VALUES (?, ?) ");
            stmt.setLong(1, salesRecordID);
            stmt.setDouble(2, CommissionToRefund);
            stmt.executeUpdate();
        }
    }
}
