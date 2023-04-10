package team15.SQLHelpers;

import java.sql.*;

public class ReportSQLHelper {

    public static ResultSet getGlobalInterlineReport(int travelAgentCode, Connection connection, Date startDate, Date endDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT StaffID, BlankID, LocalPrice, Discount, Taxes, USDPrice, PaymentMethod, Commission\n" +
                "FROM (SELECT s.* FROM SalesRecord s WHERE\n" +
                "    CAST(LEFT(s.BlankID, 3) AS INT) = 440 OR \n" +
                "    CAST(LEFT(s.BlankID, 3) AS INT) = 420 OR \n" +
                "    CAST(LEFT(s.BlankID, 3) AS INT) = 444)\n" +
                "WHERE Date >= ? AND Date <= ? AND StaffID IN ( SELECT st.StaffID FROM StaffAccount st WHERE st.TravelAgentCode = ? )");
        stmt.setDate(1,startDate);
        stmt.setDate(2,endDate);
        stmt.setInt(3,travelAgentCode);

        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static ResultSet getGlobalDomesticReport(int travelAgentCode, Connection connection, Date startDate, Date endDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT StaffID, BlankID, LocalPrice, Discount, Taxes, USDPrice, PaymentMethod, Commission\n" +
                "FROM (SELECT s.* FROM SalesRecord s WHERE\n" +
                "    CAST(LEFT(s.BlankID, 3) AS INT) = 201 OR \n" +
                "    CAST(LEFT(s.BlankID, 3) AS INT) = 101)\n" +
                "WHERE Date >= ? AND Date <= ? AND StaffID IN ( SELECT st.StaffID FROM StaffAccount st WHERE st.TravelAgentCode = ? )");
        stmt.setDate(1,startDate);
        stmt.setDate(2,endDate);
        stmt.setInt(3,travelAgentCode);

        ResultSet rs = stmt.executeQuery();
        return rs;
    }
}
