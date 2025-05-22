package servico;

import dao.FerramentaDAO;
import modelo.Ferramenta;
import java.util.ArrayList;

public class FerramentaService {

    public FerramentaDAO dao;

    // Construtor padrão (uso real com banco)
    public FerramentaService() {
        this.dao = new FerramentaDAO();
    }

    // Construtor alternativo para testes com mock do DAO
    public FerramentaService(FerramentaDAO dao) {
        this.dao = dao;
    }

    public boolean insertFerramentaBD(String nome, String marca, int valor) {
        int id = this.maiorID() + 1;
        Ferramenta ferramenta = new Ferramenta(id, nome, marca, valor);
        return dao.insertFerramentaBD(ferramenta);
    }

    public boolean deleteFerramentaBD(int id) {
        return dao.deleteFerramentaBD(id);
    }

    public boolean updateFerramentaBD(int id, String nome, String marca, int valor) {
        return dao.updateFerramentaBD(id, nome, marca, valor);
    }

    public Ferramenta carregaFerramenta(int id) {
        return dao.carregaFerramenta(id);
    }

    public ArrayList<Ferramenta> getListaFerramenta() {
        return dao.getListaFerramenta();
    }

    public int maiorID() {
        return dao.maiorID();
    }
}
