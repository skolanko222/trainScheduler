package Entities;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Wagon extends Entity{
    int id_typ_po;
    Integer id_sklad;
    public Wagon(int id_typPojazdu, Integer id_sklad) {
        super(null);
        this.id_typ_po = id_typPojazdu;
        this.id_sklad = id_sklad;

    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "wagon" +
                    "(id_sklad,id_typ_po)" +
                    " VALUES (?,?)");
            if(id_sklad == null)
                pst.setNull(1, java.sql.Types.INTEGER);
            else
                pst.setInt(1, id_sklad);
            pst.setInt(2, id_typ_po);
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
