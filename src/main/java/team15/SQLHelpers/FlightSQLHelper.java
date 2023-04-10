package team15.SQLHelpers;

import java.sql.*;
import java.time.LocalDate;

public class FlightSQLHelper {

    public static ResultSet getFlightPath(String startingAirport, String destinationAirport, LocalDate departureDate, int blankType, Connection connection) throws SQLException {
        ResultSet user = null;
        if (blankType == 444) {
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
                    "    AND f1.DepartureTime > ? AND f1.StartingAirport LIKE ? AND f4.DestinationAirport LIKE ?  AND f4.FlightType = 'International' ORDER BY f4.ArrivalTime ASC");
            stmt.setTimestamp(1, Timestamp.valueOf(departureDate.atStartOfDay()));
            stmt.setString(2, "%" + startingAirport + "%");
            stmt.setString(3, "%" + destinationAirport + "%");
            user = stmt.executeQuery();
        } else if (blankType == 440) {
            PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCTROW\n" +
                    "    f1.StartingAirport AS 'Starting',\n" +
                    "    f1.DepartureTime AS 'Departure',\n" +
                    "    f1.FlightID AS 'FlightLeg1',\n" +
                    "    f2.FlightID AS 'FlightLeg2',\n" +
                    "    f2.DestinationAirport AS 'Destination',\n" +
                    "    f2.ArrivalTime AS 'Arrival'\n" +
                    "FROM\n" +
                    "    Flight f1\n" +
                    "INNER JOIN Flight f2 WHERE\n" +
                    "    (\n" +

                    "        f1.DestinationAirport = f2.StartingAirport AND f1.ArrivalTime < DATE_ADD(f2.DepartureTime, INTERVAL 2 HOUR)\n" +
                    "    AND f1.DepartureTime > ? AND f1.StartingAirport LIKE ? AND f2.DestinationAirport LIKE ? AND f2.FlightType = 'International' ORDER BY f2.ArrivalTime ASC");
            stmt.setTimestamp(1, Timestamp.valueOf(departureDate.atStartOfDay()));
            stmt.setString(2, "%" + startingAirport + "%");
            stmt.setString(3, "%" + destinationAirport + "%");
            user = stmt.executeQuery();
        } else if (blankType == 201) {
            PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCTROW\n" +
                    "    f1.StartingAirport AS 'Starting',\n" +
                    "    f1.DepartureTime AS 'Departure',\n" +
                    "    f1.FlightID AS 'FlightLeg1',\n" +
                    "    f2.FlightID AS 'FlightLeg2',\n" +
                    "    f2.DestinationAirport AS 'Destination',\n" +
                    "    f2.ArrivalTime AS 'Arrival'\n" +
                    "FROM\n" +
                    "    Flight f1\n" +
                    "INNER JOIN Flight f2 WHERE\n" +
                    "        f1.DestinationAirport = f2.StartingAirport AND f1.ArrivalTime < DATE_ADD(f2.DepartureTime, INTERVAL 2 HOUR)\n" +
                    "    AND f1.DepartureTime > ? AND f1.StartingAirport LIKE ? AND f2.DestinationAirport LIKE ? AND f2.FlightType = 'Domestic' ORDER BY f2.ArrivalTime ASC");
            stmt.setTimestamp(1, Timestamp.valueOf(departureDate.atStartOfDay()));
            stmt.setString(2, "%" + startingAirport + "%");
            stmt.setString(3, "%" + destinationAirport + "%");
            user = stmt.executeQuery();
        } else if (blankType == 101) {
            PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCTROW\n" +
                    "    StartingAirport AS 'Starting',\n" +
                    "    DepartureTime AS 'Departure',\n" +
                    "    FlightID AS 'FlightLeg1',\n" +
                    "    DestinationAirport AS 'Destination',\n" +
                    "    ArrivalTime AS 'Arrival'\n" +
                    "FROM\n" +
                    "    Flight WHERE\n" +
                    "    DepartureTime > ? AND StartingAirport LIKE ? AND DestinationAirport LIKE ? AND FlightType = 'Domestic' ORDER BY ArrivalTime ASC");
            stmt.setTimestamp(1, Timestamp.valueOf(departureDate.atStartOfDay()));
            stmt.setString(2, "%" + startingAirport + "%");
            stmt.setString(3, "%" + destinationAirport + "%");
            user = stmt.executeQuery();

        }

        System.out.println("Query made");
        return user;
    }
}
