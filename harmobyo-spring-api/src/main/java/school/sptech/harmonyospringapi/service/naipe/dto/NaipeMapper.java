package school.sptech.harmonyospringapi.service.naipe.dto;

import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Naipe;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoMapper;

import java.util.ArrayList;

public class NaipeMapper {

    public static Naipe of(NaipeCriacaoDto naipeCriacaoDto) {
        Naipe naipe = new Naipe();

        naipe.setDescricaoNaipe(naipeCriacaoDto.getDescricaoNaipe());
        naipe.setInstrumentos(new ArrayList<>());

        return naipe;
    }

    public static NaipeExibicaoDto ofNaipeExibicao(Naipe naipe) {
        NaipeExibicaoDto naipeExibicaoDto = new NaipeExibicaoDto();

        naipeExibicaoDto.setId(naipe.getId());
        naipeExibicaoDto.setDescricaoNaipe(naipe.getDescricaoNaipe());
        naipeExibicaoDto.setInstrumentos(
                naipe.getInstrumentos()
                        .stream()
                        .map(InstrumentoMapper::ofInstrumentoExibicaoSemNaipe)
                        .toList()
        );

        return naipeExibicaoDto;
    }

    public static NaipeExibicaoSemInstrumentosDto ofNaipeExibicaoSemInstrumentos(Naipe naipe) {
        NaipeExibicaoSemInstrumentosDto naipeExibicaoSemInstrumentosDto = new NaipeExibicaoSemInstrumentosDto();

        naipeExibicaoSemInstrumentosDto.setId(naipe.getId());
        naipeExibicaoSemInstrumentosDto.setDescricaoNaipe(naipe.getDescricaoNaipe());

        return naipeExibicaoSemInstrumentosDto;
    }
}
