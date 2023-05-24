package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.harmonyospringapi.domain.Avaliacao;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    @Query("SELECT AVG(a.valor) AS Avaliacao_media FROM Avaliacao AS a WHERE a.usuarioAvaliado.id = :idProfessor")
    double getMediaAvaliacaoProfessor(Integer idProfessor);

    List<Avaliacao> findAllById_UsuarioAvaliadoFk(Integer idProfessor);

    @Query("SELECT COUNT(a.valor) FROM Avaliacao AS a WHERE a.usuarioAvaliado.id = :id")
    Integer getQuantidadeAvaliacoes(Integer id);

    boolean existsAvaliacaoByIdPedido(Integer idPedido);

}
