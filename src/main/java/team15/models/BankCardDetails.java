package team15.models;


import java.sql.ResultSet;
import java.sql.SQLException;

public class BankCardDetails {

    private int CustomerID;
    private String Bank;
    private long AccountNumber;
    private long SortCode;

    public BankCardDetails(ResultSet rs) throws SQLException {
        this.CustomerID = rs.getInt("CustomerID");
        this.Bank = rs.getString("Bank");
        this.AccountNumber = rs.getLong("AccountNumber");
        this.SortCode = rs.getLong("SortCode");
    }


    public long getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        this.CustomerID = customerID;
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

}
