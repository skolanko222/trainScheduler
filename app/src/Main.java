import Entities.TypPojazdu;
import Entities.typy;
import PostgresSQLConnection.ConsolConnection;
import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main extends JFrame{
    private JButton dodajLokomotyweButton;
    private JButton dodajWagonButton;
    private JButton dodajEZTButton;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel tabPanel;
    private JPanel wagTab;
    private JPanel eztTab;
    private JTabbedPane tabbedPane2;
    private JTabbedPane tabbedPane3;
    private JTabbedPane tabbedPane4;
    private JTextField textField1;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JButton button1;
    private JTextField textField2;
    private JTextField textField3;

    private PostgresSQLConnection connection;

    public Main(PostgresSQLConnection connection) {
        super("MainUI");
        this.connection = connection;
        this.setContentPane(mainPanel);
        this.setTitle("Bazy danych");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.pack(); // ustawia rozmiar okna do rozmiaru zawartosci
        this.setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwaWag = textField1.getText();
                double masaWag = 0;
                int iloscMiejsc = 0;
                try {
                    masaWag = Double.parseDouble(textField2.getText());
                    iloscMiejsc = Integer.parseInt(textField3.getText());
                } catch (NumberFormatException ioException) {
                    //make dialog box with error
                    JOptionPane.showMessageDialog(null, "Masa musi być liczbą!");
                    return;
                }
                boolean czyRestauracyjny = checkBox1.isSelected();
                boolean czyToaleta = checkBox2.isSelected();

                TypPojazdu objToSend = new TypPojazdu(nazwaWag, typy.wagon, masaWag, iloscMiejsc, czyRestauracyjny, czyToaleta);

                try {
                    connection.executeCommand(objToSend.getInsertQuery());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }

            }
        });
    }

    public static void main(String[] args) throws IOException {
        PostgresSQLConnection connection = new PostgresSQLConnection("u1kolanko", "1kolanko");
        new Main(connection);
        new ConsolConnection(connection);
    }
}
