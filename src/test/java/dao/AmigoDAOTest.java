package dao;

import dao.AmigoDAO;
import dao.ConexaoDAO;
import modelo.Amigo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AmigoDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private AmigoDAO amigoDAO;

    @BeforeEach
    void setUp() {
        ConexaoDAO.setMockConnection(mockConnection);  // Configura a conexão mockada
    }

    @AfterEach
    void tearDown() {
        ConexaoDAO.disableTestMode();
        AmigoDAO.ListaAmigo.clear();
    }

    // ---- TESTES PARA getListaAmigo() ----
    @Test
    void testGetListaAmigo_ComDados() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("nome")).thenReturn("João", "Maria");
        when(mockResultSet.getInt("telefone")).thenReturn(99999999, 88888888);

        ArrayList<Amigo> resultado = amigoDAO.getListaAmigo();

        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
        assertEquals("Maria", resultado.get(1).getNome());
    }

    @Test
    void testGetListaAmigo_Vazia() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Amigo> resultado = amigoDAO.getListaAmigo();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void testGetListaAmigo_ComErroSQL() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Erro de conexão"));

        ArrayList<Amigo> resultado = amigoDAO.getListaAmigo();

        assertTrue(resultado.isEmpty());
    }

    // ---- TESTES PARA insertAmigoBD() ----
    @Test
    void testInsertAmigoBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.execute()).thenReturn(true);

        Amigo amigo = new Amigo("Carlos", 3, 77777777);
        boolean resultado = amigoDAO.insertAmigoBD(amigo);

        assertTrue(resultado);
        verify(mockPreparedStatement).setInt(1, 3);
        verify(mockPreparedStatement).setString(2, "Carlos");
        verify(mockPreparedStatement).setInt(3, 77777777);
    }

    @Test
    void testInsertAmigoBD_ComErro() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.execute()).thenThrow(new SQLException("Erro de inserção"));

        Amigo amigo = new Amigo("Falha", 99, 00000000);
        assertThrows(RuntimeException.class, () -> amigoDAO.insertAmigoBD(amigo));
    }

    // ---- TESTES PARA deleteAmigoBD() ----
    @Test
    void testDeleteAmigoBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = amigoDAO.deleteAmigoBD(1);

        assertTrue(resultado);
        verify(mockPreparedStatement).setInt(1, 1);
    }

    @Test
    void testDeleteAmigoBD_ComErro() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erro de exclusão"));

        boolean resultado = amigoDAO.deleteAmigoBD(1);

        assertFalse(resultado);
    }

    // ---- TESTES PARA updateAmigoBD() ----
    @Test
    void testUpdateAmigoBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Amigo amigo = new Amigo("Atualizado", 1, 55555555);
        boolean resultado = amigoDAO.updateAmigoBD(amigo);

        assertTrue(resultado);
        verify(mockPreparedStatement).setString(1, "Atualizado");
        verify(mockPreparedStatement).setInt(2, 55555555);
        verify(mockPreparedStatement).setInt(3, 1);
    }

    @Test
    void testUpdateAmigoBD_ComErro() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erro de atualização"));

        Amigo amigo = new Amigo("Falha", 99, 00000000);

        // Testa se o RuntimeException é lançado
        assertThrows(RuntimeException.class, () -> amigoDAO.updateAmigoBD(amigo));
    }

    // ---- TESTES PARA carregaAmigo() ----
    @Test
    void testCarregaAmigo_Existente() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("nome")).thenReturn("Teste");
        when(mockResultSet.getInt("telefone")).thenReturn(12345678);

        Amigo resultado = amigoDAO.carregaAmigo(1);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.getNome());
        assertEquals(12345678, resultado.getTelefone());
    }

    @Test
    void testCarregaAmigo_NaoExistente() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Amigo resultado = amigoDAO.carregaAmigo(999);

        assertNull(resultado);
    }

    // ---- TESTES PARA maiorID() ----
    @Test
    void testMaiorID_ComDados() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(5);

        int maiorID = amigoDAO.maiorID();

        assertEquals(5, maiorID);
    }

    @Test
    void testMaiorID_ComErroSQL() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Erro ao buscar maior ID"));

        int maiorID = amigoDAO.maiorID();

        assertEquals(0, maiorID); // Esperamos 0, já que a variável foi inicializada com 0 e não houve retorno de dados
    }

    // ---- TESTES PARA Exceções em insertAmigoBD() e updateAmigoBD() ----
    @Test
    void testInsertAmigoBD_ComSQLException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.execute()).thenThrow(new SQLException("Erro de inserção"));

        Amigo amigo = new Amigo("Falha", 99, 00000000);

        // Testa se o RuntimeException é lançado
        assertThrows(RuntimeException.class, () -> amigoDAO.insertAmigoBD(amigo));
    }

    @Test
    void testUpdateAmigoBD_ComSQLException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erro de atualização"));

        Amigo amigo = new Amigo("Falha", 99, 00000000);

        // Testa se o RuntimeException é lançado
        assertThrows(RuntimeException.class, () -> amigoDAO.updateAmigoBD(amigo));
    }

    // ---- TESTE PARA CONEXÃO SETADA ----
    @Test
    void testSetMockConnection() {
        ConexaoDAO.setMockConnection(mockConnection); // A linha que configura a conexão mockada
        assertNotNull(ConexaoDAO.getConexao()); // Garante que a conexão foi configurada corretamente
    }

    // ---- TESTE PARA setListaAmigo() ----
    @Test
    void testSetListaAmigo() {
        ArrayList<Amigo> listaTeste = new ArrayList<>();
        amigoDAO.setListaAmigo(listaTeste);
        assertEquals(listaTeste, amigoDAO.ListaAmigo);
    }

   @Test
void testGetListaAmigo_ComSQLException_Tratamento() throws SQLException {
   
    when(mockConnection.createStatement()).thenThrow(new SQLException("Erro de conexão"));

    
    ArrayList<Amigo> resultado = amigoDAO.getListaAmigo();

    
    assertTrue(resultado.isEmpty(), "A lista não deve ser preenchida se houver erro");

    
    assertEquals(mockConnection, ConexaoDAO.getConexao(), "A conexão não foi reatribuída corretamente");
}
@Test
void testCarregaAmigo_ComSQLException() throws SQLException {
    when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Erro ao carregar amigo"));

    Amigo resultado = amigoDAO.carregaAmigo(1);

    assertNull(resultado); // Esperado, pois houve exceção
}


}

