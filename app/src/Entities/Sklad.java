package Entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sklad extends Entity{

    private boolean czy_przypisany_do_kursu;
    public Sklad(Integer id, boolean czy_przypisany_do_kursu) {
        super(id);
        this.czy_przypisany_do_kursu = czy_przypisany_do_kursu;
    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "sklad" +
                    "(id_sklad, czy_przypisany_do_kursu)" +
                    " VALUES (?, ?)");
            pst.setInt(1, id);
            pst.setBoolean(2, czy_przypisany_do_kursu);
            return pst.toString();
        }
        catch (SQLException e){
            e.printStackTrace();
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

    public int getIdSkladu(Connection c) {
        return super.getId();
    }
}
