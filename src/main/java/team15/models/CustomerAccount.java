package team15.models;


import java.sql.ResultSet;
import java.time.LocalDate;

public class CustomerAccount {

    private int CustomerID;
    private String FirstName;
    private String LastName;
    private double Expenditure;
    private String Status;
    private String DiscountType;
    private LocalDate DateOfBirth;


    public CustomerAccount(ResultSet rs) {
        try {
            this.CustomerID = rs.getInt("CustomerID");
            this.FirstName = rs.getString("FirstName");
            this.LastName = rs.getString("LastName");
            this.Expenditure = rs.getDouble("Expenditure");
            this.Status = rs.getString("Status");
            this.DiscountType = rs.getString("DiscountType");
            this.DateOfBirth = rs.getDate("DateOfBirth").toLocalDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
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

    public LocalDate getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }
}
