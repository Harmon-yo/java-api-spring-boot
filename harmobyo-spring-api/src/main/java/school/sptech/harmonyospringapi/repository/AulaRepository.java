package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.domain.AulaKey;

public interface AulaRepository extends JpaRepository<Aula, AulaKey> {
}
