package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.Aula;

import java.util.List;
import java.util.Optional;

public interface AulaRepository extends JpaRepository<Aula, Integer> {


    Optional<Aula> findFirstByProfessorIdOrderByValorAulaAsc(Integer professorId);

//    List<Aula> findAllByIdAluno(int id);

    List<Aula> findAllByProfessorId(int fkProfessor);

}
