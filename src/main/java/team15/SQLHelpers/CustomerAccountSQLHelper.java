package team15.SQLHelpers;

import team15.DatabaseConnector;
import team15.models.CustomerAccount;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class CustomerAccountSQLHelper {

    public static boolean checkCustomer(String firstName, String lastName, LocalDate date) {
        try (Connection connection = DatabaseConnector.connect()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CustomerAccount WHERE FirstName = ? AND LastName = ? AND DateOfBirth = ?");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setDate(3, Date.valueOf(date));

            ResultSet user = stmt.executeQuery();
            return user.next();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static CustomerAccount getCustomer(String firstName, String lastName, LocalDate date) {
        try (Connection connection = DatabaseConnector.connect()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CustomerAccount WHERE FirstName = ? AND LastName = ? AND DateOfBirth = ?");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setDate(3, Date.valueOf(date));

            ResultSet user = stmt.executeQuery();
            if (!user.next()) {
                return null;
            }
            CustomerAccount account = new CustomerAccount(user);
            return account;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
