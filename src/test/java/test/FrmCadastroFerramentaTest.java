package test;

import controle.FerramentaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.FerramentaService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FrmCadastroFerramentaTest {

    private FerramentaFake telaFake;
    private FerramentaService ferramentaServiceMock;
    private FerramentaController controller;

    @BeforeEach
    public void setUp() {
        // Cria a tela fake para capturar mensagens
        telaFake = new FerramentaFake();
        
        // Mock do serviço
        ferramentaServiceMock = mock(FerramentaService.class);
        
        // Cria o controlador passando a tela fake e o mock do serviço
        controller = new FerramentaController(telaFake, ferramentaServiceMock);
    }

    @Test
    public void deveExibirMensagemDeErroQuandoCamposEstaoVazios() {
        telaFake.getJTFNome().setText("");
        telaFake.getJTFMarca().setText("");
        telaFake.getJTFValor().setText("");

        controller.cadastrar();

        assertEquals("O nome não pode estar em branco.", telaFake.getMensagem());
    }
    
        @Test
    public void deveExibirMensagemDeErroQuandoMarcaEstaVazio() {
        telaFake.getJTFNome().setText("Carlinhos");
        telaFake.getJTFMarca().setText("");
        telaFake.getJTFValor().setText("123456");

        controller.cadastrar();

        assertEquals("A marca não pode estar em branco.", telaFake.getMensagem());
    }


    @Test
    public void deveCadastrarFerramentaComSucesso() {
        telaFake.getJTFNome().setText("Maria");
        telaFake.getJTFMarca().setText("samsung");
        telaFake.getJTFValor().setText("123456");

        // Simulando o retorno de maiorID e a inserção no banco
        when(ferramentaServiceMock.maiorID()).thenReturn(10); // Retorna ID maior 10
        when(ferramentaServiceMock.insertFerramentaBD(anyString(), anyString(), anyInt())).thenReturn(true); // Simula inserção com sucesso

        controller.cadastrar();

        assertEquals("Ferramenta cadastrada com sucesso!", telaFake.getMensagem());
    }
    
        @Test
    public void deveExibirMensagemDeErroQuandoValorNaoENumero() {
        telaFake.getJTFNome().setText("Carlos");
        telaFake.getJTFMarca().setText("sam12sug");
        telaFake.getJTFValor().setText("123adasf456");
        

        when(ferramentaServiceMock.maiorID()).thenReturn(5);
        when(ferramentaServiceMock.insertFerramentaBD(anyString(), anyString(), anyInt())).thenReturn(false);

        controller.cadastrar();

        assertEquals("O valor deve conter apenas números.", telaFake.getMensagem());
    }

    @Test
    public void deveExibirMensagemDeErroQuandoInsercaoFalha() {
        telaFake.getJTFNome().setText("Carlos");
        telaFake.getJTFMarca().setText("sam12sug");
        telaFake.getJTFValor().setText("123456");
        

        when(ferramentaServiceMock.maiorID()).thenReturn(5);
        when(ferramentaServiceMock.insertFerramentaBD(anyString(), anyString(), anyInt())).thenReturn(false);

        controller.cadastrar();

        assertEquals("Erro ao cadastrar ferramenta.", telaFake.getMensagem());
    }
}
