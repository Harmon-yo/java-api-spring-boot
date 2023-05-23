package school.sptech.harmonyospringapi.service.usuario;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)

class ProfessorServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;


    @InjectMocks
    private ProfessorService service;







}
