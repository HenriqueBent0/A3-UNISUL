package controle;

import servico.AmigoService;
import visao.FrmCadastrarAmigo;

/**
 * Controlador responsável por gerenciar as interações entre a tela de cadastro
 * de amigos e a lógica de negócios relacionada aos amigos.
 */
public class AmigoController {

    private FrmCadastrarAmigo tela;
    private AmigoService service;

    /**
     * Construtor que recebe a tela e uma instância de serviço.
     *
     * @param tela Instância da interface gráfica para cadastro de amigos.
     * @param service Instância do serviço que contém a lógica de negócio dos
     * amigos.
     */
    public AmigoController(FrmCadastrarAmigo tela, AmigoService service) {
        this.tela = tela;
        this.service = service;
    }

    /**
     * Construtor que recebe apenas a tela e cria uma nova instância de serviço.
     *
     * @param tela Instância da interface gráfica para cadastro de amigos.
     */
    public AmigoController(FrmCadastrarAmigo tela) {
        this.tela = tela;
        this.service = new AmigoService();
    }

    /**
     * Realiza o processo de cadastro de um novo amigo.
     *
     * Etapas: - Valida se os campos de nome e telefone estão preenchidos. -
     * Valida se o telefone contém apenas números. - Gera um novo ID com base no
     * maior ID atual. - Insere o novo amigo no banco de dados. - Exibe
     * mensagens na tela com base no sucesso ou falha da operação.
     */
    public void cadastrar() {
        String nome = tela.getJTFNome().getText().trim();
        String telefoneStr = tela.getJTFTelefone().getText().trim();

        // Validação dos campos
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

        // Geração de novo ID
        int novoId = service.maiorID() + 1;

        // Inserção no banco de dados
        boolean inserido = service.insertAmigoBD(nome, novoId, telefone);

        if (inserido) {
            tela.mostrarMensagem("Amigo cadastrado com sucesso!");
            limparCampos();
        } else {
            tela.mostrarMensagem("Erro ao cadastrar amigo.");
        }
    }

    /**
     * Limpa os campos de entrada da tela após o cadastro.
     */
    private void limparCampos() {
        tela.getJTFNome().setText("");
        tela.getJTFTelefone().setText("");
    }
}
