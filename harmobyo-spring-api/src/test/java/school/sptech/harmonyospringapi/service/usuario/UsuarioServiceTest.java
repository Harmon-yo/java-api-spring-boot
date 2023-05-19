package school.sptech.harmonyospringapi.service.usuario;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.sptech.harmonyospringapi.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.repository.AlunoRepository;
import school.sptech.harmonyospringapi.repository.ProfessorRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.endereco.EnderecoService;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void autenticar() {
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
    void deletarEndereco() {
    }

    @Test
    void buscarEndereco() {
    }
}