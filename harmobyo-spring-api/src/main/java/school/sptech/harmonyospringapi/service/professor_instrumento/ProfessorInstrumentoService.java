package school.sptech.harmonyospringapi.service.professor_instrumento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumento;
import school.sptech.harmonyospringapi.repository.InstrumentoRepository;
import school.sptech.harmonyospringapi.repository.ProfessorInstrumentoRepository;
import school.sptech.harmonyospringapi.repository.ProfessorRepository;
import school.sptech.harmonyospringapi.service.instrumento.InstrumentoService;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoMapper;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorInstrumentoService {

    @Autowired
    private ProfessorInstrumentoRepository professorInstrumentoRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private InstrumentoService instrumentoService;

    public List<ProfessorInstrumentoExibicaoDto> obterTodos(int id) {
        return this.professorInstrumentoRepository.findByProfessor_id(id)
                .stream()
                .map(ProfessorInstrumentoMapper::ofProfessorInstrumentoExibicao)
                .toList();
    }

    public ProfessorInstrumentoExibicaoDto criar(Integer professorId, ProfessorInstrumentoCriacaoDto professorInstrumentoCriacaoDto) {
        Integer instrumentoId = professorInstrumentoCriacaoDto.getInstrumentoId();

        Professor professorCadastrado = this.professorService.obterProfessorPorId(professorId);
        Instrumento instrumentoCadastrado = this.instrumentoService.obterInstrumentoPorId(instrumentoId);

        ProfessorInstrumento professorInstrumentoCadastrado = this.professorInstrumentoRepository
                .save(ProfessorInstrumentoMapper.of(professorInstrumentoCriacaoDto, professorCadastrado, instrumentoCadastrado));

        return ProfessorInstrumentoMapper.ofProfessorInstrumentoExibicao(professorInstrumentoCadastrado);
    }
}
