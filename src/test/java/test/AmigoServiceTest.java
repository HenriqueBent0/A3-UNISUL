package test;

import modelo.Amigo;


import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import servico.AmigoService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AmigoServiceTest {

    private AmigoService service;

    @BeforeEach
    void setup() {
        service = new AmigoService();
    }

    @Test
    @Order(1)
    void testInsertAmigoBD() {
        boolean result = service.insertAmigoBD("TesteAmigo", 0, 999999999);
        assertTrue(result);
    }

    @Test
    @Order(2)
    void testGetListaAmigo() {
        ArrayList<Amigo> lista = service.getListaAmigo();
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(a -> a.getNome().equals("TesteAmigo")));
    }

    @Test
    @Order(3)
    void testUpdateAmigoBD() {
        ArrayList<Amigo> lista = service.getListaAmigo();
        Amigo amigo = lista.stream().filter(a -> a.getNome().equals("TesteAmigo")).findFirst().orElse(null);
        assertNotNull(amigo);

        boolean result = service.updateAmigoBD("AmigoAtualizado", amigo.getId(), 123123123);
        assertTrue(result);

        Amigo atualizado = service.carregaAmigo(amigo.getId());
        assertEquals("AmigoAtualizado", atualizado.getNome());
    }

    @Test
@Order(4)
void testDeleteAmigoBD() {
    // Insere um amigo só pra testar a exclusão
    service.insertAmigoBD("AmigoTesteDelete", 0, 123456);

    // Busca o amigo recém-inserido
    ArrayList<Amigo> lista = service.getListaAmigo();
    Amigo amigo = lista.stream()
        .filter(a -> a.getNome().equals("AmigoTesteDelete"))
        .findFirst()
        .orElse(null);

    assertNotNull(amigo);

    // Tenta deletar
    boolean result = service.deleteAmigoBD(amigo.getId());
    assertTrue(result);

    // Verifica se foi realmente deletado
    Amigo deletado = service.carregaAmigo(amigo.getId());
    assertNull(deletado);
}

}
