package school.sptech.harmonyoentregaveleda;

public abstract class Instrumento {

    private String nome;
    private String nivelConhecimento;
    private String descricaoNaipe;

    public Instrumento(String nome, String nivelConhecimento, String descricaoNaipe) {
        this.nome = nome;
        this.nivelConhecimento = nivelConhecimento;
        this.descricaoNaipe = descricaoNaipe;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNivelConhecimento() {
        return nivelConhecimento;
    }

    public void setNivelConhecimento(String nivelConhecimento) {
        this.nivelConhecimento = nivelConhecimento;
    }

    public String getDescricaoNaipe() {
        return descricaoNaipe;
    }

    public void setDescricaoNaipe(String descricaoNaipe) {
        this.descricaoNaipe = descricaoNaipe;
    }
}
