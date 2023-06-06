package school.sptech.harmonyospringapi.service.usuario.dto.professor;

import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;

import java.time.LocalDateTime;
import java.util.List;

public class ProfessorPopularDto {

    private Integer id;

    private String nome;


    private String localizacao;

    private Double mediaAvaliacao;

    private LocalDateTime ultimaVezOnline;

    public ProfessorPopularDto(Integer id, String nome,  String localizacao, Double mediaAvaliacao, LocalDateTime ultimaVezOnline) {
        this.id = id;
        this.nome = nome;

        this.localizacao = localizacao;
        this.mediaAvaliacao = mediaAvaliacao;
        this.ultimaVezOnline = ultimaVezOnline;
    }

    public ProfessorPopularDto() {

    }

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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Double getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(Double mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public LocalDateTime getUltimaVezOnline() {
        return ultimaVezOnline;
    }

    public void setUltimaVezOnline(LocalDateTime ultimaVezOnline) {
        this.ultimaVezOnline = ultimaVezOnline;
    }
}
