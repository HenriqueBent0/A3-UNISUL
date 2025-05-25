package dao;

import modelo.Amigo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes para a classe AmigoDAO.
 */
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
        ConexaoDAO.setMockConnection(mockConnection);
    }

    @AfterEach
    void tearDown() {
        ConexaoDAO.disableTestMode();
        AmigoDAO.ListaAmigo.clear();
    }

    /**
     * Testa se getListaAmigo retorna amigos corretamente.
     */
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

    /**
     * Testa se getListaAmigo retorna lista vazia quando não tem dados.
     */
    @Test
    void testGetListaAmigo_Vazia() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Amigo> resultado = amigoDAO.getListaAmigo();

        assertTrue(resultado.isEmpty());
    }

    /**
     * Testa inserção de amigo no banco com sucesso.
     */
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

    /**
     * Testa exclusão de amigo pelo ID.
     */
    @Test
    void testDeleteAmigoBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = amigoDAO.deleteAmigoBD(1);

        assertTrue(resultado);
        verify(mockPreparedStatement).setInt(1, 1);
    }

    /**
     * Testa atualização de dados do amigo.
     */
    @Test
    void testUpdateAmigoBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Amigo amigo = new Amigo("Atualizado", 1, 55555555);
        boolean resultado = amigoDAO.updateAmigoBD(amigo);

        assertTrue(resultado);
    }

    /**
     * Testa carregar amigo pelo ID.
     */
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
    }

    /**
     * Testa maior ID retornado.
     */
    @Test
    void testMaiorID_ComDados() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(5);

        int maiorID = amigoDAO.maiorID();

        assertEquals(5, maiorID);
    }
}
