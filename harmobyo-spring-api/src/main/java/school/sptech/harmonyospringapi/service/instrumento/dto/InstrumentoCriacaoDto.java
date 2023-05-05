package school.sptech.harmonyospringapi.service.instrumento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import school.sptech.harmonyospringapi.domain.Naipe;

public class InstrumentoCriacaoDto {

    @Size(min = 3)
    @NotBlank
    private String nome;

    private Naipe naipe;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Naipe getNaipe() {
        return naipe;
    }

    public void setNaipe(Naipe naipe) {
        this.naipe = naipe;
    }
}
