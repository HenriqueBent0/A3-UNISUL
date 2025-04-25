package test;

import dao.FerramentaDAO;
import modelo.Ferramenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import servico.FerramentaService;

public class FerramentaServiceTest {

    private FerramentaDAO ferramentaDAOMock;
    private FerramentaService ferramentaService;

    @BeforeEach
    public void setUp() {
        ferramentaDAOMock = mock(FerramentaDAO.class);
        ferramentaService = new FerramentaService() {
            {
                this.ferramentaDAO = ferramentaDAOMock; // Injeção do mock
            }
        };
    }

    @Test
    public void testCadastrarFerramenta_ComDadosValidos_DeveRetornarTrue() {
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            when(ferramentaDAOMock.insertFerramentaBD(any(Ferramenta.class))).thenReturn(true);

            boolean resultado = ferramentaService.cadastrarFerramenta("Martelo", "Tramontina", "100");

            assertTrue(resultado);
            verify(ferramentaDAOMock, times(1)).insertFerramentaBD(any(Ferramenta.class));
        }
    }

    @Test
    public void testCadastrarFerramenta_NomeInvalido_DeveRetornarFalse() {
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            boolean resultado = ferramentaService.cadastrarFerramenta("A", "Tramontina", "100");

            assertFalse(resultado);
            verify(ferramentaDAOMock, never()).insertFerramentaBD(any(Ferramenta.class));
        }
    }

    @Test
    public void testCadastrarFerramenta_ValorVazio_DeveRetornarFalse() {
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            boolean resultado = ferramentaService.cadastrarFerramenta("Martelo", "Tramontina", "");

            assertFalse(resultado);
            verify(ferramentaDAOMock, never()).insertFerramentaBD(any(Ferramenta.class));
        }
    }

    @Test
    public void testCadastrarFerramenta_ValorInvalido_DeveRetornarFalse() {
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            boolean resultado = ferramentaService.cadastrarFerramenta("Martelo", "Tramontina", "abc");

            assertFalse(resultado);
            verify(ferramentaDAOMock, never()).insertFerramentaBD(any(Ferramenta.class));
        }
    }

    @Test
    public void testCadastrarFerramenta_FalhaNaInsercao_DeveRetornarFalse() {
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            when(ferramentaDAOMock.insertFerramentaBD(any(Ferramenta.class))).thenReturn(false);

            boolean resultado = ferramentaService.cadastrarFerramenta("Martelo", "Tramontina", "100");

            assertFalse(resultado);
            verify(ferramentaDAOMock, times(1)).insertFerramentaBD(any(Ferramenta.class));
        }
    }
    
        @Test
    public void testConstrutorPadrao_DeveInicializarComValoresPadrao() {
        Ferramenta ferramenta = new Ferramenta();

        assertEquals(0, ferramenta.getId());
        assertEquals("", ferramenta.getNome());
        assertEquals("", ferramenta.getMarca());
        assertEquals(0, ferramenta.getValor());
    }

    @Test
    public void testConstrutorComParametros_DeveInicializarComValoresInformados() {
        Ferramenta ferramenta = new Ferramenta(1, "Chave de Fenda", "Philips", 50);

        assertEquals(1, ferramenta.getId());
        assertEquals("Chave de Fenda", ferramenta.getNome());
        assertEquals("Philips", ferramenta.getMarca());
        assertEquals(50, ferramenta.getValor());
    }

    @Test
    public void testSettersAndGetters() {
        Ferramenta ferramenta = new Ferramenta();

        ferramenta.setId(2);
        ferramenta.setNome("Alicate");
        ferramenta.setMarca("Gedore");
        ferramenta.setValor(75);

        assertEquals(2, ferramenta.getId());
        assertEquals("Alicate", ferramenta.getNome());
        assertEquals("Gedore", ferramenta.getMarca());
        assertEquals(75, ferramenta.getValor());
    }
    
}