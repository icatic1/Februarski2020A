package ba.unsa.etf.rpr;

public class Neprijatelj extends Igrac{

    public Neprijatelj(String nadimak) {
        super(nadimak);
    }

    @Override
    public void napadni(String imeNapada, Igrac igrac) throws IlegalanNapad {
        Napad nadeni=new Napad("",0);
        for(int i = 0; i<getNapadi().size();i++){
            if(getNapadi().get(i).getNazivNapada().equals(imeNapada))nadeni=new Napad(imeNapada,getNapadi().get(i).getDjelovanje());
        }
        if(nadeni.getNazivNapada().equals("")) throw new IlegalanNapad(getNadimak()+" ne može izvršiti napad " + imeNapada);
        if(igrac.getZivotniPoeni() == 0) throw new IlegalanNapad("Ovaj igrač je već završio igru");
        if(igrac instanceof Neprijatelj) throw new IlegalanNapad("Nije moguće izvršiti napad na prijatelja");

        igrac.primiNapad(nadeni);
    }

    @Override
    public void primiNapad(Napad napad) {
        setZivotniPoeni(getZivotniPoeni()-napad.getDjelovanje());
        if(getZivotniPoeni()<0) setZivotniPoeni(0d);
    }

    @Override
    public void napadni(String imeNapada, Igrac igrac, double koef) throws IlegalanNapad {
        Napad nadeni=new Napad("",0);
        for(int i = 0; i<getNapadi().size();i++){
            if(getNapadi().get(i).getNazivNapada().equals(imeNapada))nadeni=new Napad(imeNapada,getNapadi().get(i).getDjelovanje());
        }
        if(nadeni.getNazivNapada().equals("")) throw new IlegalanNapad(getNadimak()+" ne može izvršiti napad " + imeNapada);
        if(igrac.getZivotniPoeni() == 0) throw new IlegalanNapad("Ovaj igrač je već završio igru");
        if(igrac instanceof Neprijatelj) throw new IlegalanNapad("Nije moguće izvršiti napad na prijatelja");

        igrac.primiNapad(nadeni,koef);
    }

    @Override
    public void primiNapad(Napad napad, double koef) {
        setZivotniPoeni(getZivotniPoeni()-napad.getDjelovanje()*koef);
        if(getZivotniPoeni()<0) setZivotniPoeni(0d);
    }

    @Override
    public void registrujNapad(Napad napad) {
        if(getNapadi().contains(napad)) throw new IllegalArgumentException("Napad sa ovim nazivom je već registrovan");
        getNapadi().add(napad);
    }

    @Override
    public int compareTo(Igrac o) {
        if(getZivotniPoeni()>o.getZivotniPoeni()) return 1;
        if(getZivotniPoeni()<o.getZivotniPoeni()) return -1;
        return getNadimak().compareTo(o.getNadimak());
    }

    public String toString() {
        return getNadimak()+"(preostalo "+getZivotniPoeni()+" životnih poena)\n";
    }

}
