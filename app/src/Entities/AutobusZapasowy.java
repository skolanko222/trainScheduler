package Entities;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import PostgresSQLConnection.PostgresSQLConnection;

public class AutobusZapasowy extends Entity implements PojazdInterface{
    int id_typ_po;
    Integer id_sklad;
    String nazwa = null;
    public AutobusZapasowy(Integer primary, Integer id_typPojazdu, Integer id_sklad) {

        super(primary);
        this.id_typ_po = id_typPojazdu;
        this.id_sklad = id_sklad;
    }

    public AutobusZapasowy() {
        super(null);
    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "autobus_zapasowy" +
                    "(id_sklad, id_typ_po)" +
                    " VALUES (?,?)");
            if(id_sklad == null)
                pst.setNull(1, java.sql.Types.INTEGER);
            else
                pst.setInt(1, id_sklad);
            pst.setInt(2, id_typ_po);
            return pst.toString();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUpdateQuery(Connection c) {
        try{
            PreparedStatement pst = c.prepareStatement("UPDATE " + schema + "autobus_zapasowy SET id_sklad = ?, id_typ_po = ? WHERE id_autobus = ?");
//            if(id_sklad == null)
//                pst.setNull(1, java.sql.Types.INTEGER);
//            else
            pst.setInt(1, id_sklad);
            pst.setInt(2, id_typ_po);
            pst.setInt(3, id);
            return pst.toString();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getDeleteQuery(Connection c) {
        return null;
    }

    public int getMasa(PostgresSQLConnection connection) {
        return 0;
    }

    public void setId_sklad(Integer id_sklad) {
        this.id_sklad = id_sklad;
    }

    @Override
    public String getNazwa(PostgresSQLConnection connection) {
        if(nazwa != null)
            return nazwa;
        try {
            PreparedStatement pst = connection.getConnection().prepareStatement("SELECT nazwa_wagonu FROM " + schema + "typ_pojazdu_osobowego" +
                    " JOIN " + schema + "autobus_zapasowy ON " + schema + "typ_pojazdu_osobowego.id_typ_po = " + schema + "autobus_zapasowy.id_typ_po"+
                    " WHERE id_autobus = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            nazwa = rs.getString(1);
            return nazwa;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void build(int id, int id_typPojazdu, int id_pojazd) {
        this.id = id;
        this.id_typ_po = id_typPojazdu;
        this.id_sklad = id_pojazd;
    }

    @Override
    public Double getMaxUciag(PostgresSQLConnection connection) {
        return 0.;
    }
}
