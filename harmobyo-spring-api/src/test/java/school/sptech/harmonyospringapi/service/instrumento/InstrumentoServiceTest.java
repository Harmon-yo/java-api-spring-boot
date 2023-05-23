package school.sptech.harmonyospringapi.service.instrumento;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Naipe;
import school.sptech.harmonyospringapi.repository.InstrumentoRepository;
import school.sptech.harmonyospringapi.repository.NaipeRepository;
import school.sptech.harmonyospringapi.service.exceptions.EntidadeConflitanteException;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.naipe.NaipeService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class InstrumentoServiceTest {

    @Mock
    private InstrumentoRepository instrumentoRepository;

    @Mock
    private NaipeService naipeService;

    @InjectMocks
    private InstrumentoService instrumentoService;

    @Test
    public void quandoAcionadoInstrumentoDeveSerCadastradoCorretamente() {
        // Arrange
        InstrumentoCriacaoDto instrumentoCriacaoDto = new InstrumentoCriacaoDto();
        instrumentoCriacaoDto.setNome("Violão");
        instrumentoCriacaoDto.setNaipe(1);

        Naipe naipe = new Naipe();
        naipe.setId(1);
        naipe.setDescricao("Cordas");

        Instrumento novoInstrumento = new Instrumento();
        novoInstrumento.setId(1);
        novoInstrumento.setNome("Violão");
        novoInstrumento.setNaipe(naipe);

        Mockito.when(instrumentoRepository.existsInstrumentoByNomeIgnoreCase("Violão")).thenReturn(false);
        Mockito.when(instrumentoRepository.save(Mockito.any(Instrumento.class))).thenReturn(novoInstrumento);
        Mockito.when(naipeService.buscarPorId(anyInt())).thenReturn(naipe);


        // Act
        InstrumentoExibicaoDto resultado = instrumentoService.cadastrar(instrumentoCriacaoDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Violão", resultado.getNome());
        assertEquals(1, resultado.getNaipe().getId());

        Mockito.verify(instrumentoRepository, times(1)).existsInstrumentoByNomeIgnoreCase("Violão");
        Mockito.verify(naipeService, times(1)).buscarPorId(Mockito.anyInt());
        Mockito.verify(instrumentoRepository, times(1)).save(Mockito.any(Instrumento.class));
    }

    @Test
    public void quandoAcionadoDeveRetornarEntidadeConflitanteException() {
        // Arrange
        InstrumentoCriacaoDto instrumentoCriacaoDto = new InstrumentoCriacaoDto();
        instrumentoCriacaoDto.setNome("Violão");
        instrumentoCriacaoDto.setNaipe(1);

        Mockito.when(instrumentoRepository.existsInstrumentoByNomeIgnoreCase("Violão")).thenReturn(true);

        // Act & Assert
        assertThrows(EntidadeConflitanteException.class, () -> instrumentoService.cadastrar(instrumentoCriacaoDto));

        Mockito.verify(instrumentoRepository, times(1)).existsInstrumentoByNomeIgnoreCase("Violão");
        Mockito.verify(naipeService, never()).buscarPorId(anyInt());
        Mockito.verify(instrumentoRepository, never()).save(Mockito.any(Instrumento.class));
    }
}