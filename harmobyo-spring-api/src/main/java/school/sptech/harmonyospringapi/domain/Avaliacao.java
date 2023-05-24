package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Avaliacao {

    @EmbeddedId
    private AvaliacaoKey id;

    private Double valor;

    private String comentario;

    @ManyToOne
    @MapsId("usuarioAvaliadoFk")
    @JoinColumn(name = "usuario_avaliado_fk")
    private Usuario usuarioAvaliado;

    private LocalDate dataAvaliacao;

    @ManyToOne
    @MapsId("usuarioAvaliadorFk")
    @JoinColumn(name = "usuario_avaliador_fk")
    private Usuario usuarioAvaliador;

    @ManyToOne
    @MapsId("pedidoFk")
    @JoinColumns({
            @JoinColumn(name = "pedido_aluno_fk", referencedColumnName = "aluno_fk"),
            @JoinColumn(name = "pedido_professor_fk", referencedColumnName = "professor_fk"),
            @JoinColumn(name = "pedido_fk", referencedColumnName = "pedido_id")

    })
    private Pedido pedido;

    public AvaliacaoKey getId() {
        return id;
    }

    public void setId(AvaliacaoKey id) {
        this.id = id;
    }

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

    public Usuario getUsuarioAvaliado() {
        return usuarioAvaliado;
    }

    public void setUsuarioAvaliado(Usuario usuarioAvaliado) {
        this.usuarioAvaliado = usuarioAvaliado;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public Usuario getUsuarioAvaliador() {
        return usuarioAvaliador;
    }

    public void setUsuarioAvaliador(Usuario usuarioAvaliador) {
        this.usuarioAvaliador = usuarioAvaliador;
    }

    public Pedido getPedidoAula() {
        return pedido;
    }

    public void setPedidoAula(Pedido pedido) {
        this.pedido = pedido;
    }
}
