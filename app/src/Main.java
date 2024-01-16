import Entities.*;
import PostgresSQLConnection.ConsolConnection;
import PostgresSQLConnection.PostgresSQLConnection;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Main extends JFrame{
    private JButton dodajLokomotyweButton;
    private JButton buttonRaport;
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
    private JTextField fieldNazwaLini;
    private JButton buttonSaveLinia;
    private JFormattedTextField fieldDataPrzyjazdu;
    private JButton dodajPrzystanekButton;
    private JComboBox comboStacje;
    private JFormattedTextField fieldDataOdjazdu;
    private JFormattedTextField fieldGodzinaPrzyjazdu;
    private JFormattedTextField fieldGodzinaOdjazdu;
    private JButton zapiszKursButton;
    private JTable tableLinia;
    private JButton buttonPushUp;
    private JButton buttonPushDown;
    private JComboBox comboLiniaForCreatedPrzystanek;
    private JComboBox comboTypWagonPojazd;
    private JLabel comboEgzPojazd;
    private JComboBox comboEgLokWagPojazd;
    private JTable tabelaPojazd;
    private JButton buttonAddPojazd;
    private JPanel pojTab;
    private JButton buttonAddSklad;
    private JComboBox comboSkladDoKursu;
    private JComboBox comboKursDoSkladu;
    private JButton przypiszSkładDoKursuButton;
    private JButton usuńZaznaczonyPojazdButton;
    private JButton usuńZaznaczonyButton;
    private JLabel labelAktualnyUciag;
    private JLabel labelAktualnaWaga;
    private String[] pobraneTypyEnum;
    private String[] nazwyEgzemplarzyWagon;
    private String[] nazwyEgzemplarzyLokomotyw;
    private String[] nazwyStacji;
    private String[] nazwyLinii;
    private String wybranyTypEgzemplarza;
    private PostgresSQLConnection connection;
    private int counterPrzystanek = 0;
    private PrzystanekTableModel przystanekTableModel;
    private PojazdTableModel pojazdTableModel;
    ArrayList<String> nazwyPojazdowSkladu = new ArrayList<>();
    Sklad skladToBeSend = null;

    private class IllegalNameException extends Exception {
        public IllegalNameException(String message) {
            super(message);
        }
    }
    private void updateLabelWagaSkladu(){
        double waga = pojazdTableModel.getWagaCalkowita();
        double uciag = pojazdTableModel.getMaxUciag();
        labelAktualnaWaga.setText("Aktualna waga składu: " + waga + " t");
        labelAktualnyUciag.setText("Aktualny uciąg składu: " + uciag + " t");

    }
    private void reloadComboBoxes() {
        pobraneTypyEnum = TypPojazdu.getTypEnum(connection);
        comboBox1.setModel(new DefaultComboBoxModel(pobraneTypyEnum));
        comboBox3.setModel(new DefaultComboBoxModel(pobraneTypyEnum)); // typ wagonu dodawanego jako egzemplarz
        //convert pobraneTypyEnum to ArrayList<String>
        ArrayList<String> typy = new ArrayList<>(Arrays.asList(pobraneTypyEnum));


        Sklad [] sklady = Sklad.getFreeSklad(connection);
        String [] idSkladow = new String[sklady.length];
        for(int i = 0; i < sklady.length; i++) {
            idSkladow[i] = "" + sklady[i].getIdSkladu(connection.getConnection());
        }

        KursLini [] kursy = KursLini.getFreeKurs(connection);
        String [] idKursow = new String[kursy.length];
        for(int i = 0; i < kursy.length; i++) {
            idKursow[i] = "" + kursy[i].getId();
        }

        comboKursDoSkladu.setModel(new DefaultComboBoxModel(idKursow));
        comboSkladDoKursu.setModel(new DefaultComboBoxModel(idSkladow));
        comboTypWagonPojazd.setModel(new DefaultComboBoxModel(typy.toArray()));

        nazwyStacji = Stacja.getNazwyStacji(connection);
        comboStacje.setModel(new DefaultComboBoxModel(nazwyStacji));

        nazwyEgzemplarzyWagon = TypPojazdu.getNazwyTypow(comboBox3.getSelectedItem().toString(), connection);
        nazwyEgzemplarzyLokomotyw = TypLokomotywy.getNazwyTypow(connection);
        comboBox2.setModel(new DefaultComboBoxModel(nazwyEgzemplarzyWagon));
        comboNazwyLok.setModel(new DefaultComboBoxModel(nazwyEgzemplarzyLokomotyw));

        nazwyLinii = Linia.getNazwyLinii(connection);
        comboLiniaForCreatedPrzystanek.setModel(new DefaultComboBoxModel(nazwyLinii));

        updateLabelWagon();
        updateLabelLokomotywa();
        updateLabelEgzPojazd();

    }
    private void updateLabelEgzPojazd(){
        try{
            String typ = comboTypWagonPojazd.getSelectedItem().toString();
            String query = null;
            Entity objToSend = null;
            if(typ.equals("lokomotywa")) {
                query = "SELECT nazwa, id_lokomotywa_pk, id_sklad FROM train.lokomotywa JOIN train.typ_lokomotywa ON train.lokomotywa.id_lokomotywa = train.typ_lokomotywa.id_lokomotywa WHERE train.lokomotywa.id_sklad IS NULL;";
                objToSend = new Lokomotywa();
            } else if (typ.equals("wagon")) {
                query = "SELECT nazwa_wagonu, id_wagon, id_sklad FROM train.wagon JOIN train.typ_pojazdu_osobowego ON train.wagon.id_typ_po = train.typ_pojazdu_osobowego.id_typ_po WHERE train.wagon.id_sklad IS NULL;";
                objToSend = new Wagon();
            } else if (typ.equals("EZT")) {
                query = "SELECT nazwa_wagonu, id_wagon_zt, id_sklad FROM train.wagon_zt JOIN train.typ_pojazdu_osobowego ON train.wagon_zt.id_typ_po = train.typ_pojazdu_osobowego.id_typ_po WHERE train.wagon_zt.id_sklad IS NULL;";
                objToSend = new wagonEZT();
            } else if (typ.equals("autobus")) {
                query = "SELECT nazwa_wagonu, id_autobus, id_sklad FROM train.autobus_zapasowy JOIN train.typ_pojazdu_osobowego ON train.autobus_zapasowy.id_typ_po = train.typ_pojazdu_osobowego.id_typ_po WHERE train.autobus_zapasowy.id_sklad IS NULL;";
                objToSend = new AutobusZapasowy();
            }
            ResultSet temp = connection.executeCommand(query);
            nazwyPojazdowSkladu.clear();
            while(temp.next()) {
                nazwyPojazdowSkladu.add(temp.getString(1) + " - " + temp.getString(2));
            }
            comboEgLokWagPojazd.setModel(new DefaultComboBoxModel(nazwyPojazdowSkladu.toArray()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
    private String setDateField(JFormattedTextField field, DateFormat df) {
        try{
            field.setText(df.format(df.parse(field.getText())));
            return field.getText();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Niepoprawny format daty przyjazdu!");
            field.setText(df.format(Calendar.getInstance().getTime()));
            return field.getText();
        }
    }
    private void checkNazwaCorrectness(String nazwa) throws IllegalNameException{
        if(nazwa.contains("-")) throw new IllegalNameException("Nazwa nie może zawierać znaku '-'");
    }
    public void close(){


    }
    public Main(PostgresSQLConnection connection) {
        super("MainUI");
        // priont size of window

        this.connection = connection;
        this.setContentPane(mainPanel);
        this.setTitle("Bazy danych");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 600);
        this.pack(); // ustawia rozmiar okna do rozmiaru zawartosci
        this.setVisible(true);
        przystanekTableModel = new PrzystanekTableModel(connection);
        pojazdTableModel = new PojazdTableModel(connection);
        System.out.println(this.getSize());


        reloadComboBoxes();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat tf = new SimpleDateFormat("HH:mm");
        fieldDataPrzyjazdu.setText(df.format(Calendar.getInstance().getTime()));
        fieldDataOdjazdu.setText(df.format(Calendar.getInstance().getTime()));
        fieldGodzinaPrzyjazdu.setText(tf.format(Calendar.getInstance().getTime()));
        fieldGodzinaOdjazdu.setText(tf.format(Calendar.getInstance().getTime()));
        tableLinia.setModel(przystanekTableModel);
        tabelaPojazd.setModel(pojazdTableModel);


        /**
         * Dodanie typu pojazdu wagonu
         */
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwaWag = textField1.getText();
                double masaWag = 0;
                int iloscMiejsc = 0;
                try{
                    checkNazwaCorrectness(nazwaWag);
                } catch (IllegalNameException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                    return;
                }
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
                        wagonEZT objToSend = new wagonEZT(null, id, null);
                        connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));

                    }
                    else if(typ.equals("wagon")) {
                        Wagon objToSend = new Wagon(null, id,null);
                        connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));

                    }
                    else if(typ.equals("autobus")) {
                        AutobusZapasowy objToSend = new AutobusZapasowy(null, id, null    );
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
                reloadComboBoxes();
            }
        });
        buttonSaveLinia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwa = fieldNazwaLini.getText();
                Linia objToSend = new Linia(null, nazwa);
                try {
                    connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
                reloadComboBoxes();
            }
        });
        fieldDataPrzyjazdu.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setDateField(fieldDataPrzyjazdu, df);
            }
        });
        fieldDataOdjazdu.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setDateField(fieldDataOdjazdu, df);
            }
        });
        fieldGodzinaPrzyjazdu.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setDateField(fieldGodzinaPrzyjazdu, tf);
            }
        });
        dodajPrzystanekButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwaStacji = comboStacje.getSelectedItem().toString();
                String queryNazwa = "SELECT id_stacja FROM train.stacja WHERE nazwa = '" + nazwaStacji + "';";
                int id = 0;
                try {
                    ResultSet temp = connection.executeCommand(queryNazwa);
                    temp.next();
                    id = temp.getInt(1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    return;
                }
                LocalDate dataPrzyjazdu = null;
                Time godzinaPrzyjazdu = null;
                LocalDate dataOdjazdu = null;
                Time godzinaOdjazdu = null;
                try{
                     dataPrzyjazdu = df.parse(fieldDataPrzyjazdu.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                     dataOdjazdu = df.parse(fieldDataOdjazdu.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                     godzinaPrzyjazdu = Time.valueOf(tf.parse(fieldGodzinaPrzyjazdu.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
                     godzinaOdjazdu = Time.valueOf(tf.parse(fieldGodzinaOdjazdu.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                    return;
                }
                System.out.println(dataPrzyjazdu + " " + dataOdjazdu + " " + godzinaPrzyjazdu + " " + godzinaOdjazdu);

                Przystanek objToSend = new Przystanek(null, dataPrzyjazdu, dataOdjazdu, godzinaPrzyjazdu, godzinaOdjazdu, id, counterPrzystanek, null);
                counterPrzystanek++;
                przystanekTableModel.addPrzystanek(objToSend);

            }
        });
        buttonPushDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableLinia.getSelectedRow();
                przystanekTableModel.moveDown(selectedRow);
                tableLinia.setRowSelectionInterval(selectedRow+1,selectedRow+1);
            }
        });
        buttonPushUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableLinia.getSelectedRow();
                przystanekTableModel.moveUp(selectedRow);
                tableLinia.setRowSelectionInterval(selectedRow-1,selectedRow-1);
            }
        });
        zapiszKursButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwaLini = comboLiniaForCreatedPrzystanek.getSelectedItem().toString();
                String queryNazwa = "SELECT id_linia FROM train.linia WHERE nazwa = '" + nazwaLini + "';";
                int idLinia = 0;
                int idKurs = 0;
                try {
                    ResultSet temp = connection.executeCommand(queryNazwa);
                    temp.next();
                    idLinia = temp.getInt(1);
                    String nextIdQuery = "SELECT nextval('train.kurs_lini_id_kurs_seq');";
                    temp = connection.executeCommand(nextIdQuery);
                    temp.next();
                    idKurs = temp.getInt(1);

                    KursLini objToSend = new KursLini(idKurs, null, idLinia);
                    connection.executeCommand(objToSend.getInsertQuery(connection.getConnection()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                // PObIERZ ID KURSU insertowanego wyżej
                for(int i = 0; i < przystanekTableModel.getRowCount(); i++) {
                    try{
                    Przystanek przystanek = przystanekTableModel.getPrzystanki().get(i);
                    przystanek.setId_kursu(idKurs);
                    przystanek.setNr_kolejnosc(i);
                    connection.executeCommand(przystanek.getInsertQuery(connection.getConnection()));
                    } catch (PSQLException exception) {
                        System.out.println(exception.getMessage());
                    }
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                }
                przystanekTableModel.getPrzystanki().clear();
                reloadComboBoxes();
            }
        });
        comboTypWagonPojazd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLabelEgzPojazd();
            }
        });
        buttonAddPojazd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typ = comboTypWagonPojazd.getSelectedItem().toString();
                String nazwa = comboEgLokWagPojazd.getSelectedItem().toString();
                int id = 0;
                int id_typPojazdu = 0;
                Entity objToSend = null;
                try {
                    id = Integer.parseInt(nazwa.split(" - ")[1]);
                } catch (NumberFormatException ioException) {
                    JOptionPane.showMessageDialog(null, "Niepoprawny format id!");
                    return;
                }
                if(skladToBeSend == null)
                    try {
                        String nextIdQuery = "SELECT nextval('train.sklad_id_sklad_seq');";
                        ResultSet temp = connection.executeCommand(nextIdQuery);
                        temp.next();
                        int idSklad = temp.getInt(1);
                        skladToBeSend = new Sklad(idSklad, false);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                String query = null;
                if(typ.equals("lokomotywa")) {
                    query = "SELECT id_lokomotywa FROM train.lokomotywa WHERE id_lokomotywa_pk = " + id + ";";
                    try{
                        ResultSet temp = connection.executeCommand(query);
                        temp.next();
                        id_typPojazdu = temp.getInt(1);
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                        return;
                    }
                    objToSend = new Lokomotywa(id, id_typPojazdu, skladToBeSend.getIdSkladu(connection.getConnection()));
                } else if (typ.equals("wagon")) {
                    query = "SELECT id_typ_po FROM train.wagon WHERE id_wagon = " + id + ";";
                    try{
                        ResultSet temp = connection.executeCommand(query);
                        temp.next();
                        id_typPojazdu = temp.getInt(1);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                        return;
                    }
                    objToSend = new Wagon(id, id_typPojazdu, skladToBeSend.getIdSkladu(connection.getConnection()));
                } else if (typ.equals("EZT")) {
                    query = "SELECT id_typ_po FROM train.wagon_zt WHERE id_wagon_zt = " + id + ";";
                    try{
                        ResultSet temp = connection.executeCommand(query);
                        temp.next();
                        id_typPojazdu = temp.getInt(1);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                        return;
                    }
                    objToSend = new wagonEZT(id, id_typPojazdu, skladToBeSend.getIdSkladu(connection.getConnection()));
                } else if (typ.equals("autobus")) {
                    query = "SELECT id_autobus FROM train.autobus_zapasowy WHERE id_autobus = " + id + ";";
                    try{
                        ResultSet temp = connection.executeCommand(query);
                        temp.next();
                        id_typPojazdu = temp.getInt(1);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                        return;
                    }
                    objToSend = new AutobusZapasowy(id, id_typPojazdu, skladToBeSend.getIdSkladu(connection.getConnection()));

                }
                if(pojazdTableModel.checkIfAddedAlready(objToSend)) {
                    JOptionPane.showMessageDialog(null, "Pojazd został już dodany!");
                    return;
                }
                else {
                    pojazdTableModel.addEntity(objToSend);
                }

                updateLabelEgzPojazd();
                updateLabelWagaSkladu();


            }
        });
        tabbedPane2.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);  reloadComboBoxes();
            }
        });
        tabbedPane2.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e); reloadComboBoxes();
            }
        });
        tabbedPane3.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e); reloadComboBoxes();
            }
        });
        tabbedPane3.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e); reloadComboBoxes();
            }
        });
        buttonAddSklad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pojazdTableModel.getWagaCalkowita() > pojazdTableModel.getMaxUciag()) {
                    JOptionPane.showMessageDialog(null, "Waga składu przekracza maksymalny uciąg!");
                    return;
                }
                    try {
                        connection.executeCommand(skladToBeSend.getInsertQuery(connection.getConnection()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                boolean flaga = true;
                for(Entity t : pojazdTableModel.getPojazdy() ) {
                    try {
                        connection.executeCommand(t.getUpdateQuery(connection.getConnection()));
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                        if(exception.getMessage().equals("Zapytanie nie zwróciło żadnych wyników."))
                            System.out.println("Dodano pojazd do składu");
                        else{
                            exception.printStackTrace();
                            flaga = false;
                        }
                    }
                }
                if(flaga)
                    JOptionPane.showMessageDialog(null, "Dodano skład!");
                else
                    JOptionPane.showMessageDialog(null, "Nie udało się dodać składu!");

                skladToBeSend = null;
                pojazdTableModel.getPojazdy().clear();
                pojazdTableModel.fireTableDataChanged();
                reloadComboBoxes();
                pojazdTableModel.setMaxUciag(0);
                pojazdTableModel.setWagaCalkowita(0);
                updateLabelWagaSkladu();

            }
        });
        przypiszSkładDoKursuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idSklad, idKurs;

                try{
                     idSklad = Integer.parseInt(comboSkladDoKursu.getSelectedItem().toString());
                     idKurs = Integer.parseInt(comboKursDoSkladu.getSelectedItem().toString());
                }catch (NullPointerException exception) {
                    JOptionPane.showMessageDialog(null, "Brak wybranego kursu/skladu!");
                    reloadComboBoxes();
                    return;
                }
                Sklad skladToUpdate = new Sklad(idSklad, true);
                KursLini kursToUpdate2 = new KursLini(idKurs, idSklad, 0); //id_lini ignored while updating
//                try {
//                    //connection.executeCommand(skladToUpdate.getUpdateQuery(connection.getConnection()));
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
                try {
                    connection.executeCommand(kursToUpdate2.getUpdateQuery(connection.getConnection()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                reloadComboBoxes();
            }
        });
        dodajLokomotyweButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableView tableView = new TableView(connection);
            }
        });
        buttonRaport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RaporView raporView = new RaporView(connection);

            }
        });
        usuńZaznaczonyPojazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaPojazd.getSelectedRow();
                pojazdTableModel.removeRow(selectedRow);
                updateLabelWagaSkladu();

            }
        });
        usuńZaznaczonyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableLinia.getSelectedRow();
                przystanekTableModel.removeRow(selectedRow);
            }
        });
    }
    public static void main(String[] args) throws IOException {
        PostgresSQLConnection connection = new PostgresSQLConnection("u1kolanko", "1kolanko");
        connection.connect();
        Main program = new Main(connection);
        new ConsolConnection(connection);
        program.close();
        connection.close();

    }
}
