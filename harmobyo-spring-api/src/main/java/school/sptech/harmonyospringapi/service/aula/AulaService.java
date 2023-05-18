package school.sptech.harmonyospringapi.service.aula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.repository.AulaRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


}
