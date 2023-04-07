package team15.SQLHelpers;
import team15.Application;
import team15.DatabaseConnector;
import team15.models.StaffAccount;

import java.sql.*;

import static java.sql.Types.NULL;
import static team15.DatabaseConnector.connection;

public class StaffAccountSQLHelper {

    // ============================ LOGIN SQL HELPER ======================= //t
    public static boolean checkAccountLogin(long StaffID, String Password) {

        try (Connection connection = DatabaseConnector.connect()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM StaffAccount WHERE StaffID = ? AND Password = ?");
            stmt.setLong(1, StaffID);
            stmt.setString(2,Password);
            ResultSet user = stmt.executeQuery();
            if (!user.next()){
                return false;
            }
            StaffAccount sa = new StaffAccount(user);
            System.out.println(sa.getFirstName() + " " + sa.getLastName() + "\n"
                    + sa.getRole());
            Application.setActiveUser(sa);
            System.out.println("Active User = " + Application.getActiveUser().getFirstName() + " " + Application.getActiveUser().getLastName());
            return true;

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }


    // ============================ VIEW STAFF ACCOUNTS =================== //
    public static Boolean checkStaffAccount(String search){
        try (Connection connection = DatabaseConnector.connect()){

            ResultSet rs;

            // ------ IF @param search only contains numbers -------- //
            if (search.matches("[0-9]+")) {
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM StaffAccount WHERE (StaffID = ? OR TravelAgentCode = ?) ORDER BY StaffID ASC");
                stmt.setLong(1, (Long.parseLong(search)));
                stmt.setLong(2, (Long.parseLong(search)));
                rs = stmt.executeQuery();
                if (!rs.next()) {
                    return false;
                }
                return true;
            }
            // ---- IF @param search contains letters ---- //
            else{
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT * FROM StaffAccount WHERE (FirstName LIKE ? OR LastName LIKE ? OR Role LIKE ?) ORDER BY FirstName ASC");
                stmt.setString(1,"%"+search+"%");
                stmt.setString(2,"%"+search+"%");
                stmt.setString(3,"%"+search+"%");
                rs = stmt.executeQuery();
                if (!rs.next()){
                        return false;
                }
                return true;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }

    }


    // ============================ GET STAFF ACCOUNT RESULT SET ====================== //
    public static ResultSet getResultSet(String search, Connection connection) throws SQLException {
        ResultSet rs;

        // ------ IF @param search only contains numbers -------- //
        if (search.matches("[0-9]+")) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM StaffAccount WHERE (StaffID = ? OR TravelAgentCode = ?) ORDER BY StaffID ASC");
            stmt.setLong(1, (Long.parseLong(search)));
            stmt.setLong(2, (Long.parseLong(search)));
            rs = stmt.executeQuery();
            return rs;
        }
        // ---- IF @param search contains letters ---- //
        else{
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM StaffAccount WHERE (FirstName LIKE ? OR LastName LIKE ? OR Role LIKE ?) ORDER BY FirstName ASC");
            stmt.setString(1,"%"+search+"%");
            stmt.setString(2,"%"+search+"%");
            stmt.setString(3,"%"+search+"%");
            rs = stmt.executeQuery();
            return rs;
        }

    }


    // =========================== CREATE STAFF ACCOUNT ===================== //
    public static Boolean createStaffAccount(StaffAccount staffAccount, Connection connection) throws SQLException {

        // ----- Searches for matching record ----- //
        PreparedStatement stmt = connection.prepareStatement
                ("SELECT * FROM StaffAccount WHERE FirstName = ? AND LastName = ? AND Role =? AND TravelAgentCode = ? AND SupervisorID =?");
        stmt.setString(1, staffAccount.getFirstName());
        stmt.setString(2, staffAccount.getLastName());
        stmt.setString(3, staffAccount.getRole());

        if(staffAccount.getTravelAgentCode() == 0){stmt.setNull(4,NULL);}
        else {stmt.setInt(4, staffAccount.getTravelAgentCode());}

        if(staffAccount.getSupervisorID() == 0){stmt.setNull(5,NULL);}
        else{stmt.setInt(5,staffAccount.getSupervisorID());}

        ResultSet user = stmt.executeQuery();

        // ----- if staff does not exist ----- //
        if (!user.next()){
            switch(staffAccount.getRole()){
                case "Administrator":
                    stmt = connection.prepareStatement(
                            "INSERT IGNORE INTO StaffAccount (StaffID, Password, FirstName, LastName, Role) VALUES (?, ?, ?, ?, ?)");
                    stmt.setInt(1,staffAccount.getStaffID());
                    stmt.setString(2,staffAccount.getPassword());
                    stmt.setString(3,staffAccount.getFirstName());
                    stmt.setString(4,staffAccount.getLastName());
                    stmt.setString(5, staffAccount.getRole());
                    break;

                case "Manager":
                    stmt = connection.prepareStatement(
                            "INSERT IGNORE INTO StaffAccount (StaffID, Password, FirstName, LastName, Role, TravelAgentCode) VALUES (?, ?, ?, ?, ?, ?)");
                    stmt.setInt(1,staffAccount.getStaffID());
                    stmt.setString(2,staffAccount.getPassword());
                    stmt.setString(3,staffAccount.getFirstName());
                    stmt.setString(4,staffAccount.getLastName());
                    stmt.setString(5, staffAccount.getRole());
                    stmt.setInt(6, staffAccount.getTravelAgentCode());
                    break;

                case "Travel Advisor":
                    stmt = connection.prepareStatement(
                            "INSERT INTO StaffAccount (StaffID, Password, FirstName, LastName, Role, TravelAgentCode, SupervisorID) VALUES (?, ?, ?, ?, ?, ?, ?)");
                    stmt.setInt(1,staffAccount.getStaffID());
                    stmt.setString(2,staffAccount.getPassword());
                    stmt.setString(3,staffAccount.getFirstName());
                    stmt.setString(4,staffAccount.getLastName());
                    stmt.setString(5, staffAccount.getRole());
                    stmt.setInt(6, staffAccount.getTravelAgentCode());
                    stmt.setInt(7, staffAccount.getSupervisorID());
            }

            stmt.executeUpdate();
            System.out.println("Account Made");
            return true;
        }
        else{
            System.out.println("Account Already Exists");
            return false;
        }

    }

}