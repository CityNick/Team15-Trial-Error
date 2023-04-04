package team15;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLToTable {

    // ================= RESULT SET INTO TABLE VIEW (Staff ============= //
    public static void fillTableView(TableView tableView, ResultSet resultSet){
        try{
            tableView.getColumns().clear();
            for(int i=0; i<resultSet.getMetaData().getColumnCount(); i++){
                TableColumn column = new TableColumn<>();
                switch(resultSet.getMetaData().getColumnName(i+1)){
                    default:
                        column.setText(resultSet.getMetaData().getColumnName(i+1));
                }
                column.setCellValueFactory(new PropertyValueFactory<>(resultSet.getMetaData().getColumnName(i+1)));
                tableView.getColumns().add(column);
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    // ========== EXTRACT DATA FROM RESULT SET INTO LIST ========== //


}
