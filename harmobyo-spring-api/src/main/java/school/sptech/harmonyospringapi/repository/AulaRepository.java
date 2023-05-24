package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.domain.AulaKey;

import java.util.List;
import java.util.Optional;

public interface AulaRepository extends JpaRepository<Aula, AulaKey> {


    Optional<Aula> findFirstByIdProfessorFkOrderByValorAulaAsc(Integer professorId);

//    List<Aula> findAllByIdAluno(int id);

    List<Aula> findAllByIdProfessorFk(int fkProfessor);

}
