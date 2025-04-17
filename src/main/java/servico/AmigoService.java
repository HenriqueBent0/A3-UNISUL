package servico;

import dao.AmigoDAO;
import modelo.Amigo;
import java.util.ArrayList;

public class AmigoService {

    private AmigoDAO dao;

    // Construtor padrão (uso normal com banco de dados real)
    public AmigoService() {
        this.dao = new AmigoDAO();
    }

    // Construtor para injetar um DAO (útil para testes com Mockito)
    public AmigoService(AmigoDAO dao) {
        this.dao = dao;
    }

    public boolean insertAmigoBD(String nome, int id, int telefone) {
        id = this.maiorID() + 1;
        Amigo objeto = new Amigo(nome, id, telefone);
        return dao.insertAmigoBD(objeto);
    }

    public boolean deleteAmigoBD(int id) {
        return dao.deleteAmigoBD(id);
    }

    public boolean updateAmigoBD(String nome, int id, int telefone) {
        Amigo objeto = new Amigo(nome, id, telefone);
        return dao.updateAmigoBD(objeto);
    }

    public Amigo carregaAmigo(int id) {
        return dao.carregaAmigo(id);
    }

    public ArrayList<Amigo> getListaAmigo() {
        return dao.getListaAmigo();
    }

    public int maiorID() {
        return dao.maiorID();
    }
}
