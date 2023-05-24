package school.sptech.harmonyospringapi.service.pedido;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.harmonyospringapi.repository.*;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private AlunoRepository alunoRepository;
    @Mock
    private AulaRepository aulaRepository;
    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private PedidoService service;


    @DisplayName("Deve retornar 0 quando não houver pedidos")
    @Test
    void retornaZeroQuandoNaoHouverPedido(){

        Mockito.when(repository.findAll())
                .thenReturn(List.of());

        List<PedidoExibicaoDto> pedidos = service.obterTodos();

        assertEquals(0, pedidos.size());

    }
/*
   @DisplayName("Criar pedido quando PedidoCriacaoDto for válido")
    @Test
    void criarPedidoQuandoPedidoCriacaoDtoForValido(){

            PedidoCriacaoDto pedidoCriacaoDto = new PedidoCriacaoDto();
            pedidoCriacaoDto.setAlunoId(1);
            pedidoCriacaoDto.setProfessorId(1);
            pedidoCriacaoDto.setStatusId(1);

            Aluno aluno = new Aluno();
            aluno.setId(1);

            Professor professor = new Professor();
            professor.setId(1);

            Status status = new Status();
            status.setId(1);

            Pedido novoPedido = new Pedido();
            Integer integer = new Integer();
            integer.setAlunoFk(1);
            integer.setProfessorFk(1);
            novoPedido.setId(integer);
            novoPedido.setAluno(aluno);
            novoPedido.setProfessor(professor);
            novoPedido.setStatus(status);

            Aula aula = new Aula();
            Instrumento instrumento = new Instrumento();
            Naipe naipe = new Naipe();
            naipe.setId(1);
            instrumento.setNaipe(naipe);
            AulaKey aulaKey = new AulaKey();
            aulaKey.setUsuarioFk(1);
            aulaKey.setInstrumentoFk(2);
            instrumento.setId(2);
            aula.setId(aulaKey);
            aula.setUsuario(aluno);
            aula.setInstrumento(instrumento);
            novoPedido.setAula(aula);


            Mockito.when(alunoRepository.findById(1)).thenReturn(java.util.Optional.of(aluno));
            Mockito.when(professorRepository.findById(1)).thenReturn(java.util.Optional.of(professor));
            Mockito.when(statusRepository.findById(1)).thenReturn(java.util.Optional.of(status));
            Mockito.when(aulaRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(aula));
            Mockito.when(repository.save(Mockito.any(Pedido.class))).thenReturn(novoPedido);

            PedidoExibicaoDto resultado = service.criar(pedidoCriacaoDto);

            assertEquals(integer, resultado.getId());
            assertEquals(1, resultado.getAluno().getId());
            assertEquals(1, resultado.getProfessor().getId());
            assertEquals(1, resultado.getStatus().getId());

        }

    @DisplayName("Deve lançar exceção quando aluno não existir")
    @Test
    void deveLancarExcecaoQuandoAlunoNaoExistir(){

        PedidoCriacaoDto pedidoCriacaoDto = new PedidoCriacaoDto();
        pedidoCriacaoDto.setAlunoId(1);
        pedidoCriacaoDto.setProfessorId(1);
        pedidoCriacaoDto.setStatusId(1);

        Mockito.when(alunoRepository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(EntitadeNaoEncontradaException.class, () -> service.criar(pedidoCriacaoDto));

    }

    @DisplayName("Deve lançar exceção quando professor não existir")
    @Test
    void deveLancarExcecaoQuandoProfessorNaoExistir(){

        PedidoCriacaoDto pedidoCriacaoDto = new PedidoCriacaoDto();
        pedidoCriacaoDto.setAlunoId(1);
        pedidoCriacaoDto.setProfessorId(1);
        pedidoCriacaoDto.setStatusId(1);

        Aluno aluno = new Aluno();
        aluno.setId(1);

        Mockito.when(alunoRepository.findById(1)).thenReturn(java.util.Optional.of(aluno));
        Mockito.when(professorRepository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(EntitadeNaoEncontradaException.class, () -> service.criar(pedidoCriacaoDto));

    }

    @DisplayName("Deve lançar exceção quando status não existir")
    @Test
    void deveLancarExcecaoQuandoStatusNaoExistir(){

        PedidoCriacaoDto pedidoCriacaoDto = new PedidoCriacaoDto();
        pedidoCriacaoDto.setAlunoId(1);
        pedidoCriacaoDto.setProfessorId(1);
        pedidoCriacaoDto.setStatusId(1);

        Aluno aluno = new Aluno();
        aluno.setId(1);

        Professor professor = new Professor();
        professor.setId(1);

        Mockito.when(alunoRepository.findById(1)).thenReturn(java.util.Optional.of(aluno));
        Mockito.when(professorRepository.findById(1)).thenReturn(java.util.Optional.of(professor));
        Mockito.when(statusRepository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(EntitadeNaoEncontradaException.class, () -> service.criar(pedidoCriacaoDto));

    }
    */

}