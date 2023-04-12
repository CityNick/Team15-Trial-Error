package team15.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlexibleDiscount {

    private int TravelAgentCode;
    private double Threshold;
    private double Percentage;

    public FlexibleDiscount(ResultSet rs) throws SQLException {
        this.TravelAgentCode = rs.getInt("TravelAgentCode");
        this.Threshold = rs.getDouble("Threshold");
        this.Percentage = rs.getDouble("Percentage");
    }



    public int getTravelAgentCode() {
        return TravelAgentCode;
    }

    public void setTravelAgentCode(int travelAgentCode) {
        TravelAgentCode = travelAgentCode;
    }

    public double getThreshold() {
        return Threshold;
    }

    public void setThreshold(double threshold) {
        Threshold = threshold;
    }

    public double getPercentage() {
        return Percentage;
    }

    public void setPercentage(double percentage) {
        Percentage = percentage;
    }
}
