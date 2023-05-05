package school.sptech.harmonyospringapi.service.instrumento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Naipe;
import school.sptech.harmonyospringapi.repository.InstrumentoRepository;
import school.sptech.harmonyospringapi.repository.NaipeRepository;
import school.sptech.harmonyospringapi.service.exceptions.EntidadeConflitanteException;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoMapper;

import java.util.List;
import java.util.Optional;

@Service
public class InstrumentoService {

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Autowired
    private NaipeRepository naipeRepository;

    /* Ver se da para criar o naipe depois de criar o instrumento */
    public InstrumentoExibicaoDto cadastrar(InstrumentoCriacaoDto instrumentoCriacaoDto) {
        if (this.instrumentoRepository.existsInstrumentoByNomeIgnoreCase(instrumentoCriacaoDto.getNome())) throw new EntidadeConflitanteException("Erro ao cadastrar. Instrumento já cadastrado!");
        Optional<Naipe> optionalNaipe = this.naipeRepository.findById(instrumentoCriacaoDto.getNaipe().getId());
        if (optionalNaipe.isEmpty()) throw new EntitadeNaoEncontradaException("Erro ao cadastrar. Naipe não cadastrado!");



        instrumentoCriacaoDto.setNaipe(optionalNaipe.get());
        Instrumento novoInstrumento = InstrumentoMapper.of(instrumentoCriacaoDto);
        this.instrumentoRepository.save(novoInstrumento);
        return InstrumentoMapper.ofInstrumentoExibicao(novoInstrumento);
    }

    public List<InstrumentoExibicaoDto> listar() {
        return this.instrumentoRepository.findAll().stream().map(InstrumentoMapper::ofInstrumentoExibicao).toList();
    }
}
