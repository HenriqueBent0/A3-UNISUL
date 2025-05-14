package test;

import visao.FrmCadastrarAmigo;

/**
 * Classe que cria um formulário de cadastro de amigo falso para ser utilizado nos testes.
 */
public class AmigoFake extends FrmCadastrarAmigo {

    public AmigoFake() {
       
    }
  @Override
    public void mostrarMensagem(String mensagem) {
        //Atribui a mensagem ao atributo para ser utilizado nos testes
        setMensagem(mensagem);

        //Mostra a mensagem
        System.out.println("Mensagem:" + mensagem);
    }
}
