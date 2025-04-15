package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Devolucao;

/**
 * Classe que define as operações de acesso a dados para a entidade Devolucao.
 */
public class DevolucaoDAO {

    /**
     * Lista para armazenar objetos de Devolucao recuperados do banco de dados.
     */
    public static ArrayList<Devolucao> listaDevolucao = new ArrayList<>();

    /**
     * Método para obter a lista de devoluções do banco de dados.
     */
    public ArrayList<Devolucao> getListaDevolucao() {
        listaDevolucao.clear();
        try {
            Connection conn = ConexaoDAO.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_devolucao");
            while (res.next()) {
                int id = res.getInt("id");
                String nomeAmigo = res.getString("nomeAmigo");
                String data = res.getString("data");
                String nomeDaFerramenta = res.getString("nomeDaFerramenta");
                int idFerramenta = res.getInt("idFerramenta");

                Devolucao devolucao = new Devolucao(nomeAmigo, idFerramenta, data, id, nomeDaFerramenta);
                listaDevolucao.add(devolucao);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return listaDevolucao;
    }

    /**
     * Método para inserir uma devolução no banco de dados.
     */
    public boolean insertDevolucaoBD(Devolucao objeto) {
        String sql = "INSERT INTO tb_devolucao(nomeAmigo, idFerramenta, nomeDaFerramenta, data, id) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = ConexaoDAO.getConexao();

            // Descobre o próximo ID
            String sqlUltimoId = "SELECT MAX(id) AS max_id FROM tb_devolucao";
            Statement stmtUltimoId = conn.createStatement();
            ResultSet rsUltimoId = stmtUltimoId.executeQuery(sqlUltimoId);

            int ultimoId = 0;
            if (rsUltimoId.next()) {
                ultimoId = rsUltimoId.getInt("max_id");
            }
            rsUltimoId.close();
            stmtUltimoId.close();

            int novoId = ultimoId + 1;

            // Insere a devolução
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, objeto.getNomeAmigo());
            stmt.setInt(2, objeto.getIdFerramenta());
            stmt.setString(3, objeto.getNomeDaFerramenta());
            stmt.setString(4, objeto.getData());
            stmt.setInt(5, novoId);

            stmt.execute();
            stmt.close();

            return true;
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    /**
     * Conta quantas devoluções um determinado amigo já fez.
     */
    public int contarEmprestimosPorPessoa(String nomeAmigo) {
        int count = 0;
        for (Devolucao devolucao : listaDevolucao) {
            if (devolucao.getNomeAmigo().equals(nomeAmigo)) {
                count++;
            }
        }
        return count;
    }
}
