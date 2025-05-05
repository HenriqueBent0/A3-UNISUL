package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import servico.AmigoService;
import visao.FrmCadastrarAmigo;

import javax.swing.*;

import static org.mockito.Mockito.*;

class FRMCadastrarAmigoTest {

    private FrmCadastrarAmigo tela;
    private AmigoService amigoServiceMock;

    @BeforeEach
    void setUp() {
        // Forçar a execução em modo headless
        System.setProperty("java.awt.headless", "true");

        // Criação da tela com a configuração para não carregar a GUI completa
        tela = new FrmCadastrarAmigo(true);

        // Inicializando manualmente os campos de texto
        tela.setNome("Nome Teste");
        tela.setTelefone("123456789");

        // Mockando o serviço AmigoService
        amigoServiceMock = mock(AmigoService.class);
        tela.setAmigoService(amigoServiceMock);
    }

    @Test
    void deveCadastrarComDadosValidos() {
        // Configura os dados de entrada
        tela.setNome("João");
        tela.setTelefone("123456789");

        // Definir comportamento esperado do mock
        when(amigoServiceMock.insertAmigoBD("João", 0, 123456789)).thenReturn(true);

        try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
            // Chamando o método de cadastro
            tela.cadastrar("João", "123456789");

            // Verificando se a mensagem de sucesso foi exibida
            mockJOptionPane.verify(() -> 
                JOptionPane.showMessageDialog(null, "Amigo Cadastrado com Sucesso!")
            );
        }
    }

    @Test
    void deveMostrarErroQuandoNomeInvalido() {
        tela.setNome("J"); // Nome inválido
        tela.setTelefone("123456789");

        try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
            // Chamando o método de cadastro
            tela.cadastrar("J", "123456789");

            // Verificando se a mensagem de erro foi exibida
            mockJOptionPane.verify(() -> 
                JOptionPane.showMessageDialog(null, "Nome deve conter ao menos 2 caracteres.")
            );
        }
    }

    @Test
    void deveMostrarErroSeTelefoneNaoForNumero() {
        tela.setNome("João");
        tela.setTelefone("abcdefghi"); // Telefone inválido

        try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
            // Chamando o método de cadastro
            tela.cadastrar("João", "abcdefghi");

            // Verificando se a mensagem de erro foi exibida
            mockJOptionPane.verify(() -> 
                JOptionPane.showMessageDialog(null, "Informe um número válido.")
            );
        }
    }

    @Test
    void deveMostrarErroQuandoCadastroFalha() {
        tela.setNome("João");
        tela.setTelefone("123456789");

        // Simula falha ao cadastrar
        when(amigoServiceMock.insertAmigoBD("João", 0, 123456789)).thenReturn(false);

        try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
            // Chamando o método de cadastro
            tela.cadastrar("João", "123456789");

            // Verificando se a mensagem de erro foi exibida
            mockJOptionPane.verify(() -> 
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar amigo.")
            );
        }
    }

    @Test
    void deveExecutarAcaoCadastrarDoBotao() {
        tela.setNome("João");
        tela.setTelefone("123456789");

        when(amigoServiceMock.insertAmigoBD("João", 0, 123456789)).thenReturn(true);

        try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
            // Simula o clique no botão de cadastrar
            tela.getBotaoCadastrar().doClick();

            // Verificando se a mensagem de sucesso foi exibida
            mockJOptionPane.verify(() -> 
                JOptionPane.showMessageDialog(null, "Amigo Cadastrado com Sucesso!")
            );
        }
    }

    @Test
    void deveExecutarMetodoMainSemErros() {
        // Testa a execução do método main sem erros
        FrmCadastrarAmigo.main(new String[]{});
    }
}
