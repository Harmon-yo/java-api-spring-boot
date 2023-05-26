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
import school.sptech.harmonyospringapi.service.aula.dto.AulaAtualizacaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaExibicaoDto;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @InjectMocks
    private static AulaService service;

    @Mock
    private static AulaRepository repository;


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
    void lancarExcecaoQuandoAulaIdForInvalido(){

        Aula aula = new Aula();

        aula.setId(1);

        Mockito.when(repository.findById(aula.getId())).thenReturn(Optional.empty());

        assertThrows(EntitadeNaoEncontradaException.class, () -> service.buscarPorId(aula.getId()));
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

    @DisplayName("Deve atualizar a aula quando o id for válido")
    @Test
    void atualizarAulaQuandoIdForVaildo(){
        double valor = 100.0;

        Professor professor = new Professor();
        professor.setId(1);

        Naipe naipe = new Naipe();
        naipe.setId(1);

        Instrumento instrumento = new Instrumento();
        instrumento.setId(1);
        instrumento.setNaipe(naipe);

        AulaAtualizacaoDto dto = new AulaAtualizacaoDto();
        dto.setValorAula(valor);

        Aula aula = new Aula();
        aula.setId(1);
        aula.setProfessor(professor);
        aula.setInstrumento(instrumento);
        aula.setValorAula(50.0);

        Mockito.when(repository.findById(aula.getId()))
                .thenReturn(Optional.of(aula));
        Mockito.when(repository.save(Mockito.any(Aula.class)))
                .thenReturn(aula);

        service.atualizarAulaPorId(aula.getId(), dto);

        double resultado = service.buscarPorId(aula.getId()).getValorAula();

        assertEquals(100.0, resultado);
    }

    @DisplayName("Devolver exceção quando atualizar uma aula quando id for inválido")
    @Test
    void devolverExcecaoQuandoAtualizarAulaQuandoIdForInvalido(){

        AulaAtualizacaoDto dto = new AulaAtualizacaoDto();
        dto.setValorAula(100.0);

        Mockito.when(repository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        EntitadeNaoEncontradaException exception = assertThrows(EntitadeNaoEncontradaException.class,
                () -> service.atualizarAulaPorId(Mockito.anyInt(), dto));

        assertEquals("ID de Aula Inválido. Aula não encontrada !", exception.getMessage());
    }
}

