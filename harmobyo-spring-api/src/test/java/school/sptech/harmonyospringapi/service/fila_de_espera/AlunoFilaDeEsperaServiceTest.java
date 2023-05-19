package school.sptech.harmonyospringapi.service.fila_de_espera;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.exceptions.FilaVaziaException;
import school.sptech.harmonyospringapi.service.fila_de_espera.dto.AlunoFilaDeEsperaDTO;
import school.sptech.harmonyospringapi.utils.FilaObj;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlunoFilaDeEsperaServiceTest {

    @Mock
    AlunoFilaDeEsperaDTO alunoDTO;

    @Mock
    FilaObj<AlunoFilaDeEsperaDTO> fila;

    @InjectMocks
    AlunoFilaDeEsperaService service;
    @Test
    public void quandoAcionadoOPrimeiroAlunoDaFilaDeveEstarInteressadoEmTeclado() {
        // Arrange
        AlunoFilaDeEsperaDTO alunoParaFila1 = new AlunoFilaDeEsperaDTO(
                1, "joaozinho", "teclado"
        );

        AlunoFilaDeEsperaDTO alunoParaFila2 = new AlunoFilaDeEsperaDTO(
                1, "joaozinho", "violao"
        );

        AlunoFilaDeEsperaDTO alunoParaFila3 = new AlunoFilaDeEsperaDTO(
                1, "maria", "xilofone"
        );

        service.getFilaEsperaAluno().insert(alunoParaFila1);
        service.getFilaEsperaAluno().insert(alunoParaFila2);
        service.getFilaEsperaAluno().insert(alunoParaFila3);

        assertEquals("teclado", service.pollAluno().getAlunoInstrumento());
    }

    @Test
    public void quandoAcionadoDeveRetornarFilaVaziaException() {
        FilaObj<AlunoFilaDeEsperaDTO> filaMock = Mockito.mock(FilaObj.class);
        AlunoFilaDeEsperaService service = new AlunoFilaDeEsperaService();

        service.filaAluno1 = filaMock;

        Mockito.when(filaMock.isEmpty()).thenReturn(true);

        assertThrows(FilaVaziaException.class, () -> service.pollAluno());
    }

}