package team15.models;


import java.sql.ResultSet;

public class RefundRecord {

    private long SalesRecordRecordID;
    private java.sql.Date Date;
    private java.sql.Time Time;
    private double CommissionToRefund;

    public void RefundRecord(ResultSet rs) {
        try {
            this.SalesRecordRecordID = rs.getLong("SalesRecordRecordID");
            this.Date = rs.getDate("Date");
            this.Time = rs.getTime("Time");
            this.CommissionToRefund = rs.getDouble("CommissionToRefund");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getSalesRecordRecordID() {
        return SalesRecordRecordID;
    }

    public void setSalesRecordRecordID(long salesRecordRecordID) {
        this.SalesRecordRecordID = salesRecordRecordID;
    }


    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        this.Date = date;
    }


    public java.sql.Time getTime() {
        return Time;
    }

    public void setTime(java.sql.Time time) {
        this.Time = time;
    }


    public double getCommissionToRefund() {
        return CommissionToRefund;
    }

    public void setCommissionToRefund(double commissionToRefund) {
        this.CommissionToRefund = commissionToRefund;
    }

}
