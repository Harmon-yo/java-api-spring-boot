package school.sptech.harmonyospringapi.service.instrumento.dto;

import school.sptech.harmonyospringapi.service.naipe.dto.NaipeExibicaoSemInstrumentosDto;

public class InstrumentoExibicaoDto {

    private Integer id;

    private String nome;

    private NaipeExibicaoSemInstrumentosDto naipe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public NaipeExibicaoSemInstrumentosDto getNaipe() {
        return naipe;
    }

    public void setNaipe(NaipeExibicaoSemInstrumentosDto naipe) {
        this.naipe = naipe;
    }
}
