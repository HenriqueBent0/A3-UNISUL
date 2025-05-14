package controle;

import servico.AmigoService;
import visao.FrmCadastrarAmigo;

public class AmigoController {

    private final FrmCadastrarAmigo tela;
    private final AmigoService service;

    public AmigoController(FrmCadastrarAmigo tela, AmigoService amigoServiceMock) {
        this.tela = tela;
        this.service = new AmigoService(); // ou injete um mock se necessário
    }
public AmigoController(FrmCadastrarAmigo tela) {
    this.tela = tela;
    this.service = new AmigoService(); // Criação real do service
}
    public void cadastrar() {
        try {
            String nome = tela.getJTFNome().getText().trim();
            String telefoneStr = tela.getJTFTelefone().getText().trim();

            // Validação simples
            if (nome.isEmpty()) {
                tela.mostrarMensagem("O nome não pode estar em branco.");
                return;
            }
            if (telefoneStr.isEmpty()) {
                tela.mostrarMensagem("O telefone não pode estar em branco.");
                return;
            }

            int telefone;
            try {
                telefone = Integer.parseInt(telefoneStr);
            } catch (NumberFormatException e) {
                tela.mostrarMensagem("Telefone deve conter apenas números.");
                return;
            }

            boolean sucesso = service.insertAmigoBD(nome, 0, telefone);
            if (sucesso) {
                tela.mostrarMensagem("Amigo cadastrado com sucesso!");
                tela.getJTFNome().setText("");
                tela.getJTFTelefone().setText("");
            } else {
                tela.mostrarMensagem("Erro ao cadastrar amigo.");
            }

        } catch (Exception e) {
            tela.mostrarMensagem("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
