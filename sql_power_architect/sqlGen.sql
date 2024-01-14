-- --v1
-- CREATE TABLE train.linia (
--                 id_linia SERIAL NOT NULL,
--                 nazwa VARCHAR NOT NULL,
--                 CONSTRAINT linia_pk PRIMARY KEY (id_linia)
-- );


-- CREATE TABLE train.stacja (
--                 id_stacja SERIAL NOT NULL,
--                 nazwa VARCHAR NOT NULL,
--                 CONSTRAINT id_stacja PRIMARY KEY (id_stacja)
-- );


-- CREATE TABLE train.przystanek (
--                 id_przystanek SERIAL NOT NULL,
--                 id_stacja SERIAL NOT NULL,
--                 data_przyjazdu DATE NOT NULL,
--                 data_odjazdu DATE NOT NULL,
--                 godz_przyjazdu TIME NOT NULL,
--                 godz_odjazdu TIME NOT NULL,
--                 nr_kolejnosc INTEGER NOT NULL,
--                 CONSTRAINT id_przystanek PRIMARY KEY (id_przystanek)
-- );


-- CREATE TABLE train.pojazd (
--                 id_pojazd SERIAL NOT NULL,
--                 CONSTRAINT id_pojazd PRIMARY KEY (id_pojazd)
-- );


-- CREATE TABLE train.sklad (
--                 id_sklad SERIAL NOT NULL,
--                 id_pojazd SERIAL,
--                 CONSTRAINT id_sklad PRIMARY KEY (id_sklad)
-- );


-- CREATE TABLE train.zespol_trakcyjny (
--                 id_zt SERIAL NOT NULL,
--                 id_sklad SERIAL,
--                 nazwa VARCHAR NOT NULL,
--                 CONSTRAINT id_zt PRIMARY KEY (id_zt)
-- );


-- CREATE TABLE train.kurs_lini (
--                 id_kurs SERIAL NOT NULL,
--                 id_pojazd SERIAL NOT NULL,
--                 id_linia SERIAL NOT NULL,
--                 id_przystanek SERIAL NOT NULL,
--                 CONSTRAINT kurs_lini_pk PRIMARY KEY (id_kurs)
-- );


-- CREATE TABLE train.lokomotywa (
--                 id_lokomotywa SERIAL NOT NULL,
--                 id_sklad SERIAL,
--                 nazwa VARCHAR NOT NULL,
--                 moc INTEGER NOT NULL,
--                 max_pre INTEGER NOT NULL,
--                 max_uciag INTEGER NOT NULL,
--                 CONSTRAINT id_lokomotywa PRIMARY KEY (id_lokomotywa)
-- );

-- CREATE TYPE typ AS ENUM ('EZT', 'wagon', 'autobus');
-- CREATE TABLE train.typ_pojazdu_osobowego (
--                 id_typ_po SERIAL NOT NULL,
-- 				typPojazdu typ NOT NULL,
--                 nazwa_wagonu VARCHAR NOT NULL,
--                 czy_restauracyjny BOOLEAN NOT NULL,
--                 czy_wc BOOLEAN NOT NULL,
--                 waga DOUBLE PRECISION NOT NULL,
--                 ilosc_miejsc INTEGER NOT NULL,
--                 CONSTRAINT id_typ_wagonu PRIMARY KEY (id_typ_po)
-- );


-- CREATE TABLE train.autobus_zapasowy (
--                 id_autobus SERIAL NOT NULL,
--                 id_pojazd SERIAL,
--                 id_typ_po SERIAL NOT NULL,
--                 CONSTRAINT id_autobus PRIMARY KEY (id_autobus)
-- );


-- CREATE TABLE train.wagon_zt (
--                 id_wagon_zt SERIAL NOT NULL,
--                 id_zt SERIAL,
--                 id_typ_po SERIAL NOT NULL,
--                 CONSTRAINT id_wagon_zt PRIMARY KEY (id_wagon_zt)
-- );


