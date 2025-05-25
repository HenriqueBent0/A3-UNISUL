package frm;

import dao.FerramentaDAO;
import modelo.Ferramenta;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO fake para testar Ferramenta sem banco de dados real. Mantém uma lista
 * interna para simular CRUD.
 */
public class FerramentaDAOFake extends FerramentaDAO {

    private List<Ferramenta> ferramentas = new ArrayList<>();

    /**
     * Construtor que inicializa a lista com alguns dados.
     */
    public FerramentaDAOFake() {
        ferramentas.add(new Ferramenta(1, "treco", "alguma marca", 100));
        ferramentas.add(new Ferramenta(2, "bagulho", "outra marca", 200));
    }

    /**
     * Insere uma ferramenta na lista.
     */
    @Override
    public boolean insertFerramentaBD(Ferramenta ferramenta) {
        return ferramentas.add(ferramenta);
    }

    /**
     * Remove a ferramenta pelo id.
     */
    @Override
    public boolean deleteFerramentaBD(int id) {
        return ferramentas.removeIf(a -> a.getId() == id);
    }

    /**
     * Atualiza os dados de uma ferramenta existente.
     */
    public boolean updateFerramentaBD(Ferramenta ferramenta) {
        for (Ferramenta a : ferramentas) {
            if (a.getId() == ferramenta.getId()) {
                a.setNome(ferramenta.getNome());
                a.setValor(ferramenta.getValor());
                a.setMarca(ferramenta.getMarca());
                return true;
            }
        }
        return false;
    }

    /**
     * Busca uma ferramenta pelo id.
     */
    @Override
    public Ferramenta carregaFerramenta(int id) {
        for (Ferramenta a : ferramentas) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    /**
     * Retorna a lista de ferramentas.
     */
    @Override
    public ArrayList<Ferramenta> getListaFerramenta() {
        return new ArrayList<>(ferramentas);
    }

    /**
     * Retorna o maior id usado na lista.
     */
    @Override
    public int maiorID() {
        return ferramentas.stream()
                .mapToInt(Ferramenta::getId)
                .max()
                .orElse(0);
    }
}
