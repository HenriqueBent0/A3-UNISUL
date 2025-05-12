package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe de testes para a funcionalidade de cadastro de amigo.
 */
public class FrmCadastroAmigoTest {

    private AmigoFake amigoFake;

    /**
     * Inicializa a instância de AmigoFake antes de cada teste.
     */
    @BeforeEach
    public void setUp() {
        amigoFake = new AmigoFake();
    }

    /**
     * Testa a definição e recuperação do nome.
     */
    @Test
    public void testSetNome() {
        amigoFake.setNome("João");
        assertEquals("João", amigoFake.getCampoNome());
    }

    /**
     * Testa a definição e recuperação do telefone.
     */
    @Test
    public void testSetTelefone() {
        amigoFake.setTelefone("123456789");
        assertEquals("123456789", amigoFake.getCampoTelefone());
    }

    /**
     * Testa o método de exibição de mensagem (substituído para testes).
     */
    @Test
    public void testMostrarMensagem() {
        amigoFake.mostrarMensagem("Cadastro realizado com sucesso!");
        assertEquals("Cadastro realizado com sucesso!", amigoFake.getMensagem());
    }

    /**
     * Testa a simulação do cadastro.
     * OBS: Aqui não há assertiva, mas o método deve rodar sem erros.
     */
    @Test
    public void testCadastrar() {
        amigoFake.setNome("João");
        amigoFake.setTelefone("123456789");
        amigoFake.cadastrar(); // Simula ação de cadastro
    }
}
