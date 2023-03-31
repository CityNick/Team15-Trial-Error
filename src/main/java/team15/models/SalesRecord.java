package team15.models;


public class SalesRecord {

  private long recordId;
  private long blankBlankId;
  private long customerAccountCustomerId;
  private long staffAccountStaffId;
  private java.sql.Date date;
  private java.sql.Time time;
  private double localPrice;
  private long discount;
  private double usdPrice;
  private double usdConversionRate;
  private double commission;
  private double taxRate;
  private String paymentType;
  private String bank;
  private long accountNumber;
  private long sortcode;
  private String customerFirstName;
  private String customerLastName;


  public long getRecordId() {
    return recordId;
  }

  public void setRecordId(long recordId) {
    this.recordId = recordId;
  }


  public long getBlankBlankId() {
    return blankBlankId;
  }

  public void setBlankBlankId(long blankBlankId) {
    this.blankBlankId = blankBlankId;
  }


  public long getCustomerAccountCustomerId() {
    return customerAccountCustomerId;
  }

  public void setCustomerAccountCustomerId(long customerAccountCustomerId) {
    this.customerAccountCustomerId = customerAccountCustomerId;
  }


  public long getStaffAccountStaffId() {
    return staffAccountStaffId;
  }

  public void setStaffAccountStaffId(long staffAccountStaffId) {
    this.staffAccountStaffId = staffAccountStaffId;
  }


  public java.sql.Date getDate() {
    return date;
  }

  public void setDate(java.sql.Date date) {
    this.date = date;
  }


  public java.sql.Time getTime() {
    return time;
  }

  public void setTime(java.sql.Time time) {
    this.time = time;
  }


  public double getLocalPrice() {
    return localPrice;
  }

  public void setLocalPrice(double localPrice) {
    this.localPrice = localPrice;
  }


  public long getDiscount() {
    return discount;
  }

  public void setDiscount(long discount) {
    this.discount = discount;
  }


  public double getUsdPrice() {
    return usdPrice;
  }

  public void setUsdPrice(double usdPrice) {
    this.usdPrice = usdPrice;
  }


  public double getUsdConversionRate() {
    return usdConversionRate;
  }

  public void setUsdConversionRate(double usdConversionRate) {
    this.usdConversionRate = usdConversionRate;
  }


  public double getCommission() {
    return commission;
  }

  public void setCommission(double commission) {
    this.commission = commission;
  }


  public double getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(double taxRate) {
    this.taxRate = taxRate;
  }


  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
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


  public long getSortcode() {
    return sortcode;
  }

  public void setSortcode(long sortcode) {
    this.sortcode = sortcode;
  }


  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }


  public String getCustomerLastName() {
    return customerLastName;
  }

  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

}
