package modelo;

/**
 * Classe que representa uma devolução.
 */
public class Devolucao {

    // Atributos de Devolução
    private String nomeAmigo;
    private int idFerramenta;
    private String data;
    private int id;
    private String nomeDaFerramenta;

    // Construtores
    public Devolucao() {
        this("", 0, "", 0, "");
    }

    /**
     * Construtor para criar um objeto Devolução.
     */
    public Devolucao(String nomeAmigo, int idFerramenta, String data, int id, String nomeDaFerramenta) {
        this.nomeAmigo = nomeAmigo;
        this.idFerramenta = idFerramenta;
        this.data = data;
        this.id = id;
        this.nomeDaFerramenta = nomeDaFerramenta;

    }

    // Getters e Setters
    public String getNomeAmigo() {
        return nomeAmigo;
    }

    public void setNomeAmigo(String nomeAmigo) {
        this.nomeAmigo = nomeAmigo;
    }

    public int getIdFerramenta() {
        return idFerramenta;
    }

    public void setIdFerramenta(int idFerramenta) {
        this.idFerramenta = idFerramenta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDaFerramenta() {
        return nomeDaFerramenta;
    }

    public void setNomeDaFerramenta(String nomeDaFerramenta) {
        this.nomeDaFerramenta = nomeDaFerramenta;
    }

}
