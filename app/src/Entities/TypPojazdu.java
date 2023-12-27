package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class TypPojazdu extends Entity{

    private String nazwa;
    private String typ;
    private double masa;
    private int iloscMiejsc;
    private boolean czyRestauracyjny;
    private boolean czyWC;

    public TypPojazdu(String nazwa, String typ, double masa, int iloscMiejsc, boolean czyRestauracyjny, boolean czyWC) {
        super(null);
        this.nazwa = nazwa;
        this.typ = typ;
        this.masa = masa;
        this.iloscMiejsc = iloscMiejsc;
        this.czyRestauracyjny = czyRestauracyjny;
        this.czyWC = czyWC;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String isTyp() {
        return typ;
    }

    public double getMasa() {
        return masa;
    }

    public int getIloscMiejsc() {
        return iloscMiejsc;
    }

    public boolean isRestauracyjny() {
        return czyRestauracyjny;
    }

    public boolean isWC() {
        return czyWC;
    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "typ_pojazdu_osobowego" +
                    "(typPojazdu, nazwa_wagonu, czy_restauracyjny, czy_wc, waga, ilosc_miejsc)" +
                    " VALUES (?,?,?,?,?,?)");
            pst.setString(1, typ.toString());
            pst.setString(2, nazwa);
            pst.setBoolean(3, czyRestauracyjny);
            pst.setBoolean(4, czyWC);
            pst.setDouble(5, masa);
            pst.setInt(6, iloscMiejsc);
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
            PreparedStatement pst = c.prepareStatement("UPDATE " + schema + "typ_pojazdu_osobowego SET " +
                    "nazwa_wagonu = ?, czy_restauracyjny = ?, czy_wc = ?, waga = ?, ilosc_miejsc = ?" +
                    "WHERE id_typu_pojazdu = ?");
            pst.setString(1, nazwa);
            pst.setBoolean(2, czyRestauracyjny);
            pst.setBoolean(3, czyWC);
            pst.setDouble(4, masa);
            pst.setInt(5, iloscMiejsc);
            pst.setInt(6, id);
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
        try{
            PreparedStatement pst = c.prepareStatement("DELETE FROM " + schema + "typ_pojazdu_osobowego WHERE id_typu_pojazdu = ?");
            pst.setInt(1, id);
            return pst.toString();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    static public String[] getTypEnum(PostgresSQLConnection c) {
        String [] fetchTypy = null;
        try{
            ResultSet temp = c.executeCommand("SELECT unnest(enum_range(NULL::typ));");
            fetchTypy = new String[temp.getRow()];
            for(int i = 0; temp.next(); i++) {
                fetchTypy = Arrays.copyOf(fetchTypy, fetchTypy.length + 1);
                fetchTypy[i] = temp.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return fetchTypy;
    }

}
