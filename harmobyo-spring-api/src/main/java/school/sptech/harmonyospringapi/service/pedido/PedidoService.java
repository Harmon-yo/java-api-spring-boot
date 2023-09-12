package school.sptech.harmonyospringapi.service.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.*;
import school.sptech.harmonyospringapi.repository.*;
import school.sptech.harmonyospringapi.service.aula.AulaService;
import school.sptech.harmonyospringapi.service.exceptions.EntidadeConflitanteException;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoCriacaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoMapper;
import school.sptech.harmonyospringapi.service.pedido.hashing.HashTableService;
import school.sptech.harmonyospringapi.service.status.StatusService;
import school.sptech.harmonyospringapi.service.usuario.AlunoService;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;
import school.sptech.harmonyospringapi.utils.PilhaObj;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private HashTableService hashTableService;

    public List<PedidoExibicaoDto> obterTodos() {

        return this.repository.findAll()
                .stream()
                .map(PedidoMapper::ofPedidoExibicaoDto)
                .toList();
    }

    public PilhaObj<PedidoExibicaoDto> obterPedidosPendentes(int idProfessor){

        List<Pedido> pedidosPendentes = repository.encontrarPedidosPendentesPorIdProfessor(idProfessor);
        PilhaObj<PedidoExibicaoDto> pilhaPedidos = new PilhaObj<>(pedidosPendentes.size());

        pedidosPendentes.stream().map(PedidoMapper::ofPedidoExibicaoDto).forEach(pilhaPedidos::push);

        return pilhaPedidos;
    }

    public PedidoExibicaoDto criar(PedidoCriacaoDto pedidoCriacaoDto) {

        Aluno aluno = this.alunoService.buscarPorId(pedidoCriacaoDto.getAlunoId());
        Professor professor = this.professorService.buscarPorId(pedidoCriacaoDto.getProfessorId());
        Aula aula = this.aulaService.buscarPorId(pedidoCriacaoDto.getAulaId());
        Status status = this.statusService.buscarPorDescricao("Pendente");

        pedidoCriacaoDto.setDataAula(pedidoCriacaoDto.getDataAula().withSecond(0));

        return PedidoMapper.ofPedidoExibicaoDto(this.repository.save(
                PedidoMapper.of(pedidoCriacaoDto, aluno, professor, status, aula)
        ));

    }

    /* ============= PESQUISA ============== */

    public Pedido buscarPorId(Integer integer) {
        return repository.findById(integer)
                .orElseThrow(() -> new EntitadeNaoEncontradaException("Pedido não encontrado"));
    }

    public List<PedidoExibicaoDto> buscarPorUsuarioIdConfirmado(Integer id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        List<Pedido> pedidos = this.repository.buscarPorUsuarioIdConfirmado(usuario.getId());

        return pedidos.stream().map(PedidoMapper::ofPedidoExibicaoDto).toList();
    }

    /* ============= ACEITAR E RECUSAR ============== */
    public List<PedidoExibicaoDto> buscarPorUsuarioId(Integer id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        List<Pedido> pedidos = this.repository.buscarPorUsuarioId(usuario.getId());

        return pedidos.stream().map(PedidoMapper::ofPedidoExibicaoDto).toList();
    }

    public PedidoExibicaoDto buscarPorIdParaExibicao(Integer id) {
        Pedido pedido = this.buscarPorId(id);

        return PedidoMapper.ofPedidoExibicaoDto(pedido);
    }

    /* ============= ACEITAR / RECUSAR / CANCELAR / CONCLUIR ============== */
    public PedidoExibicaoDto aceitarPropostaDoAluno(Integer id) {
        Pedido pedido = this.buscarPorId(id);

        switch (pedido.getStatus().getDescricao()) {
            case "Confirmado" -> throw new EntitadeNaoEncontradaException("Pedido já confirmado");
            case "Recusado" -> throw new EntitadeNaoEncontradaException("Pedido já recusado");
            case "Cancelado" -> throw new EntitadeNaoEncontradaException("Pedido já cancelado");
            case "Aguardando Pagamento" -> throw new EntitadeNaoEncontradaException("Pedido já está aguardando pagamento");
        }

        this.hashTableService.atualizarStatusPedidoPorId(id, pedido, "Aguardando Pagamento");
        pedido = atualizarStatus(pedido, "Aguardando Pagamento");

        return PedidoMapper.ofPedidoExibicaoDto(pedido);
    }

    public PedidoExibicaoDto recusarPropostaDoAluno(Integer id) {
        Pedido pedido = this.buscarPorId(id);

        switch (pedido.getStatus().getDescricao()) {
            case "Recusado" -> throw new EntidadeConflitanteException("Pedido já recusado");
            case "Cancelado" -> throw new EntidadeConflitanteException("Pedido já cancelado");
            case "Aguardando Pagamento" ->
                    throw new EntidadeConflitanteException("Pedido já está aguardando pagamento");
        }

        this.hashTableService.atualizarStatusPedidoPorId(id, pedido, "Recusado");
        pedido = atualizarStatus(pedido, "Recusado");

        return PedidoMapper.ofPedidoExibicaoDto(pedido);
    }

    public PedidoExibicaoDto cancelarPedido(Integer id){
        Pedido pedido = this.buscarPorId(id);

        if (this.buscarPorId(id).getStatus().getDescricao().equals("Cancelado")) {
            throw new EntidadeConflitanteException("Pedido já cancelado");
        } else if (this.buscarPorId(id).getStatus().getDescricao().equals("Recusado")) {
            throw new EntidadeConflitanteException("Pedido já recusado");
        }
        this.hashTableService.atualizarStatusPedidoPorId(id, pedido, "Cancelado");
        pedido = atualizarStatus(pedido, "Cancelado");

        return PedidoMapper.ofPedidoExibicaoDto(pedido);
    }

    public PedidoExibicaoDto concluirPedidoPorId(Integer id) {
        Pedido pedido = this.buscarPorId(id);

        if (this.buscarPorId(id).getStatus().getDescricao().equals("Concluído")) {
            throw new EntidadeConflitanteException("Pedido já concluído");
        } else if (this.buscarPorId(id).getStatus().getDescricao().equals("Cancelado")) {
            throw new EntidadeConflitanteException("Pedido já cancelado");
        } else if (this.buscarPorId(id).getStatus().getDescricao().equals("Recusado")) {
            throw new EntidadeConflitanteException("Pedido já recusado");
        }

        this.hashTableService.atualizarStatusPedidoPorId(id, pedido, "Concluído");
        pedido = atualizarStatus(pedido, "Concluído");

        return PedidoMapper.ofPedidoExibicaoDto(pedido);
    }

    public Pedido atualizarStatus(Pedido pedido, String nomeStatus) {
        Status status = this.statusService.buscarPorDescricao(nomeStatus);

        pedido.setStatus(status);
        pedido.setHoraResposta(LocalDateTime.now());
//      Colocar para salvar mudança de status
        return this.repository.save(pedido);
    }

    public List<PedidoExibicaoDto> buscarAulasPorIdUsuarioEDataAula(int fkProfessor, LocalDateTime data) {
        List<Pedido> aulas = repository.findAllByUsuarioIdAndAulaData(fkProfessor, data);
        return aulas.stream().map(PedidoMapper::ofPedidoExibicaoDto).toList();
    }

    public List<PedidoExibicaoDto> buscarAulasPorIdUsuarioEMesAula(int fkUsuario, LocalDateTime localDateTime) {
        List<Pedido> aulas = repository.findAllByUsuarioIdAndAulaDataMes(fkUsuario, localDateTime);
        return aulas.stream().map(PedidoMapper::ofPedidoExibicaoDto).toList();
    }

    /* ============= METRICAS ADMIN ================ */

    public List<Integer> obterQuantidadePedidosRealizadosSemana() {
        List<Integer> valoresDaSemana = new ArrayList<>();
        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        LocalDateTime diaInicial = c.getTime().toInstant().atZone(c.getTimeZone().toZoneId()).toLocalDateTime();
        diaInicial = diaInicial.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime diaFinal = diaInicial.withHour(23).withMinute(59).withSecond(59);
        for (int i = 0; i < 7; i++) {

            valoresDaSemana.add(this.repository.obterQuantidadePedidosRealizadosDuranteDatas(diaInicial, diaFinal));
            c.add(Calendar.DATE, 1);
            diaInicial = c.getTime().toInstant().atZone(c.getTimeZone().toZoneId()).toLocalDateTime();
            diaInicial = diaInicial.withHour(0).withMinute(0).withSecond(0);
            diaFinal = diaInicial.withHour(23).withMinute(59).withSecond(59);
        }

        return valoresDaSemana;
    }

    public List<Integer> obterQuantidadePedidosPendentesSemana() {
        List<Integer> valoresDaSemana = new ArrayList<>();

        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        LocalDateTime diaInicial = c.getTime().toInstant().atZone(c.getTimeZone().toZoneId()).toLocalDateTime();

        diaInicial = diaInicial.withHour(0).withMinute(0).withSecond(0);

        LocalDateTime diaFinal = diaInicial.withHour(23).withMinute(59).withSecond(59);

        for (int i = 0; i < 7; i++) {

            valoresDaSemana.add(this.repository.obterQuantidadePedidosPendentesDuranteDatas(diaInicial, diaFinal));

            c.add(Calendar.DATE, 1);

            diaInicial = c.getTime().toInstant().atZone(c.getTimeZone().toZoneId()).toLocalDateTime();

            diaInicial = diaInicial.withHour(0).withMinute(0).withSecond(0);

            diaFinal = diaInicial.withHour(23).withMinute(59).withSecond(59);
        }

        return valoresDaSemana;
    }

    public List<Integer> obterQuantidadePedidosCanceladosSemana() {
        List<Integer> valoresDaSemana = new ArrayList<>();

        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        LocalDateTime diaInicial = c.getTime().toInstant().atZone(c.getTimeZone().toZoneId()).toLocalDateTime();

        diaInicial = diaInicial.withHour(0).withMinute(0).withSecond(0);

        LocalDateTime diaFinal = diaInicial.withHour(23).withMinute(59).withSecond(59);

        for (int i = 0; i < 7; i++) {

            valoresDaSemana.add(this.repository.obterQuantidadePedidosCanceladosDuranteDatas(diaInicial, diaFinal));

            c.add(Calendar.DATE, 1);

            diaInicial = c.getTime().toInstant().atZone(c.getTimeZone().toZoneId()).toLocalDateTime();

            diaInicial = diaInicial.withHour(0).withMinute(0).withSecond(0);

            diaFinal = diaInicial.withHour(23).withMinute(59).withSecond(59);
        }

        return valoresDaSemana;
    }

    public Integer obterQuantidadePedidosRealizadosTotalnaSemana() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        LocalDateTime diaInicial = c.getTime().toInstant().atZone(c.getTimeZone().toZoneId()).toLocalDateTime();
        diaInicial = diaInicial.withHour(0).withMinute(0).withSecond(0);

        LocalDateTime diaFinal = diaInicial.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        return this.repository.obterQuantidadePedidosRealizadosDuranteDatas(diaInicial, diaFinal);
    }

    public Integer obterQuantidadePedidosPendentesTotalnaSemana() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        LocalDateTime diaInicial = c.getTime().toInstant().atZone(c.getTimeZone().toZoneId()).toLocalDateTime();
        diaInicial = diaInicial.withHour(0).withMinute(0).withSecond(0);

        LocalDateTime diaFinal = diaInicial.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        return this.repository.obterQuantidadePedidosPendentesDuranteDatas(diaInicial, diaFinal);
    }

    public Integer obterQuantidadePedidosCanceladosTotalnaSemana() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        LocalDateTime diaInicial = c.getTime().toInstant().atZone(c.getTimeZone().toZoneId()).toLocalDateTime();
        diaInicial = diaInicial.withHour(0).withMinute(0).withSecond(0);

        c.add(Calendar.DATE, 6);

        LocalDateTime diaFinal = diaInicial.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        return this.repository.obterQuantidadePedidosCanceladosDuranteDatas(diaInicial, diaFinal);
    }
}
