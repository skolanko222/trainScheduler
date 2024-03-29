package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KursLini extends Entity{
    private Integer id_sklad;
    private int id_linia;
    public KursLini(Integer id, Integer id_sklad, int id_linia) {
        super(id);
        this.id_sklad = id_sklad;
        this.id_linia = id_linia;

    }
    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst;

            if(id == null){
                pst = c.prepareStatement("INSERT INTO " + schema + "kurs_lini" +
                        "(id_sklad, id_linia)" +
                        " VALUES (?,?)");
                if(id_sklad == null)
                    pst.setNull(1, java.sql.Types.INTEGER);
                else
                    pst.setInt(1, id_sklad);
                pst.setInt(2, id_linia);

            }
            else{
                pst = c.prepareStatement("INSERT INTO " + schema + "kurs_lini" +
                        "(id_kurs,id_sklad, id_linia)" +
                        " VALUES (?,?,?)");

                pst.setInt(1, id);
                if(id_sklad == null)
                    pst.setNull(2, java.sql.Types.INTEGER);
                else
                    pst.setInt(2, id_sklad);
                pst.setInt(3, id_linia);

            }
            return pst.toString();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    @Override
    public String getUpdateQuery(Connection c) {
        try{
            PreparedStatement pst = c.prepareStatement("UPDATE " + schema + "kurs_lini" +
                    " SET id_sklad = ?" +
                    " WHERE id_kurs = ?");
            if(id_sklad == null)
                throw new SQLException("id_sklad is null while updating kurs_lini");
            else
                pst.setInt(1, id_sklad);
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

    public static KursLini[] getFreeKurs(PostgresSQLConnection c) {
        try {
            ArrayList<KursLini> sklad = new ArrayList<>();

            ResultSet rs = c.executeFunction("train.kursy_bez_skladu()");
            while(rs.next()){
                sklad.add(new KursLini(rs.getInt("id_kurs"), rs.getInt("id_sklad"), rs.getInt("id_linia")));
            }
            return sklad.toArray(new KursLini[0]);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
