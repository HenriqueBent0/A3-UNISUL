package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoDAO {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SERVER = "127.0.0.1";
    private static final String DATABASE = "emprestimodeferramentas";
    private static final String URL = "jdbc:mysql://" + SERVER + ":3306/" + DATABASE + "?useTimezone=true&serverTimezone=UTC";

    // Usando variáveis de ambiente configuradas na JVM
    private static final String USER = System.getProperty("DB_USER", "root"); // 'root' é o valor padrão
    private static final String PASSWORD = System.getProperty("DB_PASSWORD", "root"); // 'root' é o valor padrão
    
    // Variável estática para armazenar a conexão
    private static Connection conexao;
    
    // Flag para controle de modo de teste
    private static boolean testeMode = false;

    // Logger para registrar as exceções
    private static final Logger logger = Logger.getLogger(ConexaoDAO.class.getName());

    public static Connection getConexao() {
        if (testeMode) {
            return conexao; // Retorna a conexão mockada em modo de teste
        }
        
        try {
            // Registra o driver
            Class.forName(DRIVER);
            // Cria a conexão com o banco usando as credenciais da JVM
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Status: Conectado com sucesso!");
            return conexao;
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Erro: Driver não encontrado: " + e.getMessage(), e);
            return null;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro: Conexão falhou: " + e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Método para configurar uma conexão mockada (usado em testes)
     * @param mockConnection A conexão mockada
     */
    public static void setMockConnection(Connection mockConnection) {
        ConexaoDAO.conexao = mockConnection;
        testeMode = true; // Ativa o modo de teste
    }
    
    /**
     * Desativa o modo de teste e limpa a conexão mockada
     */
    public static void disableTestMode() {
        testeMode = false;
        conexao = null;
    }
}
