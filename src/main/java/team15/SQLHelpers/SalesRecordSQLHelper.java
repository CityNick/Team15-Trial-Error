package team15.SQLHelpers;

import team15.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static java.sql.Types.NULL;

public class SalesRecordSQLHelper {

    public static void createNewRecord(long blankID, long customerID, int staffID, double localPrice,
                                       double discount, double usdPrice, double usdConversion,
                                       double commission, double taxRate, String paymentType,
                                       String bank, long accountNumber, long sortcode,
                                       String customerFirstName, String customerLastName){
        try (Connection connection = DatabaseConnector.connect()){

            // ---- adds new sales record ----- ..
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO SalesRecord (BlankID, CustomerID, StaffID, Date, LocalPrice, Discount, USDPrice, USDConversionRate, Commission, TaxRate, PaymentType, Bank, AccountNumber, Sortcode, CustomerFirstName,CustomerLastName)"+
            " Values (?, ?, ?, CURRENT_TIMESTAMP, ?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setLong(1, blankID);

            if (customerID != -1){
                stmt.setLong(2, customerID);
            }
            else{
                stmt.setNull(2,NULL);
            }

            stmt.setInt(3, staffID);
            stmt.setDouble(4, localPrice);
            stmt.setDouble(5, discount);
            stmt.setDouble(6, usdPrice);
            stmt.setDouble(7, usdConversion);
            stmt.setDouble(8, commission);
            stmt.setDouble(9, taxRate);
            stmt.setString(10, paymentType);

            if (bank != ""){
                stmt.setString(11, bank);
                stmt.setLong(12, accountNumber);
                stmt.setLong(13, sortcode);
            }
            else{
                stmt.setNull(11,NULL);
                stmt.setNull(12,NULL);
                stmt.setNull(13,NULL);
            }


            stmt.setString(14, customerFirstName);
            stmt.setString(15, customerLastName);

            stmt.executeUpdate();
            System.out.println("Sales Record Created");



        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
