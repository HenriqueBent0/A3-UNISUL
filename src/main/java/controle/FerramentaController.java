package controle;

import visao.FrmCadastrarFerramenta;
import servico.FerramentaService;

public class FerramentaController {

    private FrmCadastrarFerramenta frm;
    private FerramentaService ferramentaService;

    public FerramentaController(FrmCadastrarFerramenta frm) {
        this(frm, new FerramentaService());
    }

    public FerramentaController(FrmCadastrarFerramenta frm, FerramentaService ferramentaService) {
        this.frm = frm;
        this.ferramentaService = ferramentaService;
        adicionarListeners();
    }

    private void adicionarListeners() {
        frm.getJBCadastrar().addActionListener(e -> cadastrar());
        frm.getJBCancelar().addActionListener(e -> frm.dispose());
    }

    public void cadastrar() {
        String nome = frm.getJTFNomeField().getText();
        String marca = frm.getJTFMarcaField().getText();
        String valor = frm.getJTFValorField().getText();

        boolean sucesso = ferramentaService.cadastrarFerramenta(nome, marca, valor);

        if (sucesso) {
            frm.getJTFNomeField().setText("");
            frm.getJTFMarcaField().setText("");
            frm.getJTFValorField().setText("");
        }
    }
}
