package school.sptech.harmonyospringapi.service.aluno_instrumento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.AlunoInstrumento;
import school.sptech.harmonyospringapi.repository.AlunoInstrumentoRepository;
import school.sptech.harmonyospringapi.repository.AlunoRepository;
import school.sptech.harmonyospringapi.service.aluno_instrumento.dto.AlunoInstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.aluno_instrumento.dto.AlunoInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.usuario.AlunoService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlunoInstrumentoServiceTest {

    @InjectMocks
    private static AlunoInstrumentoService service;

    @InjectMocks
    private static AlunoService alunoService;
    @Mock
    private static AlunoRepository repository;
    @Mock
    private static AlunoInstrumentoRepository alunoInstrumentoRepository;

    @Test
    @DisplayName("Devolver lista de instrumentos de um aluno quando o id for válido e existente")
    void devolverListaDeInstrumentosDeUmAlunoQuandoIdValidoEExistente() {

        //given
        int id = 1;

        //when
        Mockito.when(alunoInstrumentoRepository.findByAluno_id(id))
                .thenReturn(List.of());
        //then
        List<AlunoInstrumentoExibicaoDto> resultado = service.obterTodos(id);

        assertEquals(0, resultado.size());

    }

    @Test
    @DisplayName("Devolver exceção quando o id do aluno não for válido")
    void devolverExcecaoQuandoIdAlunoNaoValido(){

        //given
        int id1 = -1;
        int id2 = 0;
        Integer id3 = null;

        //when
        Mockito.when(alunoInstrumentoRepository.findByAluno_id(Mockito.anyInt()))
                .thenReturn(List.of());
        //then
        EntitadeNaoEncontradaException exception1 = assertThrows(EntitadeNaoEncontradaException.class, () -> service.obterTodos(id1));
        EntitadeNaoEncontradaException exception2 = assertThrows(EntitadeNaoEncontradaException.class, () -> service.obterTodos(id2));
        EntitadeNaoEncontradaException exception3 = assertThrows(EntitadeNaoEncontradaException.class, () -> service.obterTodos(id3));


        assertEquals("Id inválido", exception1.getMessage());
        assertEquals("Id inválido", exception2.getMessage());
        assertEquals("Id inválido", exception3.getMessage());



    }

    @Test
    @DisplayName("Devolver exceção quando o id do aluno não existir")
    void devolverExcecaoQuandoIdAlunoNaoExistir(){

        //given
        int id = 1;

        //when
        Mockito.when(alunoInstrumentoRepository.findByAluno_id(id))
                .thenReturn(List.of());
        //then
        EntitadeNaoEncontradaException exception = assertThrows(EntitadeNaoEncontradaException.class, () -> service.obterTodos(id));

        assertEquals("Aluno não encontrado", exception.getMessage());

    }

    @Test
    @DisplayName("Cadastrar AlunoInstrumento quando o id do aluno for válido e AlunoInstrumentoCriacaoDto for válido")
    void cadastrarAlunoInstrumentoQuandoIdAlunoForValidoEAlunoInstrumentoCriacaoDtoForValido(){


    }



}
