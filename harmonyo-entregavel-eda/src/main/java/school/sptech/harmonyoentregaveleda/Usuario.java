package school.sptech.harmonyoentregaveleda;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public abstract class Usuario {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private Date dataNasc;
    private String sexo;
    private String email;
    private String senha;
    private String telefone;
    private Integer fkEndereco;

    public Usuario() {
    }

    public Usuario(Integer id, String nome, String sobrenome, String cpf, Date dataNasc, String sexo, String email, String senha, String telefone, Integer fkEndereco) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.fkEndereco = fkEndereco;
    }

    public abstract Boolean validarIdade();

    public Integer getIdade(){

        Date dataAtual = Date.from(Instant.now());

        Calendar calendar = Calendar.getInstance();

        Integer idade = 0;

        calendar.setTime(dataAtual);

        if (calendar.get(Calendar.MONTH) >= this.dataNasc.getMonth()) {
            if (calendar.get(Calendar.DAY_OF_MONTH) >= this.dataNasc.getDay()) {
                idade = calendar.get(Calendar.YEAR) - this.dataNasc.getYear();

            }
        } else {
            idade = calendar.get(Calendar.YEAR) - this.dataNasc.getYear() - 1;
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


    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
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

    public Integer getFkEndereco() {
        return fkEndereco;
    }

    public void setFkEndereco(Integer fkEndereco) {
        this.fkEndereco = fkEndereco;
    }
}
