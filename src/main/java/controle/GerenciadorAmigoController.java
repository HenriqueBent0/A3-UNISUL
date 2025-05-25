package controle;

import visao.FrmGerenciadorAmigo;
import servico.AmigoService;
import modelo.Amigo;

/**
 * Controlador responsável por gerenciar as interações entre a interface de
 * gerenciamento de amigos e a lógica de negócios correspondente.
 */
public class GerenciadorAmigoController {

    private FrmGerenciadorAmigo view;
    private AmigoService amigoService;

    /**
     * Construtor que inicializa o controlador com a view e o serviço de amigos.
     *
     * @param view Instância da interface gráfica de gerenciamento de amigos.
     * @param amigoService Instância do serviço que contém a lógica de negócio
     * dos amigos.
     */
    public GerenciadorAmigoController(FrmGerenciadorAmigo view, AmigoService amigoService) {
        this.view = view;
        this.amigoService = amigoService;
    }

    /**
     * Define ou atualiza a interface de gerenciamento de amigos.
     *
     * @param view Nova instância da interface gráfica.
     */
    public void setView(FrmGerenciadorAmigo view) {
        this.view = view;
    }

    /**
     * Realiza o processo de exclusão de um amigo selecionado na tabela.
     *
     * Etapas: - Verifica se um amigo foi selecionado. - Obtém o ID do amigo
     * selecionado. - Chama o serviço para exclusão. - Exibe mensagens de
     * sucesso ou erro. - Recarrega a tabela com os dados atualizados.
     */
    public void apagarAmigo() {
        try {
            int id = 0;
            if (view.getJTableAmigos().getSelectedRow() == -1) {
                view.mostrarMensagem("Selecione um Amigo para apagar primeiro");
                return;
            } else {
                id = Integer.parseInt(view.getJTableAmigos()
                        .getValueAt(view.getJTableAmigos().getSelectedRow(), 0).toString());
            }

            if (amigoService.deleteAmigoBD(id)) {
                view.clearFields();
                view.mostrarMensagem("Amigo Apagado com Sucesso.");
            }

        } catch (RuntimeException erro) {
            view.mostrarMensagem(erro.getMessage());
        } finally {
            carregarTabela();
        }
    }

    /**
     * Realiza o processo de edição dos dados de um amigo.
     *
     * Etapas: - Valida os campos de nome e telefone. - Verifica se algum amigo
     * está selecionado. - Converte o telefone para número inteiro. - Atualiza
     * os dados do amigo no banco de dados. - Exibe mensagens de sucesso ou
     * erro. - Recarrega a tabela com os dados atualizados.
     */
    public void editarAmigo() {
        try {
            int id = 0;
            String nome = view.getJTFNome();
            String telefoneStr = view.getJTFTelefone();

            if (nome.length() < 1) {
                view.mostrarMensagem("Nome deve conter ao menos 1 caractere.");
                return;
            }

            if (telefoneStr.length() < 9 || telefoneStr.length() > 10) {
                view.mostrarMensagem("O Telefone deve ter entre 9 e 10 dígitos.");
                return;
            }

            int telefone;
            try {
                telefone = Integer.parseInt(telefoneStr);
            } catch (NumberFormatException e) {
                view.mostrarMensagem("O telefone deve conter apenas números.");
                return;
            }

            if (view.getJTableAmigos().getSelectedRow() == -1) {
                view.mostrarMensagem("Escolha um Amigo para Editar Primeiro");
                return;
            } else {
                id = Integer.parseInt(view.getJTableAmigos()
                        .getValueAt(view.getJTableAmigos().getSelectedRow(), 0).toString());
            }

            if (amigoService.updateAmigoBD(nome, id, telefone)) {
                view.clearFields();
                view.mostrarMensagem("Amigo Editado com sucesso.");
            }

        } catch (RuntimeException erro) {
            view.mostrarMensagem(erro.getMessage());
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado: " + e.getMessage());
        } finally {
            carregarTabela();
        }
    }

    /**
     * Carrega os dados do amigo selecionado na tabela para os campos da
     * interface.
     *
     * Etapas: - Verifica se uma linha foi selecionada na tabela. - Preenche os
     * campos de nome e telefone com os dados do amigo.
     */
    public void carregarTabelaGerenciador() {
        try {
            if (view.getJTableAmigos().getSelectedRow() != -1) {
                String nome = view.getJTableAmigos()
                        .getValueAt(view.getJTableAmigos().getSelectedRow(), 1).toString();
                String telefone = view.getJTableAmigos()
                        .getValueAt(view.getJTableAmigos().getSelectedRow(), 2).toString();

                view.setJTFNome(nome);
                view.setJTFTelefone(telefone);
            } else {
                view.mostrarMensagem("Selecione um amigo para editar.");
            }
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao carregar dados para edição: " + e.getMessage());
        }
    }

    /**
     * Carrega todos os amigos do banco de dados e atualiza a tabela na
     * interface.
     *
     * Etapas: - Limpa a tabela atual. - Adiciona uma linha para cada amigo
     * existente.
     */
    public void carregarTabela() {
        view.clearTable();
        for (Amigo amigo : amigoService.getListaAmigo()) {
            view.addRowToTable(amigo.getId(), amigo.getNome(), amigo.getTelefone());
        }
    }
}
