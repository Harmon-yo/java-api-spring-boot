package school.sptech.harmonyospringapi.service.professor_instrumento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumento;
import school.sptech.harmonyospringapi.repository.InstrumentoRepository;
import school.sptech.harmonyospringapi.repository.ProfessorInstrumentoRepository;
import school.sptech.harmonyospringapi.repository.ProfessorRepository;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoMapper;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorInstrumentoService {

    @Autowired
    private ProfessorInstrumentoRepository professorInstrumentoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    public List<ProfessorInstrumentoExibicaoDto> listar() {
        return this.professorInstrumentoRepository.findAll()
                .stream()
                .map(ProfessorInstrumentoMapper::ofProfessorInstrumentoExibicao)
                .toList();
    }

    public ProfessorInstrumentoExibicaoDto cadastrar(ProfessorInstrumentoCriacaoDto professorInstrumentoCriacaoDto) {
        Integer professorId = professorInstrumentoCriacaoDto.getProfessor().getId();
        Integer instrumentoId = professorInstrumentoCriacaoDto.getInstrumento().getId();

        Optional<Professor> optionalProfessor = this.professorRepository.findById(professorId);
        Optional<Instrumento> optionalInstrumento = this.instrumentoRepository.findById(instrumentoId);

        if (optionalProfessor.isEmpty()) throw new EntitadeNaoEncontradaException("Professor com id inexistente");
        if (optionalInstrumento.isEmpty()) throw new EntitadeNaoEncontradaException("Instrumento com id inexistente");

        professorInstrumentoCriacaoDto.setProfessor(optionalProfessor.get());
        professorInstrumentoCriacaoDto.setInstrumento(optionalInstrumento.get());

        ProfessorInstrumento professorInstrumentoCadastrado = this.professorInstrumentoRepository
                .save(ProfessorInstrumentoMapper.of(professorInstrumentoCriacaoDto));

        return ProfessorInstrumentoMapper.ofProfessorInstrumentoExibicao(professorInstrumentoCadastrado);
    }
}
