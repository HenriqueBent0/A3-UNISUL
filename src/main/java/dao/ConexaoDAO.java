package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SERVER = "127.0.0.1"; // Altere para 127.0.0.1 ou o nome do serviço Docker
    private static final String DATABASE = "emprestimodeferramentas"; // Nome do banco de dados
    private static final String URL = "jdbc:mysql://" + SERVER + ":3306/" + DATABASE + "?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root"; // Usuário do MySQL
    private static final String PASSWORD = "root"; // Senha do MySQL

    public static Connection getConexao() {
        try {
            // Tenta carregar o driver JDBC para MySQL
            Class.forName(DRIVER);

            // Tenta estabelecer a conexão com o banco de dados
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            System.out.println("Status: Conectado com sucesso!");
            return connection;
        } catch (ClassNotFoundException e) {
            // Caso o driver não seja encontrado
            System.out.println("Erro: O driver do MySQL não foi encontrado: " + e.getMessage());
            e.printStackTrace();  // Detalha o erro
            return null;
        } catch (SQLException e) {
            // Caso haja erro na conexão
            System.out.println("Erro: Não foi possível conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();  // Detalha o erro
            return null;
        }
    }
}
