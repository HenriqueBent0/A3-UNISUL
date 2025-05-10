package test;

import visao.FrmCadastrarAmigo;

public class FrmAmigoFake extends FrmCadastrarAmigo {

    // Atributo para capturar a última mensagem mostrada
    private String ultimaMensagem;

    @Override
    public void mostrarMensagem(String mensagem) {
        ultimaMensagem = mensagem;
        System.out.println(mensagem); // Em vez de JOptionPane, imprime no console
    }

    // Método para acessar a última mensagem
    public String getUltimaMensagem() {
        return ultimaMensagem;
    }
}
