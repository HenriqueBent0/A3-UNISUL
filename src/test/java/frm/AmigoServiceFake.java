package frm;


import modelo.Amigo;
import servico.AmigoService;

import java.util.ArrayList;

public class AmigoServiceFake extends AmigoService {

    private AmigoDAOFake daoFake;

    public AmigoServiceFake() {
        this.daoFake = new AmigoDAOFake();
        // Passa o daoFake para o AmigoService via construtor
        super.dao = daoFake;
    }

    // Sobrescreve os métodos para usar daoFake internamente (opcional, já funcionaria pelo super)

    @Override
    public boolean insertAmigoBD(String nome, int id, int telefone) {
        int novoId = maiorID() + 1;
        Amigo novoAmigo = new Amigo(nome, novoId, telefone);
        return daoFake.insertAmigoBD(novoAmigo);
    }

    @Override
    public boolean deleteAmigoBD(int id) {
        return daoFake.deleteAmigoBD(id);
    }

    @Override
    public boolean updateAmigoBD(String nome, int id, int telefone) {
        Amigo amigo = new Amigo(nome, id, telefone);
        return daoFake.updateAmigoBD(amigo);
    }

    @Override
    public Amigo carregaAmigo(int id) {
        return daoFake.carregaAmigo(id);
    }

    @Override
    public ArrayList<Amigo> getListaAmigo() {
        return daoFake.getListaAmigo();
    }

    @Override
    public int maiorID() {
        return daoFake.maiorID();
    }
}
