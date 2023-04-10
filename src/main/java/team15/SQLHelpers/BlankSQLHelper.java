package team15.SQLHelpers;

import team15.DatabaseConnector;
import team15.models.Blank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class BlankSQLHelper {

    public static Boolean checkTravelAgentBlank(String search) {
        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs;
            if (search == "") {
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM Blank");
                rs = stmt.executeQuery();
            } else {
                // ------ IF @param search only contains numbers -------- //
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM Blank WHERE TravelAgentCode = ? ");
                stmt.setInt(1, Integer.parseInt(search));
                rs = stmt.executeQuery();
            }

            return rs.next();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static ResultSet getTravelAgentBlankResultSet(String search, Connection connection) throws SQLException {

        ResultSet rs;
        if (search != "") {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Blank WHERE TravelAgentCode LIKE ?");
            stmt.setInt(1, Integer.parseInt(search));
            rs = stmt.executeQuery();
        } else {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Blank ");
            rs = stmt.executeQuery();
        }

        return rs;

    }

    public static int countTravelAgentStock(int travelAgentCode, int blankType) {

        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs;
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(BlankID) FROM Blank WHERE BlankID NOT IN(SELECT b.BlankID FROM Blank b INNER JOIN SalesRecord s ON s.BlankID = b.BlankID) AND TravelAgentCode = ? and BlankType = ?");
            stmt.setInt(1, travelAgentCode);
            stmt.setInt(2, blankType);

            rs = stmt.executeQuery();

            if (!rs.next()) {
                return 0;
            } else {
                return ((Number) rs.getObject(1)).intValue();
            }


        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }


    public static void stockUpBlanks(int travelAgentCode, int amount, int blankType) {

        try (Connection connection = DatabaseConnector.connect()) {
            long blankID;

            // ----- stock up blanks based on amount ----- //
            for (int i = 0; i < amount; ++i) {

                // ---- ensures new entry gets a unique ID ----- //
                blankID = generateUniqueID(blankType);
                while (checkBlankExists(blankID)) {
                    blankID = generateUniqueID(blankType);
                }

                // ---- adds new blank ----- ..
                PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO Blank (BlankID, BlankType, TravelAgentCode, DateIssued) VALUES (?,?,?,CURRENT_DATE)");
                stmt.setLong(1, blankID);
                stmt.setInt(2, blankType);
                stmt.setInt(3, travelAgentCode);

                stmt.executeUpdate();
                System.out.println("Blank created:" + blankID);
            }


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void deleteBlanks(int travelAgentCode, int amount, int blankType) {

        try (Connection connection = DatabaseConnector.connect()) {

            // ---- removes blanks ----- ..
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM Blank WHERE BlankType = ? AND TravelAgentCode = ? AND BlankID NOT IN (SELECT BlankID FROM SalesRecord) ORDER BY StaffID ASC LIMIT ?");
            stmt.setInt(1, blankType);
            stmt.setInt(2, travelAgentCode);
            stmt.setInt(3, amount * -1);

            stmt.executeUpdate();
            System.out.println(amount + " " + blankType + " Blanks");


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Boolean checkBlankExists(long blankID) {
        try (Connection connection = DatabaseConnector.connect()) {
            ResultSet rs;

            // ------ IF @param search only contains numbers -------- //
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Blank WHERE BlankID = ? ");
            stmt.setLong(1, blankID);
            rs = stmt.executeQuery();


            return rs.next();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static long generateUniqueID(int blankType) {
        long uniqueID = blankType * 100000000L;
        Random random = new Random();

        uniqueID += (random.nextLong(0, 99999999));

        return uniqueID;
    }

    public static ResultSet staffBlankCount(String search, int travelAgentCode, Connection connection) throws SQLException {
        ResultSet rs;
        if (search == "") {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT StaffID,\n" +
                            "    sum(case when BlankType = 444 then 1 else 0 end) AS 'StockOf444',\n" +
                            "    sum(case when BlankType = 440 then 1 else 0 end) AS 'StockOf440',\n" +
                            "    sum(case when BlankType = 420 then 1 else 0 end) AS 'StockOf420',\n" +
                            "    sum(case when BlankType = 451 then 1 else 0 end) AS 'StockOf451',\n" +
                            "    sum(case when BlankType = 452 then 1 else 0 end) AS 'StockOf452',\n" +
                            "    sum(case when BlankType = 201 then 1 else 0 end) AS 'StockOf201',\n" +
                            "    sum(case when BlankType = 101 then 1 else 0 end) AS 'StockOf101'\n" +
                            "FROM Blank WHERE TravelAgentCode = ? AND BlankID NOT IN (SELECT BlankID FROM SalesRecord)\n" +
                            "GROUP BY StaffID");
            stmt.setInt(1, travelAgentCode);
            rs = stmt.executeQuery();
        } else {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT StaffID,\n" +
                            "    sum(case when BlankType = 444 then 1 else 0 end) AS 'StockOf444',\n" +
                            "    sum(case when BlankType = 440 then 1 else 0 end) AS 'StockOf440',\n" +
                            "    sum(case when BlankType = 420 then 1 else 0 end) AS 'StockOf420',\n" +
                            "    sum(case when BlankType = 451 then 1 else 0 end) AS 'StockOf451',\n" +
                            "    sum(case when BlankType = 452 then 1 else 0 end) AS 'StockOf452',\n" +
                            "    sum(case when BlankType = 201 then 1 else 0 end) AS 'StockOf201',\n" +
                            "    sum(case when BlankType = 101 then 1 else 0 end) AS 'StockOf101'\n" +
                            "FROM Blank WHERE StaffID = ? AND TravelAgentCode = ? AND BlankID NOT IN (SELECT BlankID FROM SalesRecord)\n" +
                            "GROUP BY StaffID");
            stmt.setInt(1, Integer.parseInt(search));
            stmt.setInt(2, travelAgentCode);
            rs = stmt.executeQuery();
        }

        return rs;
    }

    public static void assignBlanks(int staffID, int travelAgentCode, int change, int blankType) {
        try (Connection connection = DatabaseConnector.connect()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Blank SET StaffID = ? WHERE StaffID IS NULL AND TravelAgentCode = ?  AND BlankType = ? LIMIT ?");
            stmt.setInt(1, staffID);
            stmt.setInt(2, travelAgentCode);
            stmt.setInt(3, blankType);
            stmt.setInt(4, change);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void unassignBlanks(int staffID, int amount, int blankType) {

        try (Connection connection = DatabaseConnector.connect()) {

            // ---- removes blanks ----- ..
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Blank SET StaffID = NULL WHERE StaffID = ? AND BlankType = ? AND BlankID NOT IN (SELECT BlankID FROM SalesRecord) LIMIT ?");
            stmt.setInt(1, staffID);
            stmt.setInt(2, blankType);
            stmt.setInt(3, amount * -1);

            stmt.executeUpdate();
            System.out.println(amount + " " + blankType + " Blanks");


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int countStaffStock(int staffID, int blankType) {

        try (Connection connection = DatabaseConnector.connect()) {

            ResultSet rs;
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(BlankID) FROM Blank WHERE BlankID NOT IN(SELECT b.BlankID FROM Blank b INNER JOIN SalesRecord s ON s.BlankID = b.BlankID) AND StaffID = ? and BlankType = ?");
            stmt.setInt(1, staffID);
            stmt.setInt(2, blankType);

            rs = stmt.executeQuery();

            if (!rs.next()) {
                return 0;
            } else {
                return ((Number) rs.getObject(1)).intValue();
            }


        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static Boolean checkBlankTypeStock(int staffID, int blankType) {
        try (Connection connection = DatabaseConnector.connect()) {
            ResultSet rs;

            // ------ IF @param search only contains numbers -------- //
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Blank WHERE StaffID = ? AND BlankType = ? AND BlankID NOT IN (SELECT BlankID FROM SalesRecord)");
            stmt.setInt(1, staffID);
            stmt.setInt(2, blankType);
            rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static Blank getBlankForSale(int staffID, int blankType) {
        try (Connection connection = DatabaseConnector.connect()) {
            ResultSet rs;

            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Blank WHERE StaffID = ? AND BlankType = ? AND BlankID NOT IN (SELECT BlankID FROM SalesRecord) LIMIT 1");
            stmt.setInt(1, staffID);
            stmt.setInt(2, blankType);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("no blank found");
                return null;
            }
            Blank blank = new Blank(rs);
            return blank;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
