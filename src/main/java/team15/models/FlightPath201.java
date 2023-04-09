package team15.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FlightPath201 {

    private String StartingAirport;
    private Timestamp Departure;
    private int FlightLeg1;
    private int FlightLeg2;
    private String Destination;
    private Timestamp Arrival;


    public FlightPath201(ResultSet rs) throws SQLException {
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

    public String getStartingAirport() {
        return StartingAirport;
    }

    public void setStartingAirport(String startingAirport) {
        StartingAirport = startingAirport;
    }

    public Timestamp getDeparture() {
        return Departure;
    }

    public void setDeparture(Timestamp departure) {
        Departure = departure;
    }

    public int getFlightLeg1() {
        return FlightLeg1;
    }

    public void setFlightLeg1(int flightLeg1) {
        FlightLeg1 = flightLeg1;
    }

    public int getFlightLeg2() {
        return FlightLeg2;
    }

    public void setFlightLeg2(int flightLeg2) {
        FlightLeg2 = flightLeg2;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public Timestamp getArrival() {
        return Arrival;
    }

    public void setArrival(Timestamp arrival) {
        Arrival = arrival;
    }

}

