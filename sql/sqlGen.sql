CREATE TABLE train.linia (
                id_linia SERIAL NOT NULL,
                nazwa VARCHAR NOT NULL,
                CONSTRAINT linia_pk PRIMARY KEY (id_linia)
);


CREATE TABLE train.stacja (
                id_stacja SERIAL NOT NULL,
                nazwa VARCHAR NOT NULL,
                miejscowosc VARCHAR NOT NULL,
                adres VARCHAR,
                numer VARCHAR,
                CONSTRAINT id_stacja PRIMARY KEY (id_stacja)
);


CREATE TABLE train.sklad (
                id_sklad SERIAL NOT NULL,
                czy_przypisany_do_kursu BOOLEAN DEFAULT false NOT NULL,
                CONSTRAINT id_sklad PRIMARY KEY (id_sklad)
);


CREATE TABLE train.kurs_lini (
                id_kurs SERIAL NOT NULL,
                id_sklad INTEGER,
                id_linia INTEGER NOT NULL,
                CONSTRAINT kurs_lini_pk PRIMARY KEY (id_kurs)
);


CREATE TABLE train.przystanek (
                id_przystanek SERIAL NOT NULL,
                id_kurs INTEGER NOT NULL,
                id_stacja INTEGER NOT NULL,
                data_przyjazdu DATE NOT NULL,
                data_odjazdu DATE NOT NULL,
                godz_przyjazdu TIME NOT NULL,
                godz_odjazdu TIME NOT NULL,
                nr_kolejnosc INTEGER NOT NULL,
                CONSTRAINT id_przystanek PRIMARY KEY (id_przystanek)
);


CREATE TABLE train.typ_lokomotywa (
                id_lokomotywa SERIAL NOT NULL,
                nazwa VARCHAR NOT NULL,
                moc INTEGER NOT NULL,
                max_pre INTEGER NOT NULL,
                max_uciag INTEGER NOT NULL,
                CONSTRAINT id_lokomotywa PRIMARY KEY (id_lokomotywa)
);


CREATE TABLE train.lokomotywa (
                id SERIAL NOT NULL,
                id_lokomotywa SERIAL NOT NULL,
                id_sklad SERIAL,
                CONSTRAINT lokomotywa_pk PRIMARY KEY (id)
);

CREATE TYPE typ AS ENUM ('EZT', 'wagon', 'autobus', 'lokomotywa');
CREATE TABLE train.typ_pojazdu_osobowego (
                id_typ_po SERIAL NOT NULL,
                nazwa_wagonu VARCHAR NOT NULL,
                czy_restauracyjny BOOLEAN NOT NULL,
                czy_wc BOOLEAN NOT NULL,
                waga DOUBLE PRECISION NOT NULL,
                ilosc_miejsc INTEGER NOT NULL,
                CONSTRAINT id_typ_wagonu PRIMARY KEY (id_typ_po)
);


CREATE TABLE train.autobus_zapasowy (
                id_autobus SERIAL NOT NULL,
                id_sklad SERIAL NOT NULL,
                id_typ_po SERIAL NOT NULL,
                CONSTRAINT id_autobus PRIMARY KEY (id_autobus)
);


CREATE TABLE train.wagon_zt (
                id_wagon_zt SERIAL NOT NULL,
                id_sklad SERIAL,
                id_typ_po SERIAL NOT NULL,
                CONSTRAINT id_wagon_zt PRIMARY KEY (id_wagon_zt)
);


CREATE TABLE train.wagon (
                id_wagon SERIAL NOT NULL,
                id_sklad SERIAL,
                id_typ_po SERIAL NOT NULL,
                CONSTRAINT id_wagon PRIMARY KEY (id_wagon)
);


