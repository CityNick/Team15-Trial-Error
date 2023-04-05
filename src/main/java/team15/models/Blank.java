package team15.models;


import java.sql.ResultSet;

public class Blank {

  private long BlankID;
  private int BlankType;
  private int TravelAgentCode;
  private int StaffID;

  public Blank(ResultSet rs){
    try {
      this.BlankID= rs.getLong("StaffID");
      this.BlankType = rs.getInt("Password");
      this.TravelAgentCode = rs.getInt("FirstName");
      this.StaffID = rs.getInt("LastName");
      //this.DateIssued = rs.getDate("DateIssued");
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


  public long getBlankType() {
    return BlankType;
  }

  public void setBlankType(int blankType) {
    this.BlankType = blankType;
  }


  public long getTravelAgentCode() {
    return TravelAgentCode;
  }

  public void setTravelAgentCode(int travelAgentCode) {
    this.TravelAgentCode = travelAgentCode;
  }


  public long getStaffID() {
    return StaffID;
  }

  public void setStaffID(int staffID) {
    this.StaffID = staffID;
  }

}
