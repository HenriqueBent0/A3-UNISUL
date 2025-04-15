package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Ferramenta;

/**
 * Classe que define as operações de acesso a dados para a entidade Ferramenta.
 */
public class FerramentaDAO {

    // Lista de Ferramentas
    public static ArrayList<Ferramenta> ListaFerramenta = new ArrayList<>();

    /**
     * Obtém a lista de ferramentas do banco de dados.
     */
    public ArrayList<Ferramenta> getListaFerramenta() {
        ListaFerramenta.clear();
        try {
            Connection conn = ConexaoDAO.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas");

            while (res.next()) {
                int id = res.getInt("id");
                String nome = res.getString("nome");
                String marca = res.getString("marca");
                int valor = res.getInt("valor");

                Ferramenta objeto = new Ferramenta(id, nome, marca, valor);
                ListaFerramenta.add(objeto);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return ListaFerramenta;
    }

    /**
     * Define a lista de ferramentas manualmente.
     */
    public void setListaFerramenta(ArrayList<Ferramenta> ListaFerramenta) {
        this.ListaFerramenta = ListaFerramenta;
    }

    /**
     * Retorna o maior ID de ferramenta no banco.
     */
    public int maiorID() {
        int maiorID = 0;
        try {
            Connection conn = ConexaoDAO.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(id) AS id FROM tb_ferramentas");

            if (res.next()) {
                maiorID = res.getInt("id");
            }

            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return maiorID;
    }

    /**
     * Insere uma nova ferramenta no banco.
     */
    public boolean insertFerramentaBD(Ferramenta objeto) {
        String sql = "INSERT INTO tb_ferramentas(id, nome, marca, valor) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = ConexaoDAO.getConexao();

            // Pega o último ID
            String sqlUltimoId = "SELECT MAX(id) AS max_id FROM tb_ferramentas";
            Statement stmtUltimoId = conn.createStatement();
            ResultSet rsUltimoId = stmtUltimoId.executeQuery(sqlUltimoId);
            int ultimoId = 0;

            if (rsUltimoId.next()) {
                ultimoId = rsUltimoId.getInt("max_id");
            }

            rsUltimoId.close();
            stmtUltimoId.close();

            int novoId = ultimoId + 1;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, novoId);
            stmt.setString(2, objeto.getNome());
            stmt.setString(3, objeto.getMarca());
            stmt.setInt(4, objeto.getValor());

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    /**
     * Remove uma ferramenta do banco.
     */
    public boolean deleteFerramentaBD(int id) {
        try {
            Connection conn = ConexaoDAO.getConexao();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM tb_ferramentas WHERE id = " + id);
            stmt.close();
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
        }
        return true;
    }

    /**
     * Atualiza os dados de uma ferramenta.
     */
    public boolean updateFerramentaBD(int id, String nome, String marca, int valor) {
        String sql = "UPDATE tb_ferramentas SET nome = ?, marca = ?, valor = ? WHERE id = ?";
        try {
            Connection conn = ConexaoDAO.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, marca);
            stmt.setInt(3, valor);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    /**
     * Carrega uma ferramenta específica com base no ID.
     */
    public Ferramenta carregaFerramenta(int id) {
        Ferramenta objeto = new Ferramenta();
        objeto.setId(id);
        try {
            Connection conn = ConexaoDAO.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas WHERE id = " + id);

            if (res.next()) {
                objeto.setNome(res.getString("nome"));
                objeto.setMarca(res.getString("marca"));
                objeto.setValor(res.getInt("valor"));
            }

            stmt.close();
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
        }
        return objeto;
    }
}
