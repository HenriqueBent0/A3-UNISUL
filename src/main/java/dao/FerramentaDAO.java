package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Ferramenta;

/**
 * Classe para operações de acesso a dados da entidade Ferramenta.
 */
public class FerramentaDAO {

    /**
     * Lista de ferramentas carregadas do banco
     */
    public static ArrayList<Ferramenta> ListaFerramenta = new ArrayList<>();

    /**
     * Retorna a lista de ferramentas do banco.
     *
     * @return Lista de objetos Ferramenta
     */
    public ArrayList<Ferramenta> getListaFerramenta() {
        ListaFerramenta.clear();
        try (Connection conn = ConexaoDAO.getConexao(); Statement stmt = conn.createStatement(); ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas")) {

            while (res.next()) {
                int id = res.getInt("id");
                String nome = res.getString("nome");
                String marca = res.getString("marca");
                int valor = res.getInt("valor");

                Ferramenta objeto = new Ferramenta(id, nome, marca, valor);
                ListaFerramenta.add(objeto);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return ListaFerramenta;
    }

    /**
     * Define a lista de ferramentas.
     *
     * @param ListaFerramenta Lista a ser setada
     */
    public void setListaFerramenta(ArrayList<Ferramenta> ListaFerramenta) {
        this.ListaFerramenta = ListaFerramenta;
    }

    /**
     * Retorna o maior ID atual da tabela.
     *
     * @return maior ID encontrado
     */
    public int maiorID() {
        int maiorID = 0;
        String sql = "SELECT MAX(id) AS id FROM tb_ferramentas";
        try (Connection conn = ConexaoDAO.getConexao(); Statement stmt = conn.createStatement(); ResultSet res = stmt.executeQuery(sql)) {

            if (res.next()) {
                maiorID = res.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return maiorID;
    }

    /**
     * Insere uma nova ferramenta no banco.
     *
     * @param objeto Ferramenta a ser inserida
     * @return true se inserção for bem-sucedida
     */
    public boolean insertFerramentaBD(Ferramenta objeto) {
        String sqlInsert = "INSERT INTO tb_ferramentas(id, nome, marca, valor) VALUES (?, ?, ?, ?)";
        String sqlUltimoId = "SELECT MAX(id) AS max_id FROM tb_ferramentas";

        try (Connection conn = ConexaoDAO.getConexao(); Statement stmtUltimoId = conn.createStatement(); ResultSet rsUltimoId = stmtUltimoId.executeQuery(sqlUltimoId)) {

            int ultimoId = 0;
            if (rsUltimoId.next()) {
                ultimoId = rsUltimoId.getInt("max_id");
            }

            int novoId = ultimoId + 1;

            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
                stmt.setInt(1, novoId);
                stmt.setString(2, objeto.getNome());
                stmt.setString(3, objeto.getMarca());
                stmt.setInt(4, objeto.getValor());
                stmt.executeUpdate();
            }
            return true;

        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    /**
     * Remove ferramenta pelo ID.
     *
     * @param id ID da ferramenta a ser removida
     * @return true se exclusão for bem-sucedida
     */
    public boolean deleteFerramentaBD(int id) {
        String sql = "DELETE FROM tb_ferramentas WHERE id = ?";
        try (Connection conn = ConexaoDAO.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            return false;
        }
    }

    /**
     * Atualiza os dados da ferramenta.
     *
     * @param id ID da ferramenta a ser atualizada
     * @param nome Novo nome
     * @param marca Nova marca
     * @param valor Novo valor
     * @return true se atualização for bem-sucedida
     */
    public boolean updateFerramentaBD(int id, String nome, String marca, int valor) {
        String sql = "UPDATE tb_ferramentas SET nome = ?, marca = ?, valor = ? WHERE id = ?";
        try (Connection conn = ConexaoDAO.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, marca);
            stmt.setInt(3, valor);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    /**
     * Carrega uma ferramenta pelo ID.
     *
     * @param id ID da ferramenta
     * @return Objeto Ferramenta ou null se não encontrado
     */
    public Ferramenta carregaFerramenta(int id) {
        Ferramenta objeto = null;
        String sql = "SELECT * FROM tb_ferramentas WHERE id = ?";

        try (Connection conn = ConexaoDAO.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    objeto = new Ferramenta();
                    objeto.setId(id);
                    objeto.setNome(rs.getString("nome"));
                    objeto.setMarca(rs.getString("marca"));
                    objeto.setValor(rs.getInt("valor"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao carregar ferramenta: ID " + id + " - " + e.getMessage());
        }
        return objeto;
    }
}
