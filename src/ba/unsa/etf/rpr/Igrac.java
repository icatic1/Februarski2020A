package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Igrac implements Comparable<Igrac>{

    private String nadimak;
    private Double zivotniPoeni;
    private List<Napad> napadi = new ArrayList<>();

    public List<Napad> getNapadi() {
        return napadi;
    }

    public void setNapadi(List<Napad> napadi) {
        this.napadi = napadi;
    }

    public Igrac(String nadimak) {
        this.nadimak = nadimak;
        zivotniPoeni = 100d;
    }

    public String getNadimak() {
        return nadimak;
    }

    public void setNadimak(String nadimak) {
        this.nadimak = nadimak;
    }

    public Double getZivotniPoeni() {
        return zivotniPoeni;
    }

    public void setZivotniPoeni(double poeni) {
        this.zivotniPoeni = poeni;
    }

    public abstract void napadni(String imeNapada, Igrac igrac) throws IlegalanNapad;

    public abstract void primiNapad(Napad napad);

    public abstract void napadni(String imeNapada, Igrac igrac, double koef) throws IlegalanNapad;

    public abstract void primiNapad(Napad napad,double koef);

    public abstract void registrujNapad(Napad napad);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Igrac igrac = (Igrac) o;
        return Objects.equals(nadimak, igrac.nadimak);
    }
}
