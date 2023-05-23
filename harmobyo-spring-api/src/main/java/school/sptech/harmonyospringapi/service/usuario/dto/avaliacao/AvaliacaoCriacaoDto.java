package school.sptech.harmonyospringapi.service.usuario.dto.avaliacao;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import school.sptech.harmonyospringapi.domain.Pedido;
import school.sptech.harmonyospringapi.domain.Usuario;

import java.time.LocalDate;

public class AvaliacaoCriacaoDto {

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double valor;

    @NotBlank
    @Size(min=3, max = 255)
    private String comentario;

    @NotNull
    private Integer usuarioAvaliadoId;

    @PastOrPresent
    private LocalDate dataAvaliacao;

    @NotNull
    private Integer usuarioAvaliadorId;

    @NotNull
    private Integer pedidoAulaId;

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

    public Integer getUsuarioAvaliadoId() {
        return usuarioAvaliadoId;
    }

    public void setUsuarioAvaliadoId(Integer usuarioAvaliadoId) {
        this.usuarioAvaliadoId = usuarioAvaliadoId;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public Integer getUsuarioAvaliadorId() {
        return usuarioAvaliadorId;
    }

    public void setUsuarioAvaliadorId(Integer usuarioAvaliadorId) {
        this.usuarioAvaliadorId = usuarioAvaliadorId;
    }

    public Integer getPedidoAulaId() {
        return pedidoAulaId;
    }

    public void setPedidoAulaId(Integer pedidoAulaId) {
        this.pedidoAulaId = pedidoAulaId;
    }
}
