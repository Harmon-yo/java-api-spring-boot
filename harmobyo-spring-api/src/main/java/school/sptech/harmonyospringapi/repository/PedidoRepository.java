package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.harmonyospringapi.domain.Pedido;
import school.sptech.harmonyospringapi.service.aula.dto.AulaGraficoInformacoesDashboardDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT p FROM Pedido p WHERE p.aluno.id = :usuarioId OR p.aula.professor.id = :usuarioId")
    List<Pedido> buscarPorUsuarioId(Integer usuarioId);

    @Query("SELECT p FROM Pedido p WHERE p.aula.professor.id = :idProfessor AND (p.status.descricao = 'Pendente' OR p.status.descricao = 'Aguardando Pagamento' OR p.status.descricao = 'Confirmado')")
    List<Pedido> encontrarPedidosPendentesPorIdProfessor(int idProfessor);

    @Query("SELECT new school.sptech.harmonyospringapi.service.aula.dto.AulaGraficoInformacoesDashboardDto(COUNT(*), " +
            "COUNT(CASE WHEN p.status.descricao = 'Cancelado' THEN 1 END), " +
            "COUNT(CASE WHEN p.status.descricao = 'Concluído' THEN 1 END)) " +
            "FROM Pedido p " +
            "WHERE p.professor.id = :id AND p.horaCriacao BETWEEN :comeco AND :fim")
    AulaGraficoInformacoesDashboardDto getDadosAulasPeriodoPorIdProfessor(int id, LocalDateTime comeco, LocalDateTime fim);
}
