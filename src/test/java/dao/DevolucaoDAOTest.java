package dao;

import dao.ConexaoDAO;
import dao.DevolucaoDAO;
import modelo.Devolucao;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DevolucaoDAOTest {

    private DevolucaoDAO dao;
    private Connection connectionMock;
    private Statement statementMock;
    private PreparedStatement preparedStatementMock;
    private ResultSet resultSetMock;

    @BeforeEach
    void setup() {
        dao = new DevolucaoDAO();
        connectionMock = mock(Connection.class);
        statementMock = mock(Statement.class);
        preparedStatementMock = mock(PreparedStatement.class);
        resultSetMock = mock(ResultSet.class);
    }

    @AfterEach
    void tearDown() {
        DevolucaoDAO.listaDevolucao.clear();
    }

    @Test
    @Order(1)
    void testInsertDevolucaoBD() throws Exception {
        try (MockedStatic<dao.ConexaoDAO> conexaoDAOMock = Mockito.mockStatic(dao.ConexaoDAO.class)) {
            conexaoDAOMock.when(() -> ConexaoDAO.getConexao()).thenReturn(connectionMock);

            when(connectionMock.createStatement()).thenReturn(statementMock);
            when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true);
            when(resultSetMock.getInt("max_id")).thenReturn(1);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.execute()).thenReturn(true);

            Devolucao devolucao = new Devolucao("Teste", 1, "2025-04-25", 0, "Martelo");

            boolean result = dao.insertDevolucaoBD(devolucao);

            assertTrue(result);
            verify(preparedStatementMock, times(1)).execute();
        }
    }

    @Test
    @Order(2)
    void testInsertDevolucaoBDWithSQLException() throws Exception {
        try (MockedStatic<dao.ConexaoDAO> conexaoDAOMock = Mockito.mockStatic(dao.ConexaoDAO.class)) {
            conexaoDAOMock.when(() -> ConexaoDAO.getConexao()).thenReturn(connectionMock);

            when(connectionMock.createStatement()).thenReturn(statementMock);
            when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true);
            when(resultSetMock.getInt("max_id")).thenReturn(1);

            // Simulando erro de SQL ao preparar o statement
            when(connectionMock.prepareStatement(anyString())).thenThrow(new SQLException("Erro ao preparar statement"));

            Devolucao devolucao = new Devolucao("Teste", 1, "2025-04-25", 0, "Martelo");

            // Esperamos que o método lance uma exceção em caso de erro
            assertThrows(RuntimeException.class, () -> dao.insertDevolucaoBD(devolucao));
        }
    }

    @Test
    @Order(3)
    void testGetListaDevolucao() throws Exception {
        try (MockedStatic<dao.ConexaoDAO> conexaoDAOMock = Mockito.mockStatic(dao.ConexaoDAO.class)) {
            conexaoDAOMock.when(() -> ConexaoDAO.getConexao()).thenReturn(connectionMock);

            when(connectionMock.createStatement()).thenReturn(statementMock);
            when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);

            when(resultSetMock.next()).thenReturn(true, false);
            when(resultSetMock.getInt("id")).thenReturn(1);
            when(resultSetMock.getString("nomeAmigo")).thenReturn("Teste");
            when(resultSetMock.getString("data")).thenReturn("2025-04-25");
            when(resultSetMock.getString("nomeDaFerramenta")).thenReturn("Martelo");
            when(resultSetMock.getInt("idFerramenta")).thenReturn(10);

            ArrayList<Devolucao> lista = dao.getListaDevolucao();

            assertNotNull(lista);
            assertEquals(1, lista.size());
            assertEquals("Teste", lista.get(0).getNomeAmigo());
        }
    }

    @Test
    @Order(4)
    void testGetListaDevolucaoWithSQLException() throws Exception {
        try (MockedStatic<dao.ConexaoDAO> conexaoDAOMock = Mockito.mockStatic(dao.ConexaoDAO.class)) {
            conexaoDAOMock.when(() -> ConexaoDAO.getConexao()).thenReturn(connectionMock);

            when(connectionMock.createStatement()).thenReturn(statementMock);
            when(statementMock.executeQuery(anyString())).thenThrow(new SQLException("Erro ao executar consulta"));

            // Esperamos que o método lance uma exceção ou retorne uma lista vazia
            ArrayList<Devolucao> lista = dao.getListaDevolucao();

            // A lista pode ser vazia ou o método pode lançar uma exceção dependendo da implementação
            assertTrue(lista.isEmpty());
        }
    }

    @Test
    @Order(5)
    void testContarEmprestimosPorPessoa() {
        Devolucao devolucao1 = new Devolucao("João", 1, "2025-04-25", 1, "Chave de Fenda");
        Devolucao devolucao2 = new Devolucao("João", 2, "2025-04-26", 2, "Martelo");
        Devolucao devolucao3 = new Devolucao("Maria", 3, "2025-04-27", 3, "Serrote");

        DevolucaoDAO.listaDevolucao.clear();
        DevolucaoDAO.listaDevolucao.add(devolucao1);
        DevolucaoDAO.listaDevolucao.add(devolucao2);
        DevolucaoDAO.listaDevolucao.add(devolucao3);

        int countJoao = dao.contarEmprestimosPorPessoa("João");
        int countMaria = dao.contarEmprestimosPorPessoa("Maria");

        assertEquals(2, countJoao);
        assertEquals(1, countMaria);
    }

    @Test
    @Order(6)
    void testConstrutorPadrao() {
        DevolucaoDAO daoPadrao = new DevolucaoDAO();
        assertNotNull(daoPadrao);
    }
}
