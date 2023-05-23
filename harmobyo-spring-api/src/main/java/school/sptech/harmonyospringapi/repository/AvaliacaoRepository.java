package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.harmonyospringapi.domain.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    @Query("SELECT AVG(a.valor) AS Avaliacao_media FROM Avaliacao AS a WHERE a.usuarioAvaliado.id = :idProfessor")
    double getMediaAvaliacaoProfessor(Integer idProfessor);
}
