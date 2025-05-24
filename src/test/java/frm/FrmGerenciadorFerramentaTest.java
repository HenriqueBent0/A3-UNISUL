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
void testCarregarTabelaGerenciadorComLinhaSelecionada() {
    // Seleciona a primeira linha da tabela
    fakeView.getJTableFerramenta().setRowSelectionInterval(0, 0);

    // Chama o método que carrega os dados do item selecionado
    controller.carregarTabelaGerenciador();

    // Agora você pode verificar se os campos da view foram atualizados corretamente
    assertEquals("treco", fakeView.getJTFNome());
    assertEquals("alguma marca", fakeView.getJTFMarca());
    assertEquals("100", fakeView.getJTFValor());
}
@Test
void testClearFields() {
    fakeView.setJTFNome("nome qualquer");
    fakeView.setJTFMarca("marca qualquer");
    fakeView.setJTFValor("123");

    fakeView.clearFields();

    assertEquals("", fakeView.getJTFNome());
    assertEquals("", fakeView.getJTFMarca());
    assertEquals("", fakeView.getJTFValor());
}

@Test
void testGettersBotoes() {
    assertNotNull(fakeView.getJBApagar());
    assertNotNull(fakeView.getJBCancelar());
    assertNotNull(fakeView.getJBEditar());
}
@Test
void testApagarFerramentaComLinhaSelecionada() {
    // Seleciona a primeira linha da tabela
    fakeView.getJTableFerramenta().setRowSelectionInterval(0, 0);

    // Chama o método apagarFerramenta
    controller.apagarFerramenta();

    // Verifica se a mensagem de sucesso apareceu
    assertEquals("Ferramenta Apagada com Sucesso.", fakeView.getMensagem());

    // Pode também verificar se os campos foram limpos (se desejar)
    assertEquals("", fakeView.getJTFNome());
    assertEquals("", fakeView.getJTFMarca());
    assertEquals("", fakeView.getJTFValor());
}
}

