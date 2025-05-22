package test;

import dao.FerramentaDAO;
import modelo.Ferramenta;
import java.util.ArrayList;
import java.util.List;

public class FerramentaDAOFake extends FerramentaDAO {

    private List<Ferramenta> ferramentas = new ArrayList<>();

    public FerramentaDAOFake() {
        ferramentas.add(new Ferramenta(1, "treco", "tramontina", 200));
        ferramentas.add(new Ferramenta(2, "bagulho", "nike", 220));
    }

    @Override
    public boolean insertFerramentaBD(Ferramenta ferramenta) {
        return ferramentas.add(ferramenta);
    }

    @Override
    public boolean deleteFerramentaBD(int id) {
        return ferramentas.removeIf(a -> a.getId() == id);
    }


    public boolean updateFerramentaBD(Ferramenta ferramenta) {
        for (Ferramenta a : ferramentas) {
            if (a.getId() == ferramenta.getId()) {
                a.setNome(ferramenta.getNome());
                a.setMarca(ferramenta.getMarca());
                a.setValor(ferramenta.getValor());
                return true;
            }
        }
        return false;
    }

    @Override
    public Ferramenta carregaFerramenta(int id) {
        for (Ferramenta a : ferramentas) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    @Override
    public ArrayList<Ferramenta> getListaFerramenta() {
        return new ArrayList<>(ferramentas);
    }

    @Override
    public int maiorID() {
        return ferramentas.stream()
                .mapToInt(Ferramenta::getId)
                .max()
                .orElse(0);
    }
}

