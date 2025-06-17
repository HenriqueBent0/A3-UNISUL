package service;

import dao.EmprestimoDAO;
import modelo.Emprestimo;
import modelo.Ferramenta;
import servico.EmprestimoService;
import modelo.Amigo;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static dao.AmigoDAO.ListaAmigo;

/**
 * Testes unitários para a classe EmprestimoService.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoDAO emprestimoDAOMock;

    private EmprestimoService service;

    private List<Ferramenta> listaFerramenta;
    private List<Emprestimo> listaEmprestimo;

    // Configura o mock e as listas antes de cada teste
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new EmprestimoService() {
            {
                this.emprestimoDAO = emprestimoDAOMock;
            }
        };

        listaFerramenta = new ArrayList<>();
        listaEmprestimo = new ArrayList<>();
        ListaAmigo.clear();
    }

    // Testa empréstimo bem-sucedido
    @Test
    @Order(1)
    void testRegistrarEmprestimo_ComSucesso() {
        Ferramenta ferramenta = new Ferramenta(1, "Martelo", "Bosch", 10);
        listaFerramenta.add(ferramenta);
        ListaAmigo.add(new Amigo("João", 1, 123456));
        when(emprestimoDAOMock.insertEmprestimoBD(any())).thenReturn(true);

        boolean resultado = service.registrarEmprestimo("João", "1", "01/01/2025", listaFerramenta, listaEmprestimo);

        assertTrue(resultado);
    }

    // Testa falha quando a ferramenta não é encontrada
    @Test
    @Order(2)
    void testRegistrarEmprestimo_FerramentaNaoEncontrada() {
        ListaAmigo.add(new Amigo("João", 1, 123456));

        boolean resultado = service.registrarEmprestimo("João", "1", "01/01/2025", listaFerramenta, listaEmprestimo);

        assertFalse(resultado);
    }

    // Testa falha quando a ferramenta já está emprestada
    @Test
    @Order(3)
    void testRegistrarEmprestimo_FerramentaJaEmprestada() {
        Ferramenta ferramenta = new Ferramenta(1, "Martelo", "Bosch", 10);
        listaFerramenta.add(ferramenta);
        listaEmprestimo.add(new Emprestimo(1, "João", "Martelo", "01/01/2025", 1));
        ListaAmigo.add(new Amigo("João", 1, 123456));

        boolean resultado = service.registrarEmprestimo("João", "1", "01/01/2025", listaFerramenta, listaEmprestimo);

        assertFalse(resultado);
    }

    // Testa falha quando o amigo não é encontrado
    @Test
    @Order(4)
    void testRegistrarEmprestimo_AmigoNaoEncontrado() {
        Ferramenta ferramenta = new Ferramenta(1, "Martelo", "Bosch", 10);
        listaFerramenta.add(ferramenta);

        boolean resultado = service.registrarEmprestimo("João", "1", "01/01/2025", listaFerramenta, listaEmprestimo);

        assertFalse(resultado);
    }

    // Testa o construtor padrão do serviço
    @Test
    @Order(5)
    void testConstrutorPadrao() {
        EmprestimoService servicePadrao = new EmprestimoService();
        assertNotNull(servicePadrao);
    }

    // Testa falha quando a ferramenta já está emprestada (mensagem alternativa)
    @Test
    @Order(6)
    void testRegistrarEmprestimo_FerramentaJaEmprestada_Mensagem() {
        Ferramenta ferramenta = new Ferramenta(1, "Martelo", "Bosch", 10);
        listaFerramenta.add(ferramenta);
        listaEmprestimo.add(new Emprestimo(1, "Maria", "Martelo", "01/01/2025", 1));
        ListaAmigo.add(new Amigo("João", 2, 654321));

        boolean resultado = service.registrarEmprestimo("João", "1", "02/01/2025", listaFerramenta, listaEmprestimo);

        assertFalse(resultado);
    }

    // Testa getters e setters da entidade Emprestimo
    @Test
    @Order(7)
    void testEmprestimoGettersESetters() {
        Emprestimo emprestimo = new Emprestimo(1, "João", "01/01/2025", "Martelo", 1);

        assertEquals(1, emprestimo.getId());
        assertEquals("João", emprestimo.getNomeAmigo());
        assertEquals("01/01/2025", emprestimo.getData());
        assertEquals(1, emprestimo.getIdFerramenta());

        emprestimo.setId(2);
        assertEquals(2, emprestimo.getId());
    }
}
