package school.sptech.harmonyoentregaveleda;

public class Endereco {
    private String endereco;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(String endereco, String numero, String complemento, String cidade, String estado, String cep) {
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    public void validarEndereco() {
        return;
    }
}
