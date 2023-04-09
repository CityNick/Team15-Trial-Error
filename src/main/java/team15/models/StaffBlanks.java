package team15.models;

import java.sql.ResultSet;

public class StaffBlanks {



    private int StaffID;
    private int StockOf444;
    private int StockOf440;
    private int StockOf420;
    private int StockOf451;
    private int StockOf452;
    private int StockOf201;
    private int StockOf101;


    public StaffBlanks(ResultSet rs){
        try {
            this.StaffID = rs.getInt("StaffID");
            this.StockOf444 = rs.getInt("StockOf444");
            this.StockOf440 = rs.getInt("StockOf440");
            this.StockOf420 = rs.getInt("StockOf420");
            this.StockOf451 = rs.getInt("StockOf451");
            this.StockOf452 = rs.getInt("StockOf452");
            this.StockOf201 = rs.getInt("StockOf201");
            this.StockOf101 = rs.getInt("StockOf101");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int staffID) {
        StaffID = staffID;
    }

    public int getStockOf444() {
        return StockOf444;
    }

    public void setStockOf444(int stockOf444) {
        StockOf444 = stockOf444;
    }

    public int getStockOf440() {
        return StockOf440;
    }

    public void setStockOf440(int stockOf440) {
        StockOf440 = stockOf440;
    }

    public int getStockOf420() {
        return StockOf420;
    }

    public void setStockOf420(int stockOf420) {
        StockOf420 = stockOf420;
    }

    public int getStockOf451() {
        return StockOf451;
    }

    public void setStockOf451(int stockOf451) {
        StockOf451 = stockOf451;
    }

    public int getStockOf452() {
        return StockOf452;
    }

    public void setStockOf452(int stockOf452) {
        StockOf452 = stockOf452;
    }

    public int getStockOf201() {
        return StockOf201;
    }

    public void setStockOf201(int stockOf201) {
        StockOf201 = stockOf201;
    }

    public int getStockOf101() {
        return StockOf101;
    }

    public void setStockOf101(int stockOf101) {
        StockOf101 = stockOf101;
    }
}
