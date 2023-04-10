package team15.SQLHelpers;

import team15.DatabaseConnector;
import team15.models.TravelAgent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TravelAgentSQLHelper {


    public static Boolean checkTravelAgent(String search) {
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs;


            if (search == "") {
                return false;
            } else {
                // ------ IF @param search only contains numbers -------- //
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM TravelAgent WHERE TravelAgentCode = ? ");
                stmt.setInt(1, Integer.parseInt(search));
                rs = stmt.executeQuery();
            }

            return rs.next();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static TravelAgent getTravelAgent(int travelAgentCode) {
        try (Connection connection = DatabaseConnector.connect()) {
            ResultSet rs;

            // ------ IF @param search only contains numbers -------- //
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM TravelAgent WHERE TravelAgentCode = ? ");
            stmt.setInt(1, travelAgentCode);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }
            TravelAgent ta = new TravelAgent(rs);
            return ta;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
