package school.sptech.harmonyoentregaveleda;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public abstract class Usuario {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String cpf;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataNasc;
    private String sexo;
    private String email;
    private String senha;
    private String telefone;
    private Endereco endereco;

    public Usuario() {
    }

    public Usuario(Integer id, String nome, String sobrenome, String cpf,
                   LocalDate dataNasc, String sexo, String email,
                   String senha, String telefone) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.dataNasc = dataNasc;
    }

    @JsonProperty("endereco")
    public void desempacotarEndereco(Map<String, String> endereco) {
        this.endereco = new Endereco(
                endereco.get("endereco"),
                endereco.get("numero"),
                endereco.get("complemento"),
                endereco.get("cidade"),
                endereco.get("estado"),
                endereco.get("cep")
        );
    }

    public abstract Boolean validarIdade();

    public Integer calcularIdade(){

        LocalDate dataAtual = LocalDate.now();
        Integer idade = dataAtual.compareTo(this.dataNasc);

        if (dataAtual.getMonth().getValue() < this.dataNasc.getMonth().getValue() &&
        dataAtual.getDayOfMonth() < this.dataNasc.getDayOfMonth()) {
            idade--;
        }

        return idade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setFkEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
