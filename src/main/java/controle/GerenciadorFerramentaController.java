package controle;

import visao.FrmGerenciadorFerramenta;
import servico.FerramentaService;
import modelo.Ferramenta;

/**
 * Controlador responsável por gerenciar a interface e lógica de negócios
 * relacionada ao gerenciamento de ferramentas.
 */
public class GerenciadorFerramentaController {

    private FrmGerenciadorFerramenta view;
    private FerramentaService ferramentaService;

    /**
     * Construtor que inicializa o controlador com a view e o serviço de
     * ferramenta.
     *
     * @param view Instância da interface gráfica de gerenciamento de
     * ferramentas.
     * @param ferramentaService Serviço que lida com as operações de negócio
     * relacionadas às ferramentas.
     */
    public GerenciadorFerramentaController(FrmGerenciadorFerramenta view, FerramentaService ferramentaService) {
        this.view = view;
        this.ferramentaService = ferramentaService;
    }

    /**
     * Define ou atualiza a view utilizada pelo controlador.
     *
     * @param view Nova instância da view de ferramentas.
     */
    public void setView(FrmGerenciadorFerramenta view) {
        this.view = view;
    }

    /**
     * Exclui uma ferramenta selecionada na tabela.
     *
     * Etapas: - Verifica se uma ferramenta está selecionada. - Obtém o ID da
     * ferramenta. - Realiza a exclusão através do serviço. - Atualiza a
     * interface com mensagens e recarrega a tabela.
     */
    public void apagarFerramenta() {
        try {
            int id = 0;
            if (view.getJTableFerramenta().getSelectedRow() == -1) {
                view.mostrarMensagem("Selecione uma Ferramenta para apagar primeiro");
                return;
            } else {
                id = Integer.parseInt(view.getJTableFerramenta()
                        .getValueAt(view.getJTableFerramenta().getSelectedRow(), 0).toString());
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

    /**
     * Calcula o valor total das ferramentas listadas na tabela.
     *
     * Soma os valores da coluna de preços e exibe no campo de total da
     * interface. Ignora valores não numéricos e registra no console.
     */
    public void calcularValorTotal() {
        double total = 0;

        try {
            for (int i = 0; i < view.getJTableFerramenta().getRowCount(); i++) {
                String valorStr = view.getJTableFerramenta().getValueAt(i, 3).toString();
                try {
                    double valor = Double.parseDouble(valorStr);
                    total += valor;
                } catch (NumberFormatException e) {
                    System.err.println("Valor inválido na linha " + i + ": " + valorStr);
                }
            }

            view.setLabelTotal("R$ " + String.format("%.2f", total));

        } catch (Exception e) {
            view.mostrarMensagem("Erro ao calcular o valor total: " + e.getMessage());
        }
    }

    /**
     * Edita uma ferramenta selecionada com os dados informados na interface.
     *
     * Valida os dados de entrada, converte o valor para número e atualiza a
     * ferramenta utilizando o serviço. Exibe mensagens de sucesso ou erro e
     * atualiza a tabela.
     */
    public void editarFerramenta() {
        try {
            int id = 0;
            String nome = view.getJTFNome();
            String valorStr = view.getJTFValor();
            String marca = view.getJTFMarca();

            if (nome.length() < 1) {
                view.mostrarMensagem("Nome deve conter ao menos 1 caractere.");
                return;
            }

            if (marca.length() < 1) {
                view.mostrarMensagem("A marca deve conter ao menos 1 caractere.");
                return;
            }

            int valor;
            try {
                valor = Integer.parseInt(valorStr);
            } catch (NumberFormatException e) {
                view.mostrarMensagem("O valor deve conter apenas números.");
                return;
            }

            if (view.getJTableFerramenta().getSelectedRow() == -1) {
                view.mostrarMensagem("Escolha uma Ferramenta para Editar Primeiro");
                return;
            } else {
                id = Integer.parseInt(view.getJTableFerramenta()
                        .getValueAt(view.getJTableFerramenta().getSelectedRow(), 0).toString());
            }

            if (ferramentaService.updateFerramentaBD(id, nome, marca, valor)) {
                view.clearFields();
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

    /**
     * Carrega os dados da ferramenta selecionada na tabela para os campos da
     * interface.
     *
     * Preenche os campos de nome, marca e valor com os dados da linha
     * selecionada.
     */
    public void carregarTabelaGerenciador() {
        try {
            if (view.getJTableFerramenta().getSelectedRow() != -1) {
                String nome = view.getJTableFerramenta()
                        .getValueAt(view.getJTableFerramenta().getSelectedRow(), 1).toString();
                String marca = view.getJTableFerramenta()
                        .getValueAt(view.getJTableFerramenta().getSelectedRow(), 2).toString();
                String valor = view.getJTableFerramenta()
                        .getValueAt(view.getJTableFerramenta().getSelectedRow(), 3).toString();

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

    /**
     * Recarrega a tabela da interface com a lista atualizada de ferramentas.
     *
     * Limpa os dados antigos da tabela e insere os dados atuais obtidos do
     * serviço.
     */
    public void carregarTabela() {
        view.clearTable();
        for (Ferramenta ferramenta : ferramentaService.getListaFerramenta()) {
            view.addRowToTable(ferramenta.getId(), ferramenta.getNome(), ferramenta.getMarca(), ferramenta.getValor());
        }
    }
}
