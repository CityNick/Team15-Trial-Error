package team15.SQLHelpers;

import team15.DatabaseConnector;
import team15.models.TravelAgentContract;

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
                    "SELECT * FROM TravelAgentContract WHERE TravelAgentCode = ? ORDER BY DateIssued DESC LIMIT 1");
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

    public static void deleteContract(TravelAgentContract selectedContract) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {

            PreparedStatement stmt = connection.prepareStatement(
                    "SET FOREIGN_KEY_CHECKS = 0;\n");

            stmt.executeUpdate();

            stmt = connection.prepareStatement(
                    "DELETE IGNORE FROM TravelAgentContract WHERE TravelAgentCode = ? AND DateCreated = ?");
            stmt.setInt(1, selectedContract.getTravelAgentCode());
            stmt.setTimestamp(2, selectedContract.getDateCreated());
            stmt.executeUpdate();

            stmt = connection.prepareStatement(
                    "SET FOREIGN_KEY_CHECKS = 1;\n");

            stmt.executeUpdate();


            stmt.executeUpdate();
            System.out.println("Travel Agent Contract Deleted");
        }
    }

    public static Boolean checkContractExists(int travelAgentCode, double commission444, double commission440,
                                              double commission420, double commission451, double commission452,
                                              double commission201, double commission101) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {

            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM TravelAgentContract WHERE " +
                            "TravelAgentCode = ? AND " +
                            "CommissionRate444 = ? AND " +
                            "CommissionRate440 = ? AND " +
                            "CommissionRate420 = ? AND " +
                            "CommissionRate451 = ? AND " +
                            "CommissionRate452 = ? AND " +
                            "CommissionRate201 = ? AND " +
                            "CommissionRate101 = ?");
            stmt.setInt(1, travelAgentCode);
            stmt.setDouble(2, commission444);
            stmt.setDouble(3, commission440);
            stmt.setDouble(4, commission420);
            stmt.setDouble(5, commission451);
            stmt.setDouble(6, commission452);
            stmt.setDouble(7, commission201);
            stmt.setDouble(8, commission101);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Contracted Does Not Exist");
                return false;
            } else {
                System.out.println("Contracted Exists");
                return true;
            }
        }
    }

    public static void createTravelAgentContract(int travelAgentCode, double commission444, double commission440,
                                                 double commission420, double commission451, double commission452,
                                                 double commission201, double commission101) throws SQLException {
        try (Connection connection = DatabaseConnector.connect()) {

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO TravelAgentContract " +
                            "(TravelAgentCode, DateCreated, CommissionRate444, CommissionRate440, " +
                            "CommissionRate420, CommissionRate451, CommissionRate452, " +
                            "CommissionRate201, CommissionRate101) " +
                            "VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, travelAgentCode);
            stmt.setDouble(2, commission444);
            stmt.setDouble(3, commission440);
            stmt.setDouble(4, commission420);
            stmt.setDouble(5, commission451);
            stmt.setDouble(6, commission452);
            stmt.setDouble(7, commission201);
            stmt.setDouble(8, commission101);

            stmt.executeUpdate();
            System.out.println("Contract Created");
        }
    }
}

