package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class wagonEZT extends Entity{

    int id_typPojazdu;
    Integer id_zt;
    public wagonEZT(int id_typPojazdu, Integer id_zt) {
        super(null);
        this.id_typPojazdu = id_typPojazdu;
        this.id_zt = id_zt;

    }
    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "wagon_zt" +
                    "(id_zt, id_typ_po)" +
                    " VALUES (?,?)");
            if(id_zt == null)
                pst.setNull(1, java.sql.Types.INTEGER);
            else
                pst.setInt(1, id_zt);
            pst.setInt(2, id_typPojazdu);
            return pst.toString();

        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    @Override
    public String getUpdateQuery(Connection c) {
        //TODO
        try{
            PreparedStatement pst = c.prepareStatement("UPDATE " + schema + "wagon_zt SET " +
                    "id_typ_pojazdu = ?" +
                    "WHERE id_wagonu_zt = ?");
            return pst.toString();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        // return "UPDATE typ_pojazdu_osobowego SET nazwa = " + nazwa + ", typ = " + typ + ", masa = " + masa + ", ilosc_miejsc = " + iloscMiejsc + ", czy_restauracyjny = " + czyRestauracyjny + ", czy_wc = " + czyWC + " WHERE id_typu_pojazdu = " + id + ";";
        return null;
    }

    @Override
    public String getDeleteQuery(Connection c) {
        return null;
    }
}
