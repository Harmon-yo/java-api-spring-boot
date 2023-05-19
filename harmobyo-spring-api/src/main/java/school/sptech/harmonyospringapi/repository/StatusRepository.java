package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
