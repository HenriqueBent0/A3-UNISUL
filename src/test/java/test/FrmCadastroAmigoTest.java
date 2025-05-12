package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Classe de testes para a funcionalidade de cadastro de amigo.
 */
public class FrmCadastroAmigoTest {

    private AmigoFake amigoFake;

    // Usando @BeforeEach ao invés de @Before para JUnit 5
    @BeforeEach
    public void setUp() {
        // Instanciamos a classe fake antes de cada teste
        amigoFake = new AmigoFake();
    }

    @Test
    public void testSetNome() {
        // Define o nome
        amigoFake.setNome("João");

        // Verifica se o nome foi definido corretamente
        assertEquals("João", amigoFake.getCampoNome());
    }

    @Test
    public void testSetTelefone() {
        // Define o telefone
        amigoFake.setTelefone("123456789");

        // Verifica se o telefone foi definido corretamente
        assertEquals("123456789", amigoFake.getCampoTelefone());
    }

    @Test
    public void testMostrarMensagem() {
        // Simula uma mensagem
        amigoFake.mostrarMensagem("Cadastro realizado com sucesso!");

        // Verifica se a mensagem foi armazenada corretamente
        assertEquals("Cadastro realizado com sucesso!", amigoFake.getMensagem());
    }

    @Test
    public void testCadastrar() {
        // Define o nome e telefone
        amigoFake.setNome("João");
        amigoFake.setTelefone("123456789");

        // Simula o cadastro
        amigoFake.cadastrar();

        // Aqui você pode adicionar alguma verificação específica se necessário
        // Para verificar a impressão no console, seria necessário redirecionar o System.out
        // ou validar de alguma outra forma dependendo da sua implementação.
    }
}