-- CREATE TABLE train.wagon (
--                 id_wagon SERIAL NOT NULL,
--                 id_sklad SERIAL,
--                 id_typ_po SERIAL NOT NULL,
--                 CONSTRAINT id_wagon PRIMARY KEY (id_wagon)
-- );


-- ALTER TABLE train.kurs_lini ADD CONSTRAINT linia_kurs_lini_fk
-- FOREIGN KEY (id_linia)
-- REFERENCES train.linia (id_linia)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.przystanek ADD CONSTRAINT stacja_przystanek_fk
-- FOREIGN KEY (id_stacja)
-- REFERENCES train.stacja (id_stacja)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.kurs_lini ADD CONSTRAINT przystanek_kurs_lini_fk
-- FOREIGN KEY (id_przystanek)
-- REFERENCES train.przystanek (id_przystanek)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.kurs_lini ADD CONSTRAINT pojazd_kurs_lini_fk
-- FOREIGN KEY (id_pojazd)
-- REFERENCES train.pojazd (id_pojazd)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.autobus_zapasowy ADD CONSTRAINT pojazd_autobus_zapasowy_fk
-- FOREIGN KEY (id_pojazd)
-- REFERENCES train.pojazd (id_pojazd)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.sklad ADD CONSTRAINT pojazd_sklad_fk
-- FOREIGN KEY (id_pojazd)
-- REFERENCES train.pojazd (id_pojazd)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.wagon ADD CONSTRAINT pojazd_szynowy_wagon_fk
-- FOREIGN KEY (id_sklad)
-- REFERENCES train.sklad (id_sklad)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.lokomotywa ADD CONSTRAINT pojazd_szynowy_lokomotywa_fk
-- FOREIGN KEY (id_sklad)
-- REFERENCES train.sklad (id_sklad)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.zespol_trakcyjny ADD CONSTRAINT sklad_zespol_trakcyjny_fk
-- FOREIGN KEY (id_sklad)
-- REFERENCES train.sklad (id_sklad)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.wagon_zt ADD CONSTRAINT zespol_trakcyjny_wagon_zt_fk
-- FOREIGN KEY (id_zt)
-- REFERENCES train.zespol_trakcyjny (id_zt)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.wagon ADD CONSTRAINT typ_wagonu_wagon_fk
-- FOREIGN KEY (id_typ_po)
-- REFERENCES train.typ_pojazdu_osobowego (id_typ_po)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.wagon_zt ADD CONSTRAINT typ_wagonu_wagon_zt_fk
-- FOREIGN KEY (id_typ_po)
-- REFERENCES train.typ_pojazdu_osobowego (id_typ_po)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.autobus_zapasowy ADD CONSTRAINT typ_pojazdu_osobowego_autobus_zapasowy_fk
-- FOREIGN KEY (id_typ_po)
-- REFERENCES train.typ_pojazdu_osobowego (id_typ_po)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;






-- --v2
-- CREATE TABLE train.linia (
--                 id_linia SERIAL NOT NULL,
--                 nazwa VARCHAR NOT NULL,
--                 CONSTRAINT linia_pk PRIMARY KEY (id_linia)
-- );


-- CREATE TABLE train.stacja (
--                 id_stacja SERIAL NOT NULL,
--                 nazwa VARCHAR NOT NULL,
--                 CONSTRAINT id_stacja PRIMARY KEY (id_stacja)
-- );


-- CREATE TABLE train.przystanek (
--                 id_przystanek SERIAL NOT NULL,
--                 id_stacja SERIAL NOT NULL,
--                 data_przyjazdu DATE NOT NULL,
--                 data_odjazdu DATE NOT NULL,
--                 godz_przyjazdu TIME NOT NULL,
--                 godz_odjazdu TIME NOT NULL,
--                 nr_kolejnosc INTEGER NOT NULL,
--                 CONSTRAINT id_przystanek PRIMARY KEY (id_przystanek)
-- );


-- CREATE TABLE train.pojazd (
--                 id_pojazd SERIAL NOT NULL,
--                 CONSTRAINT id_pojazd PRIMARY KEY (id_pojazd)
-- );


