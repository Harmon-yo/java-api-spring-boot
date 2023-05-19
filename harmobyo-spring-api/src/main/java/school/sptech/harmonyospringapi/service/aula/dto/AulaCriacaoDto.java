package school.sptech.harmonyospringapi.service.aula.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AulaCriacaoDto {

    @Min(0)
    @NotNull
    private Double valorAula;

    @Min(1)
    @NotNull
    private Integer usuarioId;

    @Min(1)
    @NotNull
    private Integer instrumentoId;

    public Double getValorAula() {
        return valorAula;
    }

    public void setValorAula(Double valorAula) {
        this.valorAula = valorAula;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getInstrumentoId() {
        return instrumentoId;
    }

    public void setInstrumentoId(Integer instrumentoId) {
        this.instrumentoId = instrumentoId;
    }
}
