package eu.pl.snk.senseibunny.museomaster.models;

public class Report {
    private final int idPracownika;
    private final int idReportu;
    private final String nazwaUzytkownika;
    private final String opis;

    public Report(int idPracownika, int idReportu, String nazwaUzytkownika, String opis) {
        this.idPracownika = idPracownika;
        this.idReportu = idReportu;
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.opis = opis;
    }

    public int getIdReportu() {
        return idReportu;
    }

    public String getNazwaUzytkownika() {
        return nazwaUzytkownika;
    }

    public int getIdPracownika() {
        return idPracownika;
    }

    public String getOpis() {
        return opis;
    }
}