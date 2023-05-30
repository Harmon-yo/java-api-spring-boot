package school.sptech.harmonyospringapi.service.usuario.dto.professor;

import jakarta.persistence.OneToMany;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;

import java.util.List;

public class ProfessorExibicaoResumidoDto {

    private Integer id;

    private String nome;

    @OneToMany(mappedBy = "professor")
    private List<InstrumentoExibicaoDto> ltInstrumentos;

    private Double distancia;

    private Double valorMinimo;

    private Double valorMaximo;

    private boolean emprestaInstrumento;

    private Double mediaAvaliacao;

    private Integer qtdeAvaliacoes;

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

    public Double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public boolean isEmprestaInstrumento() {
        return emprestaInstrumento;
    }

    public void setEmprestaInstrumento(boolean emprestaInstrumento) {
        this.emprestaInstrumento = emprestaInstrumento;
    }

    public Double getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(Double mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public Integer getQtdeAvaliacoes() {
        return qtdeAvaliacoes;
    }

    public void setQtdeAvaliacoes(Integer qtdeAvaliacoes) {
        this.qtdeAvaliacoes = qtdeAvaliacoes;
    }
}
