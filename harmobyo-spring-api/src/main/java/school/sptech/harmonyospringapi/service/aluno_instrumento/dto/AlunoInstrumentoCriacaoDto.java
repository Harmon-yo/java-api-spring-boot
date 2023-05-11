package school.sptech.harmonyospringapi.service.aluno_instrumento.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoApenasIdDto;

public class AlunoInstrumentoCriacaoDto {

    @Min(1)
    @NotNull
    private Integer instrumentoId;

    @NotBlank
    private String nivelConhecimento;

    public Integer getInstrumentoId() {
        return instrumentoId;
    }

    public void setInstrumentoId(Integer instrumentoId) {
        this.instrumentoId = instrumentoId;
    }

    public String getNivelConhecimento() {
        return nivelConhecimento;
    }

    public void setNivelConhecimento(String nivelConhecimento) {
        this.nivelConhecimento = nivelConhecimento;
    }
}
