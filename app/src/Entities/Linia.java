package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Linia extends Entity{

    private String nazwa;
    public Linia(Integer id, String nazwa) {
        super(id);
        this.nazwa = nazwa;
    }

    public static String[] getNazwyLinii(PostgresSQLConnection connection) {
        try {
            String[] nazwy = new String[0];
            PreparedStatement pst = connection.getConnection().prepareStatement("SELECT nazwa FROM " + schema + "linia");
            var rs = pst.executeQuery();
            while (rs.next()) {
                String[] temp = new String[nazwy.length + 1];
                System.arraycopy(nazwy, 0, temp, 0, nazwy.length);
                temp[nazwy.length] = rs.getString("nazwa");
                nazwy = temp;
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
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "linia" +
                    "(nazwa)" +
                    " VALUES (?)");
            pst.setString(1, nazwa);

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
