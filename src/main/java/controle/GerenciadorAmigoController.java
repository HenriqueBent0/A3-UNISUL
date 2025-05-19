package controle;

import visao.FrmGerenciadorAmigo;
import servico.AmigoService;
import modelo.Amigo;

public class GerenciadorAmigoController {

    private FrmGerenciadorAmigo view;
    private AmigoService amigoService;

    public GerenciadorAmigoController(FrmGerenciadorAmigo view, AmigoService amigoService) {
        this.view = view;
        this.amigoService = amigoService;
    }

    public void setView(FrmGerenciadorAmigo view) {
        this.view = view;
    }

    // Lógica de apagar amigo
    public void apagarAmigo() {
        try {
            int id = 0;
            if (view.getJTableAmigos().getSelectedRow() == -1) {
                view.mostrarMensagem("Selecione um Amigo para apagar primeiro");
                return;
            } else {
                id = Integer.parseInt(view.getJTableAmigos().getValueAt(view.getJTableAmigos().getSelectedRow(), 0).toString());
            }

            // Apaga o amigo sem a confirmação extra
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

    // Lógica de editar amigo
    public void editarAmigo() {
        try {
            int id = 0;
            String nome = view.getJTFNome();
            String telefoneStr = view.getJTFTelefone(); // Pega o telefone como String

            // Verifica se o nome não está vazio
            if (nome.length() < 1) {
                view.mostrarMensagem("Nome deve conter ao menos 1 caractere.");
                return;
            }

            // Verifica se o telefone contém apenas números e tem o tamanho correto
            if (telefoneStr.length() < 9 || telefoneStr.length() > 10) {
                view.mostrarMensagem("O Telefone deve ter entre 9 e 10 dígitos.");
                return;
            }

            // Tenta converter o telefone para número (se possível)
            int telefone;
            try {
                telefone = Integer.parseInt(telefoneStr); // Converte para inteiro
            } catch (NumberFormatException e) {
                view.mostrarMensagem("O telefone deve conter apenas números.");
                return;
            }

            // Verifica se algum amigo foi selecionado
            if (view.getJTableAmigos().getSelectedRow() == -1) {
                view.mostrarMensagem("Escolha um Amigo para Editar Primeiro");
                return;
            } else {
                id = Integer.parseInt(view.getJTableAmigos().getValueAt(view.getJTableAmigos().getSelectedRow(), 0).toString());
            }

            // Realiza a atualização do amigo
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

    // Lógica para carregar dados na tabela quando um item é selecionado
    public void carregarTabelaGerenciador() {
        try {
            if (view.getJTableAmigos().getSelectedRow() != -1) {
                String nome = view.getJTableAmigos().getValueAt(view.getJTableAmigos().getSelectedRow(), 1).toString();
                String telefone = view.getJTableAmigos().getValueAt(view.getJTableAmigos().getSelectedRow(), 2).toString();

                view.setJTFNome(nome);
                view.setJTFTelefone(telefone);
            } else {
                view.mostrarMensagem("Selecione um amigo para editar.");
            }
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao carregar dados para edição: " + e.getMessage());
        }

    }

    public void carregarTabela() {
        view.clearTable();
        for (Amigo amigo : amigoService.getListaAmigo()) {
            view.addRowToTable(amigo.getId(), amigo.getNome(), amigo.getTelefone());
        }
    }
}
