package ba.unsa.etf.rpr;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class IgraTest {

    private static Igra igra = new Igra();
    private static List<Igrac> igraci = new ArrayList<>();

    @BeforeAll
    static void setUpClass() {
        Igrac igrac1 = new Heroj("superman", 5);
        Igrac igrac2 = new Heroj("spiderman", 6);
        Igrac igrac3 = new Heroj("batman", 4);

        Igrac neprijatelj1 = new Neprijatelj("joker");
        Igrac neprijatelj2 = new Neprijatelj("sandman");
        Igrac neprijatelj3 = new Neprijatelj("venom");

        igraci.add(igrac1);
        igraci.add(igrac2);
        igraci.add(igrac3);

        igraci.add(neprijatelj1);
        igraci.add(neprijatelj2);
        igraci.add(neprijatelj3);
    }

    @BeforeEach
    void setUpTest() {
        igra = new Igra();
        for(Igrac igrac : igraci){
            igrac.setZivotniPoeni(100);
            igrac.setNapadi(new ArrayList<>());
            igra.registrujIgraca(igrac);
        }
    }

    @Test
    void registrujIgracaTest() {
        igra.registrujIgraca(new Heroj("Wolverine", 5));
        assertAll(
                () -> assertEquals(7, igra.getIgraci().size()),
                () -> assertTrue(igra.getIgraci().contains(new Heroj("Wolverine", 5)))
        );
    }

    @Test
    void registrujIgracaIzuzetakTest() {
        try{
            igra.registrujIgraca(new Heroj("superman", 8));
        }catch (IllegalArgumentException e){
            assertEquals("Već je u igri igrač sa ovim nadimkom", e.getMessage());
        }
    }

    @Test
    void registrujNapadZaIgracaTest1() {
        Napad napad = new Napad("fizicki napad", 4.5);
        Igrac igrac = igra.getIgraci().get(1);
        igra.registrujNapadZaIgraca(napad, igrac);

        assertAll(
                () -> assertTrue(igrac.getNapadi().contains(napad)),
                () -> assertDoesNotThrow(() -> igrac.napadni("fizicki napad", igraci.get(3)))
        );
    }

    @Test
    void registrujNapadZaIgracaTest2() {
        Napad napad = new Napad("fizicki napad", 4.5);
        Igrac igrac = new Heroj("Wolverine", 5);
        igra.registrujNapadZaIgraca(napad, igrac);

        assertAll(
                () -> assertTrue(igrac.getNapadi().contains(napad)),
                () -> assertDoesNotThrow(() -> igrac.napadni("fizicki napad", igraci.get(3))),
                () -> assertEquals(7, igra.getIgraci().size()),
                () -> assertTrue(igra.getIgraci().contains(igrac))
        );
    }

    @Test
    void dajIgracePoKriterijuTest() {
        List<Igrac> filtrirano1 = igra.dajIgracePoKriteriju(igrac -> igrac.getNadimak().contains("man"));
        List<Igrac> filtrirano2 = igra.dajIgracePoKriteriju(igrac -> igrac instanceof Heroj && ((Heroj) igrac).getOdbrambeniPoeni() == 6);
        assertAll(
                () -> assertEquals(4, filtrirano1.size()),
                () -> assertTrue(filtrirano1.contains(igraci.get(0))),
                () -> assertTrue(filtrirano1.contains(igraci.get(1))),
                () -> assertTrue(filtrirano1.contains(igraci.get(2))),
                () -> assertTrue(filtrirano1.contains(igraci.get(4))),
                () -> assertEquals(1, filtrirano2.size()),
                () -> assertTrue(filtrirano2.contains(igraci.get(1)))
        );
    }

    @Test
    void dajHerojeTest() {
        List<Heroj> heroji = new ArrayList<>();
        heroji.add((Heroj)igraci.get(0));
        heroji.add((Heroj)igraci.get(1));
        heroji.add((Heroj)igraci.get(2));

        assertEquals(heroji, igra.dajHeroje());
    }

    @Test
    void dajNeprijateljeTest() {
        List<Neprijatelj> neprijatelji = new ArrayList<>();
        neprijatelji.add((Neprijatelj) igraci.get(3));
        neprijatelji.add((Neprijatelj) igraci.get(4));
        neprijatelji.add((Neprijatelj) igraci.get(5));

        assertEquals(neprijatelji, igra.dajNeprijatelje());
    }

    @Test
    void dajPrezivjeleHerojeTest() {
        List<Heroj> heroji = new ArrayList<>();

        for(Igrac igrac : igra.getIgraci()){
            igrac.setZivotniPoeni(0);
        }

        igra.getIgraci().get(0).setZivotniPoeni(14);
        igra.getIgraci().get(1).setZivotniPoeni(15);

        heroji.add((Heroj)igraci.get(0));
        heroji.add((Heroj)igraci.get(1));

        assertEquals(heroji, igra.dajPrezivjeleHeroje());
    }

    @Test
    void dajPrezivjeleNeprijateljeTest() {
        List<Neprijatelj> neprijatelji = new ArrayList<>();

        for(Igrac igrac : igra.getIgraci()){
            igrac.setZivotniPoeni(0);
        }

        igra.getIgraci().get(3).setZivotniPoeni(14);
        igra.getIgraci().get(5).setZivotniPoeni(15);

        neprijatelji.add((Neprijatelj)igraci.get(3));
        neprijatelji.add((Neprijatelj)igraci.get(5));

        assertEquals(neprijatelji, igra.dajPrezivjeleNeprijatelje());
    }

    @Test
    void dajPrezivjeleIgraceTest1() {
        TreeSet<Igrac> prezivjeliIgraci = new TreeSet<>();

        for(Igrac igrac : igra.getIgraci()){
            igrac.setZivotniPoeni(0);
        }

        igra.getIgraci().get(3).setZivotniPoeni(14);
        igra.getIgraci().get(5).setZivotniPoeni(15);

        prezivjeliIgraci.add(IgraTest.igraci.get(3));
        prezivjeliIgraci.add(IgraTest.igraci.get(5));

        String sortirano = igra.getIgraci().get(3).toString() + "\n" + igra.getIgraci().get(5).toString() + "\n";
        StringBuilder rezultatMetode = new StringBuilder();
        for(Igrac i : igra.dajPrezivjeleIgrace()){
            rezultatMetode.append(i).append("\n");
        }

        assertAll(
                () -> assertEquals(prezivjeliIgraci, igra.dajPrezivjeleIgrace()),
                () -> assertEquals(sortirano, rezultatMetode.toString())
        );
    }

    @Test
    void dajPrezivjeleIgraceTest2() {
        TreeSet<Igrac> prezivjeliIgraci = new TreeSet<>();

        for(Igrac igrac : igra.getIgraci()){
            igrac.setZivotniPoeni(0);
        }

        igra.getIgraci().get(3).setZivotniPoeni(15);
        igra.getIgraci().get(5).setZivotniPoeni(15);

        prezivjeliIgraci.add(IgraTest.igraci.get(3));
        prezivjeliIgraci.add(IgraTest.igraci.get(5));

        String sortirano = igra.getIgraci().get(3).toString() + "\n" + igra.getIgraci().get(5).toString() + "\n";
        StringBuilder rezultatMetode = new StringBuilder();
        for(Igrac i : igra.dajPrezivjeleIgrace()){
            rezultatMetode.append(i).append("\n");
        }

        assertAll(
                () -> assertEquals(prezivjeliIgraci, igra.dajPrezivjeleIgrace()),
                () -> assertEquals(sortirano, rezultatMetode.toString())
        );
    }

    @Test
    void dajIgraceUVodstvuTest1() {
        igra.getIgraci().get(2).setZivotniPoeni(140);
        igra.getIgraci().get(0).setZivotniPoeni(25);
        igra.getIgraci().get(3).setZivotniPoeni(140);

        TreeSet<Igrac> igraci = new TreeSet<>();
        igraci.add(igra.getIgraci().get(2));
        igraci.add(igra.getIgraci().get(3));

        assertEquals(igraci, igra.dajIgraceUVodstvu());
    }

    @Test
    void izvrsiNapadIzuzetakTest1() {
        Napad napad = new Napad("magicni napad", 20);
        Igrac meta = igra.getIgraci().get(0);
        Igrac napadac = igra.getIgraci().get(1);

        igra.registrujNapadZaIgraca(napad, napadac);
        napadac.setZivotniPoeni(0);
        try{
            igra.izvrsiNapad(napadac, meta, napad);
        }catch (IlegalanNapad e){
            assertEquals("Nije moguće napasti sa igračem koji nema preostalih životnih poena", e.getMessage());
        }
    }

    @Test
    void izvrsiNapadIzuzetakTest2() {
        Napad napad = new Napad("obicni napad", 20);
        Igrac meta = igra.getIgraci().get(0);
        Igrac napadac = igra.getIgraci().get(1);


        igra.registrujNapadZaIgraca(napad, napadac);
        meta.setZivotniPoeni(0);
        try{
            igra.izvrsiNapad(napadac, meta, napad);
        }catch (IlegalanNapad e){
            assertEquals("Ovaj igrač je već završio igru", e.getMessage());
        }
    }

    @Test
    void izvrsiNapadIzuzetakTest3() {
        Napad napad = new Napad("obicni napad", 20);
        Igrac meta = igra.getIgraci().get(0);
        Igrac napadac = igra.getIgraci().get(3);

        try{
            igra.izvrsiNapad(napadac, meta, napad);
        }catch (IlegalanNapad e){
            assertEquals("joker ne može izvršiti napad obicni napad", e.getMessage());
        }
    }

    @Test
    void izvrsiNapadIzuzetakTest4()  {
        Napad napad = new Napad("obicni napad", 20);
        Igrac meta = igra.getIgraci().get(0);
        Igrac napadac = igra.getIgraci().get(1);

        igra.registrujNapadZaIgraca(napad, napadac);

        try{
            igra.izvrsiNapad(napadac, meta, napad);
        }catch (IlegalanNapad e){
            assertEquals("Nije moguće izvršiti napad na prijatelja", e.getMessage());
        }
    }

    @Test
    void izvrsiNapadTest1() throws IlegalanNapad {
        Napad napad = new Napad("obicni napad", 20);
        Igrac meta = igra.getIgraci().get(0);
        Igrac napadac = igra.getIgraci().get(3);

        igra.registrujNapadZaIgraca(napad, napadac);
        igra.izvrsiNapad(napadac, meta, napad);

        assertEquals(85, meta.getZivotniPoeni());
    }

    @Test
    void izvrsiNapadTest2() throws IlegalanNapad {
        Napad napad = new Napad("obicni napad", 10);
        Igrac napadac = igra.getIgraci().get(1);
        Igrac meta = igra.getIgraci().get(4);

        napadac.setZivotniPoeni(15);

        igra.registrujNapadZaIgraca(napad, napadac);
        igra.izvrsiNapad(napadac, meta, napad);

        assertEquals(100 - 10 * 0.15, meta.getZivotniPoeni());
    }

    @Test
    void izvrsiSerijuNapadaIzuzetakTest1(){
        //jedan se ne moze izvrsiti
        Napad napad = new Napad("obicni napad", 10);
        Igrac napadac = igraci.get(0);
        HashMap<Igrac, Napad> metaNapad = new HashMap<>();
        metaNapad.put(igraci.get(3), napad);
        metaNapad.put(igraci.get(4), napad);
        metaNapad.put(igraci.get(1), napad);
        napadac.registrujNapad(napad);
        try{
            igra.izvrsiSerijuNapada(napadac, metaNapad);
        }catch (IlegalanNapad e){
            assertAll(
                    () -> assertEquals("Nije moguće izvršiti napad na prijatelja", e.getMessage()),
                    () -> assertEquals(100, igraci.get(3).getZivotniPoeni()),
                    () -> assertEquals(100, igraci.get(4).getZivotniPoeni())
            );
        }
    }

    @Test
    void izvrsiSerijuNapadaIzuzetakTest2(){
        //jedan se ne moze izvrsiti
        Napad napad = new Napad("obicni napad", 10);
        Igrac napadac = igraci.get(0);
        napadac.setZivotniPoeni(0);
        HashMap<Igrac, Napad> metaNapad = new HashMap<>();
        metaNapad.put(igraci.get(3), napad);
        metaNapad.put(igraci.get(4), napad);

        try{
            igra.izvrsiSerijuNapada(napadac, metaNapad);
        }catch (IlegalanNapad e){
            assertAll(
                    () -> assertEquals("Nije moguće napasti sa igračem koji nema preostalih životnih poena", e.getMessage()),
                    () -> assertEquals(100, igraci.get(3).getZivotniPoeni()),
                    () -> assertEquals(100, igraci.get(4).getZivotniPoeni())
            );
        }
    }

    @Test
    void izvrsiSerijuNapadaTest1() throws IlegalanNapad {
        Napad napad = new Napad("obicni napad", 10);
        Igrac napadac = igraci.get(0);
        HashMap<Igrac, Napad> metaNapad = new HashMap<>();
        metaNapad.put(igraci.get(3), napad);
        metaNapad.put(igraci.get(4), napad);

        igra.registrujNapadZaIgraca(napad, napadac);
        igra.izvrsiSerijuNapada(napadac, metaNapad);

        assertAll(
                () -> assertEquals(90, igraci.get(3).getZivotniPoeni()),
                () -> assertEquals(90, igraci.get(4).getZivotniPoeni())
        );
    }

    @Test
    void daLiJeMoguceNapastiTest() {
        Napad napad = new Napad("obicni napad", 10);
        Igrac napadac = igraci.get(0);
        Igrac meta1 = igraci.get(1);
        Igrac meta2 = igraci.get(3);

        //ne moze jer nema napad
        boolean rez1 = igra.daLiJeMoguceNapasti(napadac, meta2, napad);

        napadac.registrujNapad(napad);
        //moze
        boolean rez2 = igra.daLiJeMoguceNapasti(napadac, meta2, napad);
        //ne moze jer je heroj vs heroj
        boolean rez3 = igra.daLiJeMoguceNapasti(napadac, meta1, napad);

        napadac.setZivotniPoeni(0);
        //ne moze jer nema poena
        boolean rez4 = igra.daLiJeMoguceNapasti(napadac, meta2, napad);

        assertAll(
                () -> assertFalse(rez1),
                () -> assertTrue(rez2),
                () -> assertFalse(rez3),
                () -> assertFalse(rez4),
                () -> assertEquals(100, meta1.getZivotniPoeni()),
                () -> assertEquals(100, meta2.getZivotniPoeni())
        );
    }

    @Test
    void prikaziStanjeTest1() {
        igra.getIgraci().get(1).setZivotniPoeni(50);
        igra.getIgraci().get(3).setZivotniPoeni(20);

        String rezultat1 = igra.prikaziStanje();
        String tacanRezultat1 = "Heroji koji su u igri:\n";
        tacanRezultat1 += "spiderman (preostalo 50.0 životnih poena),\n" +
                "batman (preostalo 100.0 životnih poena),\nsuperman (preostalo 100.0 životnih poena).\n";

        tacanRezultat1 += "Neprijatelji koji su u igri:\n";
        tacanRezultat1 += "joker (preostalo 20.0 životnih poena),\n" +
                "sandman (preostalo 100.0 životnih poena),\nvenom (preostalo 100.0 životnih poena).\n";

        igra.getIgraci().get(0).setZivotniPoeni(70);
        String rezultat2 = igra.prikaziStanje();

        String tacanRezultat2 = "Heroji koji su u igri:\n";
        tacanRezultat2 += "spiderman (preostalo 50.0 životnih poena),\n" +
                "superman (preostalo 70.0 životnih poena),\nbatman (preostalo 100.0 životnih poena).\n";

        tacanRezultat2 += "Neprijatelji koji su u igri:\n";
        tacanRezultat2 += "joker (preostalo 20.0 životnih poena),\nsandman (preostalo 100.0 životnih poena),\n" +
                "venom (preostalo 100.0 životnih poena).\n";


        String finalTacanRezultat = tacanRezultat1;
        String finalTacanRezultat1 = tacanRezultat2;
        assertAll(
                () -> assertEquals(finalTacanRezultat, rezultat1),
                () -> assertEquals(finalTacanRezultat1, rezultat2)
        );
    }

    @Test
    void statusIgreTest1() {
        int status1 = igra.statusIgre();

        for(int i = 0; i < 3; i++){
            igra.getIgraci().get(i).setZivotniPoeni(0);
        }
        int status2 = igra.statusIgre();

        for(int i = 3; i < 6; i++){
            igra.getIgraci().get(i).setZivotniPoeni(0);
        }
        int status3 = igra.statusIgre();

        for(int i = 0; i < 6; i++){
            if(i < 3 ) {
                igra.getIgraci().get(i).setZivotniPoeni(110);
            }
            else{
                igra.getIgraci().get(i).setZivotniPoeni(0);
            }
        }
        int status4 = igra.statusIgre();

        assertAll(
                () -> assertEquals(3, status1),
                () -> assertEquals(2, status2),
                () -> assertEquals(0, status3),
                () -> assertEquals(1, status4)
        );
    }

    @Test
    void ukloniGubitnikeTest1() {
        for(int i = 0; i < 6; i++) {
            if(i%2 == 0) {
                igra.getIgraci().get(i).setZivotniPoeni(0);
            }
        }

        igra.ukloniGubitnike();
        assertAll(
                () -> assertEquals(3, igra.getIgraci().size()),
                () -> assertTrue(igra.getIgraci().contains(igraci.get(1))),
                () -> assertFalse(igra.getIgraci().contains(igraci.get(2)))
        );
    }

    @Test
    void ukloniGubitnikeTest2() {
        for(int i = 0; i < 6; i++) {
            if(i%2 == 1 || i == 0) {
                igra.getIgraci().get(i).setZivotniPoeni(0);
            }
        }

        igra.ukloniGubitnike();
        assertAll(
                () -> assertEquals(2, igra.getIgraci().size()),
                () -> assertTrue(igra.getIgraci().contains(igraci.get(2))),
                () -> assertFalse(igra.getIgraci().contains(igraci.get(1))),
                () -> assertFalse(igra.getIgraci().contains(igraci.get(0)))
        );
    }

    @Test
    void restartujTest() {
        for(int i = 0; i < 6; i++) {
            if(i%2 == 1 || i == 0) {
                igra.getIgraci().get(i).setZivotniPoeni(20);
            }
        }

        igra.restartuj();

        assertAll(
                () -> assertEquals(100, igra.getIgraci().get(0).getZivotniPoeni()),
                () -> assertEquals(100, igra.getIgraci().get(1).getZivotniPoeni()),
                () -> assertEquals(100, igra.getIgraci().get(2).getZivotniPoeni()),
                () -> assertEquals(100, igra.getIgraci().get(3).getZivotniPoeni()),
                () -> assertEquals(100, igra.getIgraci().get(4).getZivotniPoeni()),
                () -> assertEquals(100, igra.getIgraci().get(5).getZivotniPoeni())
        );
    }
}