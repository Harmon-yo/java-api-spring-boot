package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import org.hibernate.annotations.Columns;

import java.io.Serializable;

@Embeddable
public class AvaliacaoKey implements Serializable {
    @Column(name = "usuario_avaliador_fk")
    private Integer usuarioAvaliadorFk;

    @Column(name = "usuario_avaliado_fk")
    private Integer usuarioAvaliadoFk;

    @Columns(columns = {@Column(name = "aluno_fk"), @Column(name = "professor_fk")})
    private PedidoKey pedido;

    public Integer getUsuarioAvaliadorFk() {
        return usuarioAvaliadorFk;
    }

    public void setUsuarioAvaliadorFk(Integer usuarioAvaliadorFk) {
        this.usuarioAvaliadorFk = usuarioAvaliadorFk;
    }

    public Integer getUsuarioAvaliadoFk() {
        return usuarioAvaliadoFk;
    }

    public void setUsuarioAvaliadoFk(Integer usuarioAvaliadoFk) {
        this.usuarioAvaliadoFk = usuarioAvaliadoFk;
    }

    public PedidoKey getPedido() {
        return pedido;
    }

    public void setPedido(PedidoKey pedido) {
        this.pedido = pedido;
    }
}
