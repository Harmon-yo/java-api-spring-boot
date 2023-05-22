package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double valor;

    private String comentario;

    @ManyToOne
    private Usuario usuarioAvaliado;

    private LocalDate dataAvaliacao;

    @ManyToOne
    private Usuario usuarioAvaliador;

    @ManyToOne
    private Pedido pedidoAula;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return pedidoAula;
    }

    public void setPedidoAula(Pedido pedidoAula) {
        this.pedidoAula = pedidoAula;
    }
}
