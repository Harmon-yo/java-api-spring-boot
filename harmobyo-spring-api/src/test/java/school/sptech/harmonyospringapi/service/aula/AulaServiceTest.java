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
import school.sptech.harmonyospringapi.repository.ProfessorRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.aula.dto.AulaCriacaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaExibicaoDto;
import school.sptech.harmonyospringapi.service.instrumento.InstrumentoService;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.usuario.dto.professor_instrumento.ProfessorInstrumentoExibicaoDto;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @InjectMocks
    private static AulaService service;

    @InjectMocks
    private static UsuarioService usuarioService;

    @Mock
    private static AulaRepository repository;
    @Mock
    private static InstrumentoRepository instrumentoRepository;
    @Mock
    private static UsuarioRepository usuarioRepository;
    @Mock
    private static ProfessorRepository professorRepository;

    @DisplayName("Deve retornar uma lista vazia quando não existir aulas")
    @Test
    void devolverListaVaziaQuandoNaoExistirAulas() {
      //when
        when(repository.findAll())
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
        Professor professor = new Professor();
        professor.setId(1);

        Instrumento instrumento = new Instrumento();
        instrumento.setId(1);

        Naipe naipe = new Naipe();
        naipe.setId(1);
        instrumento.setNaipe(naipe);

        aula.setProfessor(professor);
        aula.setInstrumento(instrumento);
        aulas.add(aula);
        aulas.add(aula);
        aulas.add(aula);

        //when
        when(repository.findAll())
                .thenReturn(aulas);

        //then
        List<AulaExibicaoDto> aulasExibicao = service.obterTodos();
        assertEquals(3, aulasExibicao.size());
    }


    @DisplayName("Deve criar uma aula quando AulaCriacaoDto for válido")
    @Test
    void criarUmaAulaQuandoAulaCriacaoDtoForValido(){

        int id = 1;
        double valor = 100.00;

        AulaCriacaoDto dto = new AulaCriacaoDto();
        dto.setValorAula(valor);
        dto.setInstrumentoId(id);
        dto.setProfessorId(id);

        Professor professor = new Professor();
        professor.setId(id);
        Instrumento instrumento = new Instrumento();
        instrumento.setId(id);
        instrumento.setNome("teclado");
        Naipe naipe = new Naipe();
        naipe.setDescricao("cordas");
        instrumento.setNaipe(naipe);

        Aula aula = new Aula();
        aula.setId(id);
        aula.setInstrumento(instrumento);
        aula.setProfessor(professor);
        aula.setValorAula(valor);

        Mockito.when(repository.save(Mockito.any(Aula.class)))
                .thenReturn(aula);

        Aula save = repository.save(aula);

        assertEquals(id, save.getId());

    }

    @DisplayName("Deve lançar uma exceção quando AulaId for inválido")
    @Test
    void lancarExcecaoQuandoAulaKeyIdForInvalido(){

        Aula aula = new Aula();

        aula.setId(1);

        Mockito.when(repository.findById(aula.getId())).thenReturn(Optional.empty());

        assertThrows(EntitadeNaoEncontradaException.class, () -> service.buscarPorId(aula.getId()) );
    }

    @DisplayName("Retornar Aula quando AulaId for válido")
    @Test
    void retornarAulaQuandoAulaIdForValido(){

        Aula aula = new Aula();

        aula.setId(1);

        Mockito.when(repository.findById(aula.getId())).thenReturn(Optional.of(aula));

        Aula aulaRetornada = service.buscarPorId(aula.getId());
        assertEquals(aula, aulaRetornada);
    }

    @DisplayName("Retornar uma lista vazia quando não existe aulas para determinado professor")
    @Test
    void devolverListaVaziaQuandoNaoExistirAulasParaDeterminadoProfessor(){
        List<Aula> aulas = new ArrayList<>();
        Aula aula = new Aula();
        Professor professor = new Professor();
        professor.setId(1);

        Instrumento instrumento = new Instrumento();
        instrumento.setId(1);

        Naipe naipe = new Naipe();
        naipe.setId(1);
        instrumento.setNaipe(naipe);

        aula.setProfessor(professor);
        aula.setInstrumento(instrumento);
        aulas.add(aula);
        aulas.add(aula);
        aulas.add(aula);

        Professor professorNovo = new Professor();
        professorNovo.setId(2);

        when(repository.findAll())
                .thenReturn(List.of());

        when(repository.findAll())
                .thenReturn(aulas);

        List<AulaExibicaoDto> aulasExibicaoGeral = service.obterTodos();
        List<AulaExibicaoDto> aulasExibicaoFiltro = service.buscarAulasPorIdProfessor(professorNovo.getId());

        assertTrue(aulasExibicaoFiltro.isEmpty());
        assertEquals(3, aulasExibicaoGeral.size());
    }

    @DisplayName("Retornar uma lista com 2 aulas para determinado professor")
    @Test
    void devolverListaCom2AulasParaDeterminadoProfessor(){
        List<Aula> aulas = new ArrayList<>();
        Aula aula = new Aula();
        Professor professor = new Professor();
        professor.setId(1);

        Instrumento instrumento = new Instrumento();
        instrumento.setId(1);

        Naipe naipe = new Naipe();
        naipe.setId(1);
        instrumento.setNaipe(naipe);

        aula.setProfessor(professor);
        aula.setInstrumento(instrumento);
        aulas.add(aula);
        aulas.add(aula);
        aulas.add(aula);

        Professor professorNovo = new Professor();
        professorNovo.setId(2);

        List<Aula> aulasFilto = new ArrayList<>();

        Aula aulaFiltro = new Aula();
        aulaFiltro.setProfessor(professorNovo);
        aulaFiltro.setInstrumento(instrumento);

        aulas.add(aulaFiltro);
        aulas.add(aulaFiltro);

        aulasFilto.add(aulaFiltro);
        aulasFilto.add(aulaFiltro);


        when(repository.findAll())
                .thenReturn(aulas);

        when(repository.findAllByProfessorId(professorNovo.getId()))
                .thenReturn(aulasFilto);

        List<AulaExibicaoDto> aulasExibicaoGeral = service.obterTodos();
        List<AulaExibicaoDto> aulasExibicaoFiltro = service.buscarAulasPorIdProfessor(professorNovo.getId());

        assertEquals(5, aulasExibicaoGeral.size());
        assertEquals(2, aulasExibicaoFiltro.size());
    }

    @DisplayName("Deve atualizar a aula quando os dados forem válidos")
    @Test
    void atualizarAulaQuandoDadosForemValidos(){
        /*double valor = 100.0;
        double valorNovo = 150.0;

        Professor professor = new Professor();
        professor.setId(1);

        Instrumento instrumento = new Instrumento();
        instrumento.setId(1);

        Naipe naipe = new Naipe();
        naipe.setId(1);
        instrumento.setNaipe(naipe);

        Aula aula = new Aula();
        aula.setValorAula(valor);
        aula.setProfessor(professor);
        aula.setInstrumento(instrumento);

        Aula aulaAtualizada = new Aula();
        aulaAtualizada.setValorAula(valorNovo);
        aulaAtualizada.setProfessor(professor);
        aulaAtualizada.setInstrumento(instrumento);

        Mockito.when(repository.findById(aula.getId()))
                .thenReturn(Optional.of(aula));

        AulaExibicaoDto aulaAtualizada = service.atualizarAulaPorId(aula.getId(), )*/
    }

    @DisplayName("Devolver exceção quando atualizar uma aula com dados inválidos")
    @Test
    void devolverExcecaoQuandoAtualizarAulaComDadosInvalidos(){

    }

    @DisplayName("Deve deletar a aula quando o id da aula for válido")
    @Test
    void deletarAulaQuandoInformarAulaIdValido(){

    }

    @DisplayName("Devolver exceção quando deletar a aula com o id da aula inválido")
    @Test
    void devolverExcecaoQuandoDeletarAulaComAulaIdInvalido(){

    }
}
