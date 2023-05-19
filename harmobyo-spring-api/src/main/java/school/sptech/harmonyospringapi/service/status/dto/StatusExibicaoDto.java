package school.sptech.harmonyospringapi.service.status.dto;

import school.sptech.harmonyospringapi.domain.Pedido;

import java.util.List;

public class StatusExibicaoDto {

    private Integer id;

    private List<Pedido> pedidos;

    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
