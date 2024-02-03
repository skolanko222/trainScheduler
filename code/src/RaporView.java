import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaporView extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTable table1;
    private JButton generujRaportButton;
    private JTable table2;
    private JButton generujRaportButton1;
    private JTable table3;
    private JButton generujRaportButton2;
    private JTable table4;
    private JTable table5;
    private JButton generujRaportButton4;
    private JButton generujRaportButton3;
    private JButton generujRaportButton5;
    private JButton generujRaportButton6;
    private JTextField textField2;
    private JTextField textField1;
    private JTable table7;
    private JTable table6;
    private JButton generujRaportButton7;
    private JTable table8;
    private JTextField textField4;
    private PostgresSQLConnection connection;

    public RaporView(PostgresSQLConnection c) {
        connection = c;
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
        setVisible(true);
        setSize(900, 344);

        generujRaportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT * FROM train.lokomotywa_dane";
                TableFun.showTable(connection, query, table1);

            }
        });
        generujRaportButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id = null;
                try{
                    id = Integer.parseInt(textField1.getText());
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Podaj poprawny id kursu!");
                    return;
                }
                String query = "SELECT * FROM train.pojazdy_kursu_detailed( " + id + ")";
                TableFun.showTable(connection, query, table2);

            }
        });
        generujRaportButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT * FROM train.lokomotywa_ilosc";
                TableFun.showTable(connection, query, table3);
            }
        });
        generujRaportButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT * FROM train.wagon_ilosc";
                TableFun.showTable(connection, query, table4);
            }
        });
        generujRaportButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT * FROM train.wagon_zt_ilosc";
                TableFun.showTable(connection, query, table5);
            }
        });
        generujRaportButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT * FROM train.autobus_zapasowy_ilosc";
                TableFun.showTable(connection, query, table6);
            }
        });
        generujRaportButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id = null;
                try{
                    id = Integer.parseInt(textField2.getText());
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Podaj poprawny id kursu!");
                    return;
                }
                String query = "SELECT * FROM train.pojazdy_skladu_detailed( " + id + ")";
                TableFun.showTable(connection, query, table7);

            }
        });
        generujRaportButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id = null;
                try{
                    id = Integer.parseInt(textField4.getText());
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Podaj poprawny id kursu!");
                    return;
                }
                String query = "SELECT * FROM train.przystanki_kursu( " + id + ")";
                TableFun.showTable(connection, query, table8);
            }
        });
    }

}
