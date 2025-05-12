package test;

import visao.FrmCadastrarAmigo;

/**
 * Classe que cria um formulário de cadastro de amigo falso para ser utilizado nos testes.
 */
public class AmigoFake extends FrmCadastrarAmigo {

    private String nome;
    private String telefone;
    private String mensagem;

    public AmigoFake() {
        super();
    }

    public void mostrarMensagem(String mensagem) {
        this.mensagem = mensagem;
        System.out.println("Mensagem: " + mensagem);
    }

    public String getCampoNome() {
        return nome;
    }

    public String getCampoTelefone() {
        return telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void cadastrar() {
        // Simula o processo de cadastro, mas sem acessar banco de dados
        System.out.println("Cadastro realizado: " + getCampoNome() + " - " + getCampoTelefone());
    }
}
