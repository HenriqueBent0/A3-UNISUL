package test;

import modelo.Ferramenta;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FerramentaTest {

    @Test
    void testConstrutorComParametros() {

        Ferramenta ferramenta = new Ferramenta(1, "Martelo", "Tramontina", 100);
        assertEquals(1, ferramenta.getId());
        assertEquals("Martelo", ferramenta.getNome());
        assertEquals("Tramontina", ferramenta.getMarca());
        assertEquals(100, ferramenta.getValor());
    }

    @Test
    void testConstrutorPadrao() {

        Ferramenta ferramenta = new Ferramenta();
        assertEquals(0, ferramenta.getId());
        assertEquals("", ferramenta.getNome());
        assertEquals("", ferramenta.getMarca());
        assertEquals(0, ferramenta.getValor());
    }

    @Test
    void testSetGetId() {
        Ferramenta ferramenta = new Ferramenta();
        ferramenta.setId(10);
        assertEquals(10, ferramenta.getId());
    }

    @Test
    void testSetGetNome() {
        Ferramenta ferramenta = new Ferramenta();
        ferramenta.setNome("Chave Inglesa");
        assertEquals("Chave Inglesa", ferramenta.getNome());
    }

    @Test
    void testSetGetMarca() {
        Ferramenta ferramenta = new Ferramenta();
        ferramenta.setMarca("Vonder");
        assertEquals("Vonder", ferramenta.getMarca());
    }

    @Test
    void testSetGetValor() {
        Ferramenta ferramenta = new Ferramenta();
        ferramenta.setValor(150);
        assertEquals(150, ferramenta.getValor());
    }
}
