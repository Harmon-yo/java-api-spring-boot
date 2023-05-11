package school.sptech.harmonyospringapi.service.aula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.repository.AulaRepository;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;
}
