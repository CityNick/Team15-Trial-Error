package team15.SQLHelpers;

import team15.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class BlankSQLHelper {

    public static Boolean checkTravelAgentBlank(String search){
        try (Connection connection = DatabaseConnector.connect()){

            ResultSet rs;
            if (search == ""){
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM Blank");
                rs = stmt.executeQuery();
            }
            else{
                // ------ IF @param search only contains numbers -------- //
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM Blank WHERE TravelAgentCode = ? ");
                stmt.setInt(1, Integer.parseInt(search));
                rs = stmt.executeQuery();
            }

            if (!rs.next()) {
                return false;
            }
            return true;

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public static ResultSet getTravelAgentBlankResultSet(String search, Connection connection) throws SQLException {

        ResultSet rs;
        if (search != ""){
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Blank WHERE TravelAgentCode LIKE ?");
            stmt.setInt(1, Integer.parseInt(search));
            rs = stmt.executeQuery();
        }
        else{
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Blank ");
            rs = stmt.executeQuery();
        }

        return rs;

    }

    public static int countTravelAgentStock(int travelAgentCode, int blankType){

        try (Connection connection = DatabaseConnector.connect()){

            ResultSet rs;
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(BlankID) FROM Blank WHERE BlankID NOT IN(SELECT b.BlankID FROM Blank b INNER JOIN SalesRecord s ON s.BlankID = b.BlankID) AND TravelAgentCode = ? and BlankType = ?");
            stmt.setInt(1, travelAgentCode);
            stmt.setInt(2, blankType);

            rs = stmt.executeQuery();

            if (!rs.next()) {
                return 0;
            }
            else{
                return ((Number) rs.getObject(1)).intValue();
            }


        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return 0;
    }





    public static void stockUpBlanks(int travelAgentCode, int amount, int blankType){

        try (Connection connection = DatabaseConnector.connect()){
            long blankID;

            // ----- stock up blanks based on amount ----- //
            for (int i = 0; i < amount; ++i){

                // ---- ensures new entry gets a unique ID ----- //
                blankID = generateUniqueID(blankType);
                while (checkBlankExists(blankID)){
                    blankID = generateUniqueID(blankType);
                }

                // ---- adds new blank ----- ..
                PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO Blank (BlankID, BlankType, TravelAgentCode, DateIssued) VALUES (?,?,?,CURRENT_DATE)");
                stmt.setLong(1, blankID);
                stmt.setInt(2, blankType);
                stmt.setInt(3, travelAgentCode);

                stmt.executeUpdate();
                System.out.println("Blank created:"+ blankID);
            }


        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void deleteBlanks(int travelAgentCode, int amount, int blankType){

        try (Connection connection = DatabaseConnector.connect()){

            // ---- removes blanks ----- ..
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM Blank WHERE BlankType = ? AND TravelAgentCode = ? AND BlankID NOT IN (SELECT BlankID FROM SalesRecord) ORDER BY StaffID ASC LIMIT ?");;
            stmt.setInt(1, blankType);
            stmt.setInt(2, travelAgentCode);
            stmt.setInt(3, amount*-1);

            stmt.executeUpdate();
            System.out.println(amount + " " + blankType + " Blanks");



        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static Boolean checkBlankExists(long blankID){
        try (Connection connection = DatabaseConnector.connect()){
            ResultSet rs;

            // ------ IF @param search only contains numbers -------- //
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Blank WHERE BlankID = ? ");
            stmt.setLong(1, blankID);
            rs = stmt.executeQuery();


            if (!rs.next()) {
                return false;
            }

            else {
                return true;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public static long generateUniqueID(int blankType){
        long uniqueID = blankType * 100000000L;
        Random random = new Random();

        uniqueID += (random.nextLong(0,99999999));

        return uniqueID;
    }
}
