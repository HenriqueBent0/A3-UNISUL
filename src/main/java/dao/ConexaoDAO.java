package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SERVER = "localhost";
    private static final String DATABASE = "emprestimodeferramentas";
    private static final String URL = "jdbc:mysql://" + SERVER + ":3306/" + DATABASE + "?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConexao() {
        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Status: Conectado!");
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("O driver não foi encontrado: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar: " + e.getMessage());
            return null;
        }
    }
}
