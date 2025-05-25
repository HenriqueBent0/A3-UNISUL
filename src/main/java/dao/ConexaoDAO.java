package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável pela conexão com o banco de dados MySQL.
 */
public class ConexaoDAO {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SERVER = "127.0.0.1";
    private static final String DATABASE = "emprestimodeferramentas";
    private static final String URL = "jdbc:mysql://" + SERVER + ":3306/" + DATABASE + "?useTimezone=true&serverTimezone=UTC";
    private static final String USER = System.getProperty("DB_USER", "root");
    private static final String PASSWORD = System.getProperty("DB_PASSWORD", "root");

    private static Connection conexao;
    private static boolean testeMode = false;
    private static final Logger logger = Logger.getLogger(ConexaoDAO.class.getName());

    /**
     * Retorna a conexão com o banco de dados.
     *
     * @return Conexão ativa ou null em caso de erro.
     */
    public static Connection getConexao() {
        if (testeMode) {
            return conexao;
        }
        try {
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Status: Conectado com sucesso!");
            return conexao;
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Erro: Driver não encontrado.", e);
            return null;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro: Conexão falhou.", e);
            return null;
        }
    }

    /**
     * Define uma conexão mockada para testes.
     *
     * @param mockConnection Conexão simulada.
     */
    public static void setMockConnection(Connection mockConnection) {
        conexao = mockConnection;
        testeMode = true;
    }

    /**
     * Desativa o modo de teste e limpa a conexão mockada.
     */
    public static void disableTestMode() {
        testeMode = false;
        conexao = null;
    }
}
