package school.sptech.harmonyospringapi.service.usuario;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.harmonyospringapi.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.harmonyospringapi.domain.*;
import school.sptech.harmonyospringapi.repository.*;
import school.sptech.harmonyospringapi.service.endereco.EnderecoService;
import school.sptech.harmonyospringapi.service.endereco.dto.EnderecoAtualizacaoDto;
import school.sptech.harmonyospringapi.service.endereco.dto.EnderecoExibicaoDto;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.pedido.PedidoService;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.avaliacao.AvaliacaoMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock
    private static UsuarioRepository usuarioRepository;

    @Mock
    private static AlunoRepository alunoRepository;

    @Mock
    private static ProfessorRepository professorRepository;

    @Mock
    private static PedidoRepository pedidoRepository;

    @Mock
    private static AvaliacaoRepository avaliacaoRepository;

    @Mock
    private static EnderecoService enderecoService;

    @Mock
    private static PedidoService pedidoService;

    @InjectMocks
    private static UsuarioService usuarioService;

    @Mock
    private static PasswordEncoder passwordEncoder;

    @Mock
    private static GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private static AuthenticationManager authenticationManager;

    @DisplayName("Retornar lista vazia quando não houver usuários cadastrados")
    @Test
    void quandoAcionadoDeveRetornarListaVazia() {
        Mockito.when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<UsuarioExibicaoDto> listaUsuarioExibicaoDtos = usuarioService.listarCadastrados();

        assertEquals(0, listaUsuarioExibicaoDtos.size());
    }

    @DisplayName("Retornar lista com 6 usuários cadastrados")
    @Test
    void quandoAcionadoDeveRetornar6Usuarios() {
        Professor usuario1 = new Professor();
        Professor usuario2 = new Professor();
        Professor usuario3 = new Professor();
        Aluno aluno1 = new Aluno();
        Aluno aluno2 = new Aluno();
        Aluno aluno3 = new Aluno();

        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2, usuario3, aluno1, aluno2, aluno3);
        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioExibicaoDto> listaUsuarioExibicaoDtos = usuarioService.listarCadastrados();

        assertEquals(6, listaUsuarioExibicaoDtos.size());
    }

    @DisplayName("Autenticar usuário e vericar se o processo de autenticação foi feito")
    @Test
    void quandoAutenticarVerificarHouveAutenticacao() {
        Professor professor = new Professor();
        professor.setEmail("professor@gmail.com");
        professor.setSenha("1234");

        UsuarioLoginDto dto = new UsuarioLoginDto();
        dto.setEmail("professor@gmail.com");
        dto.setSenha("1234");

        Mockito.when(usuarioRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(professor));

        usuarioService.autenticar(dto);

        Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(Mockito.any());
    }

    @DisplayName("Lançar exceção quando tentar autenticar com email inválido")
    @Test
    void lancarExcecaoQuandoAutenticarComEmailInvalido() {
        UsuarioLoginDto dto = new UsuarioLoginDto();
        dto.setEmail("professor@gmail.com");
        dto.setSenha("1234");

        Mockito.when(usuarioRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioService.autenticar(dto));

        assertEquals("Email de usuário não cadastrado", exception.getReason());
    }

    @DisplayName("Quando exibeTodosOrdemAlfabética for acionado deve retorna lista vazia")
    @Test
    void quandoExibeTodosOrdemAlfabeticaForAcionadoDeveRetornarListaVazia() {
        Mockito.when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<UsuarioExibicaoDto> listaUsuarioExibicaoDtos = usuarioService.exibeTodosOrdemAlfabetica();

        assertTrue(listaUsuarioExibicaoDtos.isEmpty());
    }

    @DisplayName("Deve retornar lista ordenada quando acionar exibeTodosOrdemAlfabetica")
    @Test
    void deveRetornarListaOrdenadaQuandoAcionarExibeTodosOrdemAlfabetica() {
        List<Usuario> usuarios = new ArrayList<>();
        List<Usuario> comparacao = new ArrayList<>();

        Professor professor1 = new Professor();
        professor1.setNome("Manoel");

        Professor professor2 = new Professor();
        professor2.setNome("Wesley");

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Maria");

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Amanda");

        usuarios.add(professor1);
        usuarios.add(professor2);
        usuarios.add(aluno1);
        usuarios.add(aluno2);

        comparacao.add(aluno2);
        comparacao.add(professor1);
        comparacao.add(aluno1);
        comparacao.add(professor2);

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioExibicaoDto> resultado = usuarioService.exibeTodosOrdemAlfabetica();

        assertEquals(comparacao.size(), resultado.size());
        assertEquals(comparacao.get(0).getNome(), resultado.get(0).getNome());
        assertEquals(comparacao.get(1).getNome(), resultado.get(1).getNome());
        assertEquals(comparacao.get(2).getNome(), resultado.get(2).getNome());
        assertEquals(comparacao.get(3).getNome(), resultado.get(3).getNome());
    }

    @DisplayName("Retornar verdadeiro quando acionado existeUsuarioPorEmail")
    @Test
    void retornarTrueQuandoAcionadoExisteUsuarioPorEmail() {
        Aluno aluno = new Aluno();
        aluno.setEmail("email@gmail.com");

        Mockito.when(usuarioRepository.existsByEmail(aluno.getEmail()))
                .thenReturn(true);

        boolean resultado = usuarioRepository.existsByEmail(aluno.getEmail());

        assertTrue(resultado);
    }

    @DisplayName("Retornar falso quando email não existir e for acionado existeUsuarioPorEmail")
    @Test
    void retornarFalseQuandoEmailNaoExistirQuandoAcionadoExisteUsuarioPorEmail() {

        boolean resultado = usuarioRepository.existsByEmail("teste@gmail.com");

        assertFalse(resultado);
    }

    @DisplayName("Retornar verdadeiro quando acionado existeUsuarioPorCpf")
    @Test
    void retornarTrueQuandoAcionadoExisteUsuarioPorCpf() {
        Aluno aluno = new Aluno();
        aluno.setCpf("25238181854");

        Mockito.when(usuarioRepository.existsByCpf(aluno.getCpf()))
                .thenReturn(true);

        boolean resultado = usuarioRepository.existsByCpf(aluno.getCpf());

        assertTrue(resultado);
    }

    @DisplayName("Retornar falso quando cpf não existir e for acionado existeUsuarioPorCpf")
    @Test
    void retornarFalseQuandoCpfNaoExistirQuandoAcionadoExisteUsuarioPorCpf() {

        boolean resultado = usuarioRepository.existsByCpf("64584252578");

        assertFalse(resultado);
    }

    @DisplayName("Retornar usuário quando buscarPorId com Id válido")
    @Test
    void retornarUsuarioQuandoBuscarPorIdComIdValido() {
        Aluno aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("Vitor");
        aluno.setEmail("vitor@gmail.com");
        aluno.setCpf("32391393945");

        Mockito.when(usuarioRepository.findById(aluno.getId()))
                .thenReturn(Optional.of(aluno));

        Usuario resultado = usuarioService.buscarPorId(aluno.getId());

        assertNotNull(resultado);
        assertEquals(aluno.getNome(), resultado.getNome());
        assertEquals(aluno.getEmail(), resultado.getEmail());
        assertEquals(aluno.getCpf(), resultado.getCpf());
    }

    @DisplayName("Lançar exceção quando buscarPorId com Id inválido")
    @Test
    void lancarExcecaoQuandoBuscarPorIdComIdInvalido() {
        int idUsuario = 999;

        EntitadeNaoEncontradaException exception = assertThrows(EntitadeNaoEncontradaException.class,
                () -> usuarioService.buscarPorId(idUsuario));

        assertEquals("Aluno não encontrado", exception.getMessage());
    }

    @DisplayName("Deve inserir endereço no usuário quando dados forem válidos")
    @Test
    void deveInserirEnderecoNoUsuarioQuandoDadosForemValidos() {
        String cep = "09074039";
        int id = 1;

        Professor professor = new Professor();
        professor.setId(id);

        Endereco endereco = new Endereco();
        endereco.setId(id);
        endereco.setCep(cep);

        Mockito.when(usuarioRepository.findById(professor.getId()))
                .thenReturn(Optional.of(professor));
        Mockito.when(enderecoService.cadastrarEndereco(Mockito.any(Endereco.class)))
                .thenReturn(endereco);

        UsuarioExibicaoDto resultado = usuarioService.inserirEndereco(professor.getId(), endereco);

        assertNotNull(resultado);
        assertEquals(endereco, resultado.getEndereco());
        assertEquals(id, resultado.getEndereco().getId());
        assertEquals(cep, resultado.getEndereco().getCep());
    }

    @DisplayName("retornar endereço no usuário quando dados forem válidos")
    @Test
    void lancarExcecaoQuandoInserirEnderecoComIdInvalido() {
        int id = 999;

        Endereco endereco = new Endereco();
        endereco.setId(id);

        EntitadeNaoEncontradaException exception = assertThrows(EntitadeNaoEncontradaException.class,
                () -> usuarioService.inserirEndereco(id, endereco));

        assertEquals("Usuario não encontrado", exception.getMessage());
    }

    @DisplayName("Deve atualizar o endereco do usuario caso os dados forem válidos")
    @Test
    void deveAtualizarEnderecoQuandoDadosForemValidos() {
        int id = 1;
        String cidade = "São Paulo";
        String cidadeNovo = "Santo André";

        Endereco endereco = new Endereco();
        endereco.setId(id);
        endereco.setCidade(cidade);

        Professor professor = new Professor();
        professor.setId(id);
        professor.setEndereco(endereco);

        EnderecoAtualizacaoDto dto = new EnderecoAtualizacaoDto();
        dto.setCidade(cidadeNovo);

        endereco.setCidade(cidadeNovo);

        Mockito.when(usuarioRepository.findById(professor.getId()))
                .thenReturn(Optional.of(professor));
        Mockito.when(enderecoService.atualizarEndereco(Mockito.any(Endereco.class)))
                .thenReturn(endereco);

        UsuarioExibicaoDto resultado = usuarioService.atualizarEndereco(id, dto);

        assertNotNull(resultado);
        assertEquals(cidadeNovo, resultado.getEndereco().getCidade());
    }

    @DisplayName("Quando deletarEndereco tiver Id inválido deve retornar exception")
    @Test
    void quandoDeletarEnderecoComIdInvalidoForAcionadoDeveRetornarException() {
        int idUsuario = 999;

        Mockito.when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.empty());

        assertThrows(EntitadeNaoEncontradaException.class,
                () -> usuarioService.deletarEndereco(idUsuario));
    }

    @DisplayName("Quando deletarEndereco tiver Id válido deve excluir endereço")
    @Test
    void quandoDeletarEnderecoComIdValidoForAcionadoDeveExcluirEndereco() {
        int idUsuario = 1;

        Usuario usuario = new Professor();
        usuario.setId(idUsuario);
        Endereco endereco = new Endereco();
        endereco.setId(1);
        usuario.setEndereco(endereco);

        Mockito.when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(usuario));

        usuarioService.deletarEndereco(idUsuario);

        Mockito.verify(usuarioRepository).findById(idUsuario);
        assertNull(usuario.getEndereco());
    }

    @DisplayName("Retornar endereço quando acionado buscarEndereco com Id válido")
    @Test
    void retornarEnderecoQuandoAcionadoBuscarEnderecoComIdValido() {
        String cidade = "São Paulo";
        int id = 1;

        Endereco endereco = new Endereco();
        endereco.setId(id);
        endereco.setCep(cidade);

        Professor professor = new Professor();
        professor.setId(id);
        professor.setEndereco(endereco);

        Mockito.when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(professor));

        EnderecoExibicaoDto resultado = usuarioService.buscarEndereco(id);

        assertNotNull(resultado);
        assertEquals(professor.getEndereco().getCidade(), resultado.getCidade());
    }

    @DisplayName("Lançar exceção quando acionado buscarEndereco com Id inválido")
    @Test
    void lancarExcecaoQuandoAcionadoBuscarEnderecoComIdInvalido() {
        int id = 999;

        Mockito.when(usuarioRepository.findById(id))
                .thenReturn(Optional.empty());
        EntitadeNaoEncontradaException exception = assertThrows(EntitadeNaoEncontradaException.class,
                () -> usuarioService.buscarEndereco(id));

        assertEquals("Aluno não encontrado", exception.getMessage());
    }

    @DisplayName("Deve criar uma avaliação quando acionado criarAvaliacao com dados válidos")
    @Test
    void deveCriarUmaAvaliacaoQuandoAcionadoCriarAvaliacaoComDadosValidos() {
        int idGeral = 1;
        int idProfessor = 2;
        int idAluno = 3;

        Aluno aluno = new Aluno();
        aluno.setId(idAluno);

        Professor professor = new Professor();
        professor.setId(idProfessor);

        Naipe naipe = new Naipe();
        naipe.setId(idGeral);

        Instrumento instrumento = new Instrumento();
        instrumento.setId(idGeral);
        instrumento.setNaipe(naipe);

        Aula aula = new Aula();
        aula.setId(idGeral);
        aula.setProfessor(professor);
        aula.setInstrumento(instrumento);

        Status status = new Status();
        status.setId(idGeral);
        status.setDescricao("Concluído");

        Pedido pedido = new Pedido();
        pedido.setId(idGeral);
        pedido.setAluno(aluno);
        pedido.setProfessor(professor);
        pedido.setAula(aula);
        pedido.setStatus(status);

        AvaliacaoCriacaoDto dto = new AvaliacaoCriacaoDto();
        dto.setUsuarioAvaliadorId(aluno.getId());
        dto.setPedidoId(pedido.getId());
        dto.setValor(4.5);
        dto.setComentario("Muito boa a aula!");

        Avaliacao avaliacao = AvaliacaoMapper.of(dto, professor, aluno, pedido);

        Mockito.when(usuarioRepository.findById(idProfessor))
                .thenReturn(Optional.of(professor));
        Mockito.when(usuarioRepository.findById(idAluno))
                .thenReturn(Optional.of(aluno));
        Mockito.when(pedidoService.buscarPorId(idGeral))
                .thenReturn(pedido);
        Mockito.when(avaliacaoRepository.save(Mockito.any(Avaliacao.class)))
                .thenReturn(avaliacao);

        AvaliacaoExibicaoDto resultado = usuarioService.criarAvaliacao(idProfessor, dto);

        assertNotNull(resultado);
        assertEquals(aluno.getId(), resultado.getUsuarioAvaliador().getId());
        assertEquals(pedido.getId(), resultado.getPedidoAula().getId());
        assertEquals("Muito boa a aula!", resultado.getComentario());
        assertEquals(4.5, resultado.getValor());
    }

    @DisplayName("Lançar exceção quando acionado criarAvaliacao e o Id do aluno for igual ao Id do Professor")
    @Test
    void lancarExcecaoQuandoAcionadoCriarAvaliacaoComIdAlunoIgualIdProfessor() {
        int idGeral = 1;
        int idProfessor = 2;
        int idAluno = 2;

        Aluno aluno = new Aluno();
        aluno.setId(idAluno);

        Professor professor = new Professor();
        professor.setId(idProfessor);

        Naipe naipe = new Naipe();
        naipe.setId(idGeral);

        Instrumento instrumento = new Instrumento();
        instrumento.setId(idGeral);
        instrumento.setNaipe(naipe);

        Aula aula = new Aula();
        aula.setId(idGeral);
        aula.setProfessor(professor);
        aula.setInstrumento(instrumento);

        Status status = new Status();
        status.setId(idGeral);
        status.setDescricao("Concluído");

        Pedido pedido = new Pedido();
        pedido.setId(idGeral);
        pedido.setAluno(aluno);
        pedido.setProfessor(professor);
        pedido.setAula(aula);
        pedido.setStatus(status);

        AvaliacaoCriacaoDto dto = new AvaliacaoCriacaoDto();
        dto.setUsuarioAvaliadorId(aluno.getId());
        dto.setPedidoId(pedido.getId());
        dto.setValor(4.5);
        dto.setComentario("Muito boa a aula!");

        Mockito.when(usuarioRepository.findById(idProfessor))
                .thenReturn(Optional.of(professor));
        Mockito.when(usuarioRepository.findById(idAluno))
                .thenReturn(Optional.of(aluno));
        Mockito.when(pedidoService.buscarPorId(idGeral))
                .thenReturn(pedido);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioService.criarAvaliacao(idProfessor, dto));

        assertEquals("Não é possível avaliar a si mesmo", exception.getReason());
    }

    @DisplayName("Lançar exceção quando acionado criarAvaliacao e o Id do aluno for igual ao Id do Professor")
    @Test
    void lancarExcecaoQuandoAcionadoCriarAvaliacaoComIdAlunoIgualIdProfessor() {
        int idGeral = 1;
        int idProfessor = 2;
        int idAluno = 2;

        Aluno aluno = new Aluno();
        aluno.setId(idAluno);

        Professor professor = new Professor();
        professor.setId(idProfessor);

        Naipe naipe = new Naipe();
        naipe.setId(idGeral);

        Instrumento instrumento = new Instrumento();
        instrumento.setId(idGeral);
        instrumento.setNaipe(naipe);

        Aula aula = new Aula();
        aula.setId(idGeral);
        aula.setProfessor(professor);
        aula.setInstrumento(instrumento);

        Status status = new Status();
        status.setId(idGeral);
        status.setDescricao("Concluído");

        Pedido pedido = new Pedido();
        pedido.setId(idGeral);
        pedido.setAluno(aluno);
        pedido.setProfessor(professor);
        pedido.setAula(aula);
        pedido.setStatus(status);

        AvaliacaoCriacaoDto dto = new AvaliacaoCriacaoDto();
        dto.setUsuarioAvaliadorId(aluno.getId());
        dto.setPedidoId(pedido.getId());
        dto.setValor(4.5);
        dto.setComentario("Muito boa a aula!");

        Mockito.when(usuarioRepository.findById(idProfessor))
                .thenReturn(Optional.of(professor));
        Mockito.when(usuarioRepository.findById(idAluno))
                .thenReturn(Optional.of(aluno));
        Mockito.when(pedidoService.buscarPorId(idGeral))
                .thenReturn(pedido);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioService.criarAvaliacao(idProfessor, dto));

        assertEquals("Não é possível avaliar a si mesmo", exception.getReason());
    }
}