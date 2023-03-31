package team15.models;


public class TravelAgent {

  private long travelAgentCode;
  private String address;
  private String postCode;


  public long getTravelAgentCode() {
    return travelAgentCode;
  }

  public void setTravelAgentCode(long travelAgentCode) {
    this.travelAgentCode = travelAgentCode;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

}
