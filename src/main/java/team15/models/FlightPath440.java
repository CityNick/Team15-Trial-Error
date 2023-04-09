package team15.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FlightPath440 {

    private String StartingAirport;
    private Timestamp Departure;
    private int FlightLeg1;
    private int FlightLeg2;
    private String Destination;
    private Timestamp Arrival;


    public FlightPath440(ResultSet rs) throws SQLException {
        String _StartingAirport = rs.getString(1);
        Timestamp _Departure = rs.getTimestamp(2);
        int _FlightLeg1 = rs.getInt(3);
        int _FlightLeg2 = rs.getInt(4);
        String _Destination = rs.getString(5);
        Timestamp _Arrival = rs.getTimestamp(6);

        this.StartingAirport = _StartingAirport;
        this.Departure = _Departure;
        this.FlightLeg1 = _FlightLeg1;
        this.FlightLeg2 = _FlightLeg2;
        this.Destination = _Destination;
        this.Arrival = _Arrival;

    }
}
