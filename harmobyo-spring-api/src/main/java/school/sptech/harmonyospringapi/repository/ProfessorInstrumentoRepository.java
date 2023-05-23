package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumento;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumentoKey;

import java.util.List;
import java.util.Optional;

public interface ProfessorInstrumentoRepository extends JpaRepository<ProfessorInstrumento, ProfessorInstrumentoKey> {

    List<ProfessorInstrumento> findByProfessor_id(int id);

    @Query("SELECT i FROM Instrumento i INNER JOIN ProfessorInstrumento pi ON pi.instrumento.id = i.id WHERE pi.professor.id = :idProfessor")
    List<Instrumento> listarInstrumentosPeloIdDoProfessor(int idProfessor);
}
