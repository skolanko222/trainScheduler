package Entities;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Linia extends Entity{

    private String nazwa;
    public Linia(Integer id, String nazwa) {
        super(id);
        this.nazwa = nazwa;
    }

    @Override
    public String getInsertQuery(Connection c) {
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO " + schema + "linia" +
                    "(nazwa)" +
                    " VALUES (?)");
            pst.setString(1, nazwa);

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
