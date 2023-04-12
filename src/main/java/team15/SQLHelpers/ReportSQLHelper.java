package team15.SQLHelpers;

import team15.Application;

import java.sql.*;

public class ReportSQLHelper {

    public static ResultSet getGlobalInterlineReport(int travelAgentCode, Connection connection, Date startDate, Date endDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT\n" +
                "    x.StaffID,\n" +
                "    x.BlankID,\n" +
                "    x.LocalPrice,\n" +
                "    x.Discount,\n" +
                "    x.TaxRate,\n" +
                "    x.USDPrice,\n" +
                "    x.PaymentType,\n" +
                "    x.Commission,\n" +
                "    x.Date\n" +
                "FROM\n" +
                "    (\n" +
                "    SELECT\n" +
                "        s.*\n" +
                "    FROM\n" +
                "        SalesRecord s\n" +
                "    WHERE\n" +
                "        CAST(LEFT(s.BlankID, 3) AS INT) = 440 OR CAST(LEFT(s.BlankID, 3) AS INT) = 420 OR CAST(LEFT(s.BlankID, 3) AS INT) = 444\n" +
                ") x\n" +
                "WHERE\n" +
                "    x.Date >= ? AND x.Date <= ? AND StaffID IN(\n" +
                "    SELECT\n" +
                "        st.StaffID\n" +
                "    FROM\n" +
                "        StaffAccount st\n" +
                "    WHERE\n" +
                "        st.TravelAgentCode = ?\n" +
                ")");
        stmt.setDate(1,startDate);
        stmt.setDate(2,endDate);
        stmt.setInt(3,travelAgentCode);

        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static ResultSet getGlobalDomesticReport(int travelAgentCode, Connection connection, Date startDate, Date endDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT\n" +
                "    x.StaffID,\n" +
                "    x.BlankID,\n" +
                "    x.LocalPrice,\n" +
                "    x.Discount,\n" +
                "    x.TaxRate,\n" +
                "    x.USDPrice,\n" +
                "    x.PaymentType,\n" +
                "    x.Commission,\n" +
                "    x.Date\n" +
                "FROM\n" +
                "    (\n" +
                "    SELECT\n" +
                "        s.*\n" +
                "    FROM\n" +
                "        SalesRecord s\n" +
                "    WHERE\n" +
                "        CAST(LEFT(s.BlankID, 3) AS INT) = 101 OR CAST(LEFT(s.BlankID, 3) AS INT) = 201\n" +
                ") x\n" +
                "WHERE\n" +
                "    x.Date >= ? AND x.Date <= ? AND StaffID IN(\n" +
                "    SELECT\n" +
                "        st.StaffID\n" +
                "    FROM\n" +
                "        StaffAccount st\n" +
                "    WHERE\n" +
                "        st.TravelAgentCode = ?\n" +
                ")");
        stmt.setDate(1,startDate);
        stmt.setDate(2,endDate);
        stmt.setInt(3,travelAgentCode);

        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static ResultSet getIndividualInterlineReport(Connection connection, Date startDate, Date endDate, int staffID) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT\n" +
                "    x.StaffID,\n" +
                "    x.BlankID,\n" +
                "    x.LocalPrice,\n" +
                "    x.Discount,\n" +
                "    x.TaxRate,\n" +
                "    x.USDPrice,\n" +
                "    x.PaymentType,\n" +
                "    x.Commission,\n" +
                "    x.Date\n" +
                "FROM\n" +
                "    (\n" +
                "    SELECT\n" +
                "        s.*\n" +
                "    FROM\n" +
                "        SalesRecord s\n" +
                "    WHERE\n" +
                "        CAST(LEFT(s.BlankID, 3) AS INT) = 440 OR CAST(LEFT(s.BlankID, 3) AS INT) = 420 OR CAST(LEFT(s.BlankID, 3) AS INT) = 444\n" +
                ") x\n" +
                "WHERE\n" +
                "    x.Date >= ? AND x.Date <= ? AND StaffID = ?");
        stmt.setDate(1,startDate);
        stmt.setDate(2,endDate);
        stmt.setInt(3,staffID);

        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static ResultSet getIndividualDomesticReport(Connection connection, Date startDate, Date endDate, int staffID) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT\n" +
                "    x.StaffID,\n" +
                "    x.BlankID,\n" +
                "    x.LocalPrice,\n" +
                "    x.Discount,\n" +
                "    x.TaxRate,\n" +
                "    x.USDPrice,\n" +
                "    x.PaymentType,\n" +
                "    x.Commission,\n" +
                "    x.Date\n" +
                "FROM\n" +
                "    (\n" +
                "    SELECT\n" +
                "        s.*\n" +
                "    FROM\n" +
                "        SalesRecord s\n" +
                "    WHERE\n" +
                "        CAST(LEFT(s.BlankID, 3) AS INT) = 101 OR CAST(LEFT(s.BlankID, 3) AS INT) = 201\n" +
                ") x\n" +
                "WHERE\n" +
                "    x.Date >= ? AND x.Date <= ? AND StaffID = ?");
        stmt.setDate(1,startDate);
        stmt.setDate(2,endDate);
        stmt.setInt(3,staffID);

        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static ResultSet getAgentStock(Connection connection, Date startDate, Date endDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT BlankType, COUNT(*) AS COUNT\n" +
                "FROM\n" +
                "    Blank\n" +
                "WHERE \n" +
                "    DateIssued >= ? AND DateIssued <= ? AND TravelAgentCode = ?\n "+
                "GROUP BY\n" +
                "    BlankType");
        stmt.setDate(1,startDate);
        stmt.setDate(2,endDate);
        stmt.setInt(3, Application.getActiveUser().getTravelAgentCode());

        ResultSet rs = stmt.executeQuery();
        return rs;
    }


    public static ResultSet getSubAgentStock(Connection connection, Date startDate, Date endDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT StaffID, BlankType, COUNT(*) AS COUNT\n" +
                "FROM\n" +
                "    Blank\n" +
                "WHERE \n" +
                "    DateIssued >= ? AND DateIssued <= ? AND TravelAgentCode = ? AND StaffID IS NOT NULL \n "+
                "GROUP BY\n" +
                "    StaffID, BlankType");
        stmt.setDate(1,startDate);
        stmt.setDate(2,endDate);
        stmt.setInt(3, Application.getActiveUser().getTravelAgentCode());

        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static ResultSet getAssignStock(Connection connection, Date startDate, Date endDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT StaffID, BlankType, COUNT(*) AS COUNT\n" +
                "FROM\n" +
                "    Blank\n" +
                "WHERE \n" +
                "    DateIssued < ? AND TravelAgentCode = ? AND StaffID IS NOT NULL \n "+
                "GROUP BY\n" +
                "    StaffID, BlankType");
        stmt.setDate(1,startDate);
        stmt.setInt(2, Application.getActiveUser().getTravelAgentCode());

        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static ResultSet getUsedStock(Connection connection, Date startDate, Date endDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT\n" +
                "    StaffID,\n" +
                "    BlankType,\n" +
                "    COUNT(*) AS COUNT\n" +
                "FROM\n" +
                "    Blank\n" +
                "WHERE\n" +
                "    TravelAgentCode = ? AND BlankID IN(\n" +
                "    SELECT\n" +
                "        BlankID\n" +
                "    FROM\n" +
                "        SalesRecord\n" +
                "    WHERE\n" +
                "        DATE >= ? AND DATE <= ?\n" +
                ")\n" +
                "GROUP BY\n" +
                "    StaffID,\n" +
                "    BlankType;");
        stmt.setInt(1, Application.getActiveUser().getTravelAgentCode());
        stmt.setDate(2,startDate);
        stmt.setDate(3,endDate);

        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static ResultSet getRemainingStock(Connection connection, Date startDate, Date endDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT\n" +
                "    StaffID,\n" +
                "    BlankType,\n" +
                "    COUNT(*) AS COUNT\n" +
                "FROM\n" +
                "    Blank\n" +
                "WHERE\n" +
                "    TravelAgentCode = ? AND DateIssued >= ? AND DateIssued <= ? AND BlankID NOT IN(\n" +
                "    SELECT\n" +
                "        BlankID\n" +
                "    FROM\n" +
                "        SalesRecord\n" +
                ")\n" +
                "GROUP BY\n" +
                "    StaffID,\n" +
                "    BlankType;");
        stmt.setInt(1, Application.getActiveUser().getTravelAgentCode());
        stmt.setDate(2,startDate);
        stmt.setDate(3,endDate);

        ResultSet rs = stmt.executeQuery();
        return rs;
    }
}
