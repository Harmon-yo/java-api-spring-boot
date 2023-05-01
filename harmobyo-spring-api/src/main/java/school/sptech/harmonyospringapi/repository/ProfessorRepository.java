package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.harmonyospringapi.domain.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

}
