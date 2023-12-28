package Entities;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
