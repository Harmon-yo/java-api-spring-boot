package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.domain.AulaKey;

import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, AulaKey> {

    List<Aula> findAllByIdAluno(int id);

}
