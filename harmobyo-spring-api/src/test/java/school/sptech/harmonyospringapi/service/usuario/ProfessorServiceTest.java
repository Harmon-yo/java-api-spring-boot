package school.sptech.harmonyospringapi.service.usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.repository.ProfessorRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.aluno_instrumento.dto.AlunoInstrumentoExibicaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioExibicaoDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ProfessorService service;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Quando obter todos os professores mas não há professores, deve retornar lista vazia")
    void quandoObterProfessoresDeveRetornarListaVazia(){
        //given
        List<UsuarioExibicaoDto> listaProfessores = service.obterTodosEmOrdemAlfabetica();
        //when/then
        Mockito.when(service.obterTodosEmOrdemAlfabetica()).thenReturn(listaProfessores);
        //assert
        assertEquals(0, listaProfessores.size());
    }




}