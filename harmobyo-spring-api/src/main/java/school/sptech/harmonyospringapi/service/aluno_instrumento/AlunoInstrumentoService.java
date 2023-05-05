package school.sptech.harmonyospringapi.service.aluno_instrumento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.AlunoInstrumento;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.repository.AlunoInstrumentoRepository;
import school.sptech.harmonyospringapi.repository.AlunoRepository;
import school.sptech.harmonyospringapi.repository.InstrumentoRepository;
import school.sptech.harmonyospringapi.service.aluno_instrumento.dto.AlunoInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.aluno_instrumento.dto.AlunoInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.aluno_instrumento.dto.AlunoInstrumentoMapper;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoInstrumentoService {

    @Autowired
    private AlunoInstrumentoRepository alunoInstrumentoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    public List<AlunoInstrumentoExibicaoDto> listar() {
        return this.alunoInstrumentoRepository.findAll()
                .stream()
                .map(AlunoInstrumentoMapper::ofAlunoInstrumentoExibicao)
                .toList();
    }

    public AlunoInstrumentoExibicaoDto cadastrar(AlunoInstrumentoCriacaoDto alunoInstrumentoCriacaoDto) {
        Integer alunoId = alunoInstrumentoCriacaoDto.getAluno().getId();
        Integer instrumentoId = alunoInstrumentoCriacaoDto.getInstrumento().getId();

        Optional<Aluno> optionalAluno = this.alunoRepository.findById(alunoId);
        Optional<Instrumento> optionalInstrumento = this.instrumentoRepository.findById(instrumentoId);

        if (optionalAluno.isEmpty()) throw new EntitadeNaoEncontradaException("Aluno com id inexistente");
        if (optionalInstrumento.isEmpty()) throw new EntitadeNaoEncontradaException("Instrumento com id inexistente");

        alunoInstrumentoCriacaoDto.setAluno(optionalAluno.get());
        alunoInstrumentoCriacaoDto.setInstrumento(optionalInstrumento.get());

        AlunoInstrumento alunoInstrumento = this.alunoInstrumentoRepository
                .save(AlunoInstrumentoMapper.of(alunoInstrumentoCriacaoDto));

        return AlunoInstrumentoMapper.ofAlunoInstrumentoExibicao(alunoInstrumento);
    }
}
