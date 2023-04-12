package team15.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FixedDiscount {

    private int TravelAgentCode;
    private double FixedDiscount;

    public FixedDiscount(ResultSet rs) throws SQLException {
        this.TravelAgentCode = rs.getInt("TravelAgentCode");
        this.FixedDiscount = rs.getDouble("FixedDiscount");

    }



    public int getTravelAgentCode() {
        return TravelAgentCode;
    }

    public void setTravelAgentCode(int travelAgentCode) {
        TravelAgentCode = travelAgentCode;
    }

    public double getFixedDiscount() {
        return FixedDiscount;
    }

    public void setFixedDiscount(double fixedDiscount) {
        FixedDiscount = fixedDiscount;
    }
}
