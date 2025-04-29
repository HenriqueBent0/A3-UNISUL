package test;

import dao.ConexaoDAO;
import dao.FerramentaDAO;
import modelo.Ferramenta;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FerramentaDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private FerramentaDAO ferramentaDAO;

    @BeforeEach
    void setUp() {
        ConexaoDAO.setMockConnection(mockConnection);
    }

    @AfterEach
    void tearDown() {
        ConexaoDAO.disableTestMode();
        FerramentaDAO.ListaFerramenta.clear();
    }

    @Test
    void testGetListaFerramenta_ComDados() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("descricao")).thenReturn("Martelo", "Chave de Fenda");
        when(mockResultSet.getInt("quantidade")).thenReturn(5, 10);

        ArrayList<Ferramenta> resultado = ferramentaDAO.getListaFerramenta();

        assertEquals(2, resultado.size());
        assertEquals("Martelo", resultado.get(0).getNome());
        assertEquals(1, resultado.get(1).getValor());
    }

    @Test
    void testGetListaFerramenta_Vazia() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Ferramenta> resultado = ferramentaDAO.getListaFerramenta();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void testGetListaFerramenta_ComErroSQL() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Erro de conexão"));

        ArrayList<Ferramenta> resultado = ferramentaDAO.getListaFerramenta();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void testInsertFerramentaBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.execute()).thenReturn(true);

        Ferramenta ferramenta = new Ferramenta(1,"Alicate", "marca", 7);
        boolean resultado = ferramentaDAO.insertFerramentaBD(ferramenta);

        assertTrue(resultado);
        verify(mockPreparedStatement).setInt(1, 3);
        verify(mockPreparedStatement).setString(2, "Alicate");
        verify(mockPreparedStatement).setInt(3, 7);
    }

    @Test
    void testInsertFerramentaBD_ComErro() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.execute()).thenThrow(new SQLException("Erro de inserção"));

        Ferramenta ferramenta = new Ferramenta(2,"Falha", "marca2", 10);
        assertThrows(RuntimeException.class, () -> ferramentaDAO.insertFerramentaBD(ferramenta));
    }

    @Test
    void testDeleteFerramentaBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = ferramentaDAO.deleteFerramentaBD(1);

        assertTrue(resultado);
        verify(mockPreparedStatement).setInt(1, 1);
    }

    @Test
    void testDeleteFerramentaBD_ComErro() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erro de exclusão"));

        boolean resultado = ferramentaDAO.deleteFerramentaBD(1);

        assertFalse(resultado);
    }

    @Test
    void testUpdateFerramentaBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Ferramenta ferramenta = new Ferramenta(1, "Atualizada", "MarcaX", 15);
        boolean resultado = ferramentaDAO.updateFerramentaBD(ferramenta.getId(), ferramenta.getNome(), ferramenta.getMarca(), ferramenta.getValor());
        
        assertTrue(resultado);
        verify(mockPreparedStatement).setString(1, "Atualizada");
        verify(mockPreparedStatement).setInt(2, 15);
        verify(mockPreparedStatement).setInt(3, 1);
    }

    @Test
    void testUpdateFerramentaBD_ComErro() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erro de atualização"));

        Ferramenta ferramenta = new Ferramenta(1, "Atualizada", "MarcaX", 15);
        boolean resultado = ferramentaDAO.updateFerramentaBD(ferramenta.getId(), ferramenta.getNome(), ferramenta.getMarca(), ferramenta.getValor());
    }

    @Test
    void testCarregaFerramenta_Existente() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("descricao")).thenReturn("Parafusadeira");
        when(mockResultSet.getInt("quantidade")).thenReturn(20);

        Ferramenta resultado = ferramentaDAO.carregaFerramenta(1);

        assertNotNull(resultado);
        assertEquals("Parafusadeira", resultado.getNome());
        assertEquals(20, resultado.getValor());
    }

    @Test
    void testCarregaFerramenta_NaoExistente() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Ferramenta resultado = ferramentaDAO.carregaFerramenta(999);

        assertNull(resultado);
    }

    @Test
    void testMaiorID_ComDados() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(5);

        int maiorID = ferramentaDAO.maiorID();

        assertEquals(5, maiorID);
    }

    @Test
    void testMaiorID_ComErroSQL() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Erro ao buscar maior ID"));

        int maiorID = ferramentaDAO.maiorID();

        assertEquals(0, maiorID);
    }

    @Test
    void testSetMockConnection() {
        ConexaoDAO.setMockConnection(mockConnection);
        assertNotNull(ConexaoDAO.getConexao());
    }

    @Test
    void testSetListaFerramenta() {
        ArrayList<Ferramenta> listaTeste = new ArrayList<>();
        ferramentaDAO.setListaFerramenta(listaTeste);
        assertEquals(listaTeste, ferramentaDAO.ListaFerramenta);
    }

    @Test
    void testGetListaFerramenta_ComSQLException_Tratamento() throws SQLException {
        when(mockConnection.createStatement()).thenThrow(new SQLException("Erro de conexão"));

        ArrayList<Ferramenta> resultado = ferramentaDAO.getListaFerramenta();

        assertTrue(resultado.isEmpty());
        assertEquals(mockConnection, ConexaoDAO.getConexao());
    }
} 
