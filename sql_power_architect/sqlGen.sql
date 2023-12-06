CREATE TABLE train.linia (
                id_linia SERIAL NOT NULL,
                nazwa VARCHAR NOT NULL,
                CONSTRAINT linia_pk PRIMARY KEY (id_linia)
);


CREATE TABLE train.stacja (
                id_stacja SERIAL NOT NULL,
                nazwa VARCHAR NOT NULL,
                CONSTRAINT id_stacja PRIMARY KEY (id_stacja)
);


CREATE TABLE train.przystanek (
                id_przystanek SERIAL NOT NULL,
                id_stacja SERIAL NOT NULL,
                data_przyjazdu DATE NOT NULL,
                data_odjazdu DATE NOT NULL,
                godz_przyjazdu TIME NOT NULL,
                godz_odjazdu TIME NOT NULL,
                nr_kolejnosc INTEGER NOT NULL,
                CONSTRAINT id_przystanek PRIMARY KEY (id_przystanek)
);


CREATE TABLE train.pojazd (
                id_pojazd SERIAL NOT NULL,
                CONSTRAINT id_pojazd PRIMARY KEY (id_pojazd)
);


CREATE TABLE train.sklad (
                id_sklad SERIAL NOT NULL,
                id_pojazd SERIAL,
                CONSTRAINT id_sklad PRIMARY KEY (id_sklad)
);


CREATE TABLE train.zespol_trakcyjny (
                id_zt SERIAL NOT NULL,
                id_sklad SERIAL,
                nazwa VARCHAR NOT NULL,
                CONSTRAINT id_zt PRIMARY KEY (id_zt)
);


CREATE TABLE train.kurs_lini (
                id_kurs SERIAL NOT NULL,
                id_pojazd SERIAL NOT NULL,
                id_linia SERIAL NOT NULL,
                id_przystanek SERIAL NOT NULL,
                CONSTRAINT kurs_lini_pk PRIMARY KEY (id_kurs)
);


CREATE TABLE train.lokomotywa (
                id_lokomotywa SERIAL NOT NULL,
                id_sklad SERIAL,
                nazwa VARCHAR NOT NULL,
                moc INTEGER NOT NULL,
                max_pre INTEGER NOT NULL,
                max_uciag INTEGER NOT NULL,
                CONSTRAINT id_lokomotywa PRIMARY KEY (id_lokomotywa)
);

CREATE TYPE typ AS ENUM ('EZT', 'wagon', 'autobus');
CREATE TABLE train.typ_pojazdu_osobowego (
                id_typ_po SERIAL NOT NULL,
				typPojazdu typ NOT NULL,
                nazwa_wagonu VARCHAR NOT NULL,
                czy_restauracyjny BOOLEAN NOT NULL,
                czy_wc BOOLEAN NOT NULL,
                waga DOUBLE PRECISION NOT NULL,
                ilosc_miejsc INTEGER NOT NULL,
                CONSTRAINT id_typ_wagonu PRIMARY KEY (id_typ_po)
);


CREATE TABLE train.autobus_zapasowy (
                id_autobus SERIAL NOT NULL,
                id_pojazd SERIAL,
                id_typ_po SERIAL NOT NULL,
                CONSTRAINT id_autobus PRIMARY KEY (id_autobus)
);


CREATE TABLE train.wagon_zt (
                id_wagon_zt SERIAL NOT NULL,
                id_zt SERIAL,
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

ALTER TABLE train.kurs_lini ADD CONSTRAINT przystanek_kurs_lini_fk
FOREIGN KEY (id_przystanek)
REFERENCES train.przystanek (id_przystanek)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.kurs_lini ADD CONSTRAINT pojazd_kurs_lini_fk
FOREIGN KEY (id_pojazd)
REFERENCES train.pojazd (id_pojazd)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.autobus_zapasowy ADD CONSTRAINT pojazd_autobus_zapasowy_fk
FOREIGN KEY (id_pojazd)
REFERENCES train.pojazd (id_pojazd)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.sklad ADD CONSTRAINT pojazd_sklad_fk
FOREIGN KEY (id_pojazd)
REFERENCES train.pojazd (id_pojazd)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.wagon ADD CONSTRAINT pojazd_szynowy_wagon_fk
FOREIGN KEY (id_sklad)
REFERENCES train.sklad (id_sklad)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.lokomotywa ADD CONSTRAINT pojazd_szynowy_lokomotywa_fk
FOREIGN KEY (id_sklad)
REFERENCES train.sklad (id_sklad)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.zespol_trakcyjny ADD CONSTRAINT sklad_zespol_trakcyjny_fk
FOREIGN KEY (id_sklad)
REFERENCES train.sklad (id_sklad)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE train.wagon_zt ADD CONSTRAINT zespol_trakcyjny_wagon_zt_fk
FOREIGN KEY (id_zt)
REFERENCES train.zespol_trakcyjny (id_zt)
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