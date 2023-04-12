package team15.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Report {


    private int staffID;
    private long BlankID;
    private Double LocalPrice;
    private double Discount;
    private double Tax;
    private double USDPrice;
    private String PaymentType;
    private double Commission;
    private Date Date;

    public Report(ResultSet rs) throws SQLException {
        this.staffID = rs.getInt(1);
        this.BlankID = rs.getLong(2);
        this.LocalPrice = rs.getDouble(3);
        this.Tax = rs.getDouble(4);
        this.Discount = rs.getDouble(5);
        this.USDPrice = rs.getDouble(6);
        this.PaymentType = rs.getString(7);
        this.Commission = rs.getDouble(8);
        this.Date = rs.getDate(9);
    }

    public long getBlankID() {
        return BlankID;
    }

    public void setBlankID(long blankID) {
        BlankID = blankID;
    }

    public Double getLocalPrice() {
        return LocalPrice;
    }

    public void setLocalPrice(Double localPrice) {
        LocalPrice = localPrice;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public double getUSDPriceSold() {
        return USDPrice;
    }

    public void setUSDPriceSold(double USDPriceSold) {
        this.USDPrice = USDPriceSold;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        this.PaymentType = paymentType;
    }

    public double getCommission() {
        return Commission;
    }

    public void setCommission(double commission) {
        Commission = commission;
    }
    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public double getUSDPrice() {
        return USDPrice;
    }

    public void setUSDPrice(double USDPrice) {
        this.USDPrice = USDPrice;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

}
