package test;

import visao.FrmCadastrarAmigo;

/**
 * Formulário Fake usado nos testes.
 */
public class FrmAmigoFake extends FrmCadastrarAmigo {

    private String ultimaMensagem;

    @Override
    public void mostrarMensagem(String mensagem) {
        this.ultimaMensagem = mensagem;
        System.out.println("Mensagem (Fake): " + mensagem); // Exibe a mensagem no console
    }

    public String getUltimaMensagem() {
        return ultimaMensagem;
    }
}
