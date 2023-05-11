package school.sptech.harmonyospringapi.service.professor_instrumento.dto;

import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumento;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumentoKey;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoMapper;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

public class ProfessorInstrumentoMapper {

    public static ProfessorInstrumentoExibicaoDto ofProfessorInstrumentoExibicao(ProfessorInstrumento professorInstrumento) {
        ProfessorInstrumentoExibicaoDto professorInstrumentoExibicaoDto = new ProfessorInstrumentoExibicaoDto();

        professorInstrumentoExibicaoDto.setId(professorInstrumento.getId());
        professorInstrumentoExibicaoDto.setProfessor(UsuarioMapper.ofUsuarioExibicao(professorInstrumento.getProfessor()));
        professorInstrumentoExibicaoDto.setInstrumento(InstrumentoMapper.ofInstrumentoExibicao(professorInstrumento.getInstrumento()));
        professorInstrumentoExibicaoDto.setNivelConhecimento(professorInstrumento.getNivelConhecimento());
        professorInstrumentoExibicaoDto.setEmprestaInstrumento(professorInstrumento.isEmprestaInstrumento());

        return professorInstrumentoExibicaoDto;
    }

    public static ProfessorInstrumento of(ProfessorInstrumentoCriacaoDto professorInstrumentoCriacaoDto, Professor professor, Instrumento instrumento) {

        ProfessorInstrumentoKey professorInstrumentoKey = new ProfessorInstrumentoKey();
        professorInstrumentoKey.setProfessorFk(professor.getId());
        professorInstrumentoKey.setInstrumentoFk(instrumento.getId());

        ProfessorInstrumento professorInstrumento = new ProfessorInstrumento();
        professorInstrumento.setId(professorInstrumentoKey);
        professorInstrumento.setProfessor(professor);
        professorInstrumento.setInstrumento(instrumento);
        professorInstrumento.setNivelConhecimento(professorInstrumentoCriacaoDto.getNivelConhecimento());
        professorInstrumento.setEmprestaInstrumento(professorInstrumentoCriacaoDto.isEmprestaInstrumento());

        return professorInstrumento;
    }
}
