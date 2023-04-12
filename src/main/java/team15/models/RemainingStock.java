package team15.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RemainingStock {


    private int StaffID;

    private int BlankType;
    private int Count;

    public RemainingStock(ResultSet rs) throws SQLException {
        this.StaffID = rs.getInt(1);
        this.BlankType = rs.getInt(2);
        this.Count = rs.getInt(3);
    }

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int staffID) {
        StaffID = staffID;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public int getBlankType() {
        return BlankType;
    }

    public void setBlankType(int blankType) {
        BlankType = blankType;
    }
}
