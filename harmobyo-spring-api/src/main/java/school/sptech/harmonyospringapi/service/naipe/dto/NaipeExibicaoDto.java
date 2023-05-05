package school.sptech.harmonyospringapi.service.naipe.dto;

import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoSemNaipeDto;

import java.util.List;

public class NaipeExibicaoDto {

    private Integer id;

    private String descricaoNaipe;

    private List<InstrumentoExibicaoSemNaipeDto> instrumentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricaoNaipe() {
        return descricaoNaipe;
    }

    public void setDescricaoNaipe(String descricaoNaipe) {
        this.descricaoNaipe = descricaoNaipe;
    }

    public List<InstrumentoExibicaoSemNaipeDto> getInstrumentos() {
        return instrumentos;
    }

    public void setInstrumentos(List<InstrumentoExibicaoSemNaipeDto> instrumentos) {
        this.instrumentos = instrumentos;
    }
}
