package test;

import dao.AmigoDAO;
import modelo.Amigo;
import java.util.ArrayList;
import java.util.List;

public class AmigoDAOFake extends AmigoDAO {

    private List<Amigo> amigos = new ArrayList<>();

    public AmigoDAOFake() {
        amigos.add(new Amigo("João", 1, 123456789));
        amigos.add(new Amigo("Maria", 2, 987654321));
    }

    @Override
    public boolean insertAmigoBD(Amigo amigo) {
        return amigos.add(amigo);
    }

    @Override
    public boolean deleteAmigoBD(int id) {
        return amigos.removeIf(a -> a.getId() == id);
    }

    @Override
    public boolean updateAmigoBD(Amigo amigo) {
        for (Amigo a : amigos) {
            if (a.getId() == amigo.getId()) {
                a.setNome(amigo.getNome());
                a.setTelefone(amigo.getTelefone());
                return true;
            }
        }
        return false;
    }

    @Override
    public Amigo carregaAmigo(int id) {
        for (Amigo a : amigos) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    @Override
    public ArrayList<Amigo> getListaAmigo() {
        return new ArrayList<>(amigos);
    }

    @Override
    public int maiorID() {
        return amigos.stream()
                .mapToInt(Amigo::getId)
                .max()
                .orElse(0);
    }
}
