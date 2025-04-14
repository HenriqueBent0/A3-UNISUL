package servico;

import dao.AmigoDAO;
import modelo.Amigo;
import java.util.ArrayList;

public class AmigoService {

    private AmigoDAO dao;

    public AmigoService() {
        this.dao = new AmigoDAO();
    }

    public boolean insertAmigoBD(String nome, int id, int telefone) {
        id = this.maiorID() + 1;
        Amigo objeto = new Amigo(nome, id, telefone);
        dao.insertAmigoBD(objeto);
        return true;
    }

    public boolean deleteAmigoBD(int id) {
        dao.deleteAmigoBD(id);
        return true;
    }

    public boolean updateAmigoBD(String nome, int id, int telefone) {
        Amigo objeto = new Amigo(nome, id, telefone);
        dao.updateAmigoBD(objeto);
        return true;
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

