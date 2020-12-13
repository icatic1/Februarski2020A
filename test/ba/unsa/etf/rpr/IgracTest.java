package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IgracTest {

    @Test
    void testKonstruktor1() {
        Igrac igrac = new Heroj("superman", 5);

        assertAll(
                () ->assertEquals("superman", igrac.getNadimak()),
                () -> assertEquals(5, ((Heroj)igrac).getOdbrambeniPoeni()),
                () -> assertEquals(100, igrac.getZivotniPoeni())
        );
    }

    @Test
    void testKonstruktor2() {
        Igrac igrac = new Neprijatelj("joker");

        assertAll(
                () ->assertEquals("joker", igrac.getNadimak()),
                () -> assertEquals(100, igrac.getZivotniPoeni())
        );

    }

    @Test
    void testGetterSetter() {
        Igrac igrac = new Heroj("superman", 5);
        igrac.setNadimak("spiderman");
        igrac.setZivotniPoeni(150);
        assertAll(
                () -> assertEquals("spiderman", igrac.getNadimak()),
                () -> assertEquals(150, igrac.getZivotniPoeni()),
                () -> assertEquals(0, igrac.getNapadi().size())
        );
    }

    @Test
    void testRegistrujNapad1() {
        Igrac igrac = new Heroj("superman", 5);
        Napad napad = new Napad("magicni napad", 15);
        igrac.registrujNapad(napad);
        assertAll(
                () -> assertEquals(1, igrac.getNapadi().size()),
                () -> assertTrue(igrac.getNapadi().contains(napad))
        );
    }

    @Test
    void testRegistrujNapad2() {
        Igrac igrac = new Heroj("superman", 5);
        Napad napad = new Napad("magicni napad", 15);
        igrac.registrujNapad(napad);
        napad.setDjelovanje(20);
        try{
            igrac.registrujNapad(napad);
        }catch (IllegalArgumentException e){
            assertEquals("Napad sa ovim nazivom je već registrovan", e.getMessage());
        }
    }

    @Test
    void primiNapadTest1() {
        Napad napad = new Napad("magicni napad", 15);
        Igrac meta = new Neprijatelj("neprijatelj");

        meta.primiNapad(napad);
        assertEquals(85, meta.getZivotniPoeni());
    }

    @Test
    void primiNapadTest2() {
        Napad napad = new Napad("magicni napad", 15);
        Igrac meta = new Heroj("heroj", 20);

        meta.primiNapad(napad);
        assertEquals(100, meta.getZivotniPoeni());
    }

    @Test
    void primiNapadTest3() {
        Napad napad = new Napad("magicni napad", 15);
        Igrac meta = new Heroj("heroj", 10);

        meta.primiNapad(napad);
        assertEquals(95, meta.getZivotniPoeni());
    }

    @Test
    void primiNapadTest4() {
        Napad napad = new Napad("magicni napad", 20);
        Igrac meta = new Heroj("heroj", 3);

        meta.primiNapad(napad, 0.5);
        assertEquals(93, meta.getZivotniPoeni());
    }

    @Test
    void napadniTest1() throws IlegalanNapad {
        Napad napad = new Napad("magicni napad", 20);
        Igrac napadac = new Neprijatelj("neprijatelj");
        Igrac meta = new Heroj("heroj", 3);

        napadac.registrujNapad(napad);
        napadac.napadni("magicni napad", meta);

        assertEquals(83, meta.getZivotniPoeni());
    }

    @Test
    void napadniTest2() throws IlegalanNapad {
        Napad napad = new Napad("magicni napad", 20);
        Igrac meta = new Neprijatelj("neprijatelj");
        Igrac napadac = new Heroj("heroj", 3);

        napadac.registrujNapad(napad);
        napadac.napadni("magicni napad", meta);

        assertEquals(80, meta.getZivotniPoeni());
    }

    @Test
    void napadniIzuzetakTest1(){
        Igrac meta = new Neprijatelj("neprijatelj");
        Igrac napadac = new Heroj("heroj", 3);

        try{
            napadac.napadni("magični napad", meta);
        }catch (IlegalanNapad e){
            assertEquals("heroj ne može izvršiti napad magični napad", e.getMessage());
        }
    }

    @Test
    void napadniIzuzetakTest2(){
        Igrac meta = new Neprijatelj("neprijatelj");
        Igrac napadac = new Heroj("heroj", 3);

        napadac.registrujNapad(new Napad("magični napad", 5));
        meta.setZivotniPoeni(0);
        try{
            napadac.napadni("magični napad", meta);
        }catch (IlegalanNapad e){
            assertEquals("Ovaj igrač je već završio igru", e.getMessage());
        }
    }

    @Test
    void napadniIzuzetakTest3(){
        Igrac meta = new Neprijatelj("neprijatelj1");
        Igrac napadac = new Neprijatelj("neprijatelj2");
        napadac.registrujNapad(new Napad("magični napad", 5));
        try{
            napadac.napadni("magični napad", meta);
        }catch (IlegalanNapad e){
            assertEquals("Nije moguće izvršiti napad na prijatelja", e.getMessage());
        }
    }

    @Test
    void napadniIzuzetakTest4(){
        Igrac meta = new Heroj("heroj1",7);
        Igrac napadac = new Heroj("heroj2",3);

        napadac.registrujNapad(new Napad("magični napad", 5));
        try{
            napadac.napadni("magični napad", meta);
        }catch (IlegalanNapad e){
            assertEquals("Nije moguće izvršiti napad na prijatelja", e.getMessage());
        }
    }

    @Test
    void napadniIzuzetakTest5(){
        Igrac napadac = new Neprijatelj("neprijatelj");
        Igrac meta = new Heroj("heroj", 3);

        meta.setZivotniPoeni(0);
        napadac.registrujNapad(new Napad("magični napad", 5));
        try{
            napadac.napadni("magični napad", meta);
        }catch (IlegalanNapad e){
            assertEquals("Ovaj igrač je već završio igru", e.getMessage());
        }
    }

    @Test
    void napadniTest3() throws IlegalanNapad {
        Napad napad = new Napad("magicni napad", 17);
        Igrac meta = new Heroj("heroj", 10);
        Igrac napadac = new Neprijatelj("neprijatelj");

        napadac.registrujNapad(napad);
        napadac.napadni("magicni napad", meta);
        assertEquals(93, meta.getZivotniPoeni());
    }

    @Test
    void napadniTest4() throws IlegalanNapad {
        Napad napad = new Napad("magicni napad", 30);
        Igrac meta = new Heroj("heroj", 3);
        Igrac napadac = new Neprijatelj("neprijatelj");

        napadac.registrujNapad(napad);
        napadac.napadni("magicni napad", meta, 0.2);
        assertEquals(100 - 30*0.2 + 3, meta.getZivotniPoeni());
    }

    @Test
    void napadniTest5() throws IlegalanNapad {
        Napad napad = new Napad("magicni napad", 27);
        Igrac napadac = new Heroj("heroj", 3);
        Igrac meta = new Neprijatelj("neprijatelj");

        napadac.registrujNapad(napad);
        napadac.napadni("magicni napad", meta, 0.1);
        assertEquals(100 - 27*0.1, meta.getZivotniPoeni());
    }

    @Test
    void napadniTest6() throws IlegalanNapad {
        //moze se odbranit jer 3 > 1
        Napad napad = new Napad("magicni napad", 1);
        Igrac meta = new Heroj("heroj", 3);
        Igrac napadac = new Neprijatelj("neprijatelj");

        napadac.registrujNapad(napad);
        napadac.napadni("magicni napad", meta, 0.2);
        assertEquals(100, meta.getZivotniPoeni());
    }

}