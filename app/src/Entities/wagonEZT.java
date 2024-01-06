package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class wagonEZT extends Entity implements PojazdInterface{

    int id_typPojazdu;
    Integer id_sklad;
    public wagonEZT(Integer primary,int id_typPojazdu, Integer id_sklad) {
        super(primary);
        this.id_typPojazdu = id_typPojazdu;
        this.id_sklad = id_sklad;

    }

    public wagonEZT() {
        super(null);
    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "wagon_zt" +
                    "(id_sklad, id_typ_po)" +
                    " VALUES (?,?)");
            if(id_sklad == null)
                pst.setNull(1, java.sql.Types.INTEGER);
            else
                pst.setInt(1, id_sklad);
            pst.setInt(2, id_typPojazdu);
            return pst.toString();

        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    public void setId_sklad(Integer id_sklad) {
        this.id_sklad = id_sklad;
    }

    @Override
    public String getUpdateQuery(Connection c) {
        try{
            PreparedStatement pst = c.prepareStatement("UPDATE " + schema + "wagon_zt SET " +
                    "id_sklad = ?, id_typ_po = ?" + " WHERE id_wagon_zt = ?");
            if(id_sklad == null)
                pst.setNull(1, java.sql.Types.INTEGER);
            else
                pst.setInt(1, id_sklad);
            pst.setInt(2, id_typPojazdu);
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

    @Override
    public int getMasa(PostgresSQLConnection connection) {
        try {
            PreparedStatement pst = connection.getConnection().prepareStatement("SELECT waga FROM " + schema + "wagon_zt" +
                    " JOIN " + schema + "typ_pojazdu_osobowego ON " + schema + "wagon_zt.id_typ_po = " + schema + "typ_pojazdu_osobowego.id_typ_po" +
                    " WHERE wagon_zt.id_typ_po = ?;");
            pst.setInt(1, id_typPojazdu);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e){
            e.printStackTrace();

        }
        return 0;
    }

    @Override
    public String getNazwa(PostgresSQLConnection connection) {
        try {
            PreparedStatement pst = connection.getConnection().prepareStatement("SELECT nazwa_wagonu FROM " + schema + "typ_pojazdu_osobowego" +
                    " JOIN " + schema + "wagon_zt ON " + schema + "typ_pojazdu_osobowego.id_typ_po = " + schema + "wagon_zt.id_typ_po " +
                    " WHERE id_wagon_zt = ?");

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getString(1);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getMaxUciag(PostgresSQLConnection connection) {
        return 0;
    }

    @Override
    public void build(int id, int id_typPojazdu, int id_pojazd) {
        this.id = id;
        this.id_typPojazdu = id_typPojazdu;
        this.id_sklad = id_pojazd;
    }

}
