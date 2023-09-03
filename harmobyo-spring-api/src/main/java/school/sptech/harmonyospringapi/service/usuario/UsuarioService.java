package school.sptech.harmonyospringapi.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.harmonyospringapi.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.harmonyospringapi.domain.*;
import school.sptech.harmonyospringapi.repository.*;
import school.sptech.harmonyospringapi.service.endereco.dto.EnderecoAtualizacaoDto;
import school.sptech.harmonyospringapi.service.experiencia.dto.ExperienciaMapper;
import school.sptech.harmonyospringapi.service.experiencia.dto.ExperienciaExibicaoDto;
import school.sptech.harmonyospringapi.service.pedido.PedidoService;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioAtulizarDadosPessoaisDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioDadosPerfilDto;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoMapper;
import school.sptech.harmonyospringapi.utils.ListaGenericaObj;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.endereco.EnderecoService;
import school.sptech.harmonyospringapi.service.endereco.dto.EnderecoExibicaoDto;
import school.sptech.harmonyospringapi.service.endereco.dto.EnderecoMapper;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ExperienciaRepository experienciaRepository;

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


    public List<UsuarioExibicaoDto> listarCadastrados() {

        List<Usuario> ltUsuarios = this.usuarioRepository.findAll();

        return ltUsuarios.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }


    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {

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

        return UsuarioMapper.of(usuarioAutenticado, token);

    }

    public List<UsuarioExibicaoDto> exibeTodosOrdemAlfabetica() {

        List<Usuario> ltUsuarios = this.usuarioRepository.findAll();


        ListaGenericaObj<Usuario> ltUsuariosGenerica = new ListaGenericaObj<>(ltUsuarios.size());

        ltUsuarios.forEach(ltUsuariosGenerica::adiciona);


        ltUsuariosGenerica = new UsuarioComparador(ltUsuariosGenerica).ordenacaoAlfabetica();

        ltUsuarios.clear();

        for (int i = 0; i < ltUsuariosGenerica.size(); i++) {
            ltUsuarios.add(ltUsuariosGenerica.getElemento(i));
        }

        return ltUsuarios.stream().map(UsuarioMapper::ofUsuarioExibicao).toList();
    }

    /* ================ PESQUISA ================ */

    public boolean existeUsuarioPorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public boolean existeUsuarioPorCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuário não encontrado")
        );
    }

    /* ================ ENDEREÇO ================ */

    public UsuarioExibicaoDto inserirEndereco(Integer idUsuario, Endereco endereco) {
        Endereco enderecoInserido = enderecoService.cadastrarEndereco(endereco);
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuário não encontrado")
        );
        usuario.setEndereco(enderecoInserido);
        usuarioRepository.save(usuario);
        return UsuarioMapper.ofUsuarioExibicao(usuario);
    }

    public UsuarioExibicaoDto atualizarEndereco(Integer idUsuario, EnderecoAtualizacaoDto enderecoAtualizacaoDto) {
        Usuario usuario = buscarPorId(idUsuario);

        Endereco endereco = EnderecoMapper.of(enderecoAtualizacaoDto);
        endereco.setId(usuario.getEndereco().getId());

        Endereco enderecoInserido = this.enderecoService.atualizarEndereco(endereco);
        usuario.setEndereco(enderecoInserido);

        this.usuarioRepository.save(usuario);
        return UsuarioMapper.ofUsuarioExibicao(usuario);
    }

    public void deletarEndereco(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Usuário não encontrado")
        );
        usuario.setEndereco(null);
        usuarioRepository.save(usuario);
        Endereco endereco = usuario.getEndereco();
        enderecoService.deletarEndereco(endereco);
    }

    public EnderecoExibicaoDto buscarEndereco(Integer idUsuario) {
        Usuario usuario = buscarPorId(idUsuario);

        return EnderecoMapper.ofExibicaoDto(usuario.getEndereco());
    }

    /* ================ AVALIAÇÃO ================ */

    public AvaliacaoExibicaoDto criarAvaliacao(Integer idAvaliado, AvaliacaoCriacaoDto avaliacaoCriacaoDto) {
        Usuario receptor = buscarPorId(idAvaliado);
        Usuario autor = buscarPorId(avaliacaoCriacaoDto.getUsuarioAvaliadorId());
        Pedido pedido = pedidoService.buscarPorId(avaliacaoCriacaoDto.getPedidoId());



        if (receptor.getId().equals(autor.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível avaliar a si mesmo");
        } else if (!Objects.equals(pedido.getStatus().getDescricao(), "Concluído")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não foi concluído");
        } else if (avaliacaoRepository.existsAvaliacaoByPedidoId(pedido.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido já foi avaliado");
        } else if ((!pedido.getAluno().getId().equals(receptor.getId())) && (!pedido.getProfessor().getId().equals(receptor.getId()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não pertence ao usuário receptor");
        } else if (receptor instanceof Aluno) {
            if (autor instanceof Aluno) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno não pode avaliar outro aluno");
            } else if ((!pedido.getAluno().getId().equals(autor.getId())) && (!pedido.getProfessor().getId().equals(autor.getId()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não pertence ao usuário autor");
            }
        } else if (receptor instanceof Professor) {
            if (autor instanceof Professor) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Professor não pode avaliar outro professor");
            } else if ((!pedido.getAluno().getId().equals(autor.getId())) && (!pedido.getProfessor().getId().equals(autor.getId()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não pertence ao usuário autor");
            }
        }
        System.out.println("Avaliação criada");
        Avaliacao avaliacao = AvaliacaoMapper.of(avaliacaoCriacaoDto, receptor, autor, pedido);

        Avaliacao avaliacaoCadastrada = this.avaliacaoRepository.save(avaliacao);
        System.out.println("Avaliação autor: " + avaliacaoCadastrada.getUsuarioAvaliador().getNome());
        System.out.println("Avaliação receptor: " + avaliacaoCadastrada.getUsuarioAvaliado().getNome());
        System.out.println("Avaliação pedido: " + avaliacaoCadastrada.getPedidoAula().getId());
        System.out.println("Avaliação cadastrada");
        return AvaliacaoMapper.ofAvaliacaoExibicao(avaliacaoCadastrada);
    }

    public List<AvaliacaoExibicaoDto> listarAvaliacoesPorUsuario(Integer idUsuario) {
        Usuario usuario = buscarPorId(idUsuario);

        List<Avaliacao> avaliacoes = this.avaliacaoRepository.findByUsuarioAvaliadoId(usuario.getId());

        return avaliacoes.stream().map(AvaliacaoMapper::ofAvaliacaoExibicao).toList();
    }

    public UsuarioDadosPerfilDto obterDadosPerfilUsuario(int id) {

        Optional<Usuario> usuario = this.usuarioRepository.findById(id);

        if (usuario.isPresent()) {

            Usuario usuarioEncontrado = usuario.get();

            List<ExperienciaExibicaoDto> experiencias = new ArrayList<>();

            Double avaliacaoMedia = this.avaliacaoRepository.getMediaAvaliacaoUsuario(id).orElse(0.0);

            if (usuarioEncontrado instanceof Professor){
                experiencias = this.experienciaRepository.findAllByProfessorId(id).stream().map(ExperienciaMapper::of).toList();
            }

            String categoriaUsuario = usuarioEncontrado instanceof Professor ? "Professor": "Aluno";

            return UsuarioMapper.ofDadosPerfilUsuario(usuarioEncontrado, experiencias, avaliacaoMedia, categoriaUsuario);

        }

        throw new EntitadeNaoEncontradaException("ID de Usuário invalido. Usuário não encontrado !");
    }

    /* ================ UTILIDADE ===============*/

    public FiltroMinimoMaximo filtroMinimoMaximo() {
        Double precoMinimo = this.usuarioRepository.obterPrecoMinimo();
        Double precoMaximo = this.usuarioRepository.obterPrecoMaximo();

        FiltroMinimoMaximo filtroMinimoMaximo = new FiltroMinimoMaximo();

        filtroMinimoMaximo.setPrecoMinimo(precoMinimo);
        filtroMinimoMaximo.setPrecoMaximo(precoMaximo);
        filtroMinimoMaximo.setDistanciaMinima(0.0);
        filtroMinimoMaximo.setDistanciaMaxima(100.0);

        return filtroMinimoMaximo;
    }

    /* ================ ATUALIZAR DADOS PESSOAIS ===============*/

    public void atualizarDadosPessoais(int id, UsuarioAtulizarDadosPessoaisDto dadosUsuario){

         if (usuarioRepository.existsById(id)){
             usuarioRepository.atualizarDadosPessoais(id, dadosUsuario.getNome(), dadosUsuario.getEmail(), dadosUsuario.getDataNasc(), dadosUsuario.getSexo());
         }
         else {
             throw new EntitadeNaoEncontradaException("ID de Usuário Inválido!");
         }
    }

    public void atualizarBibliografia(int id, String bibliografia){
        if (usuarioRepository.existsById(id)){
            this.usuarioRepository.atualizarBibliografia(id, bibliografia);
        }
        else {
            throw new EntitadeNaoEncontradaException("ID de Usuário Inválido!");
        }
    }
}
