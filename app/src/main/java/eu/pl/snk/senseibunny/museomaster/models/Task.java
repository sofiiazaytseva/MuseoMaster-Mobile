package eu.pl.snk.senseibunny.museomaster.models;

public class Task {
    private final int idZadania;
    private final String temat;
    private final String opis;
    private final String startData;
    private final String endData;
    private String status;
    private final int idPracownika;
    private final String nazwaUzytkownikaNadajacego;
    private final String nazwaUzytkownika;

    public Task(int idZadania, String temat, String opis, String startData, String endData, String status, int idPracownika, String nazwaUzytkownikaNadajacego, String nazwaUzytkownika) {
        this.idPracownika = idPracownika;
        this.idZadania = idZadania;
        this.temat = temat;
        this.opis = opis;
        this.startData = startData;
        this.endData = endData;
        this.status = status;
        this.nazwaUzytkownikaNadajacego = nazwaUzytkownikaNadajacego;
        this.nazwaUzytkownika = nazwaUzytkownika;
    }

    public String getNazwaUzytkownika() {
        return nazwaUzytkownika;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNazwaUzytkownikaNadajacego() {
        return nazwaUzytkownikaNadajacego;
    }

    public int getIdZadania() {
        return idZadania;
    }

    public String getTemat() {
        return temat;
    }

    public String getOpis() {
        return opis;
    }

    public String getStartData() {
        return startData;
    }

    public String getEndData() {
        return endData;
    }

    public String getStatus() {
        return status;
    }

    public int getIdPracownika() {
        return idPracownika;
    }
}