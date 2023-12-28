package Entities;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AutobusZapasowy extends Entity{
    int id_typ_po;
    Integer id_pojazd;
    public AutobusZapasowy(Integer id_typPojazdu, Integer id_pojazd) {

        super(null);

        this.id_typ_po = id_typPojazdu;
        this.id_pojazd = id_pojazd;
    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "autobus_zapasowy" +
                    "(id_pojazd, id_typ_po)" +
                    " VALUES (?,?)");
            if(id_pojazd == null)
                pst.setNull(1, java.sql.Types.INTEGER);
            else
                pst.setInt(1, id_pojazd);
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
