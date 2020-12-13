package ba.unsa.etf.rpr;

import java.util.Objects;

public class Napad {

    private String nazivNapada;
    private double djelovanje;

    public Napad(String nazivNapada, double djelovanje) {
        this.nazivNapada = nazivNapada;
        this.djelovanje = djelovanje;
    }

    public String getNazivNapada() {
        return nazivNapada;
    }

    public void setNazivNapada(String nazivNapada) {
        this.nazivNapada = nazivNapada;
    }

    public double getDjelovanje() {
        return djelovanje;
    }

    public void setDjelovanje(double djelovanje) {
        this.djelovanje = djelovanje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Napad napad1 = (Napad) o;
        return Double.compare(napad1.djelovanje, djelovanje) == 0 &&
                Objects.equals(nazivNapada, napad1.nazivNapada);
    }

}
