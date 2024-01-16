package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Lokomotywa extends Entity implements PojazdInterface{

    private int id_lokomotywa;
    private Integer id_sklad;
    private String name = null;
    private Double maxUciag = null;
    public Lokomotywa(Integer id, int id_lokomotywa, Integer id_sklad) {
        super(id);
        this.id_lokomotywa = id_lokomotywa;
        this.id_sklad = id_sklad;
    }

    public Lokomotywa() {
        super(null);
    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "lokomotywa" +
                    "(id_lokomotywa, id_sklad)" +
                    " VALUES (?,?)");
            if(id_sklad == null) {
                pst.setNull(2, java.sql.Types.INTEGER);
            }
            else {
                pst.setInt(2, id_sklad);
            }
            pst.setInt(1, id_lokomotywa);
            return pst.toString();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void setId_sklad(Integer id_sklad) {
        this.id_sklad = id_sklad;
    }

    @Override
    public String getUpdateQuery(Connection c) {
        try{
            PreparedStatement pst = c.prepareStatement("UPDATE " + schema + "lokomotywa SET " +
                    "id_sklad = ?, id_lokomotywa = ?" + " WHERE id_lokomotywa_pk = ?");
            if(id_sklad == null)
                pst.setNull(1, java.sql.Types.INTEGER);
            else
                pst.setInt(1, id_sklad);
            pst.setInt(2, id_lokomotywa);
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

    public int getWaga(PostgresSQLConnection connection) {

        return 0;
    }
    @Override
    public int getMasa(PostgresSQLConnection connection) {
        return 0;
    }
    @Override
    public Double getMaxUciag(PostgresSQLConnection connection) {
        if(maxUciag != null)
            return maxUciag;
        try {
            PreparedStatement pst = connection.getConnection().prepareStatement("SELECT max_uciag FROM " + schema + "typ_lokomotywa" +
                    " JOIN " + schema + "lokomotywa ON " + schema + "typ_lokomotywa.id_lokomotywa = " + schema + "lokomotywa.id_lokomotywa"+
                    " WHERE id_lokomotywa_pk = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            maxUciag = rs.getInt(1) + 0.;
            return maxUciag;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0.;

    }
    @Override
    public String getNazwa(PostgresSQLConnection connection) {
        if (name != null)
            return name;
        try {
            PreparedStatement pst = connection.getConnection().prepareStatement("SELECT nazwa FROM " + schema + "lokomotywa" +
                    " JOIN " + schema + "typ_lokomotywa ON " + schema + "lokomotywa.id_lokomotywa = " + schema + "typ_lokomotywa.id_lokomotywa" +
                    " WHERE id_lokomotywa_pk = ?;");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            name = rs.getString(1);
            return name;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void build(int id, int id_typPojazdu, int id_pojazd) {
        this.id = id;
        this.id_lokomotywa = id_typPojazdu;
        this.id_sklad = id_pojazd;
    }


}
