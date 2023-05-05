package school.sptech.harmonyospringapi.service.aluno_instrumento.dto;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.validation.constraints.NotNull;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.AlunoInstrumentoKey;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

public class AlunoInstrumentoExibicaoDto {

    private AlunoInstrumentoKey id;

    private UsuarioExibicaoDto aluno;

    private InstrumentoExibicaoDto instrumento;

    private String nivelConhecimento;

    public AlunoInstrumentoKey getId() {
        return id;
    }

    public void setId(AlunoInstrumentoKey id) {
        this.id = id;
    }

    public UsuarioExibicaoDto getAluno() {
        return aluno;
    }

    public void setAluno(UsuarioExibicaoDto aluno) {
        this.aluno = aluno;
    }

    public InstrumentoExibicaoDto getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(InstrumentoExibicaoDto instrumento) {
        this.instrumento = instrumento;
    }

    public String getNivelConhecimento() {
        return nivelConhecimento;
    }

    public void setNivelConhecimento(String nivelConhecimento) {
        this.nivelConhecimento = nivelConhecimento;
    }
}
