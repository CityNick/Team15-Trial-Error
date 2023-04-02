package team15;

import team15.models.StaffAccount;

import java.sql.*;

public class StaffAccountSQL {

    private static ResultSet rs;

    // ============================ LOGIN SQL HELPER ======================= //
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

    // ============================ VIEW STAFF ACCOUNTS ================ //
    public static Boolean checkStaffAccount(String search){
        try (Connection connection = DatabaseConnector.connect()){

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
}