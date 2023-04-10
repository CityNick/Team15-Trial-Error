package team15.models;


import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelAgent {

  private int TravelAgentCode;
  private String Address;
  private String PostCode;

  private double USDConversionRate;


  public TravelAgent(ResultSet rs) throws SQLException {

    this.TravelAgentCode = rs.getInt("TravelAgentCode");
    this.Address = rs.getString("Address");
    this.PostCode = rs.getString("PostCode");
    this.USDConversionRate = rs.getFloat("USDConversionRate");
  }

  public int getTravelAgentCode() {
    return TravelAgentCode;
  }
  public void setTravelAgentCode(int travelAgentCode) {
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

  public double getUSDConversionRate() {
    return USDConversionRate;
  }

  public void setUSDConversionRate(double USDConversionRate) {
    this.USDConversionRate = USDConversionRate;
  }

}
