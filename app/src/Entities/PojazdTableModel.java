package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Objects;

public class PojazdTableModel extends DefaultTableModel {
    private ArrayList<Entity> pojazdy = new ArrayList<>();
    private PostgresSQLConnection connection;

    int wagaCalkowita = 0;

    public ArrayList<Entity> getPojazdy() {
        return pojazdy;
    }

    int maxUciag = 0;
    public PojazdTableModel(PostgresSQLConnection connection){
        super();
        this.connection = connection;
    }

    public void addEntity(Entity entity){
        pojazdy.add(entity);
        if(entity instanceof Lokomotywa){
            wagaCalkowita += ((Lokomotywa) entity).getWaga(connection);
            maxUciag += ((Lokomotywa) entity).getMaxUciag(connection);
        }
        else if(entity instanceof Wagon){
            wagaCalkowita += ((Wagon) entity).getMasa(connection);
        }
        else if(entity instanceof wagonEZT){
            wagaCalkowita += ((wagonEZT) entity).getMasa(connection);
        }
        else if(entity instanceof AutobusZapasowy){
            wagaCalkowita += ((AutobusZapasowy) entity).getMasa(connection);
        }
        fireTableDataChanged();
    }

    public boolean checkIfAddedAlready(Entity entity){
        for(Entity e : pojazdy){
            if(Objects.equals(e.getId(), entity.getId()))
                return true;
        }
        return false;
    }

    public int getRowCount() {
        if(pojazdy == null)
            return 0;
        else
            return pojazdy.size();
    }
    public int getColumnCount() {
        return 4;
    }
    public String getColumnName(int col) {
        return switch (col) {
            case 0 -> "ID";
            case 1 -> "Typ pojazdu";
            case 2 -> "Nazwa pojazdu";
            case 3 -> "Masa";
            default -> "";
        };
    }
    public Object getValueAt(int row, int col) {
        if(pojazdy.get(row) instanceof Lokomotywa){
            return switch (col) {
                case 0 -> pojazdy.get(row).getId();
                case 1 -> "Lokomotywa";
                case 2 -> ((Lokomotywa) pojazdy.get(row)).getNazwa(connection);
                default -> null;
            };
        }
        else if(pojazdy.get(row) instanceof Wagon){
            return switch (col) {
                case 0 -> pojazdy.get(row).getId();
                case 1 -> "Wagon";
                case 2 -> ((Wagon) pojazdy.get(row)).getNazwa(connection);
                case 3 -> ((Wagon) pojazdy.get(row)).getMasa(connection);
                default -> null;
            };
        }
        else if(pojazdy.get(row) instanceof wagonEZT){
            return switch (col) {
                case 0 -> pojazdy.get(row).getId();
                case 1 -> "Wagon EZT";
                case 2 -> ((wagonEZT) pojazdy.get(row)).getNazwa(connection);
                case 3 -> ((wagonEZT) pojazdy.get(row)).getMasa(connection);
                default -> null;
            };
        }
        else if(pojazdy.get(row) instanceof AutobusZapasowy){
            return switch (col) {
                case 0 -> pojazdy.get(row).getId();
                case 1 -> "Autobus zapasowy";
                case 2 -> ((AutobusZapasowy) pojazdy.get(row)).getNazwa(connection);
                default -> null;
            };
        }
        else
            return null;
    }
}
