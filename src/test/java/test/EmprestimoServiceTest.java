package test;

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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoDAO emprestimoDAOMock;

    private EmprestimoService service;

    private List<Ferramenta> listaFerramenta;
    private List<Emprestimo> listaEmprestimo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new EmprestimoService() {
            {
                this.emprestimoDAO = emprestimoDAOMock;
            }
        };

        // Resetar dados simulados
        listaFerramenta = new ArrayList<>();
        listaEmprestimo = new ArrayList<>();
        ListaAmigo.clear();
    }

    @Test
    @Order(1)
    void testRegistrarEmprestimo_ComSucesso() {
        Ferramenta ferramenta = new Ferramenta(1,"Martelo", "Bosch", 10);
        listaFerramenta.add(ferramenta);

        ListaAmigo.add(new Amigo("João", 1, 123456));

        when(emprestimoDAOMock.insertEmprestimoBD(any())).thenReturn(true);

        boolean resultado = service.registrarEmprestimo("João", "1", "01/01/2025", listaFerramenta, listaEmprestimo);
        assertTrue(resultado);
    }

    @Test
    @Order(2)
    void testRegistrarEmprestimo_FerramentaNaoEncontrada() {
        ListaAmigo.add(new Amigo("João", 1, 123456));
        boolean resultado = service.registrarEmprestimo("João", "1", "01/01/2025", listaFerramenta, listaEmprestimo);
        assertFalse(resultado);
    }

    @Test
    @Order(3)
    void testRegistrarEmprestimo_FerramentaJaEmprestada() {
        Ferramenta ferramenta = new Ferramenta(1,"Martelo", "Bosch", 10);
        listaFerramenta.add(ferramenta);
        listaEmprestimo.add(new Emprestimo(1,"João", "Martelo", "01/01/2025", 1));
        ListaAmigo.add(new Amigo("João", 1, 123456));

        boolean resultado = service.registrarEmprestimo("João", "1", "01/01/2025", listaFerramenta, listaEmprestimo);
        assertFalse(resultado);
    }

    @Test
    @Order(4)
    void testRegistrarEmprestimo_AmigoNaoEncontrado() {
        Ferramenta ferramenta = new Ferramenta(1,"Martelo", "Bosch", 10);
        listaFerramenta.add(ferramenta);

        boolean resultado = service.registrarEmprestimo("João", "1", "01/01/2025", listaFerramenta, listaEmprestimo);
        assertFalse(resultado);
    }

    @Test
    @Order(5)
    void testConstrutorPadrao() {
        EmprestimoService servicePadrao = new EmprestimoService();
        assertNotNull(servicePadrao);
    }
}
