package school.sptech.harmonyospringapi.service.usuario.dto.professor;

import jakarta.persistence.OneToMany;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumento;

import java.util.List;

public class ProfessorExibicaoDto {

    private Long id;

    @OneToMany(mappedBy = "professor")
    private List<Instrumento> ltInstrumentos;

    private Double distancia;

    private Double valorMinimo;

    private boolean empresaInstrumento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Instrumento> getLtInstrumentos() {
        return ltInstrumentos;
    }

    public void setLtInstrumentos(List<Instrumento> ltInstrumentos) {
        this.ltInstrumentos = ltInstrumentos;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(Double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public boolean isEmpresaInstrumento() {
        return empresaInstrumento;
    }

    public void setEmpresaInstrumento(boolean empresaInstrumento) {
        this.empresaInstrumento = empresaInstrumento;
    }
}
