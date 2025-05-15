package controle;

import servico.FerramentaService;
import visao.FrmCadastrarFerramenta;

public class FerramentaController {

    private final FrmCadastrarFerramenta view;
    private final FerramentaService service;

    public FerramentaController(FrmCadastrarFerramenta view, FerramentaService service) {
        this.view = view;
        this.service = service;
    }

    public FerramentaController(FrmCadastrarFerramenta view) {
        this.view = view;
        this.service = new FerramentaService();
    }

    public void cadastrar() {
        String nome = view.getJTFNome().getText().trim();
        String marca = view.getJTFMarca().getText().trim();
        String valorTexto = view.getJTFValor().getText().trim();

        if (nome.isEmpty()) {
            view.mostrarMensagem("O nome não pode estar em branco.");
            return;
        }

        if (marca.isEmpty()) {
            view.mostrarMensagem("A marca não pode estar em branco.");
            return;
        }

        int valor;
        try {
            valor = Integer.parseInt(valorTexto);
        } catch (NumberFormatException e) {
            view.mostrarMensagem("O valor deve conter apenas números.");
            return;
        }

        int id = service.maiorID() + 1;

        boolean sucesso = service.insertFerramentaBD(nome, marca, valor);

        if (sucesso) {
            view.mostrarMensagem("Ferramenta cadastrada com sucesso!");
            limparCampos();
        } else {
            view.mostrarMensagem("Erro ao cadastrar ferramenta.");
        }
    }

    private void limparCampos() {
        view.getJTFNome().setText("");
        view.getJTFMarca().setText("");
        view.getJTFValor().setText("");
        view.getJTFNome().requestFocus();
    }
}
