package school.sptech.harmonyospringapi.service.professor_instrumento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import school.sptech.harmonyospringapi.domain.*;
import school.sptech.harmonyospringapi.repository.InstrumentoRepository;
import school.sptech.harmonyospringapi.repository.ProfessorInstrumentoRepository;
import school.sptech.harmonyospringapi.repository.ProfessorRepository;
import school.sptech.harmonyospringapi.service.aluno_instrumento.dto.AlunoInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.aluno_instrumento.dto.AlunoInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.instrumento.InstrumentoService;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.professor_instrumento.dto.ProfessorInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProfessorInstrumentoServiceTest {

    @Mock
    private ProfessorInstrumentoRepository professorInstrumentoRepository;

    @InjectMocks
    private ProfessorInstrumentoService professorInstrumentoService;

    @Mock
    private InstrumentoRepository instrumentoRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @Test
    void quandoAcionadoComIdInvalidoDeveRetornarIllegalStateException(){
        //given
        int id1 = -1;
        int id2 = 0;
        Integer id3 = null;

        //when
        Mockito.when(professorInstrumentoRepository.findByProfessor_id(Mockito.anyInt()))
                .thenReturn(List.of());
        //then
        IllegalStateException exception1 = assertThrows(IllegalStateException.class, ()
                -> professorInstrumentoService.obterTodos(id1));
        IllegalStateException exception2 = assertThrows(IllegalStateException.class, ()
                -> professorInstrumentoService.obterTodos(id2));
        IllegalStateException exception3 = assertThrows(IllegalStateException.class, ()
                -> professorInstrumentoService.obterTodos(id3));

    }

    @Test
    void quandoAcionadoComIdNaoExistenteDeveRetornarEntidadeNaoEncontradaException(){
        //given
        int id1 = 99;
        //when
        Mockito.when(professorInstrumentoRepository.findByProfessor_id(Mockito.anyInt()))
                .thenReturn(List.of());
        //then
        EntitadeNaoEncontradaException exception1 = assertThrows(EntitadeNaoEncontradaException.class, ()
                -> professorInstrumentoService.obterTodos(id1));

    }

    @Test
    void quandoAcionadoDeveCadastrarProfessorInstrumentoCorretamente(){
        int id = 1;
        ProfessorInstrumentoCriacaoDto dto = new ProfessorInstrumentoCriacaoDto();
        dto.setInstrumentoId(id);
        dto.setNivelConhecimento("avancado");

        Professor professor = new Professor();
        professor.setId(id);

        Instrumento instrumento = new Instrumento();
        instrumento.setNome("teclado");
        Naipe naipe = new Naipe();
        naipe.setDescricaoNaipe("cordas");
        instrumento.setNaipe(naipe);

        ProfessorInstrumento professorInstrumento = new ProfessorInstrumento();
        professorInstrumento.setProfessor(professor);
        professorInstrumento.setInstrumento(instrumento);

        ProfessorInstrumentoKey professorInstrumentoKey = new ProfessorInstrumentoKey();
        professorInstrumentoKey.setProfessorFk(professor.getId());
        //when
        Mockito.when(professorRepository.findById(id))
                .thenReturn(Optional.of(professor));

        Mockito.when(instrumentoRepository.findById(id))
                .thenReturn(Optional.of(instrumento));

        Mockito.when(professorInstrumentoRepository.save(Mockito.any(ProfessorInstrumento.class)))
                .thenReturn(professorInstrumento);

        //then
        ProfessorInstrumentoExibicaoDto resultado = professorInstrumentoService.criar(professor.getId(), dto);

        assertEquals(id, professorInstrumentoKey.getProfessorFk());
        assertDoesNotThrow(() -> professorInstrumentoService.criar(professor.getId(), dto));
    }



}