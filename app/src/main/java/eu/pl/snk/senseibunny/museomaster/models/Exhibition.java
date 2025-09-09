package eu.pl.snk.senseibunny.museomaster.models;

import java.time.LocalDate;
import java.util.Date;

public class Exhibition {
    private final int idWystawy;
    private final String nazwaWystawy;
    private final String sala;
    private final String miejsceWykonania;
    private final String tematyka;
    private final String tworca;
    private final Date dataRozpoczecia;
    private final Date dataZakonczenia;

    public Exhibition(int idWystawy, String nazwaWystawy, String sala, String miejsceWykonania,
                      String tematyka, String tworca, Date dataRozpoczecia, Date dataZakonczenia) {
        this.idWystawy = idWystawy;
        this.nazwaWystawy = nazwaWystawy;
        this.sala = sala;
        this.miejsceWykonania = miejsceWykonania;
        this.tematyka = tematyka;
        this.tworca = tworca;
        this.dataRozpoczecia = dataRozpoczecia;
        this.dataZakonczenia = dataZakonczenia;
    }

    public int getIdWystawy() {
        return idWystawy;
    }

    public String getNazwaWystawy() {
        return nazwaWystawy;
    }

    public String getSala() {
        return sala;
    }

    public String getMiejsceWykonania() {
        return miejsceWykonania;
    }

    public String getTematyka() {
        return tematyka;
    }

    public String getTworca() {
        return tworca;
    }

    public Date getDataRozpoczecia() {
        return dataRozpoczecia;
    }

    public Date getDataZakonczenia() {
        return dataZakonczenia;
    }
}