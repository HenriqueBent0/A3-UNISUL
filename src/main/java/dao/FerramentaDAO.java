package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Ferramenta;

public class FerramentaDAO {

    public static ArrayList<Ferramenta> ListaFerramenta = new ArrayList<>();

    public ArrayList<Ferramenta> getListaFerramenta() {
        ListaFerramenta.clear();
        try (Connection conn = ConexaoDAO.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas")) {

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

    public void setListaFerramenta(ArrayList<Ferramenta> ListaFerramenta) {
        this.ListaFerramenta = ListaFerramenta;
    }

    public int maiorID() {
        int maiorID = 0;
        String sql = "SELECT MAX(id) AS id FROM tb_ferramentas";
        try (Connection conn = ConexaoDAO.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            if (res.next()) {
                maiorID = res.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return maiorID;
    }

    public boolean insertFerramentaBD(Ferramenta objeto) {
        String sqlInsert = "INSERT INTO tb_ferramentas(id, nome, marca, valor) VALUES (?, ?, ?, ?)";
        String sqlUltimoId = "SELECT MAX(id) AS max_id FROM tb_ferramentas";

        try (Connection conn = ConexaoDAO.getConexao();
             Statement stmtUltimoId = conn.createStatement();
             ResultSet rsUltimoId = stmtUltimoId.executeQuery(sqlUltimoId)) {

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

    public boolean deleteFerramentaBD(int id) {
    String sql = "DELETE FROM tb_ferramentas WHERE id = ?";
    try (Connection conn = ConexaoDAO.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, id); 
        int linhasAfetadas = stmt.executeUpdate();
        
        return linhasAfetadas > 0; 
    } catch (SQLException erro) {
        System.out.println("Erro: " + erro);
        return false;
    }
}


    public boolean updateFerramentaBD(int id, String nome, String marca, int valor) {
        String sql = "UPDATE tb_ferramentas SET nome = ?, marca = ?, valor = ? WHERE id = ?";
        try (Connection conn = ConexaoDAO.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

public Ferramenta carregaFerramenta(int id) {
    Ferramenta objeto = null;
    String sql = "SELECT * FROM tb_ferramentas WHERE id = ?";
    
    try (Connection conn = ConexaoDAO.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
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
