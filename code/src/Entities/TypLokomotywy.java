package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class TypLokomotywy extends Entity{
    private String nazwa;
    private Integer moc;
    private Integer max_pre;
    private Integer max_uciag;

    public TypLokomotywy(Integer id, String nazwa, Integer moc, Integer max_pre, Integer max_uciag) {
        super(null);
        this.nazwa = nazwa;
        this.moc = moc;
        this.max_pre = max_pre;
        this.max_uciag = max_uciag;
    }

    public static String[] getNazwyTypow(PostgresSQLConnection connection) {
        try {
            String[] nazwy = new String[0];
            String query = "SELECT nazwa FROM " + schema + "typ_lokomotywa";
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
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "typ_lokomotywa" +
                    "(nazwa, moc, max_pre, max_uciag)" +
                    " VALUES (?,?,?,?)");
            pst.setString(1, nazwa);
            pst.setInt(2, moc);
            pst.setInt(3, max_pre);
            pst.setInt(4, max_uciag);
            return pst.toString();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }
    static public int getIloscEgzemplarzy(String nazwa, PostgresSQLConnection connection) {
        try {
            String query = "SELECT COUNT(*) FROM " + schema + "lokomotywa WHERE id_lokomotywa = (SELECT id_lokomotywa FROM " + schema + "typ_lokomotywa WHERE nazwa = '" + nazwa + "');";
            ResultSet rs = connection.executeCommand(query);
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return 0;
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
