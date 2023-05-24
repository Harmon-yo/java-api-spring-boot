package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.harmonyospringapi.domain.Avaliacao;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    @Query("SELECT AVG(a.valor) AS Avaliacao_media FROM Avaliacao AS a WHERE a.usuarioAvaliado.id = :idProfessor")
    Optional<Double> getMediaAvaliacaoProfessor(Integer idProfessor);

    @Query("SELECT COUNT(a.valor) FROM Avaliacao AS a WHERE a.usuarioAvaliado.id = :id")
    Optional<Integer> getQuantidadeAvaliacoes(Integer id);

}
