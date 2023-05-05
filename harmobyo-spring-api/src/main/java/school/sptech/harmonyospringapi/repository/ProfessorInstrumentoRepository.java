package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumento;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumentoKey;

public interface ProfessorInstrumentoRepository extends JpaRepository<ProfessorInstrumento, ProfessorInstrumentoKey> {
}
