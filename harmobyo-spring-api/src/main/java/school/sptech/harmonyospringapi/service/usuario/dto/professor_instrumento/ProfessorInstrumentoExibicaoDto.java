package school.sptech.harmonyospringapi.service.usuario.dto.professor_instrumento;

import school.sptech.harmonyospringapi.domain.ProfessorInstrumentoKey;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

public class ProfessorInstrumentoExibicaoDto {

    private ProfessorInstrumentoKey id;

    private UsuarioExibicaoDto professor;

    private InstrumentoExibicaoDto instrumento;

    private String nivelConhecimento;

    private boolean emprestaInstrumento;

    public ProfessorInstrumentoKey getId() {
        return id;
    }

    public void setId(ProfessorInstrumentoKey id) {
        this.id = id;
    }

    public UsuarioExibicaoDto getProfessor() {
        return professor;
    }

    public void setProfessor(UsuarioExibicaoDto professor) {
        this.professor = professor;
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

    public boolean isEmprestaInstrumento() {
        return emprestaInstrumento;
    }

    public void setEmprestaInstrumento(boolean emprestaInstrumento) {
        this.emprestaInstrumento = emprestaInstrumento;
    }
}
