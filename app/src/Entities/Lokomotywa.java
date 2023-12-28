package Entities;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Lokomotywa extends Entity{

    private int id_lokomotywa;
    private Integer id_sklad;
    public Lokomotywa(Integer id, int id_lokomotywa, Integer id_sklad) {
        super(id);
        this.id_lokomotywa = id_lokomotywa;
        this.id_sklad = id_sklad;
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
