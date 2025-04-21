package test;

import modelo.Amigo;
import dao.AmigoDAO;
import servico.AmigoService;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AmigoServiceTest {

    @Mock
    private AmigoDAO amigoDAOMock;

    private AmigoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new AmigoService(amigoDAOMock); // Você vai precisar adaptar o AmigoService pra aceitar o DAO via construtor
    }

    @Test
    @Order(1)
    void testInsertAmigoBD() {
        when(amigoDAOMock.insertAmigoBD(any())).thenReturn(true);
        boolean result = service.insertAmigoBD("TesteAmigo", 0, 999999999);
        assertTrue(result);
    }

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
@Test
@Order(5)
void testConstrutorPadrao(){
    AmigoService servicePadrao = new AmigoService();
    assertNotNull(servicePadrao);
}
}
