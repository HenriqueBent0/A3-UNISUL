package test;

import modelo.Amigo;
import dao.AmigoDAO;
import servico.AmigoService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe AmigoService usando Mockito.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AmigoServiceTest {

    @Mock
    private AmigoDAO amigoDAOMock; // Mock do DAO

    private AmigoService service;

    // Inicializa o mock e a instância do service antes de cada teste
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new AmigoService(amigoDAOMock); // Injeta o mock no serviço
    }

    // Testa inserção de um amigo no banco de dados
    @Test
    @Order(1)
    void testInsertAmigoBD() {
        when(amigoDAOMock.insertAmigoBD(any())).thenReturn(true);
        boolean result = service.insertAmigoBD("TesteAmigo", 0, 999999999);
        assertTrue(result);
    }

    // Testa recuperação da lista de amigos
    @Test
    @Order(2)
    void testGetListaAmigo() {
        List<Amigo> amigos = new ArrayList<>();
        amigos.add(new Amigo("TesteAmigo", 1, 999999999));
        when(amigoDAOMock.getListaAmigo()).thenReturn(new ArrayList<>(amigos));

        ArrayList<Amigo> lista = service.getListaAmigo();
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(a -> a.getNome().equals("TesteAmigo")));
    }

    // Testa atualização de um amigo no banco de dados
    @Test
    @Order(3)
    void testUpdateAmigoBD() {
        Amigo mockAmigo = new Amigo("TesteAmigo", 1, 999999999);
        when(amigoDAOMock.getListaAmigo()).thenReturn(new ArrayList<>(List.of(mockAmigo)));
        when(amigoDAOMock.updateAmigoBD(any())).thenReturn(true);
        when(amigoDAOMock.carregaAmigo(1)).thenReturn(new Amigo("AmigoAtualizado", 1, 123123123));

        boolean result = service.updateAmigoBD("AmigoAtualizado", 1, 123123123);
        assertTrue(result);

        Amigo atualizado = service.carregaAmigo(1);
        assertEquals("AmigoAtualizado", atualizado.getNome());
    }

    // Testa exclusão de um amigo no banco de dados
    @Test
    @Order(4)
    void testDeleteAmigoBD() {
        Amigo amigo = new Amigo("AmigoTesteDelete", 2, 123456);
        when(amigoDAOMock.insertAmigoBD(any())).thenReturn(true);
        when(amigoDAOMock.getListaAmigo()).thenReturn(new ArrayList<>(List.of(amigo)));
        when(amigoDAOMock.deleteAmigoBD(2)).thenReturn(true);
        when(amigoDAOMock.carregaAmigo(2)).thenReturn(null);

        boolean result = service.deleteAmigoBD(2);
        assertTrue(result);

        Amigo deletado = service.carregaAmigo(2);
        assertNull(deletado);
    }

    // Testa se o construtor padrão funciona corretamente
    @Test
    @Order(5)
    void testConstrutorPadrao() {
        AmigoService servicePadrao = new AmigoService();
        assertNotNull(servicePadrao);
    }

    // Testa os métodos getter e setter do DAO
    @Test
    @Order(6)
    void testGetAndSetDao() {
        AmigoService service = new AmigoService();
        AmigoDAO mockDao = mock(AmigoDAO.class);

        service.setDao(mockDao);
        assertEquals(mockDao, service.getDao());
    }
}
