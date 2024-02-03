package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Objects;

public class PojazdTableModel extends DefaultTableModel {
    private ArrayList<Entity> pojazdy = new ArrayList<>();
    private PostgresSQLConnection connection;

    private int wagaCalkowita = 0;

    public ArrayList<Entity> getPojazdy() {
        return pojazdy;
    }

    private int maxUciag = 0;
    public PojazdTableModel(PostgresSQLConnection connection){
        super();
        this.connection = connection;
    }

    public void addEntity(Entity entity){
        pojazdy.add(entity);

        if(entity instanceof Lokomotywa lok){
            wagaCalkowita += ((Lokomotywa) entity).getWaga(connection);
            maxUciag += ((Lokomotywa) entity).getMaxUciag(connection);
        }
        else if(entity instanceof Wagon){
            wagaCalkowita += ((Wagon) entity).getMasa(connection);
        }
        else if(entity instanceof wagonEZT){
            wagaCalkowita += ((wagonEZT) entity).getMasa(connection);
            maxUciag += ((wagonEZT) entity).getMasa(connection);
        }
        else if(entity instanceof AutobusZapasowy){
            wagaCalkowita += ((AutobusZapasowy) entity).getMasa(connection);
        }
        fireTableDataChanged();
    }

    public boolean checkIfAddedAlready(Entity entity){
        for(Entity e : pojazdy){
            if(e instanceof Lokomotywa lok && entity instanceof Lokomotywa lok2) {
                if (Objects.equals(lok.getId(), lok2.getId()))
                    return true;
            }
            else if(e instanceof Wagon wagon && entity instanceof Wagon wagon2){
                if(Objects.equals(wagon.getId(), wagon2.getId()))
                    return true;
            }
            else if(e instanceof wagonEZT wagon && entity instanceof wagonEZT wagon2){
                if(Objects.equals(wagon.getId(), wagon2.getId()))
                    return true;
            }
            else if(e instanceof AutobusZapasowy autobus && entity instanceof AutobusZapasowy autobus2){
                if(Objects.equals(autobus.getId(), autobus2.getId()))
                    return true;
            }
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

    @Override
    public void removeRow(int row) {
        if(pojazdy.get(row) instanceof Lokomotywa lok){
            wagaCalkowita -= lok.getWaga(connection);
            maxUciag -= lok.getMaxUciag(connection);
        }
        else if(pojazdy.get(row) instanceof Wagon wagon){
            wagaCalkowita -= wagon.getMasa(connection);
        }
        else if(pojazdy.get(row) instanceof wagonEZT wagon){
            wagaCalkowita -= wagon.getMasa(connection);
            maxUciag -= wagon.getMasa(connection);
        }
        else if(pojazdy.get(row) instanceof AutobusZapasowy autobus){
            wagaCalkowita -= autobus.getMasa(connection);
        }
        pojazdy.remove(row);
        fireTableDataChanged();
    }

    public int getWagaCalkowita() {
        return wagaCalkowita;
    }

    public void setWagaCalkowita(int wagaCalkowita) {
        this.wagaCalkowita = wagaCalkowita;
    }

    public int getMaxUciag() {
        return maxUciag;
    }

    public void setMaxUciag(int maxUciag) {
        this.maxUciag = maxUciag;
    }
}
