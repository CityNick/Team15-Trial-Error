package team15;

import team15.models.StaffAccount;

import java.sql.*;

public class StaffAccountSQL {


    public static boolean checkAccount(long StaffID, String Password) {


        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM StaffAccount WHERE StaffID = ? AND Password = ?");
            stmt.setLong(1, StaffID);
            stmt.setString(2,Password);
            ResultSet user = stmt.executeQuery();
            if (!user.next()){
                return false;
            }
            StaffAccount sa = new StaffAccount(user);
            System.out.println(sa.getRole());
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
ad