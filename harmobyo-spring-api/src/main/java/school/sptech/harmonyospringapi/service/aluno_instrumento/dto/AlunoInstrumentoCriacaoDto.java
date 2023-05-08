package school.sptech.harmonyospringapi.service.aluno_instrumento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoApenasIdDto;

public class AlunoInstrumentoCriacaoDto {
    private Aluno aluno;

    private Instrumento instrumento;

    private String nivelConhecimento;

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public String getNivelConhecimento() {
        return nivelConhecimento;
    }

    public void setNivelConhecimento(String nivelConhecimento) {
        this.nivelConhecimento = nivelConhecimento;
    }
}
