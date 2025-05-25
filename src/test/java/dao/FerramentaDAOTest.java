package dao;

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

    /**
     * Configura o mock da conexão antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        ConexaoDAO.setMockConnection(mockConnection);
    }

    /**
     * Limpa estado compartilhado e desativa modo de teste após cada execução.
     */
    @AfterEach
    void tearDown() {
        ConexaoDAO.disableTestMode();
        FerramentaDAO.ListaFerramenta.clear();
    }

    /**
     * Testa se o método getListaFerramenta retorna uma lista com dados quando
     * há registros na base.
     */
    @Test
    void testGetListaFerramenta_ComDados() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("nome")).thenReturn("Martelo", "Chave de Fenda");
        when(mockResultSet.getInt("valor")).thenReturn(5, 10);

        ArrayList<Ferramenta> resultado = ferramentaDAO.getListaFerramenta();

        assertEquals(2, resultado.size());
        assertEquals("Martelo", resultado.get(0).getNome());
        assertEquals(10, resultado.get(1).getValor());
    }

    /**
     * Testa se o método getListaFerramenta retorna uma lista vazia quando não
     * há registros na base.
     */
    @Test
    void testGetListaFerramenta_Vazia() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Ferramenta> resultado = ferramentaDAO.getListaFerramenta();

        assertTrue(resultado.isEmpty());
    }

    /**
     * Testa o comportamento do método getListaFerramenta quando ocorre um erro
     * de SQL. Deve retornar uma lista vazia para evitar falha na aplicação.
     */
    @Test
    void testGetListaFerramenta_ComErroSQL() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Erro de conexão"));

        ArrayList<Ferramenta> resultado = ferramentaDAO.getListaFerramenta();

        assertTrue(resultado.isEmpty());
    }

    /**
     * Testa a inserção de uma nova ferramenta no banco com sucesso. Verifica se
     * o id gerado é obtido corretamente e os parâmetros são passados ao
     * PreparedStatement.
     */
    @Test
    void testInsertFerramentaBD_Sucesso() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT MAX(id) AS max_id FROM tb_ferramentas")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("max_id")).thenReturn(2);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Ferramenta ferramenta = new Ferramenta(0, "Martelo", "Tramontina", 25);

        boolean resultado = ferramentaDAO.insertFerramentaBD(ferramenta);

        assertTrue(resultado);
        verify(mockPreparedStatement).setInt(1, 3);
        verify(mockPreparedStatement).setString(2, "Martelo");
        verify(mockPreparedStatement).setString(3, "Tramontina");
        verify(mockPreparedStatement).setInt(4, 25);
    }

    /**
     * Testa a inserção da ferramenta quando ocorre erro ao tentar obter o maior
     * ID do banco. Espera que uma RuntimeException seja lançada.
     */
    @Test
    void testInsertFerramentaBD_ComErro() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        when(mockStatement.executeQuery("SELECT MAX(id) AS max_id FROM tb_ferramentas"))
                .thenThrow(new SQLException("Erro de leitura do último ID"));

        Ferramenta ferramenta = new Ferramenta(0, "Falha", "MarcaX", 10);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> ferramentaDAO.insertFerramentaBD(ferramenta));
        assertTrue(ex.getMessage().contains("Erro de leitura do último ID"));
    }

    /**
     * Testa exclusão bem-sucedida da ferramenta pelo ID. Verifica se o método
     * retorna true e o parâmetro é configurado corretamente.
     */
    @Test
    void testDeleteFerramentaBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = ferramentaDAO.deleteFerramentaBD(1);

        assertTrue(resultado);
        verify(mockPreparedStatement).setInt(1, 1);
    }

    /**
     * Testa exclusão de ferramenta quando ocorre erro SQL. O método deve
     * retornar false indicando falha na exclusão.
     */
    @Test
    void testDeleteFerramentaBD_ComErro() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erro de exclusão"));

        boolean resultado = ferramentaDAO.deleteFerramentaBD(1);

        assertFalse(resultado);
    }

    /**
     * Testa atualização bem-sucedida de uma ferramenta. Verifica se os
     * parâmetros são configurados e o retorno é true.
     */
    @Test
    void testUpdateFerramentaBD_Sucesso() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Ferramenta ferramenta = new Ferramenta(1, "Atualizada", "MarcaX", 15);
        boolean resultado = ferramentaDAO.updateFerramentaBD(ferramenta.getId(), ferramenta.getNome(), ferramenta.getMarca(), ferramenta.getValor());

        assertTrue(resultado);
        verify(mockPreparedStatement).setString(1, "Atualizada");
        verify(mockPreparedStatement).setString(2, "MarcaX");
        verify(mockPreparedStatement).setInt(3, 15);
        verify(mockPreparedStatement).setInt(4, 1);
    }

    /**
     * Testa atualização de ferramenta com falha na execução do SQL. Espera que
     * uma RuntimeException seja lançada.
     */
    @Test
    void testUpdateFerramentaBD_ComErro() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erro de atualização"));

        Ferramenta ferramenta = new Ferramenta(1, "Atualizada", "MarcaX", 15);

        assertThrows(RuntimeException.class, () -> {
            ferramentaDAO.updateFerramentaBD(
                    ferramenta.getId(), ferramenta.getNome(), ferramenta.getMarca(), ferramenta.getValor()
            );
        });
    }

    /**
     * Testa o carregamento de uma ferramenta existente pelo ID. Verifica se os
     * dados são corretamente populados no objeto retornado.
     */
    @Test
    void testCarregaFerramenta_Existente() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("nome")).thenReturn("Parafusadeira");
        when(mockResultSet.getString("marca")).thenReturn("MarcaX");
        when(mockResultSet.getInt("valor")).thenReturn(20);

        Ferramenta resultado = ferramentaDAO.carregaFerramenta(1);

        assertNotNull(resultado);
        assertEquals("Parafusadeira", resultado.getNome());
        assertEquals("MarcaX", resultado.getMarca());
        assertEquals(20, resultado.getValor());
    }

    /**
     * Testa o carregamento de ferramenta inexistente pelo ID. Espera retorno
     * null para indicar ausência de registro.
     */
    @Test
    void testCarregaFerramenta_NaoExistente() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Ferramenta resultado = ferramentaDAO.carregaFerramenta(999);

        assertNull(resultado);
    }

    /**
     * Testa o método que retorna o maior ID presente na tabela de ferramentas.
     */
    @Test
    void testMaiorID_ComDados() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(5);

        int maiorID = ferramentaDAO.maiorID();

        assertEquals(5, maiorID);
    }

    /**
     * Testa o retorno do método maiorID quando ocorre erro SQL. Deve retornar 0
     * como valor padrão.
     */
    @Test
    void testMaiorID_ComErroSQL() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Erro ao buscar maior ID"));

        int maiorID = ferramentaDAO.maiorID();

        assertEquals(0, maiorID);
    }

    /**
     * Testa se o método setListaFerramenta realmente altera a lista interna da
     * classe.
     */
    @Test
    void testSetListaFerramenta() {
        ArrayList<Ferramenta> novaLista = new ArrayList<>();
        novaLista.add(new Ferramenta(1, "Chave", "MarcaA", 12));
        ferramentaDAO.setListaFerramenta(novaLista);

        assertEquals(1, FerramentaDAO.ListaFerramenta.size());
        assertEquals("Chave", FerramentaDAO.ListaFerramenta.get(0).getNome());
    }
}
