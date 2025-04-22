package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Amigo;

/**
 * Classe que define as operações de acesso a dados para a entidade Amigo.
 */
public class AmigoDAO {
    // Cria ArrayList de Amigos
    public static ArrayList<Amigo> ListaAmigo = new ArrayList<>();

    /**
     * Obtém a lista de amigos do banco de dados.
     */
    public ArrayList<Amigo> getListaAmigo() {
        ListaAmigo.clear();
        String sql = "SELECT * FROM tb_amigos";
        try (Statement stmt = ConexaoDAO.getConexao().createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            while (res.next()) {
                int id = res.getInt("id");
                String nome = res.getString("nome");
                int telefone = res.getInt("telefone");
                Amigo objeto = new Amigo(nome, id, telefone);
                ListaAmigo.add(objeto);
            }

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return ListaAmigo;
    }

    /**
     * Define a lista de amigos.
     */
    public void setListaAmigo(ArrayList<Amigo> ListaAmigo) {
        this.ListaAmigo = ListaAmigo;
    }

    /**
     * Obtém o maior ID de amigo presente no banco de dados.
     */
    public int maiorID() {
        int maiorID = 0;
        String sql = "SELECT MAX(id) AS id FROM tb_amigos";
        try (Statement stmt = ConexaoDAO.getConexao().createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            if (res.next()) {
                maiorID = res.getInt("id");
            }

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return maiorID;
    }

    /**
     * Insere um amigo no banco de dados.
     */
    public boolean insertAmigoBD(Amigo objeto) {
        String sql = "INSERT INTO tb_amigos(id, nome, telefone) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = ConexaoDAO.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNome());
            stmt.setInt(3, objeto.getTelefone());
            stmt.execute();
            return true;
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    /**
     * Exclui um amigo do banco de dados.
     */
    public boolean deleteAmigoBD(int id) {
        String sql = "DELETE FROM tb_amigos WHERE id = ?";
        try (PreparedStatement stmt = ConexaoDAO.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            return false;
        }
    }

    /**
     * Atualiza as informações de um amigo no banco de dados.
     */
    public boolean updateAmigoBD(Amigo objeto) {
        String sql = "UPDATE tb_amigos SET nome = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = ConexaoDAO.getConexao().prepareStatement(sql)) {
            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getTelefone());
            stmt.setInt(3, objeto.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    /**
     * Carrega as informações de um amigo do banco de dados.
     */
    public Amigo carregaAmigo(int id) {
        Amigo objeto = null;
        String sql = "SELECT * FROM tb_amigos WHERE id = ?";
        try (PreparedStatement stmt = ConexaoDAO.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    objeto = new Amigo();
                    objeto.setId(id);
                    objeto.setNome(res.getString("nome"));
                    objeto.setTelefone(res.getInt("telefone"));
                }
            }
        } catch (SQLException erro) {
            System.out.println("Erro: " + erro);
        }
        return objeto;
    }
}
