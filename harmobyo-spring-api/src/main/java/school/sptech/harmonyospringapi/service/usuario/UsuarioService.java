package school.sptech.harmonyospringapi.service.usuario;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.harmonyospringapi.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.harmonyospringapi.domain.Avaliacao;
import school.sptech.harmonyospringapi.domain.Endereco;
import school.sptech.harmonyospringapi.domain.Pedido;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.repository.AvaliacaoRepository;
import school.sptech.harmonyospringapi.repository.PedidoRepository;
import school.sptech.harmonyospringapi.service.pedido.PedidoService;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoMapper;
import school.sptech.harmonyospringapi.utils.ListaGenericaObj;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.endereco.EnderecoService;
import school.sptech.harmonyospringapi.service.endereco.dto.EnderecoExibicaoDto;
import school.sptech.harmonyospringapi.service.endereco.dto.EnderecoMapper;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    @Lazy
    private PedidoService pedidoService;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;


    public List<UsuarioExibicaoDto> listarCadastrados(){

        List<Usuario> ltUsuarios = this.usuarioRepository.findAll();

        return ltUsuarios.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }


    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto){

        final UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(
                        usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha()
                );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                usuarioRepository.findByEmail(usuarioLoginDto.getEmail())
                                .orElseThrow(
                                        () -> new ResponseStatusException(404, "Email de usuário não cadastrado", null)
                                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado,token);

    }

    public List<UsuarioExibicaoDto> exibeTodosOrdemAlfabetica(){

        List<Usuario> ltUsuarios = this.usuarioRepository.findAll();

        ListaGenericaObj<Usuario> ltUsuariosGenerica = new ListaGenericaObj<>(ltUsuarios.size());

        ltUsuarios.forEach(ltUsuariosGenerica::adiciona);

        ltUsuariosGenerica = new UsuarioComparador(ltUsuariosGenerica).ordenacaoAlfabetica();

        ltUsuarios.clear();

        for (int i = 0; i < ltUsuariosGenerica.size(); i ++){
            ltUsuarios.add(ltUsuariosGenerica.getElemento(i));
        }

        return ltUsuarios.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    /* ================ PESQUISA ================ */

    public boolean existeUsuarioPorEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public boolean existeUsuarioPorCpf(String cpf){
        return usuarioRepository.existsByCpf(cpf);
    }

    public Usuario buscarPorId(Integer id){
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Aluno não encontrado")
        );
    }

    /* ================ ENDEREÇO ================ */

    public UsuarioExibicaoDto inserirEndereco(Integer idUsuario, Endereco endereco ){
        Endereco enderecoInserido = enderecoService.cadastrarEndereco(endereco);
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuario não encontrado")
        );
        usuario.setEndereco(enderecoInserido);
        usuarioRepository.save(usuario);
        return UsuarioMapper.ofUsuarioExibicao(usuario);
    }

    public UsuarioExibicaoDto atualizarEndereco(Integer idUsuario, Endereco endereco ){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuario não encontrado")
        );

        endereco.setId(usuario.getEndereco().getId());
        Endereco enderecoInserido = enderecoService.atualizarEndereco(endereco);
        usuario.setEndereco(enderecoInserido);

        usuarioRepository.save(usuario);
        return UsuarioMapper.ofUsuarioExibicao(usuario);

    }

    public void deletarEndereco(Integer idUsuario ){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuario não encontrado")
        );
        usuario.setEndereco(null);
        usuarioRepository.save(usuario);
        Endereco endereco = usuario.getEndereco();
        enderecoService.deletarEndereco(endereco);
    }

    public EnderecoExibicaoDto buscarEndereco(Integer idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuario não encontrado")
        );
        return EnderecoMapper.of(usuario.getEndereco());
    }

    /* ================ AVALIAÇÃO ================ */

    public AvaliacaoExibicaoDto criarAvaliacao(Integer idAvaliado, AvaliacaoCriacaoDto avaliacaoCriacaoDto) {
        Usuario avaliado = buscarPorId(idAvaliado);
        Usuario avaliador = buscarPorId(avaliacaoCriacaoDto.getUsuarioAvaliadorId());
        Pedido pedido = pedidoService.buscarPorId(avaliacaoCriacaoDto.getPedidoId());

        Avaliacao avaliacao = AvaliacaoMapper.of(avaliacaoCriacaoDto, avaliado, avaliador, pedido);

        Avaliacao avaliacaoCadastrada = this.avaliacaoRepository.save(avaliacao);

        return AvaliacaoMapper.ofAvaliacaoExibicao(avaliacaoCadastrada);
    }

    public List<AvaliacaoExibicaoDto> listarAvaliacoesPorUsuario(Integer idUsuario) {
        List<Avaliacao> avaliacoes = this.avaliacaoRepository.findAllById_UsuarioAvaliadoFk(idUsuario);

        return avaliacoes.stream().map(AvaliacaoMapper::ofAvaliacaoExibicao).toList();
    }
}
