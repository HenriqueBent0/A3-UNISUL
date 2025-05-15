package controle;


import servico.AmigoService;
import visao.FrmCadastrarAmigo;

public class AmigoController {

    private FrmCadastrarAmigo tela;
    private AmigoService service;

    // Construtor para testes e uso normal
    public AmigoController(FrmCadastrarAmigo tela, AmigoService service) {
        this.tela = tela;
        this.service = service;
    }

    // Construtor padrão para uso normal (usa novo AmigoService)
    public AmigoController(FrmCadastrarAmigo tela) {
        this.tela = tela;
        this.service = new AmigoService();
    }

    public void cadastrar() {
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

        // Verifica se telefone é numérico
        int telefone;
        try {
            telefone = Integer.parseInt(telefoneStr);
        } catch (NumberFormatException e) {
            tela.mostrarMensagem("Telefone deve conter apenas números.");
            return;
        }

        // Busca próximo ID para o novo amigo
        int novoId = service.maiorID() + 1;

        // Tenta inserir no banco via service
        boolean inserido = service.insertAmigoBD(nome, novoId, telefone);

        if (inserido) {
            tela.mostrarMensagem("Amigo cadastrado com sucesso!");
            limparCampos();
        } else {
            tela.mostrarMensagem("Erro ao cadastrar amigo.");
        }
    }

    private void limparCampos() {
        tela.getJTFNome().setText("");
        tela.getJTFTelefone().setText("");
    }
}
