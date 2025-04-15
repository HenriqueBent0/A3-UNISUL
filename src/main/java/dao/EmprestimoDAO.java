package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Emprestimo;

/**
 * Classe que define as operações de acesso a dados para a entidade de Emprestimo.
 */
public class EmprestimoDAO {

    // Lista de empréstimos
    public static ArrayList<Emprestimo> ListaEmprestimo = new ArrayList<>();

    /**
     * Retorna a lista de empréstimos do banco de dados.
     */
    public ArrayList<Emprestimo> getListaEmprestimo() {
        ListaEmprestimo.clear();
        try {
            Connection conn = ConexaoDAO.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_emprestimos");

            while (res.next()) {
                int id = res.getInt("id");
                String nomeAmigo = res.getString("nome");
                String data = res.getString("data");
                String nomeDaFerramenta = res.getString("nomeDaFerramenta");
                int idFerramenta = res.getInt("idFerramenta");

                Emprestimo objeto = new Emprestimo(id, nomeAmigo, data, nomeDaFerramenta, idFerramenta);
                ListaEmprestimo.add(objeto);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return ListaEmprestimo;
    }

    /**
     * Define a lista de empréstimos manualmente.
     */
    public void setListaEmprestimo(ArrayList<Emprestimo> ListaEmprestimo) {
        this.ListaEmprestimo = ListaEmprestimo;
    }

    /**
     * Insere um novo empréstimo no banco de dados.
     */
    public boolean insertEmprestimoBD(Emprestimo objeto) {
        String sql = "INSERT INTO tb_emprestimos (id, nome, data, nomeDaFerramenta, idFerramenta) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = ConexaoDAO.getConexao();

            // Pega o maior ID atual
            String sqlUltimoId = "SELECT MAX(id) AS max_id FROM tb_emprestimos";
            Statement stmtUltimoId = conn.createStatement();
            ResultSet rsUltimoId = stmtUltimoId.executeQuery(sqlUltimoId);

            int ultimoId = 0;
            if (rsUltimoId.next()) {
                ultimoId = rsUltimoId.getInt("max_id");
            }
            rsUltimoId.close();
            stmtUltimoId.close();

            int novoId = ultimoId + 1;

            // Insere o novo empréstimo
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, novoId);
            stmt.setString(2, objeto.getNomeAmigo());
            stmt.setString(3, objeto.getData());
            stmt.setString(4, objeto.getNomeDaFerramenta());
            stmt.setInt(5, objeto.getIdFerramenta());

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    /**
     * Exclui um empréstimo do banco de dados com base no ID.
     */
    public boolean deleteEmprestimoBD(int id) {
        try {
            Connection conn = ConexaoDAO.getConexao();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM tb_emprestimos WHERE id = " + id);
            stmt.close();
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
        }
        return true;
    }

    /**
     * Carrega os dados de um empréstimo específico.
     */
    public Emprestimo carregaEmprestimo(int id) {
        Emprestimo objeto = new Emprestimo();
        objeto.setId(id);
        try {
            Connection conn = ConexaoDAO.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_emprestimos WHERE id = " + id);
            if (res.next()) {
                objeto.setNomeAmigo(res.getString("nome"));
                objeto.setData(res.getString("data"));
                objeto.setNomeDaFerramenta(res.getString("nomeDaFerramenta"));
                objeto.setIdFerramenta(res.getInt("idFerramenta"));
            }
            stmt.close();
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
        }
        return objeto;
    }
}
