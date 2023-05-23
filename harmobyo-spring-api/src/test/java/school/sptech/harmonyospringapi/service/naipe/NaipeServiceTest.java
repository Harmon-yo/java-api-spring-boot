package school.sptech.harmonyospringapi.service.naipe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Naipe;
import school.sptech.harmonyospringapi.repository.NaipeRepository;
import school.sptech.harmonyospringapi.service.exceptions.EntidadeConflitanteException;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoCriacaoDto;
import school.sptech.harmonyospringapi.service.naipe.dto.NaipeCriacaoDto;
import school.sptech.harmonyospringapi.service.naipe.dto.NaipeMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class NaipeServiceTest {
    @Mock
    private NaipeRepository naipeRepository;

    @InjectMocks
    private NaipeService naipeService;

    @Test
    public void quandoAcionadoComIdInvalidoDeveRetornarEntidadeNaoEncontradaException() {
        // Arrange
        Optional<Naipe> naipeEncontrado = naipeRepository.findById(999);

        Mockito.when(naipeRepository.findById(999)).thenReturn(naipeEncontrado);

        // Act & Assert
        assertThrows(EntitadeNaoEncontradaException.class, ()
                -> naipeService.obterNaipePorId(999));
    }

    @Test
    public void quandoCadastradoCorretamenteDeveSalvarNaRepository() {
        Naipe naipeCriado = new Naipe();
        naipeCriado.setDescricaoNaipe("Naipe teste");

        Mockito.when(naipeRepository.save(Mockito.any(Naipe.class))).thenReturn(naipeCriado);

        naipeService.cadastrar(NaipeMapper.of(naipeCriado));

        Mockito.verify(naipeRepository, times(1)).save(Mockito.any(Naipe.class));

    }



}