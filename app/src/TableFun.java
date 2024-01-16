import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class TableFun {
    static public class NoDataException extends Exception{
        public NoDataException(String message){
            super(message);
        }
    }

    static void showTable(PostgresSQLConnection connection, String query, JTable table1){
        try{
            ResultSet rs = connection.executeCommand(query);
            //get all column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String [] columnNames = new String[columnCount];
            for(int i = 0; i < columnCount; i++){
                columnNames[i] = rsmd.getColumnName(i+1);
            }
            //get all rows
            ArrayList<String[]> rows = new ArrayList<>();
            while(rs.next()){
                String [] row = new String[columnCount];
                for(int i = 0; i < columnCount; i++){

                    row[i] = rs.getString(i+1);
                }
                rows.add(row);
            }
            if(rows.size() <= 0){
                throw new NoDataException("No data");
            }
            DefaultTableModel model = new DefaultTableModel(rows.toArray(new Object[][]{}), columnNames);
            table1.setModel(model);
        } catch (NoDataException e){
            JOptionPane.showMessageDialog(null, "Brak danych do wyświetlenia!");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
