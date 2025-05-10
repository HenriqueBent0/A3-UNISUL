package test;

import controle.AmigoController;
import dao.AmigoDAO;
import modelo.Amigo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FrmCadastroAmigoTest {

    private FrmAmigoFake frmCadastrarAmigoFake;
    private AmigoController controller;
    private AmigoDAO amigoDAO;

    @BeforeEach
    void setUp() {
        // Inicializa a instância do formulário fake e o controlador
        frmCadastrarAmigoFake = new FrmAmigoFake();
        controller = new AmigoController(frmCadastrarAmigoFake);
        amigoDAO = new AmigoDAO();

        // Vincula o controller ao formulário fake
        frmCadastrarAmigoFake.setController(controller);  // Agora funciona corretamente
    }

    @Test
    void testCadastroAmigoComSucesso() {
        // Configura dados válidos para o cadastro
        frmCadastrarAmigoFake.getJTFNome().setText("João");
        frmCadastrarAmigoFake.getJTFTelefone().setText("123456789");

        // Simula a ação de clicar no botão Cadastrar
        frmCadastrarAmigoFake.getJBCadastrar().doClick();

        // Verifica se a mensagem de sucesso foi exibida
        String mensagemEsperada = "Amigo Cadastrado com Sucesso!";
        assertEquals(mensagemEsperada, frmCadastrarAmigoFake.getUltimaMensagem());
    }

    @Test
    void testCadastroAmigoComTelefoneInvalido() {
        // Configura dados com telefone inválido
        frmCadastrarAmigoFake.getJTFNome().setText("Maria");
        frmCadastrarAmigoFake.getJTFTelefone().setText("abc123");

        // Simula a ação de clicar no botão Cadastrar
        frmCadastrarAmigoFake.getJBCadastrar().doClick();

        // Verifica se a mensagem de erro foi exibida
        String mensagemEsperada = "Telefone inválido. Por favor, insira um número válido.";
        assertEquals(mensagemEsperada, frmCadastrarAmigoFake.getUltimaMensagem());
    }

    @Test
    void testCadastroAmigoComCamposVazios() {
        // Deixa os campos em branco
        frmCadastrarAmigoFake.getJTFNome().setText("");
        frmCadastrarAmigoFake.getJTFTelefone().setText("");

        // Simula a ação de clicar no botão Cadastrar
        frmCadastrarAmigoFake.getJBCadastrar().doClick();

        // Verifica se a mensagem de erro foi exibida
        String mensagemEsperada = "Telefone inválido. Por favor, insira um número válido.";
        assertEquals(mensagemEsperada, frmCadastrarAmigoFake.getUltimaMensagem());
    }
}


    
