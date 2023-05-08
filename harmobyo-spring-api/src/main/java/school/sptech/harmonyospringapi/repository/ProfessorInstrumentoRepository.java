package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumento;
import school.sptech.harmonyospringapi.domain.ProfessorInstrumentoKey;

import java.util.List;
import java.util.Optional;

public interface ProfessorInstrumentoRepository extends JpaRepository<ProfessorInstrumento, ProfessorInstrumentoKey> {

    List<ProfessorInstrumento> findByProfessor_id(int id);

}
