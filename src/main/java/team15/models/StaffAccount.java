package team15.models;

import java.sql.ResultSet;

public class StaffAccount {

  private long StaffID;
  private String Password;
  private String FirstName;
  private String LastName;
  private String Role;
  private long TravelAgentCode;
  private long SupervisorID;

  public StaffAccount(ResultSet rs){
    try {
      this.StaffID = rs.getInt("StaffID");
      this.Password = rs.getString("Password");
      this.FirstName = rs.getString("FirstName");
      this.LastName = rs.getString("LastName");
      this.Role = rs.getString("Role");
      this.TravelAgentCode = rs.getInt("TravelAgentCode");
      this.SupervisorID = rs.getInt("SupervisorID");
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public long getStaffID() {
    return StaffID;
  }
  public void setStaffID(long staffID) {
    this.StaffID = staffID;
  }


  public String getPassword() {
    return Password;
  }
  public void setPassword(String password) {
    this.Password = password;
  }


  public String getFirstName() {
    return FirstName;
  }
  public void setFirstName(String firstName) {
    this.FirstName = firstName;
  }


  public String getLastName() {return LastName;}
  public void setLastName(String lastName) {
    this.LastName = lastName;
  }


  public String getRole() {
    return Role;
  }
  public void setRole(String role) {
    this.Role = role;
  }


  public long getTravelAgentCode() {
    return TravelAgentCode;
  }
  public void setTravelAgentCode(long travelAgentCode) {
    this.TravelAgentCode = travelAgentCode;
  }


  public long getSupervisorID() {
    return SupervisorID;
  }
  public void setSupervisorID(long supervisorID) {
    this.SupervisorID = supervisorID;
  }

}
