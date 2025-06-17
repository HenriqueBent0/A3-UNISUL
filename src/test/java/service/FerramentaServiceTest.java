package service;

import dao.FerramentaDAO;
import modelo.Ferramenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.FerramentaService;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe FerramentaService.
 */
public class FerramentaServiceTest {

    private FerramentaDAO daoMock;
    private FerramentaService service;

    // Configura os mocks antes de cada teste
    @BeforeEach
    public void setUp() {
        daoMock = mock(FerramentaDAO.class);
        service = new FerramentaService(daoMock);
    }

    // Testa inserção com sucesso
    @Test
    public void testInsertFerramentaBD_Success() {
        when(daoMock.maiorID()).thenReturn(10);
        when(daoMock.insertFerramentaBD(any(Ferramenta.class))).thenReturn(true);

        boolean result = service.insertFerramentaBD("Martelo", "Tramontina", 100);

        assertTrue(result);
        verify(daoMock, times(1)).insertFerramentaBD(any(Ferramenta.class));
    }

    // Testa falha ao inserir ferramenta
    @Test
    public void testInsertFerramentaBD_Failure() {
        when(daoMock.maiorID()).thenReturn(5);
        when(daoMock.insertFerramentaBD(any(Ferramenta.class))).thenReturn(false);

        boolean result = service.insertFerramentaBD("Martelo", "Tramontina", 100);

        assertFalse(result);
        verify(daoMock, times(1)).insertFerramentaBD(any(Ferramenta.class));
    }

    // Testa exclusão com sucesso
    @Test
    public void testDeleteFerramentaBD_Success() {
        when(daoMock.deleteFerramentaBD(1)).thenReturn(true);

        boolean result = service.deleteFerramentaBD(1);

        assertTrue(result);
        verify(daoMock, times(1)).deleteFerramentaBD(1);
    }

    // Testa falha ao excluir ferramenta
    @Test
    public void testDeleteFerramentaBD_Failure() {
        when(daoMock.deleteFerramentaBD(1)).thenReturn(false);

        boolean result = service.deleteFerramentaBD(1);

        assertFalse(result);
        verify(daoMock, times(1)).deleteFerramentaBD(1);
    }

    // Testa atualização com sucesso
    @Test
    public void testUpdateFerramentaBD_Success() {
        when(daoMock.updateFerramentaBD(1, "Chave", "Gedore", 50)).thenReturn(true);

        boolean result = service.updateFerramentaBD(1, "Chave", "Gedore", 50);

        assertTrue(result);
        verify(daoMock, times(1)).updateFerramentaBD(1, "Chave", "Gedore", 50);
    }

    // Testa falha ao atualizar ferramenta
    @Test
    public void testUpdateFerramentaBD_Failure() {
        when(daoMock.updateFerramentaBD(1, "Chave", "Gedore", 50)).thenReturn(false);

        boolean result = service.updateFerramentaBD(1, "Chave", "Gedore", 50);

        assertFalse(result);
        verify(daoMock, times(1)).updateFerramentaBD(1, "Chave", "Gedore", 50);
    }

    // Testa carregamento de uma ferramenta
    @Test
    public void testCarregaFerramenta() {
        Ferramenta ferramenta = new Ferramenta(1, "Furadeira", "Bosch", 300);
        when(daoMock.carregaFerramenta(1)).thenReturn(ferramenta);

        Ferramenta resultado = service.carregaFerramenta(1);

        assertNotNull(resultado);
        assertEquals("Furadeira", resultado.getNome());
        verify(daoMock, times(1)).carregaFerramenta(1);
    }

    // Testa recuperação da lista de ferramentas
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

    // Testa retorno do maior ID
    @Test
    public void testMaiorID() {
        when(daoMock.maiorID()).thenReturn(42);

        int maiorID = service.maiorID();

        assertEquals(42, maiorID);
        verify(daoMock, times(1)).maiorID();
    }
}
