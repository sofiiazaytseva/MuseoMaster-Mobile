package eu.pl.snk.senseibunny.museomaster.models;

import android.media.MediaPlayer;

public class Exhibit {
    private final int idZabytku;
    private final String nazwa_zabytku_tf;
    private final String okres_powstawnia_tf;
    private final String tematyka_tf;
    private final String tworca_tf;
    private final String akt_miej_przech_tf;
    private final String docelowe_miej_przech;
    private final String opis_ta;
    private final Integer WystawaidWystawy;
    private final Integer ZadanieidZadania;
    private final Integer SalaidSali;
    private final Integer ZadaniePracownikidPracownika;

    private String NormalExListIcon="STOP";
    private String NormalExListColor="#ff0062";

    private MediaPlayer NormalExListMedia=null;
    private Thread NormalExListThread=null;

    private Boolean checked=false;
    public Exhibit(int idZabytku, String nazwa_zabytku_tf, String okres_powstawnia_tf, String tematyka_tf, String tworca_tf, String akt_miej_przech_tf, String opis_ta, Integer WystawaidWystawy, Integer ZadanieidZadania, Integer SalaidSali, Integer ZadaniePracownikidPracownika
            ,String docelowe_miej_przech) {
        this.idZabytku = idZabytku;
        this.nazwa_zabytku_tf =  nazwa_zabytku_tf;
        this.okres_powstawnia_tf = okres_powstawnia_tf;
        this.tematyka_tf = tematyka_tf;
        this.tworca_tf = tworca_tf;
        this.akt_miej_przech_tf = akt_miej_przech_tf;
        this.opis_ta = opis_ta;
        this.WystawaidWystawy = WystawaidWystawy;
        this.ZadanieidZadania = ZadanieidZadania;
        this.SalaidSali = SalaidSali;
        this.ZadaniePracownikidPracownika = ZadaniePracownikidPracownika;
        this.docelowe_miej_przech = docelowe_miej_przech;
    }

    Integer idZad;
    Integer idZadEx;

    public Exhibit(int idZabytku, String nazwa_zabytku_tf, String akt_miej_przech_tf, String docelowe_miej_przech, Integer idZad, Integer idZadEx) {
        this.idZabytku = idZabytku;
        this.nazwa_zabytku_tf =  nazwa_zabytku_tf;
        this.akt_miej_przech_tf = akt_miej_przech_tf;
        this.docelowe_miej_przech = docelowe_miej_przech;
        this.idZad=idZad;
        this.idZadEx=idZadEx;

        this.okres_powstawnia_tf = "";
        this.tematyka_tf = "";
        this.tworca_tf = "";
        this.opis_ta = "";
        this.WystawaidWystawy = -1;
        this.ZadanieidZadania = -1;
        this.SalaidSali = -1;
        this.ZadaniePracownikidPracownika =-1;
    }

    public int getIdZabytku() {
        return idZabytku;
    }

    public String getNazwa_zabytku_tf() {
        return nazwa_zabytku_tf;
    }

    public String getOkres_powstawnia_tf() {
        return okres_powstawnia_tf;
    }

    public String getTematyka_tf() {
        return tematyka_tf;
    }

    public String getTworca_tf() {
        return tworca_tf;
    }

    public String getAkt_miej_przech_tf() {
        return akt_miej_przech_tf;
    }

    public String getDocelowe_miej_przech() {
        return docelowe_miej_przech;
    }

    public String getOpis_ta() {
        return opis_ta;
    }

    public Integer getWystawaidWystawy() {
        return WystawaidWystawy;
    }

    public Integer getZadanieidZadania() {
        return ZadanieidZadania;
    }

    public Integer getSalaidSali() {
        return SalaidSali;
    }

    public Integer getZadaniePracownikidPracownika() {
        return ZadaniePracownikidPracownika;
    }

    public String getNormalExListIcon() {
        return NormalExListIcon;
    }

    public String getNormalExListColor() {
        return NormalExListColor;
    }

    public MediaPlayer getNormalExListMedia() {
        return NormalExListMedia;
    }

    public Thread getNormalExListThread() {
        return NormalExListThread;
    }

    public Boolean getChecked() {
        return checked;
    }

    public Integer getIdZad() {
        return idZad;
    }

    public Integer getIdZadEx() {
        return idZadEx;
    }
}