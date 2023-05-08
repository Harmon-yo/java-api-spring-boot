package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.AlunoInstrumento;
import school.sptech.harmonyospringapi.domain.AlunoInstrumentoKey;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumento;

import java.util.List;

public interface AlunoInstrumentoRepository extends JpaRepository<AlunoInstrumento, AlunoInstrumentoKey> {

    List<AlunoInstrumento> findByAluno_id(int id);
}
