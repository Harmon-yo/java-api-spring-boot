package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.Instrumento;

public interface InstrumentoRepository extends JpaRepository<Instrumento, Integer> {
    boolean existsInstrumentoByNome(String nome);
}
