package frm;

import modelo.Amigo;
import servico.AmigoService;
import java.util.ArrayList;

/**
 * Serviço fake para testar Amigo sem usar banco de dados. Usa o AmigoDAOFake
 * para simular operações.
 */
public class AmigoServiceFake extends AmigoService {

    private AmigoDAOFake daoFake;

    /**
     * Inicializa o DAO fake.
     */
    public AmigoServiceFake() {
        this.daoFake = new AmigoDAOFake();
        super.dao = daoFake;
    }

    /**
     * Cria um novo amigo e adiciona na lista fake.
     */
    @Override
    public boolean insertAmigoBD(String nome, int id, int telefone) {
        int novoId = maiorID() + 1;
        Amigo novoAmigo = new Amigo(nome, novoId, telefone);
        return daoFake.insertAmigoBD(novoAmigo);
    }

    /**
     * Remove o amigo pelo id.
     */
    @Override
    public boolean deleteAmigoBD(int id) {
        return daoFake.deleteAmigoBD(id);
    }

    /**
     * Atualiza os dados do amigo.
     */
    @Override
    public boolean updateAmigoBD(String nome, int id, int telefone) {
        Amigo amigo = new Amigo(nome, id, telefone);
        return daoFake.updateAmigoBD(amigo);
    }

    /**
     * Busca o amigo pelo id.
     */
    @Override
    public Amigo carregaAmigo(int id) {
        return daoFake.carregaAmigo(id);
    }

    /**
     * Retorna a lista de amigos.
     */
    @Override
    public ArrayList<Amigo> getListaAmigo() {
        return daoFake.getListaAmigo();
    }

    /**
     * Retorna o maior id atual.
     */
    @Override
    public int maiorID() {
        return daoFake.maiorID();
    }
}
