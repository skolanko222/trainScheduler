package Entities;

public class TypPojazdu extends Entity{

    private String nazwa;
    private typy typ;
    private double masa;
    private int iloscMiejsc;
    private boolean czyRestauracyjny;
    private boolean czyWC;

    public TypPojazdu(String nazwa, typy typ, double masa, int iloscMiejsc, boolean czyRestauracyjny, boolean czyWC) {
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

    public typy isTyp() {
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
    public String getInsertQuery() {
        return "INSERT INTO " + schema + "typ_pojazdu_osobowego" +
                "(typPojazdu, nazwa_wagonu, czy_restauracyjny, czy_wc, waga, ilosc_miejsc)" +
                " VALUES ('" + typ + "', '" + nazwa + "', " + czyRestauracyjny + ", " + czyWC + ", " + masa + ", " + iloscMiejsc + ");";
    }
    @Override
    public String getUpdateQuery() {
        return "UPDATE typ_pojazdu_osobowego SET nazwa = " + nazwa + ", typ = " + typ + ", masa = " + masa + ", ilosc_miejsc = " + iloscMiejsc + ", czy_restauracyjny = " + czyRestauracyjny + ", czy_wc = " + czyWC + " WHERE id_typu_pojazdu = " + id + ";";
    }
    @Override
    public String getDeleteQuery() {
        return "DELETE FROM typ_pojazdu_osobowego WHERE id_typu_pojazdu = " + id + ";";
    }

}
