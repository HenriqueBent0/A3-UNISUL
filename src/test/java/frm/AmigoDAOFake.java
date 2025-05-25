package frm;

import dao.AmigoDAO;
import modelo.Amigo;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Fake que simula operações do banco de dados para a entidade Amigo.
 * Usada para testes ou situações em que não se quer acessar o banco real.
 */
public class AmigoDAOFake extends AmigoDAO {

    private List<Amigo> amigos = new ArrayList<>();

    /**
     * Construtor que inicializa a lista com alguns amigos pré-definidos.
     */
    public AmigoDAOFake() {
        amigos.add(new Amigo("João", 1, 123456789));
        amigos.add(new Amigo("Maria", 2, 987654321));
    }

    /**
     * Adiciona um amigo à lista fake.
     *
     * @param amigo objeto Amigo a ser adicionado.
     * @return true se o amigo foi adicionado com sucesso.
     */
    @Override
    public boolean insertAmigoBD(Amigo amigo) {
        return amigos.add(amigo);
    }

    /**
     * Remove um amigo da lista pelo id.
     *
     * @param id identificador do amigo a ser removido.
     * @return true se o amigo foi removido.
     */
    @Override
    public boolean deleteAmigoBD(int id) {
        return amigos.removeIf(a -> a.getId() == id);
    }

    /**
     * Atualiza os dados de um amigo existente na lista.
     *
     * @param amigo objeto Amigo com dados atualizados.
     * @return true se o amigo foi encontrado e atualizado.
     */
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

    /**
     * Busca um amigo pelo id na lista.
     *
     * @param id identificador do amigo.
     * @return objeto Amigo encontrado ou null se não existir.
     */
    @Override
    public Amigo carregaAmigo(int id) {
        for (Amigo a : amigos) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    /**
     * Retorna a lista completa de amigos.
     *
     * @return nova lista contendo todos os amigos.
     */
    @Override
    public ArrayList<Amigo> getListaAmigo() {
        return new ArrayList<>(amigos);
    }

    /**
     * Retorna o maior id presente na lista de amigos.
     *
     * @return maior id ou 0 se a lista estiver vazia.
     */
    @Override
    public int maiorID() {
        return amigos.stream()
                .mapToInt(Amigo::getId)
                .max()
                .orElse(0);
    }
}
