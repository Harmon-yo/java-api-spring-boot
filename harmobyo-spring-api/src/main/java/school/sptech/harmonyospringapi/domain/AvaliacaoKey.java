package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.Columns;

import java.io.Serializable;

@Embeddable
public class AvaliacaoKey implements Serializable {
    @Column(name = "usuario_avaliador_fk")
    private Integer usuarioAvaliadorFk;

    @Column(name = "usuario_avaliado_fk")
    private Integer usuarioAvaliadoFk;

    @Column(name = "pedido_fk")
    private Integer pedido;

    public java.lang.Integer getUsuarioAvaliadorFk() {
        return usuarioAvaliadorFk;
    }

    public void setUsuarioAvaliadorFk(java.lang.Integer usuarioAvaliadorFk) {
        this.usuarioAvaliadorFk = usuarioAvaliadorFk;
    }

    public java.lang.Integer getUsuarioAvaliadoFk() {
        return usuarioAvaliadoFk;
    }

    public void setUsuarioAvaliadoFk(java.lang.Integer usuarioAvaliadoFk) {
        this.usuarioAvaliadoFk = usuarioAvaliadoFk;
    }

    public Integer getPedido() {
        return pedido;
    }

    public void setPedido(Integer pedido) {
        this.pedido = pedido;
    }
}
