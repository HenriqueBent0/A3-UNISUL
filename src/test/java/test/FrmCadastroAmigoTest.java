package test;

import controle.AmigoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import visao.FrmCadastrarAmigo;
import test.FrmAmigoFake;

public class FrmCadastroAmigoTest {

    private FrmCadastrarAmigo frm;

    @BeforeEach
    public void setUp() {
        frm = new FrmAmigoFake();  // Usa a versão fake do formulário
        AmigoController controller = new AmigoController(frm); // Passando o formulário fake para o controller
        frm.setController(controller); // Setando o controller
    }

    @Test
    public void testCadastroComSucesso() {
        // Simula o cadastro com dados válidos
        frm.getJTFNome().setText("João");
        frm.getJTFTelefone().setText("123456789");

        frm.getJBCadastrar().doClick(); // Aciona o botão "Cadastrar"

        // Verifica se a mensagem de sucesso foi gerada corretamente
        String mensagemEsperada = "Amigo Cadastrado com Sucesso!";
        assertEquals(mensagemEsperada, ((FrmAmigoFake) frm).getUltimaMensagem());
    }

    @Test
    public void testCadastroComCamposVazios() {
        // Simula o cadastro com dados vazios
        frm.getJTFNome().setText("");
        frm.getJTFTelefone().setText("");

        frm.getJBCadastrar().doClick(); // Aciona o botão "Cadastrar"

        // Verifica se a mensagem de erro foi gerada corretamente
        String mensagemEsperada = "Telefone inválido. Por favor, insira um número válido.";
        assertEquals(mensagemEsperada, ((FrmAmigoFake) frm).getUltimaMensagem());
    }

    @Test
    public void testCadastroComTelefoneInvalido() {
        // Simula o cadastro com telefone inválido
        frm.getJTFNome().setText("João");
        frm.getJTFTelefone().setText("abc123");

        frm.getJBCadastrar().doClick(); // Aciona o botão "Cadastrar"

        // Verifica se a mensagem de erro foi gerada corretamente
        String mensagemEsperada = "Telefone inválido. Por favor, insira um número válido.";
        assertEquals(mensagemEsperada, ((FrmAmigoFake) frm).getUltimaMensagem());
    }
}
