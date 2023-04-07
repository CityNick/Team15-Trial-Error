package team15.models;


public class BankCardDetails {

  private long customerAccountCustomerId;
  private String bank;
  private long accountNumber;
  private long sortCode;


  public long getCustomerAccountCustomerId() {
    return customerAccountCustomerId;
  }

  public void setCustomerAccountCustomerId(long customerAccountCustomerId) {
    this.customerAccountCustomerId = customerAccountCustomerId;
  }


  public String getBank() {
    return bank;
  }
  public void setBank(String bank) {
    this.bank = bank;
  }


  public long getAccountNumber() {
    return accountNumber;
  }
  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }


  public long getSortCode() {
    return sortCode;
  }
  public void setSortCode(long sortCode) {
    this.sortCode = sortCode;
  }

}
