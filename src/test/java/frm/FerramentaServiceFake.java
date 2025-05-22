package frm;

import modelo.Ferramenta;
import servico.FerramentaService;

import java.util.ArrayList;

public class FerramentaServiceFake extends FerramentaService {

    private FerramentaDAOFake daoFake;

    public FerramentaServiceFake() {
        this.daoFake = new FerramentaDAOFake();
        // NÃO faça: super.dao = daoFake; porque daoFake não é FerramentaDAO
    }

    public boolean insertFerramentaBD(String nome, int id, int valor, String marca) {
        int novoId = maiorID() + 1;
        Ferramenta novoFerramenta = new Ferramenta(novoId, nome, marca, valor);
        return daoFake.insertFerramentaBD(novoFerramenta);
    }

    @Override
    public boolean deleteFerramentaBD(int id) {
        return daoFake.deleteFerramentaBD(id);
    }

    public boolean updateFerramentaBD(String nome, int id, int valor, String marca) {
        Ferramenta ferramenta = new Ferramenta(id, nome, marca, valor);
        return daoFake.updateFerramentaBD(ferramenta);
    }

    @Override
    public Ferramenta carregaFerramenta(int id) {
        return daoFake.carregaFerramenta(id);
    }

    @Override
    public ArrayList<Ferramenta> getListaFerramenta() {
        return daoFake.getListaFerramenta();
    }

    @Override
    public int maiorID() {
        return daoFake.maiorID();
    }
}
