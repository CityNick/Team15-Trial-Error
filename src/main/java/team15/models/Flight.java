package team15.models;


public class Flight {

  private long flightId;
  private String startingAirport;
  private String destinationAirport;
  private java.sql.Date date;
  private java.sql.Time time;


  public long getFlightId() {
    return flightId;
  }

  public void setFlightId(long flightId) {
    this.flightId = flightId;
  }


  public String getStartingAirport() {
    return startingAirport;
  }

  public void setStartingAirport(String startingAirport) {
    this.startingAirport = startingAirport;
  }


  public String getDestinationAirport() {
    return destinationAirport;
  }

  public void setDestinationAirport(String destinationAirport) {
    this.destinationAirport = destinationAirport;
  }


  public java.sql.Date getDate() {
    return date;
  }

  public void setDate(java.sql.Date date) {
    this.date = date;
  }


  public java.sql.Time getTime() {
    return time;
  }

  public void setTime(java.sql.Time time) {
    this.time = time;
  }

}
