package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.Time;
import java.time.LocalDateTime;

public class PrzystanekTableModel extends DefaultTableModel {

    private Przystanek [] przystanki = new Przystanek[0];
    private PostgresSQLConnection connection;

    public PrzystanekTableModel(PostgresSQLConnection connection){
        super();
        this.connection = connection;
    }

    public void addPrzystanek(Przystanek przystanek){
        Przystanek [] temp = new Przystanek[przystanki.length + 1];
        System.arraycopy(przystanki, 0, temp, 0, przystanki.length);
        temp[przystanki.length] = przystanek;
        przystanki = temp;
        fireTableDataChanged();
    }

    public int getRowCount() {
        if(przystanki == null)
            return 0;
        else
            return przystanki.length;
    }

    public int getColumnCount() {
        return 4;
    }

    public String getColumnName(int col) {
        return switch (col) {
            case 0 -> "Kolejność";
            case 1 -> "Stacja";
            case 2 -> "Czas przyjazdu";
            case 3 -> "Czas odjazdu";
            default -> "";
        };
    }

    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return przystanki[row].getNr_kolejnosc();
            case 1:
                return przystanki[row].getNazwa(connection);
            case 2:
                return przystanki[row].getCzas_przyjazdu();
            case 3:
                return przystanki[row].getCzas_odjazdu();
            default:
                return null;
        }
    }

    public Przystanek[] getPrzystanki(){
        return przystanki;
    }

    public void moveDown(int selectedRow) {
        if(selectedRow < przystanki.length - 1){
            Przystanek temp = przystanki[selectedRow];
            przystanki[selectedRow] = przystanki[selectedRow + 1];
            przystanki[selectedRow].setNr_kolejnosc(selectedRow );
            temp.setNr_kolejnosc(selectedRow + 1);
            przystanki[selectedRow + 1] = temp;
            fireTableDataChanged();
        }

    }
    public void moveUp(int selectedRow) {
        if(selectedRow > 0){
            Przystanek temp = przystanki[selectedRow];
            przystanki[selectedRow] = przystanki[selectedRow - 1];
            przystanki[selectedRow].setNr_kolejnosc(selectedRow );
            temp.setNr_kolejnosc(selectedRow - 1);
            przystanki[selectedRow - 1] = temp;
            fireTableDataChanged();
        }
    }
}
