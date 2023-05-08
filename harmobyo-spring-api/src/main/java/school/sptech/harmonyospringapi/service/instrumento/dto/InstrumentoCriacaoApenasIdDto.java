package school.sptech.harmonyospringapi.service.instrumento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.harmonyospringapi.domain.Naipe;

public class InstrumentoCriacaoApenasIdDto {

    @NotNull
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
