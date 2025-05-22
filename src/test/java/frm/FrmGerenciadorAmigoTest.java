package frm;

import controle.GerenciadorAmigoController;
import frm.AmigoDAOFake;
import modelo.Amigo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.AmigoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.junit.jupiter.api.Assertions.*;

    class GerenciadorAmigoTest {

    private GerenciadorAmigoFake fakeView;
    private GerenciadorAmigoController controller;
    private AmigoService amigoService;

    @BeforeEach
    void setUp() {
  
        amigoService = new AmigoServiceFake();
        controller = new GerenciadorAmigoController(null, amigoService);
        fakeView = new GerenciadorAmigoFake(controller);
        controller.setView(fakeView); // caso precise

        // Configura a tabela simulada com colunas: ID, Nome, Telefone
        String[] colunas = {"ID", "Nome", "Telefone"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);
        fakeView.setJTableAmigos(tabela);

        // Popular a tabela com os dados do DAO Fake via serviço
        atualizaTabelaComAmigos();
    }

    private void atualizaTabelaComAmigos() {
        DefaultTableModel model = (DefaultTableModel) fakeView.getJTableAmigos().getModel();
        model.setRowCount(0); // limpa a tabela
        for (Amigo a : amigoService.getListaAmigo()) {
            model.addRow(new Object[]{a.getId(), a.getNome(), String.valueOf(a.getTelefone())});
        }
    }

    @Test
    void testApagarAmigoSemSelecionar() {
        controller.apagarAmigo();
        assertEquals("Selecione um Amigo para apagar primeiro", fakeView.getMensagem());
    }

    @Test
    void testEditarAmigoNomeVazio() {
        fakeView.setJTFNome("");
        fakeView.setJTFTelefone("999999999");
        controller.editarAmigo();
        assertEquals("Nome deve conter ao menos 1 caractere.", fakeView.getMensagem());
    }

    @Test
    void testEditarAmigoTelefoneInvalido() {
        fakeView.setJTFNome("João");
        fakeView.setJTFTelefone("abc123");  // inválido
        controller.editarAmigo();
        assertEquals("O Telefone deve ter entre 9 e 10 dígitos.", fakeView.getMensagem());
    }

    @Test
    void testEditarAmigoSemSelecionar() {
        fakeView.setJTFNome("João");
        fakeView.setJTFTelefone("999999999");
        controller.editarAmigo();
        assertEquals("Escolha um Amigo para Editar Primeiro", fakeView.getMensagem());
    }

    @Test
    void testEditarAmigoComSucesso() {
        // Seleciona o primeiro amigo da tabela (que já foi populada no setUp)
        fakeView.getJTableAmigos().setRowSelectionInterval(0, 0);

        fakeView.setJTFNome("Carlos Editado");
        fakeView.setJTFTelefone("987654321");

        controller.editarAmigo();

        assertEquals("Amigo Editado com sucesso.", fakeView.getMensagem());

        // Atualiza a tabela para refletir edição (se seu controller não faz isso automaticamente)
        atualizaTabelaComAmigos();

        // Confere se o nome foi realmente atualizado no serviço
        Amigo a = amigoService.carregaAmigo(1);  // id do primeiro amigo
        assertEquals("Carlos Editado", a.getNome());
    }
}
