package frm;

import controle.GerenciadorFerramentaController;
import frm.FerramentaDAOFake;
import modelo.Ferramenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.FerramentaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.junit.jupiter.api.Assertions.*;

class GerenciadorFerramentaTest {

    private GerenciadorFerramentaFake fakeView;
    private GerenciadorFerramentaController controller;
    private FerramentaService ferramentaService;

    @BeforeEach
    void setUp() {
        ferramentaService = new FerramentaServiceFake();
        controller = new GerenciadorFerramentaController(null, ferramentaService);
        fakeView = new GerenciadorFerramentaFake(controller);
        controller.setView(fakeView);

        // Adiciona ferramentas falsas para garantir que haja algo na tabela


        // Configura a tabela
        String[] colunas = {"ID", "Nome", "Marca", "Valor"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);
        fakeView.setJTableFerramenta(tabela);

        atualizaTabelaComFerramentas();
    }
    @Test
    private void atualizaTabelaComFerramentas() {
        // Certifique-se de que 'getJTableFerramenta' está retornando a tabela corretamente
        JTable tabela = fakeView.getJTableFerramenta();
        DefaultTableModel model = (DefaultTableModel) tabela.getModel(); // Agora você pega o modelo da tabela
        model.setRowCount(0); // Limpa a tabela

        // Popular a tabela com os dados das ferramentas
        for (Ferramenta a : ferramentaService.getListaFerramenta()) {
            model.addRow(new Object[]{a.getId(), a.getNome(), a.getMarca(), String.valueOf(a.getValor())});
        }
    }

    @Test
    void testApagarFerramentaSemSelecionar() {
        controller.apagarFerramenta();
        assertEquals("Selecione uma Ferramenta para apagar primeiro", fakeView.getMensagem());
    }

    @Test
    void testEditarFerramentaNomeVazio() {
        fakeView.setJTFNome("");
        fakeView.setJTFValor("100");
        fakeView.setJTFMarca("tramontina");
        controller.editarFerramenta();
        assertEquals("Nome deve conter ao menos 1 caractere.", fakeView.getMensagem());
    }
    
        @Test
    void testEditarFerramentaMarcaVazia() {
        fakeView.setJTFNome("algo");
        fakeView.setJTFValor("100");
        fakeView.setJTFMarca("");
        controller.editarFerramenta();
        assertEquals("A marca deve conter ao menos 1 caractere.", fakeView.getMensagem());
    }
    
        @Test
    void testEditarFerramentaValorInvalido() {
        fakeView.setJTFNome("alguma coisa");
        fakeView.setJTFValor("asde");
        fakeView.setJTFMarca("tramontina");
        controller.editarFerramenta();
        assertEquals("O valor deve conter apenas números.", fakeView.getMensagem());
    }

    @Test
    void testEditarFerramentaSemSelecionar() {
        fakeView.setJTFNome("treco");
        fakeView.setJTFValor("100");
        fakeView.setJTFMarca("tramontina");
        controller.editarFerramenta();
        assertEquals("Escolha uma Ferramenta para Editar Primeiro", fakeView.getMensagem());
    }

    @Test
    void testEditarFerramentaComSucesso() {
        // Seleciona o primeiro amigo da tabela (que já foi populada no setUp)
        fakeView.getJTableFerramenta().setRowSelectionInterval(0, 0);

        fakeView.setJTFNome("treco");
        fakeView.setJTFMarca("tramontina express");
        fakeView.setJTFValor("123");

        controller.editarFerramenta();

        assertEquals("Ferramenta Editada com sucesso.", fakeView.getMensagem());

        // Atualiza a tabela para refletir edição (se seu controller não faz isso automaticamente)
        atualizaTabelaComFerramentas();

        // Confere se o nome foi realmente atualizado no serviço
        Ferramenta a = ferramentaService.carregaFerramenta(1);  
        assertEquals("treco", a.getNome());
    }
}

