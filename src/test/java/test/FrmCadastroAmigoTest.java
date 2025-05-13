package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe de testes para a funcionalidade de cadastro de amigo.
 */
public class FrmCadastroAmigoTest {

    private AmigoFake amigoFake;


    @BeforeEach
    public void setUp() {
        amigoFake = new AmigoFake();
    }


    @Test
    public void testSetNome() {
        amigoFake.setNome("João");
        assertEquals("João", amigoFake.getCampoNome());
    }


    @Test
    public void testSetTelefone() {
        amigoFake.setTelefone("123456789");
        assertEquals("123456789", amigoFake.getCampoTelefone());
    }


    @Test
    public void testMostrarMensagem() {
        amigoFake.mostrarMensagem("Cadastro realizado com sucesso!");
        assertEquals("Cadastro realizado com sucesso!", amigoFake.getMensagem());
    }


    @Test
    public void testCadastrar() {
        amigoFake.setNome("João");
        amigoFake.setTelefone("123456789");
        amigoFake.cadastrar(); 
    }
}
