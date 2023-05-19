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
    private NaipeRepository naipeRepository;

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

        Instrumento novoInstrumento = new Instrumento();
        novoInstrumento.setId(1);
        novoInstrumento.setNome("Violão");
        novoInstrumento.setNaipe(naipe);

        Mockito.when(instrumentoRepository.existsInstrumentoByNomeIgnoreCase("Violão")).thenReturn(false);
        Mockito.when(naipeRepository.findById(1)).thenReturn(Optional.of(naipe));
        Mockito.when(instrumentoRepository.save(Mockito.any(Instrumento.class))).thenReturn(novoInstrumento);

        // Act
        InstrumentoExibicaoDto resultado = instrumentoService.cadastrar(instrumentoCriacaoDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Violão", resultado.getNome());
        assertEquals(1L, resultado.getNaipe());

        Mockito.verify(instrumentoRepository, times(1)).existsInstrumentoByNomeIgnoreCase("Violão");
        Mockito.verify(naipeRepository, times(1)).findById(1);
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
        Mockito.verify(naipeRepository, never()).findById(anyInt());
        Mockito.verify(instrumentoRepository, never()).save(Mockito.any(Instrumento.class));
    }
}