-- CREATE TABLE train.sklad (
--                 id_sklad SERIAL NOT NULL,
--                 id_pojazd SERIAL,
--                 CONSTRAINT id_sklad PRIMARY KEY (id_sklad)
-- );


-- CREATE TABLE train.zespol_trakcyjny (
--                 id_zt SERIAL NOT NULL,
--                 id_sklad SERIAL,
--                 nazwa VARCHAR NOT NULL,
--                 CONSTRAINT id_zt PRIMARY KEY (id_zt)
-- );


-- CREATE TABLE train.kurs_lini (
--                 id_kurs SERIAL NOT NULL,
--                 id_pojazd SERIAL NOT NULL,
--                 id_linia SERIAL NOT NULL,
--                 id_przystanek SERIAL NOT NULL,
--                 CONSTRAINT kurs_lini_pk PRIMARY KEY (id_kurs)
-- );


-- CREATE TABLE train.typ_lokomotywa (
--                 id_lokomotywa SERIAL NOT NULL,
--                 nazwa VARCHAR NOT NULL,
--                 moc INTEGER NOT NULL,
--                 max_pre INTEGER NOT NULL,
--                 max_uciag INTEGER NOT NULL,
--                 CONSTRAINT id_lokomotywa PRIMARY KEY (id_lokomotywa)
-- );


-- CREATE TABLE train.lokomotywa (
--                 id_lokomotywa SERIAL NOT NULL,
--                 id_sklad SERIAL,
--                 CONSTRAINT lokomotywa_pk PRIMARY KEY (id_lokomotywa)
-- );

-- CREATE TYPE typ AS ENUM ('EZT', 'wagon', 'autobus');
-- CREATE TABLE train.typ_pojazdu_osobowego (
--                 id_typ_po SERIAL NOT NULL,
--                 typPojazdu typ NOT NULL,
--                 nazwa_wagonu VARCHAR NOT NULL,
--                 czy_restauracyjny BOOLEAN NOT NULL,
--                 czy_wc BOOLEAN NOT NULL,
--                 waga DOUBLE PRECISION NOT NULL,
--                 ilosc_miejsc INTEGER NOT NULL,
--                 CONSTRAINT id_typ_wagonu PRIMARY KEY (id_typ_po)
-- );


-- CREATE TABLE train.autobus_zapasowy (
--                 id_autobus SERIAL NOT NULL,
--                 id_pojazd SERIAL,
--                 id_typ_po SERIAL NOT NULL,
--                 CONSTRAINT id_autobus PRIMARY KEY (id_autobus)
-- );


-- CREATE TABLE train.wagon_zt (
--                 id_wagon_zt SERIAL NOT NULL,
--                 id_zt SERIAL,
--                 id_typ_po SERIAL NOT NULL,
--                 CONSTRAINT id_wagon_zt PRIMARY KEY (id_wagon_zt)
-- );


-- CREATE TABLE train.wagon (
--                 id_wagon SERIAL NOT NULL,
--                 id_sklad SERIAL,
--                 id_typ_po SERIAL NOT NULL,
--                 CONSTRAINT id_wagon PRIMARY KEY (id_wagon)
-- );


