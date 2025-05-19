package test;

import modelo.Amigo;
import servico.AmigoService;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe fake que simula o comportamento do serviço de amigos. Utilizada para
 * testes sem conexão com banco de dados real.
 */
public class AmigoServiceFake extends AmigoService {

    private List<Amigo> amigos = new ArrayList<>();

    public AmigoServiceFake() {
        // Adiciona alguns dados de teste
        amigos.add(new Amigo("João", 1, 123456789));
        amigos.add(new Amigo("Maria", 2, 987654321));
    }

    @Override
    public boolean deleteAmigoBD(int id) {
        return amigos.removeIf(a -> a.getId() == id);
    }

    @Override
    public boolean updateAmigoBD(String nome, int id, int telefone) {
        for (Amigo amigo : amigos) {
            if (amigo.getId() == id) {
                amigo.setNome(nome);
                amigo.setTelefone(telefone);
                return true;
            }
        }
        return false;
    }


}
