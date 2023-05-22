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
import school.sptech.harmonyospringapi.service.instrumento.InstrumentoService;
import school.sptech.harmonyospringapi.service.usuario.AlunoService;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoInstrumentoService {

    @Autowired
    private AlunoInstrumentoRepository alunoInstrumentoRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private InstrumentoService instrumentoService;

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public List<AlunoInstrumentoExibicaoDto> obterTodos(int id) {
        return this.alunoInstrumentoRepository.findByAluno_id(id)
                .stream()
                .map(AlunoInstrumentoMapper::ofAlunoInstrumentoExibicao)
                .toList();
    }
//change opt
    public AlunoInstrumentoExibicaoDto criar(Integer alunoId, AlunoInstrumentoCriacaoDto alunoInstrumentoCriacaoDto) {
        Integer instrumentoId = alunoInstrumentoCriacaoDto.getInstrumentoId();

        Optional<Aluno> alunoOpt = this.alunoRepository.findById(alunoId);
        if (alunoOpt.isEmpty()) throw new EntitadeNaoEncontradaException("Aluno com id inexistente");

        Optional<Instrumento> instrumentoOpt = this.instrumentoRepository.findById(instrumentoId);

        if (instrumentoOpt.isEmpty()) throw new EntitadeNaoEncontradaException("Instrumento com id inexistente");
        Aluno aluno = alunoOpt.get();
        Instrumento instrumento = instrumentoOpt.get();

        AlunoInstrumento alunoInstrumento = this.alunoInstrumentoRepository
                .save(AlunoInstrumentoMapper.of(alunoInstrumentoCriacaoDto, aluno, instrumento));

        return AlunoInstrumentoMapper.ofAlunoInstrumentoExibicao(alunoInstrumento);
    }
}
