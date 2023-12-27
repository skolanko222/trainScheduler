package Entities;

import java.sql.Connection;

abstract public class Entity {
    protected Integer id;
    protected String schema = "train.";

    public Entity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    abstract public String getInsertQuery(Connection c);
    abstract public String getUpdateQuery(Connection c);
    abstract public String getDeleteQuery(Connection c);

    @Override
    public String toString() {
        return  Integer.toString(id);
    }

}
