package team15.models;


import java.sql.ResultSet;

public class CustomerAccount {

  private long CustomerID;
  private String FirstName;
  private String LastName;
  private double Expenditure;
  private String Status;
  private String DiscountType;


  public void CustomerAccount(ResultSet rs){
    try {
      this.CustomerID= rs.getLong("CustomerID");
      this.FirstName = rs.getString("FirstName");
      this.LastName = rs.getString("LastName");
      this.Expenditure = rs.getDouble("Expenditure");
      this.Status = rs.getString ("Status");
      this.DiscountType = rs.getString("DiscountType");
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }



  public long getCustomerID() {
    return CustomerID;
  }
  public void setCustomerID(long customerID) {
    this.CustomerID = customerID;
  }


  public String getFirstName() {
    return FirstName;
  }
  public void setFirstName(String firstName) {
    this.FirstName = firstName;
  }


  public String getLastName() {
    return LastName;
  }
  public void setLastName(String lastName) {
    this.LastName = lastName;
  }


  public double getExpenditure() {
    return Expenditure;
  }
  public void setExpenditure(double expenditure) {
    this.Expenditure = expenditure;
  }


  public String getStatus() {
    return Status;
  }
  public void setStatus(String status) {
    this.Status = status;
  }


  public String getDiscountType() {
    return DiscountType;
  }
  public void setDiscountType(String discountType) {
    this.DiscountType = discountType;
  }

}
