package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.harmonyospringapi.domain.Avaliacao;
import school.sptech.harmonyospringapi.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
