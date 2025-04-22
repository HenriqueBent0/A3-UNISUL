package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Emprestimo;

/**
 * Classe que define as operações de acesso a dados para a entidade de
 * Emprestimo.
 */
public class EmprestimoDAO {

    public static ArrayList<Emprestimo> ListaEmprestimo = new ArrayList<>();

    public ArrayList<Emprestimo> getListaEmprestimo() {
        ListaEmprestimo.clear();
        try (Connection conn = ConexaoDAO.getConexao(); Statement stmt = conn.createStatement(); ResultSet res = stmt.executeQuery("SELECT * FROM tb_emprestimos")) {

            while (res.next()) {
                int id = res.getInt("id");
                String nomeAmigo = res.getString("nome");
                String data = res.getString("data");
                String nomeDaFerramenta = res.getString("nomeDaFerramenta");
                int idFerramenta = res.getInt("idFerramenta");

                Emprestimo objeto = new Emprestimo(id, nomeAmigo, data, nomeDaFerramenta, idFerramenta);
                ListaEmprestimo.add(objeto);
            }

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return ListaEmprestimo;
    }

    public void setListaEmprestimo(ArrayList<Emprestimo> ListaEmprestimo) {
        this.ListaEmprestimo = ListaEmprestimo;
    }

    public boolean insertEmprestimoBD(Emprestimo objeto) {
        String sqlInsert = "INSERT INTO tb_emprestimos (id, nome, data, nomeDaFerramenta, idFerramenta) VALUES (?, ?, ?, ?, ?)";
        String sqlUltimoId = "SELECT MAX(id) AS max_id FROM tb_emprestimos";

        try (Connection conn = ConexaoDAO.getConexao(); Statement stmtUltimoId = conn.createStatement(); ResultSet rsUltimoId = stmtUltimoId.executeQuery(sqlUltimoId)) {

            int ultimoId = 0;
            if (rsUltimoId.next()) {
                ultimoId = rsUltimoId.getInt("max_id");
            }

            int novoId = ultimoId + 1;

            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
                stmt.setInt(1, novoId);
                stmt.setString(2, objeto.getNomeAmigo());
                stmt.setString(3, objeto.getData());
                stmt.setString(4, objeto.getNomeDaFerramenta());
                stmt.setInt(5, objeto.getIdFerramenta());
                stmt.executeUpdate();
            }

            return true;

        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    public boolean deleteEmprestimoBD(int id) {
        String sql = "DELETE FROM tb_emprestimos WHERE id = ?";
        try (Connection conn = ConexaoDAO.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            return false;
        }
    }

    public Emprestimo carregaEmprestimo(int id) {
        Emprestimo objeto = new Emprestimo();
        objeto.setId(id);

        String sql = "SELECT * FROM tb_emprestimos WHERE id = ?";
        try (Connection conn = ConexaoDAO.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    objeto.setNomeAmigo(res.getString("nome"));
                    objeto.setData(res.getString("data"));
                    objeto.setNomeDaFerramenta(res.getString("nomeDaFerramenta"));
                    objeto.setIdFerramenta(res.getInt("idFerramenta"));
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
        }
        return objeto;
    }
}
