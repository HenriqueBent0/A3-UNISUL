package dao;

import dao.ConexaoDAO;
import dao.EmprestimoDAO;
import modelo.Emprestimo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmprestimoDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private EmprestimoDAO emprestimoDAO;

    @BeforeEach
    void setUp() {
        ConexaoDAO.setMockConnection(mockConnection);
    }

    @AfterEach
    void tearDown() {
        ConexaoDAO.disableTestMode();
        EmprestimoDAO.ListaEmprestimo.clear();
    }

    @Test
    void testGetListaEmprestimo_ComDados() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("nome")).thenReturn("João");
        when(mockResultSet.getString("data")).thenReturn("2024-05-01");
        when(mockResultSet.getString("nomeDaFerramenta")).thenReturn("Martelo");
        when(mockResultSet.getInt("idFerramenta")).thenReturn(2);

        ArrayList<Emprestimo> lista = emprestimoDAO.getListaEmprestimo();

        assertEquals(1, lista.size());
        assertEquals("João", lista.get(0).getNomeAmigo());
    }

    @Test
    void testGetListaEmprestimo_SemDados() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Emprestimo> lista = emprestimoDAO.getListaEmprestimo();

        assertTrue(lista.isEmpty());
    }

    @Test
    void testInsertEmprestimoBD_Sucesso() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT MAX(id) AS max_id FROM tb_emprestimos")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("max_id")).thenReturn(3);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Emprestimo emprestimo = new Emprestimo(0, "Ana", "2024-05-01", "Chave de Fenda", 5);

        boolean resultado = emprestimoDAO.insertEmprestimoBD(emprestimo);

        assertTrue(resultado);
        verify(mockPreparedStatement).setInt(1, 4);
        verify(mockPreparedStatement).setString(2, "Ana");
        verify(mockPreparedStatement).setString(3, "2024-05-01");
        verify(mockPreparedStatement).setString(4, "Chave de Fenda");
        verify(mockPreparedStatement).setInt(5, 5);
    }

    @Test
    void testInsertEmprestimoBD_Erro() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Erro de leitura"));

        Emprestimo emprestimo = new Emprestimo(0, "Carlos", "2024-05-02", "Serra", 4);

        assertThrows(RuntimeException.class, () -> emprestimoDAO.insertEmprestimoBD(emprestimo));
    }

    @Test
    void testDeleteEmprestimoBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = emprestimoDAO.deleteEmprestimoBD(1);

        assertTrue(resultado);
        verify(mockPreparedStatement).setInt(1, 1);
    }

    @Test
    void testDeleteEmprestimoBD_Erro() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erro ao excluir"));

        boolean resultado = emprestimoDAO.deleteEmprestimoBD(99);

        assertFalse(resultado);
    }

    @Test
    void testCarregaEmprestimo_Existente() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("nome")).thenReturn("Paulo");
        when(mockResultSet.getString("data")).thenReturn("2024-05-03");
        when(mockResultSet.getString("nomeDaFerramenta")).thenReturn("Alicate");
        when(mockResultSet.getInt("idFerramenta")).thenReturn(7);

        Emprestimo resultado = emprestimoDAO.carregaEmprestimo(10);

        assertNotNull(resultado);
        assertEquals("Paulo", resultado.getNomeAmigo());
        assertEquals("2024-05-03", resultado.getData());
        assertEquals("Alicate", resultado.getNomeDaFerramenta());
        assertEquals(7, resultado.getIdFerramenta());
    }

    @Test
    void testCarregaEmprestimo_NaoExistente() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Emprestimo resultado = emprestimoDAO.carregaEmprestimo(999);

        assertNotNull(resultado);  // objeto é instanciado mesmo sem dados
        assertEquals(999, resultado.getId());
        assertNull(resultado.getNomeAmigo());
    }

    @Test
    void testSetListaEmprestimo() {
        ArrayList<Emprestimo> novaLista = new ArrayList<>();
        emprestimoDAO.setListaEmprestimo(novaLista);

        assertEquals(novaLista, EmprestimoDAO.ListaEmprestimo);
    }
}
