package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.harmonyospringapi.domain.Pedido;
import school.sptech.harmonyospringapi.service.aula.dto.AulaGraficoInformacoesDashboardDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT p FROM Pedido p WHERE p.aluno.id = :usuarioId OR p.aula.professor.id = :usuarioId")
    List<Pedido> buscarPorUsuarioId(Integer usuarioId);

    @Query("SELECT p FROM Pedido p WHERE p.aula.professor.id = :idProfessor AND (p.status.descricao = 'Pendente' OR p.status.descricao = 'Aguardando Pagamento' OR p.status.descricao = 'Confirmado')")
    List<Pedido> encontrarPedidosPendentesPorIdProfessor(int idProfessor);

    @Query("SELECT new school.sptech.harmonyospringapi.service.aula.dto.AulaGraficoInformacoesDashboardDto( " +
            "SUM(CASE WHEN p.status.descricao = 'Cancelado' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN p.status.descricao = 'Recusado' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN p.status.descricao = 'Concluído' THEN 1 ELSE 0 END))" +
            "FROM Pedido p " +
            "WHERE p.professor.id = :id AND p.horaCriacao BETWEEN :comeco AND :fim AND p.status.descricao IN ('Cancelado', 'Recusado', 'Concluído') ")
    AulaGraficoInformacoesDashboardDto getDadosAulasPeriodoPorIdProfessor(int id, LocalDateTime comeco, LocalDateTime fim);

    @Query(value = "SELECT p from Pedido p where (p.professor.id = :fkUsuario or p.aluno.id = :fkUsuario) and (p.status.descricao = 'Confirmado' or p.status.descricao = 'Concluído')  and CAST(p.dataAula AS DATE ) = CAST(:data AS DATE)")
    List<Pedido> findAllByUsuarioIdAndAulaData(int fkUsuario, LocalDateTime data);

    @Query(value = "SELECT p FROM Pedido p WHERE (p.professor.id = :usuarioId OR p.aluno.id = :usuarioId) AND p.status.descricao = 'Confirmado'")
    List<Pedido> buscarPorUsuarioIdConfirmado(Integer usuarioId);

    @Query(value = "SELECT p from Pedido p where (p.professor.id = :fkUsuario or p.aluno.id = :fkUsuario) and (p.status.descricao = 'Confirmado' or p.status.descricao = 'Concluído') and  MONTH(CAST(p.dataAula as DATE)) = MONTH(CAST(:localDateTime AS DATE))")
    List<Pedido> findAllByUsuarioIdAndAulaDataMes(int fkUsuario, LocalDateTime localDateTime);
}
