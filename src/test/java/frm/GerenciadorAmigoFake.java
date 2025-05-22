package frm;

import controle.GerenciadorAmigoController;
import visao.FrmGerenciadorAmigo;

public class GerenciadorAmigoFake extends FrmGerenciadorAmigo {

    public GerenciadorAmigoFake(GerenciadorAmigoController controller) {
        super(controller);
    }

    @Override
    public void mostrarMensagem(String mensagem) {
        setMensagem(mensagem);
        System.out.println("Mensagem:" + mensagem);
    }
}
