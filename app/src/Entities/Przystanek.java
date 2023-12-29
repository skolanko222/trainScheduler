package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Przystanek extends Entity{

    private LocalDate data_przyjazdu;
    private LocalDate data_odjazdu;
    private LocalTime czas_przyjazdu;
    private LocalTime czas_odjazdu;
    private int id_stacja;
    private int nr_kolejnosc;



    public Przystanek(Integer id, LocalDate data_przyjazdu, LocalDate data_odjazdu, LocalTime czas_przyjazdu, LocalTime czas_odjazdu, int id_stacja, int nr_kolejnosc) {
        super(id);
        this.data_przyjazdu = data_przyjazdu;
        this.data_odjazdu = data_odjazdu;
        this.czas_przyjazdu = czas_przyjazdu;
        this.czas_odjazdu = czas_odjazdu;
        this.id_stacja = id_stacja;
        this.nr_kolejnosc = nr_kolejnosc;

    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "przystanek" +
                    "(data_przyjazdu, data_odjazdu,godz_przyjazdu,god_odjazdu, id_stacja, nr_kolejnosc)" +
                    " VALUES (?, ?, ?, ?, ? ,?)");
            pst.setDate(1, java.sql.Date.valueOf(data_przyjazdu));
            pst.setDate(2, java.sql.Date.valueOf(data_odjazdu));
            pst.setTime(3, java.sql.Time.valueOf(czas_przyjazdu));
            pst.setTime(4, java.sql.Time.valueOf(czas_odjazdu));
            pst.setInt(5, id_stacja);
            pst.setInt(6, nr_kolejnosc);

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
