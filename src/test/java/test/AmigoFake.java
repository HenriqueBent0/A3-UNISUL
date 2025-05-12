package test;

import visao.FrmCadastrarAmigo;

/**
 * Classe que cria um formulário de cadastro de amigo falso para ser utilizado nos testes.
 */
public class AmigoFake extends FrmCadastrarAmigo {

    // Variáveis para armazenar os valores de entrada
    private String nome;
    private String telefone;
    private String mensagem;

    /**
     * Inicializa os atributos do formulário.
     */
    public AmigoFake() {
        super();
        // Inicializa sem os componentes gráficos reais
    }

    /**
     * Sobrescreve o método mostrarMensagem, substituindo a interação gráfica
     * para que a mensagem seja exibida no console durante os testes.
     *
     * @param mensagem A mensagem que será exibida
     */
    public void mostrarMensagem(String mensagem) {
        this.mensagem = mensagem;
        System.out.println("Mensagem: " + mensagem);
    }

    /**
     * Sobrescreve o método de pegar o nome inserido, substituindo o campo gráfico
     * para fins de teste.
     *
     * @return O nome inserido no campo de texto
     */
    
    public String getCampoNome() {
        return nome;
    }

    /**
     * Sobrescreve o método de pegar o telefone inserido, substituindo o campo gráfico
     * para fins de teste.
     *
     * @return O telefone inserido no campo de texto
     */
    public String getCampoTelefone() {
        return telefone;
    }

    /**
     * Define o nome que será utilizado no teste.
     *
     * @param nome Nome do amigo
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o telefone que será utilizado no teste.
     *
     * @param telefone Telefone do amigo
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Métodos para acessar as variáveis internas (para assertivas nos testes)
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Método para simular o cadastro de um amigo, como seria feito na interface.
     */
    public void cadastrar() {
        // Aqui você pode chamar o serviço que vai manipular os dados
        // Simulação do cadastro de amigo utilizando o nome e telefone
        // Exemplo, com o serviço:
        // amigoService.insertAmigoBD(getCampoNome(), getCampoTelefone());
        System.out.println("Cadastro realizado: " + getCampoNome() + " - " + getCampoTelefone());
    }
}
