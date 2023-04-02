package team15.models;

import java.sql.ResultSet;

public class StaffAccount {

  private long staffID;
  private String password;
  private String firstName;
  private String lastName;
  private String role;
  private long TravelAgentCode;
  private long SupervisorID;

  public StaffAccount(ResultSet rs){
    try {
      this.staffID = rs.getLong("StaffID");
      this.password = rs.getString("Password");
      this.firstName = rs.getString("FirstName");
      this.lastName = rs.getString("LastName");
      this.role = rs.getString("Role");
      this.TravelAgentCode = rs.getLong("TravelAgentCode");
      this.SupervisorID = rs.getLong("SupervisorID");
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public long getStaffID() {
    return staffID;
  }
  public void setStaffID(long staffID) {
    this.staffID = staffID;
  }


  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }


  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
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
