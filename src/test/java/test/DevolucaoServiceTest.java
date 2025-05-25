package test;

import modelo.Devolucao;
import dao.DevolucaoDAO;
import servico.DevolucaoService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe DevolucaoService.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DevolucaoServiceTest {

    @Mock
    private DevolucaoDAO devolucaoDAOMock;

    private DevolucaoService service;

    // Inicializa mocks e instancia o serviço antes de cada teste
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new DevolucaoService(devolucaoDAOMock); // Construtor com DAO injetado
    }

    // Testa inserção de devolução
    @Test
    @Order(1)
    void testInsertDevolucao() {
        when(devolucaoDAOMock.insertDevolucaoBD(any())).thenReturn(true);

        boolean result = service.insertDevolucao("João", 1, "2025-04-21", "Martelo");

        assertTrue(result, "A devolução deveria ter sido inserida com sucesso.");
        verify(devolucaoDAOMock, times(1)).insertDevolucaoBD(any());
    }

    // Testa recuperação da lista de devoluções
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

    // Testa o construtor padrão do serviço
    @Test
    @Order(3)
    void testConstrutorPadrao() {
        DevolucaoService servicePadrao = new DevolucaoService();
        assertNotNull(servicePadrao);
    }

    // Testa o construtor padrão da entidade Devolucao
    @Test
    void testConstrutorPadrao_DeveInicializarComValoresPadrao() {
        Devolucao devolucao = new Devolucao();

        assertEquals("", devolucao.getNomeAmigo());
        assertEquals(0, devolucao.getIdFerramenta());
        assertEquals("", devolucao.getData());
        assertEquals(0, devolucao.getId());
        assertEquals("", devolucao.getNomeDaFerramenta());
    }

    // Testa o construtor com parâmetros da entidade Devolucao
    @Test
    void testConstrutorComParametros_DeveInicializarCorretamente() {
        Devolucao devolucao = new Devolucao("Carlos", 2, "2025-05-10", 5, "Chave de Fenda");

        assertEquals("Carlos", devolucao.getNomeAmigo());
        assertEquals(2, devolucao.getIdFerramenta());
        assertEquals("2025-05-10", devolucao.getData());
        assertEquals(5, devolucao.getId());
        assertEquals("Chave de Fenda", devolucao.getNomeDaFerramenta());
    }

    // Testa os métodos getters e setters da entidade Devolucao
    @Test
    void testSettersAndGetters_DeveDefinirEAcessarValoresCorretamente() {
        Devolucao devolucao = new Devolucao();

        devolucao.setNomeAmigo("Maria");
        devolucao.setIdFerramenta(3);
        devolucao.setData("2025-06-15");
        devolucao.setId(10);
        devolucao.setNomeDaFerramenta("Alicate");

        assertEquals("Maria", devolucao.getNomeAmigo());
        assertEquals(3, devolucao.getIdFerramenta());
        assertEquals("2025-06-15", devolucao.getData());
        assertEquals(10, devolucao.getId());
        assertEquals("Alicate", devolucao.getNomeDaFerramenta());
    }
}
