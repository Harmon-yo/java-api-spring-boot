package school.sptech.harmonyospringapi.service.usuario.dto.avaliacao;

import jakarta.persistence.ManyToOne;
import school.sptech.harmonyospringapi.domain.AvaliacaoKey;
import school.sptech.harmonyospringapi.domain.Pedido;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.time.LocalDate;

public class AvaliacaoExibicaoDto {
    private AvaliacaoKey id;

    private Double valor;

    private String comentario;

    private UsuarioExibicaoDto usuarioAvaliado;

    private LocalDate dataAvaliacao;

    private UsuarioExibicaoDto usuarioAvaliador;

    private PedidoExibicaoDto pedidoAula;

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

    public UsuarioExibicaoDto getUsuarioAvaliado() {
        return usuarioAvaliado;
    }

    public void setUsuarioAvaliado(UsuarioExibicaoDto usuarioAvaliado) {
        this.usuarioAvaliado = usuarioAvaliado;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public UsuarioExibicaoDto getUsuarioAvaliador() {
        return usuarioAvaliador;
    }

    public void setUsuarioAvaliador(UsuarioExibicaoDto usuarioAvaliador) {
        this.usuarioAvaliador = usuarioAvaliador;
    }

    public PedidoExibicaoDto getPedidoAula() {
        return pedidoAula;
    }

    public void setPedidoAula(PedidoExibicaoDto pedidoAula) {
        this.pedidoAula = pedidoAula;
    }
}
