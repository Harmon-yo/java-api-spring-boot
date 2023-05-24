package school.sptech.harmonyospringapi.service.usuario.dto.avaliacao;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class AvaliacaoCriacaoDto {

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double valor;

    @NotBlank
    @Size(min=3, max = 255)
    private String comentario;

    @PastOrPresent
    private LocalDate dataAvaliacao;

    @NotNull
    private java.lang.Integer usuarioAvaliadorId;

    @NotNull
    private Integer pedidoId;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public java.lang.Integer getUsuarioAvaliadorId() {
        return usuarioAvaliadorId;
    }

    public void setUsuarioAvaliadorId(java.lang.Integer usuarioAvaliadorId) {
        this.usuarioAvaliadorId = usuarioAvaliadorId;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }
}
