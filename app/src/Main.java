import Entities.*;
import PostgresSQLConnection.ConsolConnection;
import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends JFrame{
    private JButton dodajLokomotyweButton;
    private JButton dodajWagonButton;
    private JButton dodajEZTButton;
    private JPanel mainPanel;
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
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton zarejestrujPojazdButton;
    private JComboBox comboBox3;
    private JLabel stanEgzemplarzy;
    private JButton typLokomButton;
    private JTextField fieldNazwaLok;
    private JTextField fieldMoc;
    private JTextField fieldUciagLok;
    private JTextField fieldMaxPre;
    private JComboBox comboNazwyLok;
    private JLabel stanEgzemplarzyLok;
    private JButton buttonZarejestujLok;
    private JTextField fieldNazwaStacji;
    private JTextField fieldMiejscowoscStacji;
    private JTextField fieldAdresStacji;
    private JTextField fieldNumerAdresStacji;
    private JButton buttonSaveStacja;
    private String[] pobraneTypyEnum;
    private String[] nazwyEgzemplarzyWagon;
    private String[] nazwyEgzemplarzyLokomotyw;
    private String wybranyTypEgzemplarza;
    private PostgresSQLConnection connection;

    private void reloadComboBoxes() {
        pobraneTypyEnum = TypPojazdu.getTypEnum(connection);
        comboBox1.setModel(new DefaultComboBoxModel(pobraneTypyEnum));
        comboBox3.setModel(new DefaultComboBoxModel(pobraneTypyEnum)); // typ wagonu dodawanego jako egzemplarz

        nazwyEgzemplarzyWagon = TypPojazdu.getNazwyTypow(comboBox3.getSelectedItem().toString(), connection);
        nazwyEgzemplarzyLokomotyw = TypLokomotywy.getNazwyTypow(connection);
        comboBox2.setModel(new DefaultComboBoxModel(nazwyEgzemplarzyWagon));
        comboNazwyLok.setModel(new DefaultComboBoxModel(nazwyEgzemplarzyLokomotyw));

        updateLabelWagon();
        updateLabelLokomotywa();
    }

    private void updateLabelLokomotywa() {
        try{
            String nazwa = comboNazwyLok.getSelectedItem().toString();
            int ilosc = TypLokomotywy.getIloscEgzemplarzy(nazwa, connection);
            System.out.println("" + nazwa +" "+ ilosc);
            stanEgzemplarzyLok.setText("Aktualna liczba dostepnych egzemplarzy: " + ilosc);
        } catch (Exception e) {
            stanEgzemplarzyLok.setText("Aktualna liczba dostepnych egzemplarzy: 0");
        }
    }
    private void updateLabelWagon() {
        try{
            String typ = comboBox3.getSelectedItem().toString();
            String nazwa = comboBox2.getSelectedItem().toString();
            int ilosc = TypPojazdu.getIloscEgzemplarzy(typ, nazwa, connection);
            System.out.println("" + typ +" "+ ilosc);
            stanEgzemplarzy.setText("Aktualna liczba dostepnych egzemplarzy: " + ilosc);
        } catch (Exception e) {
            stanEgzemplarzy.setText("Aktualna liczba dostepnych egzemplarzy: 0");
        }
    }

    public Main(PostgresSQLConnection connection) {
        super("MainUI");
        this.connection = connection;
        this.setContentPane(mainPanel);
        this.setTitle("Bazy danych");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.pack(); // ustawia rozmiar okna do rozmiaru zawartosci
        this.setVisible(true);
        reloadComboBoxes();

        /**
         * Dodanie typu pojazdu wagonu
         */
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
                    JOptionPane.showMessageDialog(null, "Masa i ilość miejsc musi być liczbą!");
                    return;
                }
                boolean czyRestauracyjny = checkBox1.isSelected();
                boolean czyToaleta = checkBox2.isSelected();
                String typ = pobraneTypyEnum[comboBox1.getSelectedIndex()];

                TypPojazdu objToSend = new TypPojazdu(nazwaWag, typ, masaWag, iloscMiejsc, czyRestauracyjny, czyToaleta);

                try {
                    connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
                reloadComboBoxes();
            }
        });

        /**
         *  Wybór typu pojazdu do dodania jako egzemplarz
         */
        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wybranyTypEgzemplarza = comboBox3.getSelectedItem().toString();
                nazwyEgzemplarzyWagon = TypPojazdu.getNazwyTypow(wybranyTypEgzemplarza, connection);
                comboBox2.setModel(new DefaultComboBoxModel(nazwyEgzemplarzyWagon));
                int ilosc = TypPojazdu.getIloscEgzemplarzy(wybranyTypEgzemplarza, comboBox2.getSelectedItem().toString(), connection);
                System.out.println("" + wybranyTypEgzemplarza +" "+ ilosc);
                stanEgzemplarzy.setText("Aktualna liczba dostepnych egzemplarzy: " + ilosc);

            }
        });
        /**
         * Wybór typu pojazdu do dodania jako egzemplarz oraz wyświetlenie aktualnej liczby egzemplarzy
         */
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wybranyTypEgzemplarza = comboBox3.getSelectedItem().toString();
                String nazwa = comboBox2.getSelectedItem().toString();

                if(wybranyTypEgzemplarza != null){
                    updateLabelWagon();
                }
            }
        });
        /**
         * Dodanie egzemplarza wagonu
         */
        zarejestrujPojazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typ = comboBox3.getSelectedItem().toString();
                String nazwa = comboBox2.getSelectedItem().toString();
                String queryNazwa = "SELECT id_typ_po FROM train.typ_pojazdu_osobowego WHERE nazwa_wagonu = '" + nazwa + "';";
                try {
                    ResultSet temp = connection.executeCommand(queryNazwa);
                    temp.next();
                    int id = temp.getInt(1);
                    if(typ.equals("EZT")) {
                        wagonEZT objToSend = new wagonEZT(id, null);
                        connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));

                    }
                    else if(typ.equals("wagon")) {
                        Wagon objToSend = new Wagon(id,null);
                        connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));

                    }
                    else if(typ.equals("autobus")) {
                        AutobusZapasowy objToSend = new AutobusZapasowy(id, null    );
                        connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));

                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                updateLabelWagon();
            }
        });
        typLokomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwa = fieldNazwaLok.getText();
                int moc = 0;
                int max_pre = 0;
                int max_uciag = 0;
                try {
                    moc = Integer.parseInt(fieldMoc.getText());
                    max_pre = Integer.parseInt(fieldMaxPre.getText());
                    max_uciag = Integer.parseInt(fieldUciagLok.getText());
                } catch (NumberFormatException ioException) {
                    //make dialog box with error
                    JOptionPane.showMessageDialog(null, "Moc, max predkosc i max uciag musi być liczbą!");
                    return;
                }
                TypLokomotywy objToSend = new TypLokomotywy(null, nazwa, moc, max_pre, max_uciag);
                try {
                    connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
                reloadComboBoxes();
            }
        });
        buttonZarejestujLok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwa = comboNazwyLok.getSelectedItem().toString();
                String queryNazwa = "SELECT id_lokomotywa FROM train.typ_lokomotywa WHERE nazwa = '" + nazwa + "';";
                try {
                    ResultSet temp = connection.executeCommand(queryNazwa);
                    temp.next();
                    int id = temp.getInt(1);
                    Lokomotywa objToSend = new Lokomotywa(null, id, null);
                    connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                updateLabelLokomotywa();
            }
        });
        comboNazwyLok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwa = comboNazwyLok.getSelectedItem().toString();
                updateLabelLokomotywa();
            }
        });
        buttonSaveStacja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwa = fieldNazwaStacji.getText();
                String miejscowosc = fieldMiejscowoscStacji.getText();
                String ulica = fieldAdresStacji.getText();
                int numer = 0;
                try {
                    numer = Integer.parseInt(fieldNumerAdresStacji.getText());
                } catch (NumberFormatException ioException) {
                    JOptionPane.showMessageDialog(null, "Numer musi być liczbą!");
                    return;
                }
                Stacja objToSend = new Stacja(null, nazwa, miejscowosc, ulica, numer);
                try {
                    connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        PostgresSQLConnection connection = new PostgresSQLConnection("u1kolanko", "1kolanko");
        connection.connect();
        new Main(connection);
        new ConsolConnection(connection);
        connection.close();
    }
}
