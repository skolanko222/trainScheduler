import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class TableView extends JFrame{
    private JPanel panel1;
    private JButton button1;
    private JTable table1;
    private JScrollPane scrollPanel;
    private JComboBox comboBox1;
    private PostgresSQLConnection connection;
    private String [] tableNames;
    private ArrayList<String> tableNamesList = new ArrayList<>();

    public TableView(PostgresSQLConnection c){
        super("Table View");
        connection = c;
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
        setVisible(true);
        try{
            ResultSet rs = connection.executeFunction("train.get_tables('train')");
            while(rs.next()){
                comboBox1.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ResultSet rs = getTable(comboBox1.getSelectedItem().toString());
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
                    DefaultTableModel model = new DefaultTableModel(rows.toArray(new Object[][]{}), columnNames);
                    table1.setModel(model);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public ResultSet getTable(String tableName){
        try{
            return connection.executeCommand("SELECT * FROM train." + tableName + ";");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
