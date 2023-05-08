package school.sptech.harmonyospringapi.service.professor_instrumento.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoCriacaoApenasIdDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoApenasIdDto;

public class ProfessorInstrumentoCriacaoApenasIdDto {

    @NotNull
    private InstrumentoCriacaoApenasIdDto instrumento;

    @Size(min = 3)
    @NotBlank
    private String nivelConhecimento;

    @NotNull
    private boolean emprestaInstrumento;

    public InstrumentoCriacaoApenasIdDto getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(InstrumentoCriacaoApenasIdDto instrumento) {
        this.instrumento = instrumento;
    }

    public String getNivelConhecimento() {
        return nivelConhecimento;
    }

    public void setNivelConhecimento(String nivelConhecimento) {
        this.nivelConhecimento = nivelConhecimento;
    }

    public boolean isEmprestaInstrumento() {
        return emprestaInstrumento;
    }

    public void setEmprestaInstrumento(boolean emprestaInstrumento) {
        this.emprestaInstrumento = emprestaInstrumento;
    }
}
