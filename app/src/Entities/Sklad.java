package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Sklad extends Entity{

    private boolean czy_przypisany_do_kursu;
    public Sklad(Integer id, boolean czy_przypisany_do_kursu) {
        super(id);
        this.czy_przypisany_do_kursu = czy_przypisany_do_kursu;
    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "sklad" +
                    "(id_sklad, czy_przypisany_do_kursu)" +
                    " VALUES (?, ?)");
            pst.setInt(1, id);
            pst.setBoolean(2, czy_przypisany_do_kursu);
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
            PreparedStatement pst = c.prepareStatement("UPDATE " + schema + "sklad" +
                    " SET czy_przypisany_do_kursu = ?" +
                    " WHERE id_sklad = ?");
            pst.setBoolean(1, czy_przypisany_do_kursu);
            pst.setInt(2, id);
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

    public int getIdSkladu(Connection c) {
        return super.getId();
    }

    public static Sklad[] getFreeSklad(PostgresSQLConnection c) {
        try {
            ArrayList<Sklad> sklad = new ArrayList<>();

            ResultSet rs = c.executeFunction("train.sklady_bez_kursu()");
            while(rs.next()){
                sklad.add(new Sklad(rs.getInt("id_sklad"), rs.getBoolean("czy_przypisany_do_kursu")));
            }
            return sklad.toArray(new Sklad[0]);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Sklad{" +
                "czy_przypisany_do_kursu=" + czy_przypisany_do_kursu +
                ", id=" + id +
                '}';
    }
}
