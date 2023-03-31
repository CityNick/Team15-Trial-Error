package team15.models;

import java.sql.ResultSet;

public class StaffAccount {

  private long staffID;
  private String password;
  private String firstName;
  private String lastName;
  private String role;
  private long travelAgentTravelAgentCode;
  private long officeManagerSupervisorId;

  public StaffAccount(ResultSet rs){
    try {
      this.staffID = rs.getLong("StaffID");
      this.password = rs.getString("Password");
      this.firstName = rs.getString("FirstName");
      this.lastName = rs.getString("LastName");
      this.role = rs.getString("Role");
      this.travelAgentTravelAgentCode = rs.getLong("TravelAgentTravelAgentCode");
      this.officeManagerSupervisorId = rs.getLong("OfficeManagerSupervisorID");
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


  public long getTravelAgentTravelAgentCode() {
    return travelAgentTravelAgentCode;
  }
  public void setTravelAgentTravelAgentCode(long travelAgentTravelAgentCode) {
    this.travelAgentTravelAgentCode = travelAgentTravelAgentCode;
  }


  public long getOfficeManagerSupervisorId() {
    return officeManagerSupervisorId;
  }
  public void setOfficeManagerSupervisorId(long officeManagerSupervisorId) {
    this.officeManagerSupervisorId = officeManagerSupervisorId;
  }

}
