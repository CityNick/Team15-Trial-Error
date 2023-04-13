package team15.models;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TravelAgentContract {

    private int TravelAgentCode;

    private Timestamp DateCreated;

    private float CommissionRate444;
    private float CommissionRate440;
    private float CommissionRate420;
    private float CommissionRate451;
    private float CommissionRate452;
    private float CommissionRate201;
    private float CommissionRate101;


    public TravelAgentContract(ResultSet rs) throws SQLException {
        this.TravelAgentCode = rs.getInt("TravelAgentCode");
        this.DateCreated = rs.getTimestamp("DateCreated");
        this.CommissionRate444 = rs.getFloat("CommissionRate444");
        this.CommissionRate440 = rs.getFloat("CommissionRate440");
        this.CommissionRate420 = rs.getFloat("CommissionRate420");
        this.CommissionRate451 = rs.getFloat("CommissionRate451");
        this.CommissionRate452 = rs.getFloat("CommissionRate452");
        this.CommissionRate201 = rs.getFloat("CommissionRate201");
        this.CommissionRate101 = rs.getFloat("CommissionRate101");
    }

    public int getTravelAgentCode() {
        return TravelAgentCode;
    }

    public void setTravelAgentCode(int travelAgentCode) {
        this.TravelAgentCode = travelAgentCode;
    }

    public float getCommissionRate444() {
        return CommissionRate444;
    }

    public void setCommissionRate444(float commissionRate444) {
        CommissionRate444 = commissionRate444;
    }

    public float getCommissionRate440() {
        return CommissionRate440;
    }

    public void setCommissionRate440(float commissionRate440) {
        CommissionRate440 = commissionRate440;
    }

    public float getCommissionRate420() {
        return CommissionRate420;
    }

    public void setCommissionRate420(float commissionRate420) {
        CommissionRate420 = commissionRate420;
    }

    public float getCommissionRate451() {
        return CommissionRate451;
    }

    public void setCommissionRate451(float commissionRate451) {
        CommissionRate451 = commissionRate451;
    }

    public float getCommissionRate452() {
        return CommissionRate452;
    }

    public void setCommissionRate452(float commissionRate452) {
        CommissionRate452 = commissionRate452;
    }

    public float getCommissionRate201() {
        return CommissionRate201;
    }

    public void setCommissionRate201(float commissionRate201) {
        CommissionRate201 = commissionRate201;
    }

    public float getCommissionRate101() {
        return CommissionRate101;
    }

    public void setCommissionRate101(float commissionRate101) {
        CommissionRate101 = commissionRate101;
    }

    public Timestamp getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        DateCreated = dateCreated;
    }


}
