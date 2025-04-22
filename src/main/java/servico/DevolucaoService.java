package servico;

import dao.DevolucaoDAO;
import modelo.Devolucao;
import java.util.ArrayList;

/**
 * Classe que define a lógica de negócios para as devoluções.
 */
public class DevolucaoService {

    private DevolucaoDAO dao;

    // Construtor
    public DevolucaoService() {
        this.dao = new DevolucaoDAO();
    }
    public DevolucaoService(DevolucaoDAO dao) {
    this.dao = dao;
}


    /**
     * Obtém a lista de devoluções.
     */
    public ArrayList<Devolucao> getListaDevolucao() {
        return dao.getListaDevolucao();
    }

    /**
     * Insere uma nova devolução no banco de dados.
     */
    public boolean insertDevolucao(String nomeAmigo, int idFerramenta, String data, String nomeDaFerramenta) {
        // Cria um objeto Devolucao sem o ID (será gerado automaticamente no BD)
        Devolucao devolucao = new Devolucao(nomeAmigo, idFerramenta, data, 0, nomeDaFerramenta);
        return dao.insertDevolucaoBD(devolucao);
    }
}
