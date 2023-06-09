package team15.SQLHelpers;

import team15.DatabaseConnector;
import team15.models.CustomerAccount;
import team15.models.SalesRecord;

import java.sql.*;
import java.time.LocalDate;

import static java.sql.Types.NULL;

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

    public static ResultSet getAllCustomers(Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CustomerAccount");

        ResultSet customers = stmt.executeQuery();
        return customers;
    }

    public static void updateCustomerStatus(Boolean isValued, String discountType, int customerID) {
        try (Connection connection = DatabaseConnector.connect()) {

            PreparedStatement stmt = connection.prepareStatement("UPDATE CustomerAccount SET Status = ?, discountType = ? WHERE CustomerID = ?");
            if (isValued == true){
                stmt.setString(1, "Valued");
            }
            else{
                stmt.setNull(1,NULL);
            }
            stmt.setString(2, discountType);
            stmt.setInt(3, customerID);

            stmt.executeUpdate();


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void returnCommission(SalesRecord salesRecord) {
        try (Connection connection = DatabaseConnector.connect()) {

            PreparedStatement stmt = connection.prepareStatement("UPDATE CustomerAccount SET Expenditure = Expenditure - ? WHERE CustomerID = ?");
            stmt.setDouble(1, salesRecord.getCommission());
            stmt.setLong(2,salesRecord.getCustomerID());

            stmt.executeUpdate();


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void createNewCustomer(String firstName, String lastName, Date dob, String bank, long accountNumber, int sortcode) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO CustomerAccount (FirstName,LastName, Expenditure,DateOfBirth) VALUES (?,?,?,?)");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setDouble(3, 0);
            stmt.setDate(4, dob);

            stmt.executeUpdate();
            System.out.println("Customer Account Created");

            stmt = connection.prepareStatement("SELECT * FROM CustomerAccount WHERE FirstName = ? AND LastName = ? AND Expenditure = 0 AND DateOfBirth = ?");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setDate(3, dob);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                CustomerAccount customer = new CustomerAccount(rs);
                System.out.println(customer.getCustomerID());

                stmt = connection.prepareStatement("INSERT IGNORE INTO BankCardDetails (CustomerID, Bank, AccountNumber, SortCode) VALUES (?,?,?,?)");
                stmt.setInt(1, customer.getCustomerID());
                stmt.setString(2, bank);
                stmt.setLong(3, accountNumber);
                stmt.setInt(4, sortcode);

                stmt.executeUpdate();

                System.out.println("Payment Method Added");
            }
        }
    }
}
