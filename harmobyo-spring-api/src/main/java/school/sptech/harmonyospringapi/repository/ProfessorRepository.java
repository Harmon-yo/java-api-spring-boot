package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.harmonyospringapi.domain.Professor;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    @Query("SELECT p FROM Professor p LEFT JOIN Avaliacao a ON a.usuarioAvaliado.id = p.id  GROUP BY p.id ORDER BY AVG(a.valor) DESC LIMIT 50")
    List<Professor> findTop50ByOrderByAvaliacaoDesc();

    @Query("SELECT p FROM Professor p INNER JOIN Aula a" +
            " ON a.professor.id = p.id  GROUP BY p.id ORDER BY a.valorAula DESC")
    List<Professor> findTop50MaisCarosValorAula();

    @Query("SELECT p FROM Professor p INNER JOIN Aula a" +
            " ON a.professor.id = p.id GROUP BY p.id ORDER BY a.valorAula ASC")
    List<Professor> findTop50MaisBaratosValorAula();


    @Query("SELECT p FROM Professor p INNER JOIN Aula a "  +
            "ON a.professor.id = p.id WHERE a.instrumento.id = 1")
    List<Professor> getProfessoresByInstrumento(int idInstrumento);


    @Query("SELECT pi.emprestaInstrumento FROM ProfessorInstrumento pi WHERE pi.professor.id = :idProfessor AND pi.emprestaInstrumento = TRUE ORDER BY pi.emprestaInstrumento LIMIT 1")
    boolean emprestaInstrumento(Integer idProfessor);
}
