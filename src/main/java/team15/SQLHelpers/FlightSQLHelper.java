package team15.SQLHelpers;

import team15.DatabaseConnector;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FlightSQLHelper {

    public static ResultSet getFlightPath(String startingAirport, String destinationAirport, LocalDate departureDate, Connection connection) throws SQLException {
            PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCTROW\n" +
                    "    f1.StartingAirport AS 'Starting',\n" +
                    "    f1.DepartureTime AS 'Departure',\n" +
                    "    f1.FlightID AS 'FlightLeg1',\n" +
                    "    f2.FlightID AS 'FlightLeg2',\n" +
                    "    f3.FlightID As 'FlightLeg3',\n" +
                    "    f4.FlightID As 'FlightLeg4',\n" +
                    "    f4.DestinationAirport AS 'Destination',\n" +
                    "    f4.ArrivalTime AS 'Arrival'\n" +
                    "FROM\n" +
                    "    Flight f1\n" +
                    "INNER JOIN Flight f2 INNER JOIN Flight f3 INNER JOIN Flight f4 WHERE\n" +
                    "    (\n" +

                    "        f1.DestinationAirport = f2.StartingAirport AND f1.ArrivalTime < DATE_ADD(f2.DepartureTime, INTERVAL 2 HOUR) AND\n" +
                    "        f2.DestinationAirport = f3.startingAirport AND f2.ArrivalTime < DATE_ADD(f3.DepartureTime, INTERVAL 2 HOUR) AND\n" +
                    "        f3.DestinationAirport = f4.StartingAirport AND f3.ArrivalTime < DATE_ADD(f4.DepartureTime, INTERVAL 2 HOUR)\n" +
                    "    ) \n" +
                    "    AND f1.DepartureTime > ? AND f1.StartingAirport LIKE ? AND f4.DestinationAirport LIKE ? ORDER BY f4.ArrivalTime ASC");
            stmt.setTimestamp(1, Timestamp.valueOf(departureDate.atStartOfDay()));
            stmt.setString(2,"%"+startingAirport+"%");
            stmt.setString(3,"%"+destinationAirport+"%");

            ResultSet user = stmt.executeQuery();
            System.out.println("Query made");
            return user;
    }
}
