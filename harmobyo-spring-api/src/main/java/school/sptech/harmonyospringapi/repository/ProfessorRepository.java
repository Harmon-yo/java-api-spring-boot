package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.harmonyospringapi.domain.Professor;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    @Query("SELECT p FROM Professor p JOIN Avaliacao a ON a.usuarioAvaliado.id = p.id ORDER BY p.id DESC LIMIT 50")
    List<Professor> findTop50ByOrderByAvaliacaoDesc();

    @Query("SELECT pi.emprestaInstrumento FROM ProfessorInstrumento pi WHERE pi.professor.id = :idProfessor AND pi.emprestaInstrumento = TRUE ORDER BY pi.emprestaInstrumento LIMIT 1")
    Optional<Boolean> emprestaInstrumento(Integer idProfessor);
}
