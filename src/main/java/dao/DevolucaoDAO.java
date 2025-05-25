package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Devolucao;

/**
 * Classe responsável pelas operações de acesso a dados da entidade Devolucao.
 */
public class DevolucaoDAO {

    /**
     * Lista de devoluções obtidas do banco
     */
    public static ArrayList<Devolucao> listaDevolucao = new ArrayList<>();

    /**
     * Obtém a lista de todas as devoluções do banco.
     *
     * @return Lista de objetos Devolucao.
     */
    public ArrayList<Devolucao> getListaDevolucao() {
        listaDevolucao.clear();
        String sql = "SELECT * FROM tb_devolucao";

        try (Connection conn = ConexaoDAO.getConexao(); Statement stmt = conn.createStatement(); ResultSet res = stmt.executeQuery(sql)) {

            while (res.next()) {
                int id = res.getInt("id");
                String nomeAmigo = res.getString("nomeAmigo");
                String data = res.getString("data");
                String nomeDaFerramenta = res.getString("nomeDaFerramenta");
                int idFerramenta = res.getInt("idFerramenta");
                Devolucao devolucao = new Devolucao(nomeAmigo, idFerramenta, data, id, nomeDaFerramenta);
                listaDevolucao.add(devolucao);
            }

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }

        return listaDevolucao;
    }

    /**
     * Insere uma nova devolução no banco.
     *
     * @param objeto Devolucao a ser inserida.
     * @return true se inserção for bem-sucedida.
     */
    public boolean insertDevolucaoBD(Devolucao objeto) {
        String sqlSelectMaxId = "SELECT MAX(id) AS max_id FROM tb_devolucao";
        String sqlInsert = "INSERT INTO tb_devolucao(nomeAmigo, idFerramenta, nomeDaFerramenta, data, id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDAO.getConexao(); Statement stmtUltimoId = conn.createStatement(); ResultSet rsUltimoId = stmtUltimoId.executeQuery(sqlSelectMaxId)) {

            int ultimoId = 0;
            if (rsUltimoId.next()) {
                ultimoId = rsUltimoId.getInt("max_id");
            }

            int novoId = ultimoId + 1;

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, objeto.getNomeAmigo());
                stmtInsert.setInt(2, objeto.getIdFerramenta());
                stmtInsert.setString(3, objeto.getNomeDaFerramenta());
                stmtInsert.setString(4, objeto.getData());
                stmtInsert.setInt(5, novoId);
                stmtInsert.execute();
            }

            return true;

        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    /**
     * Conta quantas devoluções foram feitas por um amigo específico.
     *
     * @param nomeAmigo Nome do amigo.
     * @return Quantidade de devoluções feitas por esse amigo.
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
