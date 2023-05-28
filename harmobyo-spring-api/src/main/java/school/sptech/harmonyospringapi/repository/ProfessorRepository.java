package school.sptech.harmonyospringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDashboardDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoHistoricoDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    @Query("SELECT p FROM Professor p LEFT JOIN Avaliacao a ON a.usuarioAvaliado.id = p.id  GROUP BY p.id ORDER BY AVG(a.valor) DESC LIMIT 50")
    List<Professor> findTop50ByOrderByAvaliacaoDesc();

    @Query("SELECT p FROM Professor p INNER JOIN Aula a" +
            " ON a.professor.id = p.id  GROUP BY p.id ORDER BY a.valorAula DESC")
    List<Professor> findTop50MaisCarosValorAula();

    @Query("SELECT p FROM Professor p INNER JOIN Aula a" +
            " ON a.professor.id = p.id GROUP BY p.id ORDER BY a.valorAula ASC")
    List<Professor> findTop50MaisBaratosValorAula();


    @Query("SELECT p FROM Professor p INNER JOIN Aula a "  +
            "ON a.professor.id = p.id WHERE a.instrumento.id = 1")
    List<Professor> getProfessoresByInstrumento(int idInstrumento);


    @Query("SELECT pi.emprestaInstrumento FROM ProfessorInstrumento pi WHERE pi.professor.id = :idProfessor AND pi.emprestaInstrumento = TRUE ORDER BY pi.emprestaInstrumento LIMIT 1")
    boolean emprestaInstrumento(Integer idProfessor);

    @Query("SELECT SUM(a.valorAula) FROM Aula a  INNER JOIN Pedido p on a.id = p.aula.id  WHERE a.professor.id = :idProfessor AND p.status.descricao = 'Concluído' AND p.dataAula BETWEEN :comeco AND :fim")
    Optional<Double> getRendimentoPorPeriodo(int idProfessor, LocalDateTime comeco, LocalDateTime fim);

    @Query("SELECT COUNT(DISTINCT (p.aluno.id)) FROM Pedido p WHERE p.professor.id = :id AND p.status.descricao = 'Concluído' AND p.dataAula BETWEEN :comeco AND :fim")
    Optional<Integer> getQuantidadeAlunosPorPeriodo(int id, LocalDateTime comeco, LocalDateTime fim);

    @Query("SELECT COUNT(p.id) FROM Pedido p WHERE p.professor.id = :id AND p.status.descricao = 'Concluído' AND p.dataAula BETWEEN :comeco AND :fim")
    Optional<Integer> getQuantidadeAulasPorPeriodo(int id, LocalDateTime comeco, LocalDateTime fim);

    @Query("SELECT AVG(EXTRACT(MINUTE FROM p.horaCriacao) - EXTRACT(MINUTE FROM p.horaResposta)) FROM Pedido p WHERE p.professor.id = :id")
    Optional<Integer> getMediaTempoResposta(int id);

    @Query("SELECT new school.sptech.harmonyospringapi.service.pedido.dto.PedidoHistoricoDto(FUNCTION('MONTHNAME', p.dataAula), (SELECT COUNT(p.id) FROM Pedido where p.professor.id = :id), COUNT(p.id)) FROM Pedido p WHERE p.professor.id = :id AND p.status.descricao = 'Concluído' GROUP BY FUNCTION('MONTHNAME', p.dataAula)")
    List<PedidoHistoricoDto> getHistoricoPedidos(int id);

    @Query("SELECT new school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDashboardDto(p.aula.instrumento.id, p.aula.instrumento.nome, COUNT(p.id), SUM(p.aula.valorAula)) FROM Pedido p WHERE p.professor.id = :id AND p.status.descricao = 'Concluído' GROUP BY p.aula.instrumento.id")
    List<PedidoExibicaoDashboardDto> getAulasRealizadasAgrupadasPorInstrumento(int id);
}
