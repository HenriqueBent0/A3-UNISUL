package test;

import visao.FrmCadastrarAmigo;

/**
 * Subclasse fake de FrmCadastrarAmigo para testes, substitui JOptionPane por console e atributo.
 */
public class FrmAmigoFake extends FrmCadastrarAmigo {

    private String mensagem;

    public FrmAmigoFake() {
        super();
    }

    @Override
    public void mostrarMensagem(String mensagem) {
        this.mensagem = mensagem;
        System.out.println("Mensagem: " + mensagem);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void resetMensagem() {
        this.mensagem = "";
    }
}
