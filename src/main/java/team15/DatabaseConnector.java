package team15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection connection = null;

    public static Connection connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g15", "in2018g15_d", "P7c8XFsU");
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return connection;
    }
}
