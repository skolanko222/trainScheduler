package Entities;

import PostgresSQLConnection.PostgresSQLConnection;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Przystanek extends Entity{

    private LocalDate data_przyjazdu;
    private LocalDate data_odjazdu;
    private Time czas_przyjazdu;
    private Time czas_odjazdu;
    private int id_stacja;
    private int nr_kolejnosc;
    private Integer id_kursu;
    private String nazwa = null;



    public Przystanek(Integer id, LocalDate data_przyjazdu, LocalDate data_odjazdu, Time czas_przyjazdu, Time czas_odjazdu, int id_stacja, int nr_kolejnosc, Integer id_kursu) {
        super(id);
        this.data_przyjazdu = data_przyjazdu;
        this.data_odjazdu = data_odjazdu;
        this.czas_przyjazdu = czas_przyjazdu;
        this.czas_odjazdu = czas_odjazdu;
        this.id_stacja = id_stacja;
        this.nr_kolejnosc = nr_kolejnosc;
        this.id_kursu = id_kursu;

    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "przystanek" +
                    "(data_przyjazdu, data_odjazdu,godz_przyjazdu,godz_odjazdu, id_stacja, nr_kolejnosc,id_kurs)" +
                    " VALUES (?, ?, ?, ?, ? ,?,?)");
            pst.setDate(1, java.sql.Date.valueOf(data_przyjazdu));
            pst.setDate(2, java.sql.Date.valueOf(data_odjazdu));
            pst.setTime(3, java.sql.Time.valueOf(Time.valueOf(czas_przyjazdu.toString()).toLocalTime()));
            pst.setTime(4, java.sql.Time.valueOf(Time.valueOf(czas_odjazdu.toString()).toLocalTime()));
            pst.setInt(5, id_stacja);
            pst.setInt(6, nr_kolejnosc);
            if(id_kursu == null)
                throw new SQLException("id_kursu nie może być nullem");
            else
                pst.setInt(7, id_kursu);

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

    public Object getNr_kolejnosc() {
        return nr_kolejnosc;
    }

    public String getNazwa(PostgresSQLConnection connection) {
        String query = "SELECT nazwa FROM " + schema + "stacja WHERE id_stacja = " + id_stacja;
        if(nazwa != null)
            return nazwa;
        try {
            ResultSet rs = connection.executeCommand(query);
            rs.next();
            nazwa = rs.getString(1);
            return nazwa;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    public Time getCzas_przyjazdu() {
        return czas_przyjazdu;
    }

    public Time getCzas_odjazdu() {
        return czas_odjazdu;
    }

    public void setNr_kolejnosc(int nr_kolejnosc) {
        this.nr_kolejnosc = nr_kolejnosc;
    }

    public void setId_kursu(int id) {
        this.id_kursu = id;
    }

    @Override
    public String toString() {
        return "Przystanek{" +
                "data_przyjazdu=" + data_przyjazdu +
                ", id_stacja=" + id_stacja +
                ", nr_kolejnosc=" + nr_kolejnosc +
                '}';
    }
}