-- ALTER TABLE train.kurs_lini ADD CONSTRAINT linia_kurs_lini_fk
-- FOREIGN KEY (id_linia)
-- REFERENCES train.linia (id_linia)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.przystanek ADD CONSTRAINT stacja_przystanek_fk
-- FOREIGN KEY (id_stacja)
-- REFERENCES train.stacja (id_stacja)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.kurs_lini ADD CONSTRAINT przystanek_kurs_lini_fk
-- FOREIGN KEY (id_przystanek)
-- REFERENCES train.przystanek (id_przystanek)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.kurs_lini ADD CONSTRAINT pojazd_kurs_lini_fk
-- FOREIGN KEY (id_pojazd)
-- REFERENCES train.pojazd (id_pojazd)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.autobus_zapasowy ADD CONSTRAINT pojazd_autobus_zapasowy_fk
-- FOREIGN KEY (id_pojazd)
-- REFERENCES train.pojazd (id_pojazd)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.sklad ADD CONSTRAINT pojazd_sklad_fk
-- FOREIGN KEY (id_pojazd)
-- REFERENCES train.pojazd (id_pojazd)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.wagon ADD CONSTRAINT pojazd_szynowy_wagon_fk
-- FOREIGN KEY (id_sklad)
-- REFERENCES train.sklad (id_sklad)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.zespol_trakcyjny ADD CONSTRAINT sklad_zespol_trakcyjny_fk
-- FOREIGN KEY (id_sklad)
-- REFERENCES train.sklad (id_sklad)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.lokomotywa ADD CONSTRAINT sklad_lokomotywa_fk
-- FOREIGN KEY (id_sklad)
-- REFERENCES train.sklad (id_sklad)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.wagon_zt ADD CONSTRAINT zespol_trakcyjny_wagon_zt_fk
-- FOREIGN KEY (id_zt)
-- REFERENCES train.zespol_trakcyjny (id_zt)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.lokomotywa ADD CONSTRAINT typ_lokomotywa_lokomotywa_fk
-- FOREIGN KEY (id_lokomotywa)
-- REFERENCES train.typ_lokomotywa (id_lokomotywa)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.wagon ADD CONSTRAINT typ_wagonu_wagon_fk
-- FOREIGN KEY (id_typ_po)
-- REFERENCES train.typ_pojazdu_osobowego (id_typ_po)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.wagon_zt ADD CONSTRAINT typ_wagonu_wagon_zt_fk
-- FOREIGN KEY (id_typ_po)
-- REFERENCES train.typ_pojazdu_osobowego (id_typ_po)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

-- ALTER TABLE train.autobus_zapasowy ADD CONSTRAINT typ_pojazdu_osobowego_autobus_zapasowy_fk
-- FOREIGN KEY (id_typ_po)
-- REFERENCES train.typ_pojazdu_osobowego (id_typ_po)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION
-- NOT DEFERRABLE;

--v3

CREATE TABLE train.linia (
                id_linia INTEGER NOT NULL,
                nazwa VARCHAR NOT NULL,
                CONSTRAINT linia_pk PRIMARY KEY (id_linia)
);


CREATE TABLE train.stacja (
                id_stacja INTEGER NOT NULL,
                nazwa VARCHAR NOT NULL,
                miejscowosc VARCHAR NOT NULL,
                adres VARCHAR,
                numer VARCHAR,
                CONSTRAINT id_stacja PRIMARY KEY (id_stacja)
);


CREATE TABLE train.sklad (
                id_sklad INTEGER NOT NULL,
                czy_przypisany_do_kursu BOOLEAN DEFAULT false NOT NULL,
                CONSTRAINT id_sklad PRIMARY KEY (id_sklad)
);


CREATE TABLE train.kurs_lini (
                id_kurs INTEGER NOT NULL,
                id_sklad INTEGER,
                id_linia INTEGER NOT NULL,
                CONSTRAINT kurs_lini_pk PRIMARY KEY (id_kurs)
);


CREATE TABLE train.przystanek (
                id_przystanek INTEGER NOT NULL,
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
                id_lokomotywa INTEGER NOT NULL,
                nazwa VARCHAR NOT NULL,
                moc INTEGER NOT NULL,
                max_pre INTEGER NOT NULL,
                max_uciag INTEGER NOT NULL,
                CONSTRAINT id_lokomotywa PRIMARY KEY (id_lokomotywa)
);


CREATE TABLE train.lokomotywa (
                id VARCHAR NOT NULL,
                id_lokomotywa INTEGER NOT NULL,
                id_sklad INTEGER,
                CONSTRAINT lokomotywa_pk PRIMARY KEY (id)
);

CREATE TYPE typ AS ENUM ('EZT', 'wagon', 'autobus');
CREATE TABLE train.typ_pojazdu_osobowego (
                id_typ_po INTEGER NOT NULL,
                nazwa_wagonu VARCHAR NOT NULL,
                czy_restauracyjny BOOLEAN NOT NULL,
                czy_wc BOOLEAN NOT NULL,
                waga DOUBLE PRECISION NOT NULL,
                ilosc_miejsc INTEGER NOT NULL,
                CONSTRAINT id_typ_wagonu PRIMARY KEY (id_typ_po)
);


