package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NapadTest {
    @Test
    void testKonstruktor() {
        Napad napad = new Napad("vodeni napad", 12);
        assertAll(
                () -> assertEquals(12, napad.getDjelovanje()),
                () -> assertEquals("vodeni napad", napad.getNazivNapada())
        );
    }
}