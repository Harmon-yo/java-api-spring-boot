package school.sptech.harmonyospringapi.service.instrumento.dto;

import school.sptech.harmonyospringapi.domain.Instrumento;

public class InstrumentoMapper {
    public static Instrumento of(InstrumentoCriacaoDto instrumentoCriacaoDto) {
        Instrumento instrumento = new Instrumento();

        instrumento.setNome(instrumentoCriacaoDto.getNome());

        return instrumento;
    }

    public static InstrumentoExibicaoDto ofInstrumentoExibicao(Instrumento instrumento) {
        InstrumentoExibicaoDto instrumentoCriacaoDto = new InstrumentoExibicaoDto();

        instrumentoCriacaoDto.setId(instrumento.getId());
        instrumentoCriacaoDto.setNome(instrumento.getNome());

        return instrumentoCriacaoDto;
    }
}
