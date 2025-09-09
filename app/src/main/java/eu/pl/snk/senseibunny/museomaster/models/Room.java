package eu.pl.snk.senseibunny.museomaster.models;

public class Room {
    private final int idSali;
    private final int wielkosc;
    private final String nazwa;
    private final String typ;

    public Room(int idSali, Integer wielkosc, String nazwa, String typ) {
        this.idSali = idSali;
        this.wielkosc = wielkosc;
        this.nazwa = nazwa;
        this.typ = typ;
    }

    public int getIdSali() {
        return idSali;
    }

    public int getWielkosc() {
        return wielkosc;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getTyp() {
        return typ;
    }
}