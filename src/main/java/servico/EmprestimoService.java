package servico;

import static dao.AmigoDAO.ListaAmigo;
import dao.EmprestimoDAO;
import modelo.Emprestimo;
import modelo.Ferramenta;
import java.util.List;
import javax.swing.JOptionPane;

public class EmprestimoService {

    private EmprestimoDAO emprestimoDAO;

    public EmprestimoService() {
        emprestimoDAO = new EmprestimoDAO();
    }

    public boolean registrarEmprestimo(String nomeAmigo, String idFerramentaStr, String dataEmprestimo, List<Ferramenta> listaFerramenta, List<Emprestimo> listaEmprestimo) {
        // Procurar ferramenta pelo ID
        int idFerramenta = Integer.parseInt(idFerramentaStr);
        Ferramenta ferramentaEncontrada = null;
        for (Ferramenta ferramenta : listaFerramenta) {
            if (ferramenta.getId() == idFerramenta) {
                ferramentaEncontrada = ferramenta;
                break;
            }
        }

        if (ferramentaEncontrada == null) {
            JOptionPane.showMessageDialog(null, "Ferramenta não encontrada.");
            return false;
        }

        String nomeFerramenta = ferramentaEncontrada.getNome();

        // Verificar se a ferramenta já está emprestada
        for (Emprestimo emprestimo : listaEmprestimo) {
            if (emprestimo.getNomeDaFerramenta().equals(nomeFerramenta)) {
                JOptionPane.showMessageDialog(null, "Ferramenta já está emprestada.");
                return false;
            }
        }

        // Verificar se o amigo existe (simular aqui com uma lista de amigos)
        boolean amigoEncontrado = false;
        for (int i = 0; i < ListaAmigo.size(); i++) {
            if (ListaAmigo.get(i).getNome().equals(nomeAmigo)) {
                amigoEncontrado = true;
                break;
            }
        }

        if (!amigoEncontrado) {
            JOptionPane.showMessageDialog(null, "Amigo não encontrado.");
            return false;
        }

        // Cria um novo empréstimo
        Emprestimo novoEmprestimo = new Emprestimo();
        novoEmprestimo.setNomeAmigo(nomeAmigo);
        novoEmprestimo.setData(dataEmprestimo);
        novoEmprestimo.setNomeDaFerramenta(nomeFerramenta);
        novoEmprestimo.setIdFerramenta(idFerramenta);

        // Registrar o empréstimo no banco de dados
        return emprestimoDAO.insertEmprestimoBD(novoEmprestimo);
    }
}
