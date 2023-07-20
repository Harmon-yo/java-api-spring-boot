package school.sptech.harmonyospringapi.service.usuario.dto;

import school.sptech.harmonyospringapi.domain.Endereco;
import school.sptech.harmonyospringapi.domain.Experiencia;
import school.sptech.harmonyospringapi.service.experiencia.ExperienciaResumidaDto;

import java.time.LocalDate;
import java.util.List;

public class UsuarioDadosPerfilDto {

    private Integer id;

    private String nome;

    private String email;

    private String cpf;

    private String sexo;

    private LocalDate dataNasc;

    private String bibliografia;

    private Endereco Endereco;

    private List<ExperienciaResumidaDto> experiencia;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    public school.sptech.harmonyospringapi.domain.Endereco getEndereco() {
        return Endereco;
    }

    public void setEndereco(school.sptech.harmonyospringapi.domain.Endereco endereco) {
        Endereco = endereco;
    }

    public List<ExperienciaResumidaDto> getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(List<ExperienciaResumidaDto> experiencia) {
        this.experiencia = experiencia;
    }
}
