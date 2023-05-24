package school.sptech.harmonyospringapi.service.usuario.dto.professor;

import jakarta.persistence.OneToMany;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;

import java.util.List;

public class ProfessorExibicaoResumidoDto {

    private Integer id;

    private String nome;

    @OneToMany(mappedBy = "professor")
    private List<InstrumentoExibicaoDto> ltInstrumentos;

    private Double distancia;

    private Double valorMinimo;

    private boolean empresaInstrumento;

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

    public List<InstrumentoExibicaoDto> getLtInstrumentos() {
        return ltInstrumentos;
    }

    public void setLtInstrumentos(List<InstrumentoExibicaoDto> ltInstrumentos) {
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
