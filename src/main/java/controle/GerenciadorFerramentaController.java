
package controle;

import visao.FrmGerenciadorFerramenta;
import servico.FerramentaService;
import modelo.Ferramenta;

public class GerenciadorFerramentaController {

    private FrmGerenciadorFerramenta view;
    private FerramentaService ferramentaService;

    public GerenciadorFerramentaController(FrmGerenciadorFerramenta view, FerramentaService ferramentaService) {
        this.view = view;
        this.ferramentaService = ferramentaService;
    }

    public void setView(FrmGerenciadorFerramenta view) {
        this.view = view;
    }

    // Lógica de apagar ferramenta
    public void apagarFerramenta() {
        try {
            int id = 0;
            if (view.getJTableFerramenta().getSelectedRow() == -1) {
                view.mostrarMensagem("Selecione uma Ferramenta para apagar primeiro");
                return;
            } else {
                id = Integer.parseInt(view.getJTableFerramenta().getValueAt(view.getJTableFerramenta().getSelectedRow(), 0).toString());
            }

            
            if (ferramentaService.deleteFerramentaBD(id)) {
                view.clearFields();
                view.mostrarMensagem("Ferramenta Apagada com Sucesso.");
            }

        } catch (RuntimeException erro) {
            view.mostrarMensagem(erro.getMessage());
        } finally {
            carregarTabela();
        }
    }

    public void calcularValorTotal() {
        double total = 0;

        try {
            for (int i = 0; i < view.getJTableFerramenta().getRowCount(); i++) {
                String valorStr = view.getJTableFerramenta().getValueAt(i, 3).toString();
                try {
                    double valor = Double.parseDouble(valorStr);
                    total += valor;
                } catch (NumberFormatException e) {
                    // Ignora valores inválidos, mas poderia logar ou exibir aviso se necessário
                    System.err.println("Valor inválido na linha " + i + ": " + valorStr);
                }
            }

            view.setLabelTotal("R$ " + String.format("%.2f", total));

        } catch (Exception e) {
            view.mostrarMensagem("Erro ao calcular o valor total: " + e.getMessage());
        }
    }

    // Lógica de editar ferramenta
    public void editarFerramenta() {
        try {
            int id = 0;
            String nome = view.getJTFNome();
            String valorStr = view.getJTFValor();
            String marca = view.getJTFMarca();

            // Verifica se o nome não está vazio
            if (nome.length() < 1) {
                view.mostrarMensagem("Nome deve conter ao menos 1 caractere.");
                return;
            }

            // Verifica se a marca não está vazia
            if (marca.length() < 1) {
                view.mostrarMensagem("A marca deve conter ao menos 1 caractere.");
                return;
            }

            // Verifica se o valor é numérico e válido
            int valor;
            try {
                valor = Integer.parseInt(valorStr);
            } catch (NumberFormatException e) {
                view.mostrarMensagem("O valor deve conter apenas números.");
                return;
            }

            // Verifica se uma ferramenta foi selecionada
            if (view.getJTableFerramenta().getSelectedRow() == -1) {
                view.mostrarMensagem("Escolha uma Ferramenta para Editar Primeiro");
                return;
            } else {
                id = Integer.parseInt(view.getJTableFerramenta().getValueAt(view.getJTableFerramenta().getSelectedRow(), 0).toString());
            }

            // Atualiza a ferramenta no banco
            if (ferramentaService.updateFerramentaBD(id, nome, marca, valor)) {
                view.clearFields(); // Limpa os campos
                view.mostrarMensagem("Ferramenta Editada com sucesso.");
            }

        } catch (RuntimeException erro) {
            view.mostrarMensagem(erro.getMessage());
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado: " + e.getMessage());
        } finally {
            carregarTabela();
            calcularValorTotal();
        }
    }

    // Lógica para carregar dados na tabela quando um item é selecionado
    public void carregarTabelaGerenciador() {
        try {
            if (view.getJTableFerramenta().getSelectedRow() != -1) {
                String nome = view.getJTableFerramenta().getValueAt(view.getJTableFerramenta().getSelectedRow(), 1).toString();
                String marca = view.getJTableFerramenta().getValueAt(view.getJTableFerramenta().getSelectedRow(), 2).toString();
                String valor = view.getJTableFerramenta().getValueAt(view.getJTableFerramenta().getSelectedRow(), 3).toString();

                view.setJTFNome(nome);
                view.setJTFMarca(marca);
                view.setJTFValor(valor);

            } else {
                view.mostrarMensagem("Selecione um ferramenta para editar.");
            }
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao carregar dados para edição: " + e.getMessage());
        }

    }

    public void carregarTabela() {
        view.clearTable();
        for (Ferramenta ferramenta : ferramentaService.getListaFerramenta()) {
            view.addRowToTable(ferramenta.getId(), ferramenta.getNome(), ferramenta.getMarca(), ferramenta.getValor());
        }

    }
}

