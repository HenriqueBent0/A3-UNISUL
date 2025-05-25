package frm;

import controle.AmigoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.AmigoService;
import visao.FrmCadastrarAmigo;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe FrmCadastrarAmigo e seu controlador
 * AmigoController. Usa mock de AmigoService e uma tela fake para testar o
 * comportamento da interface.
 */
public class FrmCadastroAmigoTest {

    private AmigoFake telaFake;
    private AmigoService amigoServiceMock;
    private AmigoController controller;

    /**
     * Configura a tela fake, o mock do serviço e o controlador antes de cada
     * teste.
     */
    @BeforeEach
    public void setUp() {
        telaFake = new AmigoFake();
        amigoServiceMock = mock(AmigoService.class);
        controller = new AmigoController(telaFake, amigoServiceMock);
    }

    /**
     * Verifica se exibe mensagem de erro quando os campos estão vazios.
     */
    @Test
    public void deveExibirMensagemDeErroQuandoCamposEstaoVazios() {
        telaFake.getJTFNome().setText("");
        telaFake.getJTFTelefone().setText("");
        controller.cadastrar();
        assertEquals("O nome não pode estar em branco.", telaFake.getMensagem());
    }

    /**
     * Verifica se exibe mensagem de erro quando telefone contém letras.
     */
    @Test
    public void deveExibirMensagemDeErroQuandoTelefoneNaoEhNumero() {
        telaFake.getJTFNome().setText("João");
        telaFake.getJTFTelefone().setText("abc123");
        controller.cadastrar();
        assertEquals("Telefone deve conter apenas números.", telaFake.getMensagem());
    }

    /**
     * Testa cadastro com sucesso, simulando o comportamento do serviço.
     */
    @Test
    public void deveCadastrarAmigoComSucesso() {
        telaFake.getJTFNome().setText("Maria");
        telaFake.getJTFTelefone().setText("123456");
        when(amigoServiceMock.maiorID()).thenReturn(10);
        when(amigoServiceMock.insertAmigoBD(anyString(), anyInt(), anyInt())).thenReturn(true);
        controller.cadastrar();
        assertEquals("Amigo cadastrado com sucesso!", telaFake.getMensagem());
    }

    /**
     * Testa o cenário de falha na inserção, verificando mensagem de erro.
     */
    @Test
    public void deveExibirMensagemDeErroQuandoInsercaoFalha() {
        telaFake.getJTFNome().setText("Carlos");
        telaFake.getJTFTelefone().setText("987654");
        when(amigoServiceMock.maiorID()).thenReturn(5);
        when(amigoServiceMock.insertAmigoBD(anyString(), anyInt(), anyInt())).thenReturn(false);
        controller.cadastrar();
        assertEquals("Erro ao cadastrar amigo.", telaFake.getMensagem());
    }

    // Testes simples para getters e setters da tela FrmCadastrarAmigo
    @Test
    public void deveSetarEObterMensagemDaInterface() {
        FrmCadastrarAmigo tela = new FrmCadastrarAmigo();
        tela.setMensagem("Mensagem de teste");
        assertEquals("Mensagem de teste", tela.getMensagem());
    }

    @Test
    public void deveSetarEObterCampoNome() {
        FrmCadastrarAmigo tela = new FrmCadastrarAmigo();
        JTextField campo = new JTextField("Teste Nome");
        tela.setJTFNome(campo);
        assertEquals("Teste Nome", tela.getJTFNome().getText());
    }

    @Test
    public void deveSetarEObterCampoTelefone() {
        FrmCadastrarAmigo tela = new FrmCadastrarAmigo();
        JTextField campo = new JTextField("123456");
        tela.setJTFTelefone(campo);
        assertEquals("123456", tela.getJTFTelefone().getText());
    }

    @Test
    public void deveSetarEObterBotaoCadastrar() {
        FrmCadastrarAmigo tela = new FrmCadastrarAmigo();
        JButton botao = new JButton("Cadastrar");
        tela.setJBCadastrar(botao);
        assertEquals("Cadastrar", tela.getJBCadastrar().getText());
    }

    @Test
    public void deveSetarEObterBotaoCancelar() {
        FrmCadastrarAmigo tela = new FrmCadastrarAmigo();
        JButton botao = new JButton("Cancelar");
        tela.setJBCancelar(botao);
        assertEquals("Cancelar", tela.getJBCancelar().getText());
    }
}
