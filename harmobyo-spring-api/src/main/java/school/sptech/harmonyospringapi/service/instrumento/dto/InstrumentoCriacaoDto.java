package school.sptech.harmonyospringapi.service.instrumento.dto;

import jakarta.validation.constraints.Size;

public class InstrumentoCriacaoDto {

    @Size(min = 3)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
