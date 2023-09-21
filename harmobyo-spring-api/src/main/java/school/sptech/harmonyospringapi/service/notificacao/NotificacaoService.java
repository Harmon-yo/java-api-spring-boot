package school.sptech.harmonyospringapi.service.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Notificacao;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.repository.NotificacaoRepository;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.notificacao.dto.NotificacaoCriacaoDto;
import school.sptech.harmonyospringapi.service.notificacao.dto.NotificacaoExibicaoDto;
import school.sptech.harmonyospringapi.service.notificacao.dto.NotificacaoMapper;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public NotificacaoExibicaoDto cadastrar(NotificacaoCriacaoDto notificacaoCriacaoDto) {
        Notificacao notificacao = new Notificacao();

        notificacao.setTitulo(notificacaoCriacaoDto.getTitulo());
        notificacao.setDescricao(notificacaoCriacaoDto.getDescricao());

        Usuario usuario = usuarioService.buscarPorId(notificacaoCriacaoDto.getIdUsuario());
        notificacao.setUsuario(usuario);

        notificacao.setData(LocalDateTime.now());

        return NotificacaoMapper.ofNotificacao(this.notificacaoRepository.save(notificacao));
    }

    public NotificacaoExibicaoDto criarNotificacao(String titulo, String descricao, Usuario usuario) {
        Notificacao notificacao = new Notificacao();

        notificacao.setTitulo(titulo);
        notificacao.setDescricao(descricao);

        notificacao.setUsuario(usuario);

        notificacao.setData(LocalDateTime.now());

        return NotificacaoMapper.ofNotificacao(this.notificacaoRepository.save(notificacao));
    }

    public List<NotificacaoExibicaoDto> obterTodos() {
        return this.notificacaoRepository.findAll()
                .stream()
                .map(NotificacaoMapper::ofNotificacao)
                .toList();
    }

    public Page<NotificacaoExibicaoDto> obterPorIdUsuario(Integer idUsuario, Pageable pageable) {
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        Page<Notificacao> ltNotificacao = this.notificacaoRepository.findByUsuarioId(usuario.getId(), pageable);

        return ltNotificacao.map(NotificacaoMapper::ofNotificacao);
    }

    private Notificacao obterPorId(Integer idNotificacao) {
        return this.notificacaoRepository.findById(idNotificacao).orElseThrow(() -> new EntitadeNaoEncontradaException(String.format("Notificação com o id %d não encontrada!", idNotificacao)));
    }

    public NotificacaoExibicaoDto obterPorIdNotificacaoExibicao(Integer idNotificacao) {
        return NotificacaoMapper.ofNotificacao(this.obterPorId(idNotificacao));
    }

    public void marcarComoLida(Integer idNotificacao) {
        Notificacao notificacao = this.obterPorId(idNotificacao);
        notificacao.setLida(true);

        this.notificacaoRepository.save(notificacao);
    }

    public void marcarTodasComoLida(Integer idUsuario) {
        List<Notificacao> notificacoes = this.notificacaoRepository.findByUsuarioId(idUsuario);

        notificacoes.forEach(notificacao -> notificacao.setLida(true));

        this.notificacaoRepository.saveAll(notificacoes);
    }
}
