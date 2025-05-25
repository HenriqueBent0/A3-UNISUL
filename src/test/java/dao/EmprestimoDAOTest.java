package dao;

import modelo.Emprestimo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe EmprestimoDAO. Utiliza Mockito para simular
 * conexões e operações no banco de dados.
 */
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

    /**
     * Configura conexão mock antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        ConexaoDAO.setMockConnection(mockConnection);
    }

    /**
     * Limpa configurações após cada teste.
     */
    @AfterEach
    void tearDown() {
        ConexaoDAO.disableTestMode();
        EmprestimoDAO.ListaEmprestimo.clear();
    }

    /**
     * Testa obtenção da lista de empréstimos quando há dados.
     */
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

    /**
     * Testa obtenção da lista de empréstimos quando não há dados.
     */
    @Test
    void testGetListaEmprestimo_SemDados() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Emprestimo> lista = emprestimoDAO.getListaEmprestimo();

        assertTrue(lista.isEmpty());
    }

    /**
     * Testa inserção bem-sucedida de empréstimo no banco.
     */
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

    /**
     * Testa comportamento na inserção quando ocorre erro de SQL.
     */
    @Test
    void testInsertEmprestimoBD_Erro() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Erro de leitura"));

        Emprestimo emprestimo = new Emprestimo(0, "Carlos", "2024-05-02", "Serra", 4);

        assertThrows(RuntimeException.class, () -> emprestimoDAO.insertEmprestimoBD(emprestimo));
    }

    /**
     * Testa exclusão de empréstimo com sucesso.
     */
    @Test
    void testDeleteEmprestimoBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = emprestimoDAO.deleteEmprestimoBD(1);

        assertTrue(resultado);
        verify(mockPreparedStatement).setInt(1, 1);
    }

    /**
     * Testa exclusão de empréstimo quando ocorre erro.
     */
    @Test
    void testDeleteEmprestimoBD_Erro() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erro ao excluir"));

        boolean resultado = emprestimoDAO.deleteEmprestimoBD(99);

        assertFalse(resultado);
    }

    /**
     * Testa carregar empréstimo existente pelo id.
     */
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

    /**
     * Testa carregar empréstimo que não existe.
     */
    @Test
    void testCarregaEmprestimo_NaoExistente() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Emprestimo resultado = emprestimoDAO.carregaEmprestimo(999);

        assertNotNull(resultado);  // deve retornar objeto vazio mesmo sem dados
        assertEquals(999, resultado.getId());
        assertNull(resultado.getNomeAmigo());
    }

    /**
     * Testa a configuração da lista estática de empréstimos.
     */
    @Test
    void testSetListaEmprestimo() {
        ArrayList<Emprestimo> novaLista = new ArrayList<>();
        emprestimoDAO.setListaEmprestimo(novaLista);

        assertEquals(novaLista, EmprestimoDAO.ListaEmprestimo);
    }
}
