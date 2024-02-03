package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Stacja extends Entity{
    private String nazwa;
    private String miejscowosc;
    private String ulica;
    private Integer numer;

    public Stacja(Integer id, String nazwa, String miejscowosc, String ulica, Integer numer) {
        super(id);
        this.nazwa = nazwa;
        this.miejscowosc = miejscowosc;
        this.ulica = ulica;
        this.numer = numer;
    }

    public static String[] getNazwyStacji(PostgresSQLConnection connection) {
        try {
            String[] nazwy = new String[0];
            String query = "SELECT nazwa FROM " + schema + "stacja";
            ResultSet rs = connection.executeCommand(query);
            while(rs.next()) {
                nazwy = Arrays.copyOf(nazwy, nazwy.length + 1);
                nazwy[nazwy.length - 1] = rs.getString(1);
            }
            return nazwy;
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;

    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "stacja" +
                    "(nazwa, miejscowosc, ulica, numer)" +
                    " VALUES (?,?,?,?)");
            pst.setString(1, nazwa);
            pst.setString(2, miejscowosc);
            if(ulica == null)
                pst.setNull(3, java.sql.Types.VARCHAR);
            else
                pst.setString(3, ulica);
            if(numer == null)
                pst.setNull(4, java.sql.Types.INTEGER);
            else
                pst.setInt(4, numer);

            return pst.toString();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }


    @Override
    public String getUpdateQuery(Connection c) {
        return null;
    }

    @Override
    public String getDeleteQuery(Connection c) {
        return null;
    }
}
