package controle;

import visao.FrmCadastrarAmigo;
import servico.AmigoService;
import javax.swing.JOptionPane;

public class AmigoController {
    
    private FrmCadastrarAmigo tela;
    private AmigoService amigoService;

    public AmigoController(FrmCadastrarAmigo tela, AmigoService amigoService) {
        this.tela = tela;
        this.amigoService = amigoService;
        
        // Adiciona ações aos botões
        this.tela.getJBCadastrar().addActionListener(evt -> cadastrar());
        this.tela.getJBCancelar().addActionListener(evt -> cancelar());
    }

    // Método para cadastrar amigo
    private void cadastrar() {
        String nome = this.tela.getJTFNome().getText();
        String telefoneStr = this.tela.getJTFTelefone().getText();

        if (nome.isEmpty() || telefoneStr.isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int telefone = Integer.parseInt(telefoneStr);
            int id = amigoService.maiorID() + 1; // Define um novo ID para o amigo

            // Chama o serviço para salvar o amigo no banco
            boolean sucesso = amigoService.insertAmigoBD(nome, id, telefone);

            if (sucesso) {
                JOptionPane.showMessageDialog(tela, "Amigo cadastrado com sucesso!");
                this.tela.dispose(); // Fecha a tela após sucesso
            } else {
                JOptionPane.showMessageDialog(tela, "Erro ao cadastrar o amigo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(tela, "Telefone deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para cancelar o cadastro
    private void cancelar() {
        this.tela.dispose(); // Fecha a tela
    }
}
