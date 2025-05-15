package test;

import dao.FerramentaDAO;
import modelo.Ferramenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import servico.FerramentaService;

public class FerramentaServiceTest {

    private FerramentaDAO daoMock;
    private FerramentaService service;

    @BeforeEach
    public void setUp() {
        daoMock = mock(FerramentaDAO.class);
        service = new FerramentaService(daoMock);
    }

    @Test
    public void testInsertFerramentaBD_Success() {
        when(daoMock.maiorID()).thenReturn(10);
        when(daoMock.insertFerramentaBD(any(Ferramenta.class))).thenReturn(true);

        boolean result = service.insertFerramentaBD("Martelo", "Tramontina", 100);

        assertTrue(result);
        verify(daoMock, times(1)).insertFerramentaBD(any(Ferramenta.class));
    }

    @Test
    public void testInsertFerramentaBD_Failure() {
        when(daoMock.maiorID()).thenReturn(5);
        when(daoMock.insertFerramentaBD(any(Ferramenta.class))).thenReturn(false);

        boolean result = service.insertFerramentaBD("Martelo", "Tramontina", 100);

        assertFalse(result);
        verify(daoMock, times(1)).insertFerramentaBD(any(Ferramenta.class));
    }

    @Test
    public void testDeleteFerramentaBD_Success() {
        when(daoMock.deleteFerramentaBD(1)).thenReturn(true);

        boolean result = service.deleteFerramentaBD(1);

        assertTrue(result);
        verify(daoMock, times(1)).deleteFerramentaBD(1);
    }

    @Test
    public void testDeleteFerramentaBD_Failure() {
        when(daoMock.deleteFerramentaBD(1)).thenReturn(false);

        boolean result = service.deleteFerramentaBD(1);

        assertFalse(result);
        verify(daoMock, times(1)).deleteFerramentaBD(1);
    }

    @Test
    public void testUpdateFerramentaBD_Success() {
        when(daoMock.updateFerramentaBD(1, "Chave", "Gedore", 50)).thenReturn(true);

        boolean result = service.updateFerramentaBD(1, "Chave", "Gedore", 50);

        assertTrue(result);
        verify(daoMock, times(1)).updateFerramentaBD(1, "Chave", "Gedore", 50);
    }

    @Test
    public void testUpdateFerramentaBD_Failure() {
        when(daoMock.updateFerramentaBD(1, "Chave", "Gedore", 50)).thenReturn(false);

        boolean result = service.updateFerramentaBD(1, "Chave", "Gedore", 50);

        assertFalse(result);
        verify(daoMock, times(1)).updateFerramentaBD(1, "Chave", "Gedore", 50);
    }

    @Test
    public void testCarregaFerramenta() {
        Ferramenta ferramenta = new Ferramenta(1, "Furadeira", "Bosch", 300);
        when(daoMock.carregaFerramenta(1)).thenReturn(ferramenta);

        Ferramenta resultado = service.carregaFerramenta(1);

        assertNotNull(resultado);
        assertEquals("Furadeira", resultado.getNome());
        verify(daoMock, times(1)).carregaFerramenta(1);
    }

    @Test
    public void testGetListaFerramenta() {
        ArrayList<Ferramenta> lista = new ArrayList<>(Arrays.asList(
                new Ferramenta(1, "Martelo", "Tramontina", 100),
                new Ferramenta(2, "Chave de Fenda", "Philips", 50)
        ));
        when(daoMock.getListaFerramenta()).thenReturn(lista);

        ArrayList<Ferramenta> resultado = service.getListaFerramenta();

        assertEquals(2, resultado.size());
        verify(daoMock, times(1)).getListaFerramenta();
    }

    @Test
    public void testMaiorID() {
        when(daoMock.maiorID()).thenReturn(42);

        int maiorID = service.maiorID();

        assertEquals(42, maiorID);
        verify(daoMock, times(1)).maiorID();
    }
}
