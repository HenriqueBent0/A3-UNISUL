package servico;

import dao.FerramentaDAO;
import modelo.Ferramenta;
import javax.swing.JOptionPane;

public class FerramentaService {

    private FerramentaDAO ferramentaDAO;

    public FerramentaService() {
        ferramentaDAO = new FerramentaDAO(); // Cria uma instância do DAO
    }

    public boolean cadastrarFerramenta(String nome, String marca, String valorTexto) {
        // Valida os dados de entrada
        if (nome.length() < 2) {
            JOptionPane.showMessageDialog(null, "Nome deve conter ao menos 2 caracteres.");
            return false;
        }

        if (valorTexto.length() <= 0) {
            JOptionPane.showMessageDialog(null, "Digite um valor válido!");
            return false;
        }

        int valor;
        try {
            valor = Integer.parseInt(valorTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Informe um número válido.");
            return false;
        }

        // Cria um novo objeto Ferramenta
        Ferramenta ferramenta = new Ferramenta(0, nome, marca, valor);

        // Chama o método de inserção no banco de dados
        boolean sucesso = ferramentaDAO.insertFerramentaBD(ferramenta);

        if (sucesso) {
            JOptionPane.showMessageDialog(null, "Ferramenta Cadastrada com Sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar ferramenta.");
        }

        return sucesso;
    }
}
