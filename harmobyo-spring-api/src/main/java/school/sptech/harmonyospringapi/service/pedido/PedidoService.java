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
import school.sptech.harmonyospringapi.service.status.StatusService;
import school.sptech.harmonyospringapi.service.usuario.AlunoService;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;
import school.sptech.harmonyospringapi.utils.PilhaObj;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        List<PedidoExibicaoDto> pedidos = aulas.stream().map(a -> PedidoMapper.ofPedidoExibicaoDto(a)).toList();
        return pedidos;
    }

    public List<PedidoExibicaoDto> buscarAulasPorIdUsuarioEMesAula(int fkUsuario, LocalDateTime localDateTime) {
        List<Pedido> aulas = repository.findAllByUsuarioIdAndAulaDataMes(fkUsuario, localDateTime);
        List<PedidoExibicaoDto> pedidos = aulas.stream().map(a -> PedidoMapper.ofPedidoExibicaoDto(a)).toList();
        return pedidos;
    }
}
