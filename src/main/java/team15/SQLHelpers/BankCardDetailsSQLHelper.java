package team15.SQLHelpers;

import team15.DatabaseConnector;
import team15.models.BankCardDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BankCardDetailsSQLHelper {

    public static BankCardDetails getBankCardDetails(int customerID) {
        try (Connection connection = DatabaseConnector.connect()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM BankCardDetails WHERE CustomerID = ?");
            stmt.setInt(1, customerID);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            BankCardDetails account = new BankCardDetails(rs);
            return account;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
