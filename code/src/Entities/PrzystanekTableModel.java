package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class PrzystanekTableModel extends DefaultTableModel {

    private ArrayList<Przystanek> przystanki = new ArrayList<>();
    private PostgresSQLConnection connection;

    public PrzystanekTableModel(PostgresSQLConnection connection){
        super();
        this.connection = connection;
    }

    public void addPrzystanek(Przystanek przystanek){
        przystanki.add(przystanek);
        fireTableDataChanged();
    }

    public int getRowCount() {
        if(przystanki == null)
            return 0;
        else
            return przystanki.size();
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
                return przystanki.get(row).getNr_kolejnosc();
            case 1:
                return przystanki.get(row).getNazwa(connection);
            case 2:
                return przystanki.get(row).getCzas_przyjazdu();
            case 3:
                return przystanki.get(row).getCzas_odjazdu();
            default:
                return null;
        }
    }

    public ArrayList<Przystanek> getPrzystanki(){
        return przystanki;
    }

    public void moveDown(int selectedRow) {
        if(selectedRow < przystanki.size() - 1){
            Przystanek temp = przystanki.get(selectedRow);
            przystanki.set(selectedRow, przystanki.get(selectedRow + 1));
            przystanki.get(selectedRow).setNr_kolejnosc(selectedRow );
            temp.setNr_kolejnosc(selectedRow + 1);
            przystanki.set(selectedRow + 1, temp);
            fireTableDataChanged();
        }

    }
    public void moveUp(int selectedRow) {
        if(selectedRow > 0){
            Przystanek temp = przystanki.get(selectedRow);
            przystanki.set(selectedRow, przystanki.get(selectedRow - 1));
            przystanki.get(selectedRow).setNr_kolejnosc(selectedRow );
            temp.setNr_kolejnosc(selectedRow - 1);
            przystanki.set(selectedRow - 1, temp);
            fireTableDataChanged();
        }
    }

    @Override
    public void removeRow(int row) {
        przystanki.remove(row);
        fireTableDataChanged();
    }
}
