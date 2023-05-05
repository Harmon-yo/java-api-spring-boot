package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.AlunoInstrumento;
import school.sptech.harmonyospringapi.domain.AlunoInstrumentoKey;

public interface AlunoInstrumentoRepository extends JpaRepository<AlunoInstrumento, AlunoInstrumentoKey> {
}
