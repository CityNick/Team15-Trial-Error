package team15.models;


public class RefundRecord {

  private long salesRecordRecordId;
  private java.sql.Date date;
  private java.sql.Time time;
  private double commissionToRefund;


  public long getSalesRecordRecordId() {
    return salesRecordRecordId;
  }

  public void setSalesRecordRecordId(long salesRecordRecordId) {
    this.salesRecordRecordId = salesRecordRecordId;
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


  public double getCommissionToRefund() {
    return commissionToRefund;
  }

  public void setCommissionToRefund(double commissionToRefund) {
    this.commissionToRefund = commissionToRefund;
  }

}
