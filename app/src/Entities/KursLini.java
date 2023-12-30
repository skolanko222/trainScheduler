package Entities;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KursLini extends Entity{
    private Integer id_pojazd;
    private int id_linia;
    public KursLini(Integer id, Integer id_pojazd, int id_linia) {
        super(id);
        this.id_pojazd = id_pojazd;
        this.id_linia = id_linia;

    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "kurs_lini" +
                    "(id_pojazd, id_linia)" +
                    " VALUES (?,?)");
            if(id_pojazd == null)
                pst.setNull(1, java.sql.Types.INTEGER);
            else
                pst.setInt(1, id_pojazd);
            pst.setInt(2, id_linia);

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
