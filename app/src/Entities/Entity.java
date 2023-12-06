package Entities;

abstract public class Entity {
    protected Integer id;
    protected String schema = "train.";

    public Entity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    abstract public String getInsertQuery();
    abstract public String getUpdateQuery();
    abstract public String getDeleteQuery();

    @Override
    public String toString() {
        return  Integer.toString(id);
    }

}
