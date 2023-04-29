package school.sptech.harmonyospringapi.service.instrumento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.repository.InstrumentoRepository;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoMapper;

import java.util.List;

@Service
public class InstrumentoService {

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    public InstrumentoExibicaoDto cadastrar(InstrumentoCriacaoDto instrumentoCriacaoDto) {
        if (this.instrumentoRepository.existsInstrumentoByNome(instrumentoCriacaoDto.getNome())) return null;

        Instrumento novoInstrumento = InstrumentoMapper.of(instrumentoCriacaoDto);
        this.instrumentoRepository.save(novoInstrumento);
        return InstrumentoMapper.ofInstrumentoExibicao(novoInstrumento);
    }

    public List<InstrumentoExibicaoDto> listar() {
        return this.instrumentoRepository.findAll().stream().map(InstrumentoMapper::ofInstrumentoExibicao).toList();
    }
}
