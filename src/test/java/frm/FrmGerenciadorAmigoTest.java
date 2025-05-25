package frm;

import controle.GerenciadorAmigoController;
import modelo.Amigo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.AmigoService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a tela de Gerenciar Amigos.
 */
public class FrmGerenciadorAmigoTest {

    private GerenciadorAmigoFake fakeView;
    private GerenciadorAmigoController controller;
    private AmigoService amigoService;

    // Configura os objetos antes de cada teste
    @BeforeEach
    void setUp() {
        amigoService = new AmigoServiceFake();
        controller = new GerenciadorAmigoController(null, amigoService);
        fakeView = new GerenciadorAmigoFake(controller);
        controller.setView(fakeView);

        // Cria tabela com colunas e adiciona à view fake
        String[] colunas = {"ID", "Nome", "Telefone"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);
        fakeView.setJTableAmigos(tabela);

        // Preenche a tabela com dados falsos
        atualizaTabelaComAmigos();
    }

    // Atualiza os dados da tabela com base no serviço fake
    private void atualizaTabelaComAmigos() {
        DefaultTableModel model = (DefaultTableModel) fakeView.getJTableAmigos().getModel();
        model.setRowCount(0); // limpa
        for (Amigo a : amigoService.getListaAmigo()) {
            model.addRow(new Object[]{a.getId(), a.getNome(), String.valueOf(a.getTelefone())});
        }
    }

    // Testa apagar amigo sem selecionar ninguém
    @Test
    void testApagarAmigoSemSelecionar() {
        controller.apagarAmigo();
        assertEquals("Selecione um Amigo para apagar primeiro", fakeView.getMensagem());
    }

    // Testa editar amigo com nome vazio
    @Test
    void testEditarAmigoNomeVazio() {
        fakeView.setJTFNome("");
        fakeView.setJTFTelefone("999999999");
        controller.editarAmigo();
        assertEquals("Nome deve conter ao menos 1 caractere.", fakeView.getMensagem());
    }

    // Testa editar amigo com telefone inválido
    @Test
    void testEditarAmigoTelefoneInvalido() {
        fakeView.setJTFNome("João");
        fakeView.setJTFTelefone("abc123");
        controller.editarAmigo();
        assertEquals("O Telefone deve ter entre 9 e 10 dígitos.", fakeView.getMensagem());
    }

    // Testa editar amigo sem selecionar linha
    @Test
    void testEditarAmigoSemSelecionar() {
        fakeView.setJTFNome("João");
        fakeView.setJTFTelefone("999999999");
        controller.editarAmigo();
        assertEquals("Escolha um Amigo para Editar Primeiro", fakeView.getMensagem());
    }

    // Testa editar amigo com sucesso
    @Test
    void testEditarAmigoComSucesso() {
        fakeView.getJTableAmigos().setRowSelectionInterval(0, 0);
        fakeView.setJTFNome("Carlos Editado");
        fakeView.setJTFTelefone("987654321");
        controller.editarAmigo();
        assertEquals("Amigo Editado com sucesso.", fakeView.getMensagem());

        atualizaTabelaComAmigos();

        Amigo a = amigoService.carregaAmigo(1);
        assertEquals("Carlos Editado", a.getNome());
    }

    // Testa carregar dados do amigo selecionado para os campos de texto
    @Test
    void testCarregarTabelaGerenciadorComLinhaSelecionada() {
        fakeView.getJTableAmigos().setRowSelectionInterval(0, 0);
        controller.carregarTabelaGerenciador();
        assertEquals("João", fakeView.getJTFNome());
        assertEquals("123456789", fakeView.getJTFTelefone());
    }
}
