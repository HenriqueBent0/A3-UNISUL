package test;

import visao.FrmCadastrarFerramenta;

public class FrmFerramentaFake extends FrmCadastrarFerramenta {
    public FrmFerramentaFake() {
        super();
        setVisible(false); // impede abrir janela durante testes
    }
}
