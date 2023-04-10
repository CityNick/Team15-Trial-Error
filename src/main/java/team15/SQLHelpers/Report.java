package team15.SQLHelpers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Report {

    private long BlankID;
    private Double LocalPrice;
    private double Discount;
    private double Tax;
    private double USDPriceSold;
    private String PaymentMethod;
    private double Commission;

    public Report(ResultSet rs) throws SQLException {
        this.BlankID = rs.getLong(1);
        this.LocalPrice = rs.getDouble(2);
        this.Tax = rs.getDouble(3);
        this.USDPriceSold = rs.getDouble(4);
        this.PaymentMethod = rs.getString(5);
        this.Commission = rs.getDouble(6);
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
        return USDPriceSold;
    }

    public void setUSDPriceSold(double USDPriceSold) {
        this.USDPriceSold = USDPriceSold;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.PaymentMethod = paymentMethod;
    }

    public double getCommission() {
        return Commission;
    }

    public void setCommission(double commission) {
        Commission = commission;
    }

}
