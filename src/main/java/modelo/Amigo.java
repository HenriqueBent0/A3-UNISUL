package modelo;

/**
 * Classe que representa um Amigo.
 */
public class Amigo {

    //Atributos de Amigo
    private String nome;
    private int id;
    private int telefone;

    //Construtores
    public Amigo() {
        this("", 0, 0);
    }

    /**
     * Construtor para criar um objeto Amigo.
     */
    public Amigo(String nome, int id, int telefone) {
        this.nome = nome;
        this.id = id;
        this.telefone = telefone;

    }

    //Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

}
