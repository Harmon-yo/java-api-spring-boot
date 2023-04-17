package school.sptech.harmonyospringapi.service.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioCriacaoDto {

    @Schema(description = "Nome do usuário", example = "John Doe")
    private String nome;

    @Schema(description = "E-mail do usuário", example = "john_doe@email.com")
    private String email;
    @Schema(description = "CPF do usuário", example = "111.111.111-11")
    private String cpf;

    @Schema(description = "Sexo do usuário", example = "Homem")
    private String sexo;

    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;
    private String categoria;



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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
