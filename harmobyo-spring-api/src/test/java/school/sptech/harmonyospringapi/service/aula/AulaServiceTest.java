package school.sptech.harmonyospringapi.service.aula;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.harmonyospringapi.domain.*;
import school.sptech.harmonyospringapi.repository.AulaRepository;
import school.sptech.harmonyospringapi.repository.InstrumentoRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.aula.dto.AulaCriacaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaExibicaoDto;
import school.sptech.harmonyospringapi.service.instrumento.InstrumentoService;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @InjectMocks
    private static AulaService service;

    @InjectMocks
    private static UsuarioService usuarioService;

   @Mock
   private static InstrumentoRepository instrumentoRepository;

    @Mock
    private static AulaRepository repository;

    @Mock
    private static UsuarioRepository usuarioRepository;

    @DisplayName("Deve retornar uma lista vazia quando não existir aulas")
    @Test
    void devolverListaVaziaQuandoNaoExistirAulas() {
      //when
        Mockito.when(repository.findAll())
                .thenReturn(List.of());

        //then
        List<AulaExibicaoDto> aulas = service.obterTodos();
        assertTrue(aulas.isEmpty());
    }

    @DisplayName("Deve retornar uma lista com 3 aulas quando existirem 3 aulas")
    @Test
    void devolver3AulasQuandoExistirem3Aulas() {

        List<Aula> aulas = new ArrayList<>();
        Aula aula = new Aula();
        Aluno aluno = new Aluno();
        aluno.setId(1);

        Instrumento instrumento = new Instrumento();
        instrumento.setId(1);

        Naipe naipe = new Naipe();
        naipe.setId(1);
        instrumento.setNaipe(naipe);

        aula.setUsuario(aluno);
        aula.setInstrumento(instrumento);
        aulas.add(aula);
        aulas.add(aula);
        aulas.add(aula);

        //when
        Mockito.when(repository.findAll())
                .thenReturn(aulas);

        //then
        List<AulaExibicaoDto> aulasExibicao = service.obterTodos();
        assertEquals(3, aulasExibicao.size());
    }


    @DisplayName("Deve criar uma aula quando AulaCriacaoDto for válido")
    @Test
    void criarUmaAulaQuandoAulaCriacaoDtoForValido(){

        }
}
