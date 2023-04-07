package team15.SQLHelpers;

import team15.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TravelAgentSQLHelper {


    public static Boolean checkTravelAgent(String search){
        try (Connection connection = DatabaseConnector.connect()){

            ResultSet rs;



            if (search == ""){
                return false;
            }
            else{
                // ------ IF @param search only contains numbers -------- //
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM TravelAgent WHERE TravelAgentCode = ? ");
                stmt.setInt(1, Integer.parseInt(search));
                rs = stmt.executeQuery();
            }

            if (!rs.next()) {
                return false;
            }
            else{
                return true;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
