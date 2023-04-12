package team15.SQLHelpers;

import team15.DatabaseConnector;
import team15.models.SalesRecord;

import java.sql.*;

import static java.sql.Types.NULL;

public class SalesRecordSQLHelper {

    public static void createNewRecord(long blankID, long customerID, int staffID, double localPrice,
                                       double discount, double usdPrice, double usdConversion,
                                       double commission, double taxRate, String paymentType,
                                       String bank, long accountNumber, long sortcode,
                                       String customerFirstName, String customerLastName) {
        try (Connection connection = DatabaseConnector.connect()) {

            // ---- adds new sales record ----- ..
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO SalesRecord (BlankID, CustomerID, StaffID, Date, LocalPrice, Discount, USDPrice, USDConversionRate, Commission, TaxRate, PaymentType, Bank, AccountNumber, Sortcode, CustomerFirstName,CustomerLastName)" +
                            " Values (?, ?, ?, CURRENT_TIMESTAMP, ?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setLong(1, blankID);

            if (customerID != -1) {
                stmt.setLong(2, customerID);
            } else {
                stmt.setNull(2, NULL);
            }

            stmt.setInt(3, staffID);
            stmt.setDouble(4, localPrice);
            stmt.setDouble(5, discount);
            stmt.setDouble(6, usdPrice);
            stmt.setDouble(7, usdConversion);
            stmt.setDouble(8, commission);
            stmt.setDouble(9, taxRate);
            stmt.setString(10, paymentType);

            if (bank != "") {
                stmt.setString(11, bank);
                stmt.setLong(12, accountNumber);
                stmt.setLong(13, sortcode);
            } else {
                stmt.setNull(11, NULL);
                stmt.setNull(12, NULL);
                stmt.setNull(13, NULL);
            }


            stmt.setString(14, customerFirstName);
            stmt.setString(15, customerLastName);

            stmt.executeUpdate();

            stmt = connection.prepareStatement(
                    "UPDATE CustomerAccount SET Expenditure = Expenditure + ? Where CustomerID = ?");
            stmt.setDouble(1,usdPrice);
            stmt.setLong(2,customerID);

            stmt.executeUpdate();
            System.out.println("Sales Record Created, Expenditure Appended");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ResultSet getSalesRecords(long blankID, Date date, String firstName, String lastName, Connection connection) throws SQLException {
        ResultSet rs;

        if (blankID == 0) {
            if (date == null) {

                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM SalesRecord WHERE CustomerFirstName LIKE ? AND CustomerLastName LIKE ? AND RecordID NOT IN (SELECT SalesRecordRecordID From RefundRecord)");
                stmt.setString(1, "%" + firstName + "%");
                stmt.setString(2, "%" + lastName + "%");
                rs = stmt.executeQuery();
            } else {
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM SalesRecord WHERE CustomerFirstName LIKE ? AND CustomerLastName LIKE ? AND Date >= ? AND RecordID NOT IN (SELECT SalesRecordRecordID From RefundRecord)");
                stmt.setString(1, "%" + firstName + "%");
                stmt.setString(2, "%" + lastName + "%");
                stmt.setDate(3, date);
                rs = stmt.executeQuery();
            }
        } else {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM SalesRecord WHERE BlankID = ? ");
            stmt.setLong(1, blankID);
            rs = stmt.executeQuery();
        }
        return rs;
    }

    public static Boolean checkSalesRecord(long blankID, Date date, String firstName, String lastName) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {
            ResultSet rs;

            if (blankID == 0) {
                if (date == null) {

                    PreparedStatement stmt = connection.prepareStatement(
                            "SELECT * FROM SalesRecord WHERE CustomerFirstName LIKE ? AND CustomerLastName LIKE ? AND RecordID NOT IN (SELECT SalesRecordRecordID From RefundRecord) ");
                    stmt.setString(1, "%" + firstName + "%");
                    stmt.setString(2, "%" + lastName + "%");
                    rs = stmt.executeQuery();
                } else {
                    PreparedStatement stmt = connection.prepareStatement(
                            "SELECT * FROM SalesRecord WHERE CustomerFirstName LIKE ? AND CustomerLastName LIKE ? AND Date >= ? AND RecordID NOT IN (SELECT SalesRecordRecordID From RefundRecord)");
                    stmt.setString(1, "%" + firstName + "%");
                    stmt.setString(2, "%" + lastName + "%");
                    stmt.setDate(3, date);
                    rs = stmt.executeQuery();
                }
            } else {
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM SalesRecord WHERE BlankID = ? ");
                stmt.setLong(1, blankID);
                rs = stmt.executeQuery();
            }
            return rs.next();
        }
    }

    public static ResultSet getResultSet(long recordID, Date date, String firstName, String lastName, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM SalesRecord WHERE RecordID = ?");
        stmt.setLong(1, recordID);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()){
            stmt = connection.prepareStatement(
                    "SELECT * FROM SalesRecord WHERE Date >= ? AND CustomerFirstName LIKE ? AND CustomerLastName LIKE ?");
            stmt.setDate(1, date);
            stmt.setString(2,"%"+firstName+"%");
            stmt.setString(3,"%"+lastName+"%");


            rs = stmt.executeQuery();
            System.out.println("Query Done");
            return rs;

        }
        else{
            return rs;
        }
    }

    public static void updateRecord(SalesRecord recordToChange, double newUSDRate) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {


            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE SalesRecord SET USDConversionRate = ? WHERE RecordID = ?");
            stmt.setDouble(1, newUSDRate);
            stmt.setLong(2, recordToChange.getRecordID());
            stmt.executeUpdate();

        }
    }
}