ALTER TABLE train.kurs_lini ADD CONSTRAINT linia_kurs_lini_fk
FOREIGN KEY (id_linia)
REFERENCES train.linia (id_linia)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.przystanek ADD CONSTRAINT stacja_przystanek_fk
FOREIGN KEY (id_stacja)
REFERENCES train.stacja (id_stacja)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.wagon ADD CONSTRAINT pojazd_szynowy_wagon_fk
FOREIGN KEY (id_sklad)
REFERENCES train.sklad (id_sklad)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.lokomotywa ADD CONSTRAINT sklad_lokomotywa_fk
FOREIGN KEY (id_sklad)
REFERENCES train.sklad (id_sklad)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.wagon_zt ADD CONSTRAINT sklad_wagon_zt_fk
FOREIGN KEY (id_sklad)
REFERENCES train.sklad (id_sklad)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.autobus_zapasowy ADD CONSTRAINT sklad_autobus_zapasowy_fk
FOREIGN KEY (id_sklad)
REFERENCES train.sklad (id_sklad)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.kurs_lini ADD CONSTRAINT sklad_kurs_lini_fk
FOREIGN KEY (id_sklad)
REFERENCES train.sklad (id_sklad)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.przystanek ADD CONSTRAINT kurs_lini_przystanek_fk
FOREIGN KEY (id_kurs)
REFERENCES train.kurs_lini (id_kurs)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.lokomotywa ADD CONSTRAINT typ_lokomotywa_lokomotywa_fk
FOREIGN KEY (id_lokomotywa)
REFERENCES train.typ_lokomotywa (id_lokomotywa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.wagon ADD CONSTRAINT typ_wagonu_wagon_fk
FOREIGN KEY (id_typ_po)
REFERENCES train.typ_pojazdu_osobowego (id_typ_po)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.wagon_zt ADD CONSTRAINT typ_wagonu_wagon_zt_fk
FOREIGN KEY (id_typ_po)
REFERENCES train.typ_pojazdu_osobowego (id_typ_po)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.autobus_zapasowy ADD CONSTRAINT typ_pojazdu_osobowego_autobus_zapasowy_fk
FOREIGN KEY (id_typ_po)
REFERENCES train.typ_pojazdu_osobowego (id_typ_po)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

CREATE OR REPLACE FUNCTION train.waga_wagonu(id_wagon INTEGER)
    RETURNS DOUBLE PRECISION AS
$$
DECLARE
    waga DOUBLE PRECISION;
BEGIN
    SELECT waga INTO waga FROM train.typ_pojazdu_osobowego WHERE id_typ_po IN (SELECT id_typ_po FROM train.wagon WHERE id_wagon = id_wagon);
    RETURN waga;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION train.sklady_bez_kursu()
RETURNS TABLE(id_sklad INTEGER, czy_przypisany_do_kursu BOOLEAN) AS
$$
BEGIN
RETURN QUERY SELECT sk.id_sklad, czy_przypisany_do_kursu FROM train.sklad sk WHERE sk.czy_przypisany_do_kursu = false;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION train.kursy_bez_skladu()
    RETURNS TABLE(id_kurs INTEGER, id_sklad INTEGER, id_linia INTEGER) AS
$$
BEGIN
    RETURN QUERY SELECT kl.id_kurs, kl.id_sklad, kl.id_linia FROM train.kurs_lini kl WHERE kl.id_sklad IS NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION train.get_tables(your_schema_name VARCHAR)
    RETURNS TABLE(table_name TEXT) AS
$$
BEGIN
    RETURN QUERY SELECT it.table_name::TEXT FROM information_schema.tables it WHERE it.table_schema = your_schema_name AND it.table_type = 'BASE TABLE';
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE VIEW train.lokomotywa_dane AS
SELECT l.id_lokomotywa_pk AS numer_egzemplarza,
        tl.nazwa AS nazwa_lokomotywy,
        tl.moc AS moc_lokomotywy,
        tl.max_pre AS maksymalna_predkosc,
        tl.max_uciag AS maksymalny_uciag FROM train.lokomotywa l
        JOIN train.typ_lokomotywa tl ON l.id_lokomotywa = tl.id_lokomotywa ORDER BY tl.nazwa;

-- 
CREATE OR REPLACE FUNCTION train.pojazdy_kursu_detailed(id_kurs_param INTEGER)
    RETURNS TABLE(liczba_porzadkowa BIGINT, nazwa varchar, typ_pojazdu typ, czy_restauracyjny BOOLEAN, czy_wc BOOLEAN, ilosc_miejsc INTEGER) AS
$$
BEGIN
    RETURN QUERY
        SELECT row_number() OVER (), tl.nazwa, NULL, NULL, NULL
        FROM train.sklad s
                 JOIN train.lokomotywa l ON s.id_sklad = l.id_sklad
                 JOIN train.typ_lokomotywa tl ON l.id_lokomotywa = tl.id_lokomotywa
                 JOIN train.kurs_lini kl ON s.id_sklad = kl.id_sklad
        WHERE kl.id_kurs = id_kurs_param
        UNION ALL
        SELECT row_number() OVER (), tp.nazwa_wagonu, tp.czy_restauracyjny, tp.czy_wc, tp.ilosc_miejsc
        FROM train.sklad s
                 JOIN train.wagon w ON s.id_sklad = w.id_sklad
                 JOIN train.typ_pojazdu_osobowego tp ON w.id_typ_po = tp.id_typ_po
                 JOIN train.kurs_lini kl ON s.id_sklad = kl.id_sklad
        WHERE kl.id_kurs = id_kurs_param
        UNION ALL
        SELECT row_number() OVER (), tp.nazwa_wagonu, tp.czy_restauracyjny, tp.czy_wc, tp.ilosc_miejsc
        FROM train.sklad s
                 JOIN train.wagon_zt wz ON s.id_sklad = wz.id_sklad
                 JOIN train.typ_pojazdu_osobowego tp ON wz.id_typ_po = tp.id_typ_po
                 JOIN train.kurs_lini kl ON s.id_sklad = kl.id_sklad
        WHERE kl.id_kurs = id_kurs_param
        UNION ALL
        SELECT row_number() OVER (), tp.nazwa_wagonu, tp.czy_restauracyjny, tp.czy_wc, tp.ilosc_miejsc
        FROM train.sklad s
                 JOIN train.autobus_zapasowy az ON s.id_sklad = az.id_sklad
                 JOIN train.typ_pojazdu_osobowego tp ON az.id_typ_po = tp.id_typ_po
                 JOIN train.kurs_lini kl ON s.id_sklad = kl.id_sklad
        WHERE kl.id_kurs = id_kurs_param;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION train.pojazdy_skladu_detailed(id_sklad_param INTEGER)
    RETURNS TABLE(liczba_porzadkowa BIGINT, nazwa varchar, typ_pojazdu typ, czy_restauracyjny BOOLEAN, czy_wc BOOLEAN, ilosc_miejsc INTEGER) AS
$$
BEGIN
    RETURN QUERY
        SELECT row_number() OVER (), tl.nazwa, "lokomotywa", NULL, NULL, NULL
        FROM train.sklad s
                 JOIN train.lokomotywa l ON s.id_sklad = l.id_sklad
                 JOIN train.typ_lokomotywa tl ON l.id_lokomotywa = tl.id_lokomotywa
        WHERE s.id_sklad = id_sklad_param
        UNION ALL
        SELECT row_number() OVER (), tp.nazwa_wagonu, tp.typpojazdu, tp.czy_restauracyjny, tp.czy_wc, tp.ilosc_miejsc
        FROM train.sklad s
                 JOIN train.wagon w ON s.id_sklad = w.id_sklad
                 JOIN train.typ_pojazdu_osobowego tp ON w.id_typ_po = tp.id_typ_po
        WHERE s.id_sklad = id_sklad_param
        UNION ALL
        SELECT row_number() OVER (), tp.nazwa_wagonu, tp.typpojazdu, tp.czy_restauracyjny, tp.czy_wc, tp.ilosc_miejsc
        FROM train.sklad s
                 JOIN train.wagon_zt wz ON s.id_sklad = wz.id_sklad
                 JOIN train.typ_pojazdu_osobowego tp ON wz.id_typ_po = tp.id_typ_po
        WHERE s.id_sklad = id_sklad_param
        UNION ALL
        SELECT row_number() OVER (), tp.nazwa_wagonu, tp.typpojazdu, tp.czy_restauracyjny, tp.czy_wc, tp.ilosc_miejsc
        FROM train.sklad s
                 JOIN train.autobus_zapasowy az ON s.id_sklad = az.id_sklad
                 JOIN train.typ_pojazdu_osobowego tp ON az.id_typ_po = tp.id_typ_po
        WHERE s.id_sklad = id_sklad_param;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE VIEW train.lokomotywa_ilosc AS
SELECT tl.id_lokomotywa AS id_typu_lokomotywy, tl.nazwa AS nazwa_lokomotywy, COUNT(l.id_lokomotywa_pk) AS ilosc_egzemplarzy 
    FROM train.lokomotywa l
        JOIN train.typ_lokomotywa tl ON l.id_lokomotywa = tl.id_lokomotywa 
            GROUP BY tl.id_lokomotywa, tl.nazwa;

CREATE OR REPLACE VIEW train.wagon_ilosc AS
SELECT tp.id_typ_po AS id_typu_wagonu, tp.nazwa_wagonu AS nazwa_wagonu, COUNT(w.id_wagon) AS ilosc_egzemplarzy 
    FROM train.wagon w
        JOIN train.typ_pojazdu_osobowego tp ON w.id_typ_po = tp.id_typ_po 
            GROUP BY tp.id_typ_po, tp.nazwa_wagonu;

CREATE OR REPLACE VIEW train.wagon_zt_ilosc AS
SELECT tp.id_typ_po AS id_typu_wagonu, tp.nazwa_wagonu AS nazwa_wagonu, COUNT(wz.id_wagon_zt) AS ilosc_egzemplarzy 
    FROM train.wagon_zt wz
        JOIN train.typ_pojazdu_osobowego tp ON wz.id_typ_po = tp.id_typ_po 
            GROUP BY tp.id_typ_po, tp.nazwa_wagonu;

CREATE OR REPLACE VIEW train.autobus_zapasowy_ilosc AS
SELECT tp.id_typ_po AS id_typu_wagonu, tp.nazwa_wagonu AS nazwa_wagonu, COUNT(az.id_autobus) AS ilosc_egzemplarzy 
    FROM train.autobus_zapasowy az
        JOIN train.typ_pojazdu_osobowego tp ON az.id_typ_po = tp.id_typ_po 
            GROUP BY tp.id_typ_po, tp.nazwa_wagonu;

CREATE OR REPLACE FUNCTION train.przystanki_kursu(id_kurs_param INTEGER)
    RETURNS TABLE(nazwa_stacji VARCHAR, data_przyjazdu DATE, data_odjazdu DATE, godz_przyjazdu TIME, godz_odjazdu TIME, nr_kolejnosci INTEGER) AS
$$
BEGIN
    RETURN QUERY SELECT s.nazwa, s.data_przyjazdu, s.data_odjazdu, s.godz_przyjazdu, s.godz_odjazdu, s.nr_kolejnosci
    FROM train.przystanek s
    JOIN train.kurs_lini kl ON s.id_kurs = kl.id_kurs
    WHERE kl.id_kurs = id_kurs_param
    ORDER BY s.nr_kolejnosci;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION train.update_sklad_czy_przypisany_do_kursu()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE train.sklad SET czy_przypisany_do_kursu = NOT czy_przypisany_do_kursu WHERE id_sklad = NEW.id_sklad;
    UPDATE train.sklad SET czy_przypisany_do_kursu = NOT czy_przypisany_do_kursu WHERE id_sklad = OLD.id_sklad;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER update_sklad_czy_przypisany_do_kursu
    AFTER UPDATE OF id_sklad ON train.kurs_lini
    FOR EACH ROW
    WHEN (OLD.id_sklad IS DISTINCT FROM NEW.id_sklad)
EXECUTE PROCEDURE train.update_sklad_czy_przypisany_do_kursu();