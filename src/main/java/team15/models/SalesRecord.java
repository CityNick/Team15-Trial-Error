package team15.models;


import java.sql.ResultSet;
import java.sql.Timestamp;

public class SalesRecord {

  private long RecordID;
  private long BlankID;
  private long CustomerID;
  private long StaffID;
  private Timestamp Date;
  private double LocalPrice;
  private double Discount;
  private double USDPrice;
  private double USDConversionRate;
  private double Comission;
  private double TaxRate;
  private String PaymentType;
  private String Bank;
  private long AccountNumber;
  private long SortCode;
  private String CustomerFirstName;
  private String CustomerLastName;

  public SalesRecord(long blankID, long customerID, long staffID, Timestamp date, double localPrice, double discount, double USDPrice, double USDConversionRate, double comission, double taxRate, String paymentType, String bank, long accountNumber, long sortCode, String customerFirstName, String customerLastName) {
    BlankID = blankID;
    CustomerID = customerID;
    StaffID = staffID;
    Date = date;
    LocalPrice = localPrice;
    Discount = discount;
    this.USDPrice = USDPrice;
    this.USDConversionRate = USDConversionRate;
    Comission = comission;
    TaxRate = taxRate;
    PaymentType = paymentType;
    Bank = bank;
    AccountNumber = accountNumber;
    SortCode = sortCode;
    CustomerFirstName = customerFirstName;
    CustomerLastName = customerLastName;
  }

  public void SalesRecord(ResultSet rs){
    try {
      this.RecordID = rs.getLong("RecordID");
      this.BlankID = rs.getLong("BlankID");
      this.CustomerID = rs.getLong("CustomerID");
      this.StaffID = rs.getLong("StaffID");
      this.Date = rs.getTimestamp("Date");
      this.LocalPrice = rs.getDouble("LocalPrice");
      this.Discount = rs.getDouble("Discount");
      this.USDPrice = rs.getDouble("USDPrice");
      this.USDConversionRate = rs.getDouble("USDConversionRate");
      this.Comission = rs.getDouble("Comission");
      this.TaxRate = rs.getDouble("TaxRate");
      this.PaymentType = rs.getString("PaymentType");
      this.Bank = rs.getString("Bank");
      this.AccountNumber = rs.getLong("AccountNumber");
      this.SortCode = rs.getLong("SortCode");
      this.CustomerFirstName = rs.getString("CustomerFirstName");
      this.CustomerLastName = rs.getString("CustomerLastName");
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public long getRecordID() {
    return RecordID;
  }
  public void setRecordID(long recordID) {
    this.RecordID = recordID;
  }


  public long getBlankID() {
    return BlankID;
  }
  public void setBlankID(long blankID) {
    this.BlankID = blankID;
  }


  public long getCustomerID() {
    return CustomerID;
  }
  public void setCustomerID(long customerID) {
    this.CustomerID = customerID;
  }


  public long getStaffID() {
    return StaffID;
  }
  public void setStaffID(long staffID) {
    this.StaffID = staffID;
  }


  public Timestamp getDate() {
    return Date;
  }
  public void setDate(Timestamp date) {
    this.Date = date;
  }


  public double getLocalPrice() {
    return LocalPrice;
  }
  public void setLocalPrice(double localPrice) {
    this.LocalPrice = localPrice;
  }


  public double getDiscount() {
    return Discount;
  }
  public void setDiscount(long discount) {
    this.Discount = discount;
  }


  public double getUSDPrice() {
    return USDPrice;
  }
  public void setUSDPrice(double USDPrice) {
    this.USDPrice = USDPrice;
  }


  public double getUSDConversionRate() {
    return USDConversionRate;
  }
  public void setUSDConversionRate(double USDConversionRate) {
    this.USDConversionRate = USDConversionRate;
  }


  public double getComission() {
    return Comission;
  }
  public void setComission(double comission) {
    this.Comission = comission;
  }


  public double getTaxRate() {
    return TaxRate;
  }
  public void setTaxRate(double taxRate) {
    this.TaxRate = taxRate;
  }


  public String getPaymentType() {
    return PaymentType;
  }
  public void setPaymentType(String paymentType) {
    this.PaymentType = paymentType;
  }


  public String getBank() {
    return Bank;
  }
  public void setBank(String bank) {
    this.Bank = bank;
  }


  public long getAccountNumber() {
    return AccountNumber;
  }
  public void setAccountNumber(long accountNumber) {
    this.AccountNumber = accountNumber;
  }


  public long getSortCode() {
    return SortCode;
  }
  public void setSortCode(long sortCode) {
    this.SortCode = sortCode;
  }


  public String getCustomerFirstName() {
    return CustomerFirstName;
  }
  public void setCustomerFirstName(String customerFirstName) {
    this.CustomerFirstName = customerFirstName;
  }


  public String getCustomerLastName() {
    return CustomerLastName;
  }
  public void setCustomerLastName(String customerLastName) {
    this.CustomerLastName = customerLastName;
  }

}
