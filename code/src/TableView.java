import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Queue;

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
        setSize(900, 344);
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
                String query = "SELECT * FROM train." + comboBox1.getSelectedItem().toString();
                TableFun.showTable(connection, query, table1);
            }
        });
    }

}
