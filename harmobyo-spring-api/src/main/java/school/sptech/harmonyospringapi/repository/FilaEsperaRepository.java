package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.harmonyospringapi.domain.FilaEspera;
import school.sptech.harmonyospringapi.domain.FilaEsperaKey;

import java.util.List;

public interface FilaEsperaRepository extends JpaRepository<FilaEspera, FilaEsperaKey> {

    @Query("SELECT f FROM FilaEspera f WHERE f.id.aula.professor.id = ?1")
    List<FilaEspera> findAllByIdProfessor(int idProfessor);

    @Query("SELECT f FROM FilaEspera f WHERE f.id.aluno.id = ?1 AND f.id.aula.professor.id = ?2")
    FilaEspera findByIdAlunoIdAndProfessorId(int idAluno, int idProfessor);


}
