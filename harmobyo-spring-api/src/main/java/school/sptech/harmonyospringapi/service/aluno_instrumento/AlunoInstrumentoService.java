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

    public List<AlunoInstrumentoExibicaoDto> obterTodos(int id) {
        return this.alunoInstrumentoRepository.findByAluno_id(id)
                .stream()
                .map(AlunoInstrumentoMapper::ofAlunoInstrumentoExibicao)
                .toList();
    }

    public AlunoInstrumentoExibicaoDto cadastrar(Integer alunoId, AlunoInstrumentoCriacaoDto alunoInstrumentoCriacaoDto) {
        Integer instrumentoId = alunoInstrumentoCriacaoDto.getInstrumentoId();

        Aluno aluno = this.alunoService.obterAlunoPorId(alunoId);
        Instrumento instrumento = this.instrumentoService.obterInstrumentoPorId(instrumentoId);

        AlunoInstrumento alunoInstrumento = this.alunoInstrumentoRepository
                .save(AlunoInstrumentoMapper.of(alunoInstrumentoCriacaoDto, aluno, instrumento));

        return AlunoInstrumentoMapper.ofAlunoInstrumentoExibicao(alunoInstrumento);
    }
}
