package test;

import modelo.Devolucao;
import dao.DevolucaoDAO;
import servico.DevolucaoService;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DevolucaoServiceTest {

    @Mock
    private DevolucaoDAO devolucaoDAOMock;

    private DevolucaoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new DevolucaoService(devolucaoDAOMock); // Recomendado: criar esse construtor no DevolucaoService
    }

    @Test
    @Order(1)
    void testInsertDevolucao() {
        when(devolucaoDAOMock.insertDevolucaoBD(any())).thenReturn(true);

        boolean result = service.insertDevolucao("João", 1, "2025-04-21", "Martelo");

        assertTrue(result, "A devolução deveria ter sido inserida com sucesso.");
        verify(devolucaoDAOMock, times(1)).insertDevolucaoBD(any());
    }

    @Test
    @Order(2)
    void testGetListaDevolucao() {
        ArrayList<Devolucao> listaSimulada = new ArrayList<>();
        listaSimulada.add(new Devolucao("João", 1, "2025-04-21", 1, "Martelo"));

        when(devolucaoDAOMock.getListaDevolucao()).thenReturn(listaSimulada);

        ArrayList<Devolucao> resultado = service.getListaDevolucao();

        assertNotNull(resultado, "A lista retornada não deveria ser nula.");
        assertEquals(1, resultado.size(), "A lista deveria conter 1 item.");
        assertEquals("João", resultado.get(0).getNomeAmigo());
        assertEquals("Martelo", resultado.get(0).getNomeDaFerramenta());
    }


    @Test
    @Order(3)
    void testConstrutorPadrao() {
        DevolucaoService servicePadrao = new DevolucaoService();
        assertNotNull(servicePadrao);
    }
}
