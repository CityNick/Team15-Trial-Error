package team15.models;


import java.sql.ResultSet;

public class Flight {

  private long FlightID;
  private String StartingAirport;
  private String DestinationAirport;
  private java.sql.Date Date;
  private java.sql.Time Time;

  public void Flight(ResultSet rs){
    try {
      this.FlightID= rs.getLong("FlightID");
      this.StartingAirport = rs.getString("StartingAirport");
      this.DestinationAirport = rs.getString("DestinationAirport");
      this.Date = rs.getDate("Date");
      this.Time = rs.getTime ("Time");
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public long getFlightID() {
    return FlightID;
  }
  public void setFlightID(long flightID) {
    this.FlightID = flightID;
  }


  public String getStartingAirport() {
    return StartingAirport;
  }
  public void setStartingAirport(String startingAirport) {
    this.StartingAirport = startingAirport;
  }


  public String getDestinationAirport() {
    return DestinationAirport;
  }
  public void setDestinationAirport(String destinationAirport) {
    this.DestinationAirport = destinationAirport;
  }


  public java.sql.Date getDate() {
    return Date;
  }
  public void setDate(java.sql.Date date) {
    this.Date = date;
  }


  public java.sql.Time getTime() {
    return Time;
  }
  public void setTime(java.sql.Time time) {
    this.Time = time;
  }

}
