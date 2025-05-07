package test;

import visao.FrmCadastrarAmigo;

public class FrmAmigoFake extends FrmCadastrarAmigo {

    private String mensagem;

    public FrmAmigoFake() {
        super();
    }

    @Override
    public void mostrarMensagem(String mensagem) {
        this.mensagem = mensagem;
        System.out.println("Mensagem: " + mensagem); // Imprime a mensagem no console
    }

    public String getMensagem() {
        return mensagem;
    }

    public void resetMensagem() {
        this.mensagem = "";
    }
}
