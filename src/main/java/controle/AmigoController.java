package controle;

import dao.AmigoDAO;
import java.awt.event.ActionEvent;
import modelo.Amigo;
import visao.FrmCadastrarAmigo;

/**
 * Controller responsável pela lógica entre a interface e a entidade Amigo.
 */
public class AmigoController {

    private FrmCadastrarAmigo frm;
    private AmigoDAO amigoDao;

    public AmigoController(FrmCadastrarAmigo frm) {
        this.frm = frm;
        this.amigoDao = new AmigoDAO(); // Inicializando o DAO

        // Conectando os listeners aos botões
        this.frm.getJBCadastrar().addActionListener(this::jButtonCadastrarActionPerformed);
        this.frm.getJBCancelar().addActionListener(this::jButtonFecharActionPerformed);
    }

    public void executar() {
        frm.setVisible(true);
    }

    /**
     * Método acionado ao clicar em "Cadastrar".
     */
    public void jButtonCadastrarActionPerformed(ActionEvent e) {
    Amigo amigo = new Amigo();
    amigo.setNome(frm.getJTFNome().getText());

    try {
        // Verificando se o telefone contém apenas números
        String telefone = frm.getJTFTelefone().getText();
        if (telefone.matches("\\d+")) {  // Verifica se contém apenas dígitos
            amigo.setTelefone(Integer.parseInt(telefone));  // Convertendo para inteiro
        } else {
            frm.mostrarMensagem("Telefone inválido. Por favor, insira um número válido.");
            return;
        }
    } catch (NumberFormatException ex) {
        frm.mostrarMensagem("Telefone inválido. Por favor, insira um número válido.");
        return;
    }

    // Definir o ID manualmente
    int novoId = amigoDao.maiorID() + 1;
    amigo.setId(novoId);  // Atribui o novo ID

    // Inserção do amigo no banco
    boolean sucesso = amigoDao.insertAmigoBD(amigo);

    if (sucesso) {
        frm.mostrarMensagem("Amigo Cadastrado com Sucesso!");
    } else {
        frm.mostrarMensagem("Erro ao cadastrar o amigo.");
    }
}

    /**
     * Método acionado ao clicar em "Fechar".
     */
    public void jButtonFecharActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    // Métodos de acesso ao formulário
    public FrmCadastrarAmigo getFrm() {
        return frm;
    }

    public void setFrm(FrmCadastrarAmigo frm) {
        this.frm = frm;
    }
}
