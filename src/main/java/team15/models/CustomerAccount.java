package team15.models;


public class CustomerAccount {

  private long customerId;
  private String firstName;
  private String lastName;
  private double expenditure;
  private String status;
  private String discountType;


  public long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
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


  public double getExpenditure() {
    return expenditure;
  }

  public void setExpenditure(double expenditure) {
    this.expenditure = expenditure;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getDiscountType() {
    return discountType;
  }

  public void setDiscountType(String discountType) {
    this.discountType = discountType;
  }

}
