package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 5, message = "O logradouro deve ter no mínimo 5 caracteres")
    private String logradouro;


    private String numero;

    private String complemento;

    @NotBlank
    @Size(min = 3, message = "A cidade deve ter no mínimo 3 caracteres")
    private String cidade;

    @NotBlank
    @Size(min = 3, message = "O bairro deve ter no mínimo 3 caracteres")
    private String bairro;

    @OneToOne(mappedBy = "endereco")
    private Usuario usuario;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @NotBlank
    @Size(min = 2, message = "O estado deve ter no mínimo 2 caracteres")
    private String estado;

    @NotBlank
    @Size(min = 8, message = "O CEP deve ter no mínimo 8 caracteres")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 99999-999")
    private String cep;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
