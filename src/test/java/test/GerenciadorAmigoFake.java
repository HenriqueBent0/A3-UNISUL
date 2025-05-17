
package test;
import visao.FrmGerenciadorAmigo;


public class GerenciadorAmigoFake extends FrmGerenciadorAmigo {
    @Override
    public void mostrarMensagem(String mensagem) {
        //Atribui a mensagem ao atributo para ser utilizado nos testes
        setMensagem(mensagem);

        //Mostra a mensagem
        System.out.println("Mensagem:" + mensagem);
    }

}
