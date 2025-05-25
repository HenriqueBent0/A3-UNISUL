package controle;

import servico.FerramentaService;
import visao.FrmCadastrarFerramenta;

/**
 * Controlador responsável por gerenciar as ações da interface gráfica de
 * cadastro de ferramentas. Realiza a mediação entre a camada de visão
 * (interface gráfica) e a camada de serviço (regras de negócio).
 */
public class FerramentaController {

    private final FrmCadastrarFerramenta view;
    private final FerramentaService service;

    /**
     * Construtor que recebe a tela de cadastro e o serviço de ferramenta como
     * parâmetros.
     *
     * @param view Instância da interface gráfica para cadastro de ferramentas.
     * @param service Instância do serviço com a lógica de negócio para
     * ferramentas.
     */
    public FerramentaController(FrmCadastrarFerramenta view, FerramentaService service) {
        this.view = view;
        this.service = service;
    }

    /**
     * Construtor que recebe apenas a tela e instancia automaticamente o
     * serviço.
     *
     * @param view Instância da interface gráfica para cadastro de ferramentas.
     */
    public FerramentaController(FrmCadastrarFerramenta view) {
        this.view = view;
        this.service = new FerramentaService();
    }

    /**
     * Realiza o processo de cadastro de uma nova ferramenta.
     *
     * Passos executados: - Valida se os campos nome e marca estão preenchidos.
     * - Valida se o valor informado é numérico. - Gera um novo ID
     * automaticamente. - Envia os dados para o serviço para inserção no banco
     * de dados. - Exibe mensagens de sucesso ou erro conforme o resultado da
     * operação.
     */
    public void cadastrar() {
        String nome = view.getJTFNome().getText().trim();
        String marca = view.getJTFMarca().getText().trim();
        String valorTexto = view.getJTFValor().getText().trim();

        // Validação dos campos obrigatórios
        if (nome.isEmpty()) {
            view.mostrarMensagem("O nome não pode estar em branco.");
            return;
        }

        if (marca.isEmpty()) {
            view.mostrarMensagem("A marca não pode estar em branco.");
            return;
        }

        // Validação do valor numérico
        int valor;
        try {
            valor = Integer.parseInt(valorTexto);
        } catch (NumberFormatException e) {
            view.mostrarMensagem("O valor deve conter apenas números.");
            return;
        }

        // Geração do novo ID
        int id = service.maiorID() + 1;

        // Inserção no banco de dados
        boolean sucesso = service.insertFerramentaBD(nome, marca, valor);

        if (sucesso) {
            view.mostrarMensagem("Ferramenta cadastrada com sucesso!");
            limparCampos();
        } else {
            view.mostrarMensagem("Erro ao cadastrar ferramenta.");
        }
    }

    /**
     * Limpa os campos da interface gráfica após o cadastro. Também reposiciona
     * o cursor no campo de nome.
     */
    private void limparCampos() {
        view.getJTFNome().setText("");
        view.getJTFMarca().setText("");
        view.getJTFValor().setText("");
        view.getJTFNome().requestFocus();
    }
}
