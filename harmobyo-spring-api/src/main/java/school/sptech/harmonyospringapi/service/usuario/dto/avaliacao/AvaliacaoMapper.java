package school.sptech.harmonyospringapi.service.usuario.dto.avaliacao;

import school.sptech.harmonyospringapi.domain.Avaliacao;
import school.sptech.harmonyospringapi.domain.AvaliacaoKey;
import school.sptech.harmonyospringapi.domain.Pedido;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoMapper;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

public class AvaliacaoMapper {

    public static Avaliacao of(AvaliacaoCriacaoDto avaliacaoCriacaoDto, Usuario usuarioAvaliado,
                               Usuario usuarioAvaliador, Pedido pedido) {

        AvaliacaoKey avaliacaoKey = new AvaliacaoKey();
        avaliacaoKey.setUsuarioAvaliadoFk(usuarioAvaliado.getId());
        avaliacaoKey.setUsuarioAvaliadorFk(usuarioAvaliador.getId());
        avaliacaoKey.setPedido(pedido.getId());

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(avaliacaoKey);
        avaliacao.setValor(avaliacaoCriacaoDto.getValor());
        avaliacao.setComentario(avaliacaoCriacaoDto.getComentario());
        avaliacao.setUsuarioAvaliado(usuarioAvaliado);
        avaliacao.setDataAvaliacao(avaliacaoCriacaoDto.getDataAvaliacao());
        avaliacao.setUsuarioAvaliador(usuarioAvaliador);
        avaliacao.setPedidoAula(pedido);

        return avaliacao;
    }

    public static AvaliacaoExibicaoDto ofAvaliacaoExibicao(Avaliacao avaliacao) {
        AvaliacaoExibicaoDto avaliacaoExibicaoDto = new AvaliacaoExibicaoDto();

        avaliacaoExibicaoDto.setId(avaliacao.getId());
        avaliacaoExibicaoDto.setValor(avaliacao.getValor());
        avaliacaoExibicaoDto.setComentario(avaliacao.getComentario());
        avaliacaoExibicaoDto.setUsuarioAvaliado(UsuarioMapper.ofUsuarioExibicao(avaliacao.getUsuarioAvaliado()));
        avaliacaoExibicaoDto.setDataAvaliacao(avaliacao.getDataAvaliacao());
        avaliacaoExibicaoDto.setUsuarioAvaliador(UsuarioMapper.ofUsuarioExibicao(avaliacao.getUsuarioAvaliador()));
        avaliacaoExibicaoDto.setPedidoAula(PedidoMapper.ofPedidoExibicaoDto(avaliacao.getPedidoAula()));

        return avaliacaoExibicaoDto;
    }

}