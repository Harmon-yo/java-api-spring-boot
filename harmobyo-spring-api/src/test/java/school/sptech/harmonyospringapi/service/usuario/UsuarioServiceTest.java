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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.harmonyospringapi.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Endereco;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.repository.AlunoRepository;
import school.sptech.harmonyospringapi.repository.ProfessorRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.endereco.EnderecoService;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.harmonyospringapi.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private  static EnderecoService enderecoService;

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
        //given

        //when
        Mockito.when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());
        //then
        List<UsuarioExibicaoDto> listaUsuarioExibicaoDtos = usuarioService.listarCadastrados();
        //assert
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
    void quandoAutenticarVerificarHouveAutenticacao(){
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
    void lancarExcecaoQuandoAutenticarComEmailInvalido(){
        UsuarioLoginDto dto = new UsuarioLoginDto();
        dto.setEmail("professor@gmail.com");
        dto.setSenha("1234");

        Mockito.when(usuarioRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioService.autenticar(dto));

        assertEquals("Email de usuário não cadastrado", exception.getReason());
    }

    @Test
    void quandoDeletarEnderecoComIdInvalidoForAcionadoDeveRetornarException() {
        int idUsuario = 999;

        Mockito.when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        assertThrows(EntitadeNaoEncontradaException.class, () -> usuarioService.deletarEndereco(idUsuario));
    }
    @Test
    void quandoDeletarEnderecoComIdValidoForAcionadoDeveExcluirEndereco() {
        int idUsuario = 1;

        Usuario usuario = new Professor();
        usuario.setId(idUsuario);
        Endereco endereco = new Endereco();
        endereco.setId(1);
        usuario.setEndereco(endereco);

        Mockito.when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));

        usuarioService.deletarEndereco(idUsuario);

        Mockito.verify(usuarioRepository).findById(idUsuario);
        assertNull(usuario.getEndereco());
    }


    @Test
    void exibeTodosOrdemAlfabetica() {
    }

    @Test
    void inserirEndereco() {
    }

    @Test
    void atualizarEndereco() {
    }



    @Test
    void buscarEndereco() {
    }
}
