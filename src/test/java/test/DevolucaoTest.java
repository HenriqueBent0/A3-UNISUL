package test;

import modelo.Devolucao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DevolucaoTest {

    @Test
    void testSetGetNomeAmigo() {
        Devolucao devolucao = new Devolucao();
        devolucao.setNomeAmigo("Carlos");
        assertEquals("Carlos", devolucao.getNomeAmigo());
    }

    @Test
    void testSetGetIdFerramenta() {
        Devolucao devolucao = new Devolucao();
        devolucao.setIdFerramenta(10);
        assertEquals(10, devolucao.getIdFerramenta());
    }

    @Test
    void testSetGetData() {
        Devolucao devolucao = new Devolucao();
        devolucao.setData("2025-04-24");
        assertEquals("2025-04-24", devolucao.getData());
    }

    @Test
    void testSetGetId() {
        Devolucao devolucao = new Devolucao();
        devolucao.setId(5);
        assertEquals(5, devolucao.getId());
    }

    @Test
    void testSetGetNomeDaFerramenta() {
        Devolucao devolucao = new Devolucao();
        devolucao.setNomeDaFerramenta("Furadeira");
        assertEquals("Furadeira", devolucao.getNomeDaFerramenta());
    }

    @Test
    void testValoresPadrao() {
        Devolucao devolucao = new Devolucao();
        assertEquals(0, devolucao.getId());
        assertEquals(0, devolucao.getIdFerramenta());
        assertEquals("", devolucao.getNomeAmigo());
        assertEquals("", devolucao.getData());
        assertEquals("", devolucao.getNomeDaFerramenta());
    }
}
