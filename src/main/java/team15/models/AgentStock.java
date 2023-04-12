package team15.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgentStock {

    private int BlankType;
    private int Count;

    public AgentStock(ResultSet rs) throws SQLException {
        this.BlankType = rs.getInt(1);
        this.Count = rs.getInt(2);
    }


    public int getBlankType() {
        return BlankType;
    }

    public void setBlankType(int blankType) {
        BlankType = blankType;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
