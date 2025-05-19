package test;

import controle.AmigoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.AmigoService;
import visao.FrmCadastrarAmigo;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FrmCadastroAmigoTest {

    private AmigoFake telaFake;
    private AmigoService amigoServiceMock;
    private AmigoController controller;

    @BeforeEach
    public void setUp() {
        // Cria a tela fake para capturar mensagens
        telaFake = new AmigoFake();

        // Mock do serviço
        amigoServiceMock = mock(AmigoService.class);

        // Cria o controlador passando a tela fake e o mock do serviço
        controller = new AmigoController(telaFake, amigoServiceMock);
    }

    @Test
    public void deveExibirMensagemDeErroQuandoCamposEstaoVazios() {
        telaFake.getJTFNome().setText("");
        telaFake.getJTFTelefone().setText("");

        controller.cadastrar();

        assertEquals("O nome não pode estar em branco.", telaFake.getMensagem());
    }

    @Test
    public void deveExibirMensagemDeErroQuandoTelefoneNaoEhNumero() {
        telaFake.getJTFNome().setText("João");
        telaFake.getJTFTelefone().setText("abc123");

        controller.cadastrar();

        assertEquals("Telefone deve conter apenas números.", telaFake.getMensagem());
    }

    @Test
    public void deveCadastrarAmigoComSucesso() {
        telaFake.getJTFNome().setText("Maria");
        telaFake.getJTFTelefone().setText("123456");

        // Simulando o retorno de maiorID e a inserção no banco
        when(amigoServiceMock.maiorID()).thenReturn(10); // Retorna ID maior 10
        when(amigoServiceMock.insertAmigoBD(anyString(), anyInt(), anyInt())).thenReturn(true); // Simula inserção com sucesso

        controller.cadastrar();

        assertEquals("Amigo cadastrado com sucesso!", telaFake.getMensagem());
    }

    @Test
    public void deveExibirMensagemDeErroQuandoInsercaoFalha() {
        telaFake.getJTFNome().setText("Carlos");
        telaFake.getJTFTelefone().setText("987654");

        when(amigoServiceMock.maiorID()).thenReturn(5);
        when(amigoServiceMock.insertAmigoBD(anyString(), anyInt(), anyInt())).thenReturn(false);

        controller.cadastrar();

        assertEquals("Erro ao cadastrar amigo.", telaFake.getMensagem());
    }

    // --- TESTES ADICIONAIS PARA COBRIR GETTERS E SETTERS DA CLASSE FrmCadastrarAmigo ---

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
