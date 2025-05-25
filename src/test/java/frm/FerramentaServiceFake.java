package frm;

import modelo.Ferramenta;
import servico.FerramentaService;
import java.util.ArrayList;

/**
 * Service fake para Ferramenta, usa FerramentaDAOFake para simular acesso a
 * dados.
 */
public class FerramentaServiceFake extends FerramentaService {

    private FerramentaDAOFake daoFake;

    /**
     * Construtor que cria o DAO fake e passa para a superclasse.
     */
    public FerramentaServiceFake() {
        FerramentaDAOFake dao = new FerramentaDAOFake();
        super.dao = dao;
        this.daoFake = dao;
    }

    /**
     * Insere uma nova ferramenta com os dados fornecidos.
     */
    public boolean insertFerramentaBD(String nome, int id, int valor, String marca) {
        int novoId = maiorID() + 1;
        Ferramenta novoFerramenta = new Ferramenta(novoId, nome, marca, valor);
        return daoFake.insertFerramentaBD(novoFerramenta);
    }

    /**
     * Deleta uma ferramenta pelo id.
     */
    @Override
    public boolean deleteFerramentaBD(int id) {
        return daoFake.deleteFerramentaBD(id);
    }

    /**
     * Atualiza os dados de uma ferramenta existente.
     */
    public boolean updateFerramentaBD(String nome, int id, int valor, String marca) {
        Ferramenta ferramenta = new Ferramenta(id, nome, marca, valor);
        return daoFake.updateFerramentaBD(ferramenta);
    }

    /**
     * Busca uma ferramenta pelo id.
     */
    @Override
    public Ferramenta carregaFerramenta(int id) {
        return daoFake.carregaFerramenta(id);
    }

    /**
     * Retorna a lista de ferramentas.
     */
    @Override
    public ArrayList<Ferramenta> getListaFerramenta() {
        return daoFake.getListaFerramenta();
    }

    /**
     * Retorna o maior id utilizado na lista de ferramentas.
     */
    @Override
    public int maiorID() {
        return daoFake.maiorID();
    }
}
