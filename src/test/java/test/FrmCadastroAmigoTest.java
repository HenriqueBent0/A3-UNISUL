package test;

import controle.AmigoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.AmigoService;
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

        // Simulando o retorno de maiorID e a inserção no banco
        when(amigoServiceMock.maiorID()).thenReturn(10); // Retorna ID maior 10
        when(amigoServiceMock.insertAmigoBD(anyString(), anyInt(), anyInt())).thenReturn(true); // Simula inserção com sucesso

        controller.cadastrar();

        assertEquals("Amigo cadastrado com sucesso!", telaFake.getMensagem());
    }

    @Test
    public void deveExibirMensagemDeErroAoFalharCadastro() {
        telaFake.getJTFNome().setText("Carlos");
        telaFake.getJTFTelefone().setText("987654");

        // Simulando o retorno de maiorID e a falha na inserção
        when(amigoServiceMock.maiorID()).thenReturn(5); // Retorna ID maior 5
        when(amigoServiceMock.insertAmigoBD(anyString(), anyInt(), anyInt())).thenReturn(false); // Simula falha na inserção

        controller.cadastrar();

        assertEquals("Erro ao cadastrar o amigo.", telaFake.getMensagem());
    }
}
