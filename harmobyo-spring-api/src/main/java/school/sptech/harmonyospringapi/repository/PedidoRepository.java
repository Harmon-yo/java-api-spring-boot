package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.harmonyospringapi.domain.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT p FROM Pedido p WHERE p.aluno.id = :usuarioId OR p.aula.professor.id = :usuarioId")
    List<Pedido> buscarPorUsuarioId(Integer usuarioId);

    @Query("SELECT p FROM Pedido p WHERE p.aula.professor.id = :idProfessor AND (p.status.descricao = 'Pendente' OR p.status.descricao = 'Aguardando Pagamento' OR p.status.descricao = 'Confirmado')")
    List<Pedido> encontrarPedidosPendentesPorIdProfessor(int idProfessor);
}
