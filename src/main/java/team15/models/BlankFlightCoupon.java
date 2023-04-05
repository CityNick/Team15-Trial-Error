package team15.models;


import java.sql.ResultSet;

public class BlankFlightCoupon {

  private long BlankID;
  private long FlightID;

  public void BlankFlightCoupon(ResultSet rs){
    try {
      this.BlankID= rs.getLong("StaffID");
      this.FlightID = rs.getLong("Password");
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }


  public long getBlankID() {
    return BlankID;
  }
  public void setBlankID(long blankID) {
    this.BlankID = blankID;
  }


  public long getFlightFlightId() {
    return FlightID;
  }
  public void setFlightFlightId(long flightFlightId) {
    this.FlightID = flightFlightId;
  }

}
