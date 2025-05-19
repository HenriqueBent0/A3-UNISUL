package test;

import controle.GerenciadorAmigoController;
import modelo.Amigo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.AmigoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GerenciadorAmigoTest {

    private GerenciadorAmigoFake fakeView;
    private GerenciadorAmigoController controller;
    private AmigoService amigoService;

    @BeforeEach
    void setUp() {
        fakeView = new GerenciadorAmigoFake();
        amigoService = new AmigoServiceFake(); // você precisa criar essa classe também (um mock/fake do AmigoService)
        controller = new GerenciadorAmigoController(fakeView, amigoService);

        // Configura a tabela com colunas simuladas
        String[] colunas = {"ID", "Nome", "Telefone"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);
        fakeView.setJTableAmigos(tabela);
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
        fakeView.setJTFTelefone("abc123");
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
        // Prepara a tabela com um amigo selecionado
        DefaultTableModel model = (DefaultTableModel) fakeView.getJTableAmigos().getModel();
        model.addRow(new Object[]{1, "Carlos", "987654321"});
        fakeView.getJTableAmigos().setRowSelectionInterval(0, 0);

        fakeView.setJTFNome("Carlos");
        fakeView.setJTFTelefone("987654321");

        controller.editarAmigo();

        assertEquals("Amigo Editado com sucesso.", fakeView.getMensagem());
    }

}
