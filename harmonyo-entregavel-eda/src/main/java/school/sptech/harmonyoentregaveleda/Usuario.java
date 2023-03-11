package school.sptech.harmonyoentregaveleda;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    private EstadoConta estadoConta;
    private List<Instrumento> instrumentos;

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
        this.estadoConta = new EstadoConta();
        this.instrumentos = new ArrayList<>();
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

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Instrumento> getInstrumentos() {
        return instrumentos;
    }

    public void autenticarConta() {
        this.estadoConta.setOnline(true);
        this.estadoConta.setUltimaVezOnline(LocalDateTime.now());
    }

    public void desativarConta() {
        this.estadoConta.setAtivo(false);
        this.estadoConta.setOnline(false);
        this.estadoConta.setUltimaVezOnline(LocalDateTime.now());
    }

    public abstract void adicionarInstrumento(String nome, String nivelConhecimento, String descricaoNaipe);
//    public abstract void adicionarInstrumento(String nome, String nivelConhecimento, String descricaoNaipe, boolean emprestaInstrumento);
}
