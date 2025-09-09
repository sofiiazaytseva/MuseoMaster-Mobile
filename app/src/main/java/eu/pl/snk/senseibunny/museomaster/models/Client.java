package eu.pl.snk.senseibunny.museomaster.models;

public class Client {
    private int idPracownika;
    private String imiePracownika;
    private String nazwiskoPracownika;
    private String emailPracownika;
    private int wiekPracownika;
    private int czyUprawniony;
    private String rola;
    private int nrTelefonu;
    private String nazwaUzytkownika;

    public Client(int idPracownika, String imiePracownika, String nazwiskoPracownika, String emailPracownika, int wiekPracownika, int czyUprawniony, String rola, int nrTelefonu, String nazwaUzytkownika) {
        this.idPracownika = idPracownika;
        this.imiePracownika = imiePracownika;
        this.nazwiskoPracownika = nazwiskoPracownika;
        this.nrTelefonu = nrTelefonu;
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.emailPracownika = emailPracownika;
        this.wiekPracownika = wiekPracownika;
        this.czyUprawniony = czyUprawniony;
        this.rola = rola;
    }

    public int getIdPracownika() {
        return idPracownika;
    }

    public void setIdPracownika(int idPracownika) {
        this.idPracownika = idPracownika;
    }

    public String getImiePracownika() {
        return imiePracownika;
    }

    public void setImiePracownika(String imiePracownika) {
        this.imiePracownika = imiePracownika;
    }

    public String getNazwiskoPracownika() {
        return nazwiskoPracownika;
    }

    public void setNazwiskoPracownika(String nazwiskoPracownika) {
        this.nazwiskoPracownika = nazwiskoPracownika;
    }

    public String getEmailPracownika() {
        return emailPracownika;
    }

    public void setEmailPracownika(String emailPracownika) {
        this.emailPracownika = emailPracownika;
    }

    public int getWiekPracownika() {
        return wiekPracownika;
    }

    public void setWiekPracownika(int wiekPracownika) {
        this.wiekPracownika = wiekPracownika;
    }

    public int getCzyUprawniony() {
        return czyUprawniony;
    }

    public void setCzyUprawniony(int czyUprawniony) {
        this.czyUprawniony = czyUprawniony;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    public int getNrTelefonu() {
        return nrTelefonu;
    }

    public void setNrTelefonu(int nrTelefonu) {
        this.nrTelefonu = nrTelefonu;
    }

    public String getNazwaUzytkownika() {
        return nazwaUzytkownika;
    }

    public void setNazwaUzytkownika(String nazwaUzytkownika) {
        this.nazwaUzytkownika = nazwaUzytkownika;
    }
}