CREATE TABLE train.autobus_zapasowy (
                id_autobus INTEGER NOT NULL,
                id_sklad INTEGER NOT NULL,
                id_typ_po INTEGER NOT NULL,
                CONSTRAINT id_autobus PRIMARY KEY (id_autobus)
);


CREATE TABLE train.wagon_zt (
                id_wagon_zt INTEGER NOT NULL,
                id_sklad INTEGER,
                id_typ_po INTEGER NOT NULL,
                CONSTRAINT id_wagon_zt PRIMARY KEY (id_wagon_zt)
);


CREATE TABLE train.wagon (
                id_wagon INTEGER NOT NULL,
                id_sklad INTEGER,
                id_typ_po INTEGER NOT NULL,
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

--stwórz funkcję której argumentem będzie id-primary key a będzie zwracac masę pojazdu
CREATE OR REPLACE FUNCTION train.masa_pojazdu(id_pojazd INTEGER)
RETURNS DOUBLE PRECISION AS $$
DECLARE
    masa DOUBLE PRECISION;
BEGIN
    SELECT SUM(waga) INTO masa FROM train.typ_pojazdu_osobowego WHERE id_typ_po IN (SELECT id_typ_po FROM train.wagon WHERE id_sklad IN (SELECT id_sklad FROM train.sklad WHERE id_pojazd = id_pojazd));
    RETURN masa;
END;
$$ LANGUAGE plpgsql;


--stwórz funkcję która zwróci wagę wagonu
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

--stwórz funkcję która zwróci wagę wagonu_zt
CREATE OR REPLACE FUNCTION train.waga_wagonu_zt(id_wagon_zt INTEGER)
RETURNS DOUBLE PRECISION AS
$$
DECLARE
    waga DOUBLE PRECISION;
BEGIN
SELECT waga INTO waga FROM train.typ_pojazdu_osobowego WHERE id_typ_po IN (SELECT id_typ_po FROM train.wagon_zt WHERE id_wagon_zt = id_wagon_zt);
RETURN waga;
END;
$$ LANGUAGE plpgsql;

--widok który zwóci listę lokomotyw zgrupowanych po mocy
CREATE OR REPLACE VIEW train.lokomotywa_moc AS
SELECT moc, nazwa FROM train.typ_lokomotywa GROUP BY moc, nazwa;

-- stwórz funkcję która zwróci składy dla który7ch nie ma przypisanego kursu
CREATE OR REPLACE FUNCTION train.sklady_bez_kursu()
RETURNS TABLE(id_sklad INTEGER, czy_przypisany_do_kursu BOOLEAN) AS
$$
BEGIN
RETURN QUERY SELECT sk.id_sklad, czy_przypisany_do_kursu FROM train.sklad sk WHERE sk.czy_przypisany_do_kursu = false;
END;
$$ LANGUAGE plpgsql;


-- stwórz funkcję która zwróci kursy których nie ma przypisanego składu
CREATE OR REPLACE FUNCTION train.kursy_bez_skladu()
    RETURNS TABLE(id_kurs INTEGER, id_sklad INTEGER, id_linia INTEGER) AS
$$
BEGIN
    RETURN QUERY SELECT kl.id_kurs, kl.id_sklad, kl.id_linia FROM train.kurs_lini kl WHERE kl.id_sklad IS NULL;
END;
$$ LANGUAGE plpgsql;

-- create function that returns all tables in schema
CREATE OR REPLACE FUNCTION train.get_tables(your_schema_name VARCHAR)
    RETURNS TABLE(table_name TEXT) AS
$$
BEGIN
    RETURN QUERY SELECT it.table_name::TEXT FROM information_schema.tables it WHERE it.table_schema = your_schema_name AND it.table_type = 'BASE TABLE';
END;
$$ LANGUAGE plpgsql;




