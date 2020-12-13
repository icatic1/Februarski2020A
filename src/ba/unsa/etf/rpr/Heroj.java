package ba.unsa.etf.rpr;

public class Heroj extends Igrac{

    private Double odbrambeniPoeni;

    public Heroj(String nadimak,double odbrambeniPoeni) {
        super(nadimak);
        this.odbrambeniPoeni=odbrambeniPoeni;
    }

    @Override
    public void napadni(String imeNapada, Igrac igrac) throws IlegalanNapad {
        Napad nadeni=new Napad("",0);
        for(int i = 0; i<getNapadi().size();i++){
            if(getNapadi().get(i).getNazivNapada().equals(imeNapada))nadeni=new Napad(imeNapada,getNapadi().get(i).getDjelovanje());
        }
        if(nadeni.getNazivNapada().equals("")) throw new IlegalanNapad(getNadimak()+" ne može izvršiti napad " + imeNapada);
        if(igrac.getZivotniPoeni() == 0) throw new IlegalanNapad("Ovaj igrač je već završio igru");
        if(igrac instanceof Heroj) throw new IlegalanNapad("Nije moguće izvršiti napad na prijatelja");

        igrac.primiNapad(nadeni);
    }

    @Override
    public void primiNapad(Napad napad) {
        if(napad.getDjelovanje()>odbrambeniPoeni)setZivotniPoeni(getZivotniPoeni()-(napad.getDjelovanje()-odbrambeniPoeni));
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
        if(igrac instanceof Heroj) throw new IlegalanNapad("Nije moguće izvršiti napad na prijatelja");

        igrac.primiNapad(nadeni,koef);
    }

    @Override
    public void primiNapad(Napad napad, double koef) {
        if(napad.getDjelovanje()*koef>odbrambeniPoeni)setZivotniPoeni(getZivotniPoeni()-(napad.getDjelovanje()*koef-odbrambeniPoeni));
        if(getZivotniPoeni()<0) setZivotniPoeni(0d);
    }

    @Override
    public void registrujNapad(Napad napad) {
        if(getNapadi().contains(napad)) throw new IllegalArgumentException("Napad sa ovim nazivom je već registrovan");
        getNapadi().add(napad);
    }

    public Double getOdbrambeniPoeni() {
        return odbrambeniPoeni;
    }

    public void setOdbrambeniPoeni(Double odbrambeniPoeni) {
        this.odbrambeniPoeni = odbrambeniPoeni;
    }

    @Override
    public int compareTo(Igrac o) {
       if(getZivotniPoeni()>o.getZivotniPoeni()) return 1;
       if(getZivotniPoeni()<o.getZivotniPoeni()) return -1;
       return getNadimak().compareTo(o.getNadimak());
    }

    @Override
    public String toString() {
        return getNadimak()+"(preostalo "+getZivotniPoeni()+" životnih poena)\n";
    }

    //        if(!napadi.contains(napad)) throw new IlegalanNapad(nadimak+" ne moze izvršiti napad " + napad);
    //        if(igrac.getPoeni == 0) throw new IlegalanNapad("Ovaj igrač je već završio borbu");
    // if(igrac typeof this) throw new IlegalanNapad("Nije moguće izvršiti napad na prijatelja")
}
