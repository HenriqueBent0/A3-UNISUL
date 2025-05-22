package frm;

import visao.FrmCadastrarFerramenta;

/**
 *
 * Classe que cria um formulário de cadastro de amigo falso para ser utilizado,
 * nos testes.
 */


public class FerramentaFake extends FrmCadastrarFerramenta {



    @Override
    public void mostrarMensagem(String mensagem) {
        //Atribui a mensagem ao atributo para ser utilizado nos testes
        setMensagem(mensagem);

        //Mostra a mensagem
        System.out.println("Mensagem:" + mensagem);
    }

}
