package team15.SQLHelpers;

import team15.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelAgentContractSQLHelper {

    public static Boolean checkTravelAgentContract(String search) {
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs;
            if (search == "") {
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM TravelAgentContract");
                rs = stmt.executeQuery();
            } else {
                // ------ IF @param search only contains numbers -------- //
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM TravelAgentContract WHERE TravelAgentCode = ? ");
                stmt.setInt(1, Integer.parseInt(search));
                rs = stmt.executeQuery();
            }

            return rs.next();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static ResultSet getTravelAgentContractResultSet(String search, Connection connection) throws SQLException {

        ResultSet rs;
        if (search != "") {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM TravelAgentContract WHERE TravelAgentCode LIKE ?");
            stmt.setInt(1, Integer.parseInt(search));
            rs = stmt.executeQuery();
        } else {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM TravelAgentContract ");
            rs = stmt.executeQuery();
        }

        return rs;

    }

    public static double getCommissionRate(int travelAgentCode, String blankType) {
        try (Connection connection = DatabaseConnector.connect()) {
            ResultSet rs;

            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM TravelAgentContract WHERE TravelAgentCode = ?");
            stmt.setInt(1, travelAgentCode);

            rs = stmt.executeQuery();


            if (!rs.next()) {
                System.out.println("No Travel Agent Contract found");
                return 0;
            }
            double commissionRate = rs.getDouble("CommissionRate" + blankType);
            commissionRate /= 100;
            System.out.println(commissionRate);
            return commissionRate;

        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }
}