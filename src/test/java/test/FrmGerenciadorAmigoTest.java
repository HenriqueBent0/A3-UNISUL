package test;

import controle.GerenciadorAmigoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.AmigoService;
import visao.FrmGerenciadorAmigo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FrmGerenciadorAmigoTest {

    private GerenciadorAmigoFake telaFake;
    private GerenciadorAmigoController controllerMock;

    @BeforeEach
    public void setUp() {
        // Criando a tela fake e o controller mockado
        telaFake = new GerenciadorAmigoFake();
        controllerMock = mock(GerenciadorAmigoController.class);
        telaFake.setController(controllerMock);
    }

    @Test
    public void deveChamarMetodoEditarQuandoBotaoEditarClicado() {
        telaFake.getJBEditar().doClick();
        verify(controllerMock, times(1)).editarAmigo();
    }

    @Test
    public void deveChamarMetodoApagarQuandoBotaoApagarClicado() {
        telaFake.getJBApagar().doClick();
        verify(controllerMock, times(1)).apagarAmigo();
    }

    @Test
    public void deveFecharTelaAoClicarEmCancelar() {
        assertTrue(telaFake.isDisplayable());
        telaFake.getJBCancelar().doClick();
        assertFalse(telaFake.isDisplayable());
    }

    @Test
    public void deveExibirMensagemAoChamarMostrarMensagem() {
        String mensagem = "Teste de mensagem";
        telaFake.mostrarMensagem(mensagem);
        assertEquals(mensagem, telaFake.getMensagem());
    }
}
