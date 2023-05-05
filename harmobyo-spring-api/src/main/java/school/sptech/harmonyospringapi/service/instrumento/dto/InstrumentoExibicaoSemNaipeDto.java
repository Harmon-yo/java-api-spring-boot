package school.sptech.harmonyospringapi.service.instrumento.dto;

import school.sptech.harmonyospringapi.service.naipe.dto.NaipeExibicaoSemInstrumentosDto;

public class InstrumentoExibicaoSemNaipeDto {

    private Integer id;

    private String nome;

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

}
