package team15.models;


public class TravelAgentContract {

  private long travelAgentCode;
  private double commissionRate;


  public long getTravelAgentCode() {
    return travelAgentCode;
  }

  public void setTravelAgentCode(long travelAgentCode) {
    this.travelAgentCode = travelAgentCode;
  }


  public double getCommissionRate() {
    return commissionRate;
  }

  public void setCommissionRate(double commissionRate) {
    this.commissionRate = commissionRate;
  }

}
