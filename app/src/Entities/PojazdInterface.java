package Entities;
import PostgresSQLConnection.PostgresSQLConnection;

interface PojazdInterface {
    public int getMasa(PostgresSQLConnection connection);
    public int getMaxUciag(PostgresSQLConnection connection);
    public String getNazwa(PostgresSQLConnection connection);
    public void build(int id, int id_typPojazdu, int id_pojazd);

}
