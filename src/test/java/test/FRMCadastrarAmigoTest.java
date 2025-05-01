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
    System.setProperty("java.awt.headless", "true");  // Força o modo headlesss
    tela = new FrmCadastrarAmigo();
    amigoServiceMock = mock(AmigoService.class);
    tela.setAmigoService(amigoServiceMock);
}

    @Test
    void deveCadastrarComDadosValidos() {
        tela.setNome("João");
        tela.setTelefone("123456789");

        when(amigoServiceMock.insertAmigoBD("João", 0, 123456789)).thenReturn(true);

        try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
            tela.cadastrar();
            mockJOptionPane.verify(() ->
                JOptionPane.showMessageDialog(null, "Amigo Cadastrado com Sucesso!")
            );
        }
    }

    @Test
    void deveMostrarErroQuandoNomeInvalido() {
        tela.setNome("J"); // inválido
        tela.setTelefone("123456789");

        try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
            tela.cadastrar();
            mockJOptionPane.verify(() ->
                JOptionPane.showMessageDialog(null, "Nome deve conter ao menos 2 caracteres.")
            );
        }
    }

    @Test
    void deveMostrarErroQuandoTelefoneInvalido() {
        tela.setNome("João");
        tela.setTelefone("1234"); // inválido

        try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
            tela.cadastrar();
            mockJOptionPane.verify(() ->
                JOptionPane.showMessageDialog(null, "Informe um número válido.")
            );
        }
    }

    @Test
    void deveMostrarErroSeTelefoneNaoForNumero() {
        tela.setNome("João");
        tela.setTelefone("abcdefghi"); // não numérico

        try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
            tela.cadastrar();
            mockJOptionPane.verify(() ->
                JOptionPane.showMessageDialog(null, "Informe um número válido.")
            );
        }
    }

    @Test
    void deveMostrarErroQuandoCadastroFalha() {
        tela.setNome("João");
        tela.setTelefone("123456789");

        when(amigoServiceMock.insertAmigoBD("João", 0, 123456789)).thenReturn(false);

        try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
            tela.cadastrar();
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
            tela.getBotaoCadastrar().doClick(); // Simula clique no botão
            mockJOptionPane.verify(() ->
                JOptionPane.showMessageDialog(null, "Amigo Cadastrado com Sucesso!")
            );
        }
    }




    @Test
    void deveExecutarMetodoMainSemErros() {
        FrmCadastrarAmigo.main(new String[]{}); // Apenas garante que roda sem exceção
    }
}
