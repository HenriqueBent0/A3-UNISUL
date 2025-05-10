package controle;

import visao.FrmCadastrarAmigo;
import servico.AmigoService;

public class AmigoController {

    private FrmCadastrarAmigo frm;
    private AmigoService amigoService;

    public AmigoController(FrmCadastrarAmigo frm) {
        this.frm = frm;
        this.amigoService = new AmigoService();
        this.frm.setController(this); // Passa o controller para a visão
    }

    public void cadastrarAmigo() {
        String nome = frm.getJTFNome().getText();
        String telefoneText = frm.getJTFTelefone().getText();
        int telefone = 0;

        try {
            if (nome.length() < 2) {
                frm.mostrarMensagem("Nome deve conter ao menos 2 caracteres.");
                return;
            }

            if (telefoneText.length() != 9) {
                frm.mostrarMensagem("Informe um número válido.");
                return;
            }

            telefone = Integer.parseInt(telefoneText);

            if (amigoService.insertAmigoBD(nome, 0, telefone)) {
                frm.mostrarMensagem("Amigo Cadastrado com Sucesso!");
                frm.limparCampos();
            }

        } catch (NumberFormatException e) {
            frm.mostrarMensagem("Informe um número válido.");
        }
    }

    public void cancelarCadastro() {
        frm.dispose(); // Fecha o formulário
    }
}
