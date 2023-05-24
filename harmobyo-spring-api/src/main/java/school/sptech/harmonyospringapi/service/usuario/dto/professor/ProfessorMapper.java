package school.sptech.harmonyospringapi.service.usuario.dto.professor;

import org.springframework.beans.factory.annotation.Autowired;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumento;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;

import java.util.List;

public class ProfessorMapper {

    @Autowired
    private static ProfessorService professorService;
    public static ProfessorExibicaoResumidoDto of(Professor p){
        ProfessorExibicaoResumidoDto dto = new ProfessorExibicaoResumidoDto();
        List<InstrumentoExibicaoDto> instrumentos = professorService.listarInstrumentos(p.getId());
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setLtInstrumentos(instrumentos);
        dto.setValorMinimo(professorService.getMenorValorAula(p.getId()));
        dto.setEmpresaInstrumento(professorService.emprestaInstrumento(p.getId()));

        /*dto.setDistancia(p.getDi());*/
        return dto;
    }

}
