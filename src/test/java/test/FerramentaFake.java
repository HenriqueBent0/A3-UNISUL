package test;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import visao.FrmCadastrarFerramenta;

/**
 *
 * Classe que cria um formulário de cadastro de amigo falso para ser utilizado,
 * nos testes.
 */


public class FerramentaFake extends FrmCadastrarFerramenta {

    public FerramentaFake() {

        super();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void mostrarMensagem(String mensagem) {
        //Atribui a mensagem ao atributo para ser utilizado nos testes
        setMensagem(mensagem);

        //Mostra a mensagem
        System.out.println("Mensagem:" + mensagem);
    }

}
