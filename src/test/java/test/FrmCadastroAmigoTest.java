package test;

import controle.AmigoController;
import dao.AmigoDAO;
import modelo.Amigo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.AmigoService;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FrmCadastroAmigoTest {

    private AmigoFake telaFake;
    private AmigoDAO amigoDAOMock;
    private AmigoService amigoService;
    private AmigoController controller;

    @BeforeEach
    public void setUp() {
        System.setProperty("java.awt.headless", "true");
        telaFake = new AmigoFake();
        amigoDAOMock = mock(AmigoDAO.class);
        amigoService = new AmigoService(amigoDAOMock);
        controller = new AmigoController(telaFake, amigoService);
    }

    @Test
    public void deveExibirMensagemDeErroQuandoCamposEstaoVazios() {
        telaFake.getJTFNome().setText("");
        telaFake.getJTFTelefone().setText("");

        controller.cadastrar();

        assertEquals("Todos os campos devem ser preenchidos.", telaFake.getMensagem());
    }

    @Test
    public void deveExibirMensagemDeErroQuandoTelefoneNaoEhNumero() {
        telaFake.getJTFNome().setText("João");
        telaFake.getJTFTelefone().setText("abc123");

        controller.cadastrar();

        assertEquals("Telefone deve ser um número válido.", telaFake.getMensagem());
    }

    @Test
    public void deveCadastrarAmigoComSucesso() {
        telaFake.getJTFNome().setText("Maria");
        telaFake.getJTFTelefone().setText("123456");

        when(amigoDAOMock.maiorID()).thenReturn(10);
        when(amigoDAOMock.insertAmigoBD(any(Amigo.class))).thenReturn(true);

        controller.cadastrar();

        assertEquals("Amigo cadastrado com sucesso!", telaFake.getMensagem());
    }

    @Test
    public void deveExibirMensagemDeErroAoFalharCadastro() {
        telaFake.getJTFNome().setText("Carlos");
        telaFake.getJTFTelefone().setText("987654");

        when(amigoDAOMock.maiorID()).thenReturn(5);
        when(amigoDAOMock.insertAmigoBD(any(Amigo.class))).thenReturn(false);

        controller.cadastrar();

        assertEquals("Erro ao cadastrar o amigo.", telaFake.getMensagem());
    }
}
