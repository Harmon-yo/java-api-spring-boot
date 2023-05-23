package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.harmonyospringapi.domain.AlunoInstrumento;
import school.sptech.harmonyospringapi.domain.AlunoInstrumentoKey;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumento;

import java.util.List;

public interface AlunoInstrumentoRepository extends JpaRepository<AlunoInstrumento, AlunoInstrumentoKey> {

    List<AlunoInstrumento> findByAluno_id(int id);

    @Query("SELECT i FROM Instrumento i INNER JOIN AlunoInstrumento ai ON ai.instrumento.id = i.id WHERE ai.aluno.id = :idAluno")
    List<Instrumento> listarInstrumentosPeloIdDoAluno(int idAluno);
}
