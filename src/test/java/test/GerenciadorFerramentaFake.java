package test;

import controle.GerenciadorFerramentaController;
import visao.FrmGerenciadorFerramenta;

public class GerenciadorFerramentaFake extends FrmGerenciadorFerramenta {

    public GerenciadorFerramentaFake(GerenciadorFerramentaController controller) {
        super(controller);
    }

    @Override
    public void mostrarMensagem(String mensagem) {
        setMensagem(mensagem);
        System.out.println("Mensagem:" + mensagem);
    }
}
