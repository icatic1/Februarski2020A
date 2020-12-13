package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Igra {

    List<Igrac> igraci = new ArrayList<>();


    public List<Igrac> getIgraci() {
        return igraci;
    }


    public void registrujIgraca(Igrac igrac) {
        if(igraci.contains(igrac))throw  new IllegalArgumentException("Već je u igri igrač sa ovim nadimkom");
        igraci.add(igrac);
    }

    public void registrujNapadZaIgraca(Napad napad, Igrac igrac) {
        if(!igraci.contains(igrac))this.registrujIgraca(igrac);
        igrac.registrujNapad(napad);
    }

    public List<Igrac> dajIgracePoKriteriju(Predicate<Igrac> fun){
        return igraci.stream().filter(fun).collect(Collectors.toList());
    }

    public List<Igrac> dajHeroje() {
        return igraci.stream().filter(igrac -> igrac instanceof Heroj).collect(Collectors.toList());
    }

    public List<Igrac> dajNeprijatelje() {
        return igraci.stream().filter(igrac -> igrac instanceof Neprijatelj).collect(Collectors.toList());
    }

    public List<Igrac> dajPrezivjeleHeroje() {
        return igraci.stream().filter(igrac -> igrac instanceof Heroj && igrac.getZivotniPoeni()!=0).collect(Collectors.toList());
    }

    public List<Igrac> dajPrezivjeleNeprijatelje() {
        return igraci.stream().filter(igrac -> igrac instanceof Neprijatelj && igrac.getZivotniPoeni()!=0).collect(Collectors.toList());

    }

    public List<Igrac> dajPrezivjeleIgrace() {
        return this.dajIgracePoKriteriju(igrac -> igrac.getZivotniPoeni()!=0).stream().sorted().collect(Collectors.toList());
    }

// uraditi maks
    public TreeSet<Igrac> dajIgraceUVodstvu() {
        return (TreeSet<Igrac>) igraci.stream().filter(igrac -> igrac.getZivotniPoeni()==igraci.stream().max(Igrac::compareTo).get().getZivotniPoeni());
    }

    public void izvrsiNapad(Igrac napadac, Igrac meta, Napad napad) throws IlegalanNapad {
        if(napadac.getZivotniPoeni()==0) throw  new IlegalanNapad("Nije moguće napasti sa igračem koji nema preostalih životnih poena");
        if(napadac.getZivotniPoeni()<20)napadac.napadni(napad.getNazivNapada(), meta,napadac.getZivotniPoeni()/100.);
        else napadac.napadni(napad.getNazivNapada(), meta);
    }

    public void izvrsiSerijuNapada(Igrac napadac, HashMap<Igrac, Napad> metaNapad) throws IlegalanNapad {
        if(napadac.getZivotniPoeni()==0) throw  new IllegalArgumentException("Nije moguće napasti sa igračem koji nema preostalih životnih poena");
        if(metaNapad.values().stream().filter(napad -> !napadac.getNapadi().contains(napad)).count()!=0){

        }else {
            for(var x:metaNapad.keySet()){
                napadac.napadni(metaNapad.get(x).getNazivNapada(),x);
            }
        }

    }

    public boolean daLiJeMoguceNapasti(Igrac napadac, Igrac meta, Napad napad) {
        if(napadac.getNapadi().contains(napad) && napadac.getZivotniPoeni()!=0 && meta.getZivotniPoeni()!=0 &&((napadac instanceof Heroj && meta instanceof Neprijatelj)||(napadac instanceof Neprijatelj && meta instanceof Heroj)) ) return true;
        return false;
    }

    public String prikaziStanje() {
        StringBuilder sb = new StringBuilder();
        sb.append("Heroji koji su u igri:\n");
        for(var x:this.dajPrezivjeleHeroje()){
            sb.append(x.toString());
        }
        sb.append("Neprijatelji koji su u igri:\n");
        for(var x:this.dajPrezivjeleNeprijatelje()){
            sb.append(x.toString());
        }
        return sb.toString().trim();
    }

    public int statusIgre() {
        if(this.dajPrezivjeleIgrace().isEmpty())return 0;
        if(this.dajPrezivjeleIgrace().size()==this.getIgraci().size()) return 3;
        if(!this.dajPrezivjeleHeroje().isEmpty() && this.dajPrezivjeleNeprijatelje().isEmpty())return 1;
        return 2;
    }

    public void ukloniGubitnike() {
        igraci=igraci.stream().filter(igrac -> this.dajPrezivjeleIgrace().contains(igrac)).collect(Collectors.toList());
    }

    public void restartuj() {
        igraci.stream().forEach(igrac -> igrac.setZivotniPoeni(100d));
    }
}
