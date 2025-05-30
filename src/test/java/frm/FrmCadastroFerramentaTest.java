package frm;

import controle.FerramentaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.FerramentaService;
import visao.FrmCadastrarFerramenta;
import javax.swing.JButton;
import javax.swing.JTextField;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe FrmCadastroFerramenta.
 * Verifica validações de campos, cadastro com sucesso e falhas.
 */
public class FrmCadastroFerramentaTest {

    private FerramentaFake telaFake;
    private FerramentaService ferramentaServiceMock;
    private FerramentaController controller;

    /**
     * Configura o ambiente antes de cada teste:
     * - Cria a tela fake
     * - Cria mock do serviço
     * - Cria o controller com esses objetos
     */
    @BeforeEach
    public void setUp() {
        telaFake = new FerramentaFake();
        ferramentaServiceMock = mock(FerramentaService.class);
        controller = new FerramentaController(telaFake, ferramentaServiceMock);
    }

    /**
     * Testa se exibe mensagem de erro quando todos os campos estão vazios.
     */
    @Test
    public void deveExibirMensagemDeErroQuandoCamposEstaoVazios() {
        telaFake.getJTFNome().setText("");
        telaFake.getJTFMarca().setText("");
        telaFake.getJTFValor().setText("");

        controller.cadastrar();

        assertEquals("O nome não pode estar em branco.", telaFake.getMensagem());
    }

    /**
     * Testa se exibe mensagem de erro quando o campo marca está vazio.
     */
    @Test
    public void deveExibirMensagemDeErroQuandoMarcaEstaVazio() {
        telaFake.getJTFNome().setText("Carlinhos");
        telaFake.getJTFMarca().setText("");
        telaFake.getJTFValor().setText("123456");

        controller.cadastrar();

        assertEquals("A marca não pode estar em branco.", telaFake.getMensagem());
    }

    /**
     * Testa cadastro com sucesso quando todos os dados são válidos.
     */
    @Test
    public void deveCadastrarFerramentaComSucesso() {
        telaFake.getJTFNome().setText("Maria");
        telaFake.getJTFMarca().setText("samsung");
        telaFake.getJTFValor().setText("123456");

        when(ferramentaServiceMock.maiorID()).thenReturn(10);
        when(ferramentaServiceMock.insertFerramentaBD(anyString(), anyString(), anyInt())).thenReturn(true);

        controller.cadastrar();

        assertEquals("Ferramenta cadastrada com sucesso!", telaFake.getMensagem());
    }

    /**
     * Testa se exibe mensagem de erro quando o valor contém caracteres não numéricos.
     */
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

    /**
     * Testa se exibe mensagem de erro quando a inserção no banco falha.
     */
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

    /**
     * Testa os getters e setters do campo nome da tela.
     */
    @Test
    public void deveSetarEObterCampoNome() {
        FrmCadastrarFerramenta tela = new FrmCadastrarFerramenta();
        JTextField campoNome = new JTextField("Teste Nome");
        tela.setJTFNome(campoNome);

        assertEquals("Teste Nome", tela.getJTFNome().getText());
    }

    /**
     * Testa os getters e setters do campo marca da tela.
     */
    @Test
    public void deveSetarEObterCampoMarca() {
        FrmCadastrarFerramenta tela = new FrmCadastrarFerramenta();
        JTextField campoMarca = new JTextField("Teste Marca");
        tela.setJTFMarca(campoMarca);

        assertEquals("Teste Marca", tela.getJTFMarca().getText());
    }

    /**
     * Testa os getters e setters do campo valor da tela.
     */
    @Test
    public void deveSetarEObterCampoValor() {
        FrmCadastrarFerramenta tela = new FrmCadastrarFerramenta();
        JTextField campoValor = new JTextField("999");
        tela.setJTFValor(campoValor);

        assertEquals("999", tela.getJTFValor().getText());
    }

    /**
     * Testa os getters e setters do botão cancelar da tela.
     */
    @Test
    public void deveSetarEObterBotaoCancelar() {
        FrmCadastrarFerramenta tela = new FrmCadastrarFerramenta();
        JButton botaoCancelar = new JButton("Cancelar");
        tela.setJBCancelar(botaoCancelar);

        assertEquals("Cancelar", tela.getJBCancelar().getText());
    }

    /**
     * Testa os getters e setters do botão cadastrar da tela.
     */
    @Test
    public void deveSetarEObterBotaoCadastrar() {
        FrmCadastrarFerramenta tela = new FrmCadastrarFerramenta();
        JButton botaoCadastrar = new JButton("Cadastrar");
        tela.setJBCadastrar(botaoCadastrar);

        assertEquals("Cadastrar", tela.getJBCadastrar().getText());
    }
}
