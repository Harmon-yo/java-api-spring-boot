package school.sptech.harmonyospringapi.service.usuario;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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

    @InjectMocks
    private  static EnderecoService enderecoService;

    @InjectMocks
    private static UsuarioService usuarioService;

    @Mock
    private static PasswordEncoder passwordEncoder;

    @Mock
    private static GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private static AuthenticationManager authenticationManager;

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

    //TODO pedir ajuda test Autenticar

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
