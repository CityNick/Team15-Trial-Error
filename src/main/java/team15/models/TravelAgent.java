package team15.models;


import java.sql.ResultSet;

public class TravelAgent {

  private long TravelAgentCode;
  private String Address;
  private String PostCode;

  public void TravelAgent(ResultSet rs){
    try {
      this.TravelAgentCode = rs.getLong("TravelAgentCode");
      this.Address = rs.getString("Address");
      this.PostCode = rs.getString("PostCode");
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public long getTravelAgentCode() {
    return TravelAgentCode;
  }
  public void setTravelAgentCode(long travelAgentCode) {
    this.TravelAgentCode = travelAgentCode;
  }


  public String getAddress() {
    return Address;
  }
  public void setAddress(String address) {
    this.Address = address;
  }


  public String getPostCode() {
    return PostCode;
  }
  public void setPostCode(String postCode) {
    this.PostCode = postCode;
  }

}
