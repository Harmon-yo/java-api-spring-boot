package school.sptech.harmonyospringapi.service.pedido;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Pedido;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.repository.AlunoRepository;
import school.sptech.harmonyospringapi.repository.PedidoRepository;
import school.sptech.harmonyospringapi.repository.ProfessorRepository;
import school.sptech.harmonyospringapi.repository.StatusRepository;
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

    @DisplayName("Deve retornar 3 quando houver 3 pedidos")
    @Test
    void retornaTresQuandoHouverTresPedidos(){

        Pedido pedido = new Pedido();
        Aluno aluno = new Aluno();
        pedido.setAluno(aluno);
        List<Pedido> pedidos = List.of(
                pedido,
                pedido,
                pedido
        );

        Mockito.when(repository.findAll())
                .thenReturn(pedidos);

        List<PedidoExibicaoDto> pedidosRetorno = service.obterTodos();

        assertEquals(pedidos.size(), pedidosRetorno.size());

    }
}