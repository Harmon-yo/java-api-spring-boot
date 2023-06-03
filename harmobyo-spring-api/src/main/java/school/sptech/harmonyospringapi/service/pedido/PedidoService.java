package school.sptech.harmonyospringapi.service.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.*;
import school.sptech.harmonyospringapi.repository.*;
import school.sptech.harmonyospringapi.service.aula.AulaService;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoCriacaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoPilhaDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoMapper;
import school.sptech.harmonyospringapi.service.status.StatusService;
import school.sptech.harmonyospringapi.service.usuario.AlunoService;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;
import school.sptech.harmonyospringapi.utils.PilhaObj;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private StatusService statusService;

    public List<PedidoExibicaoDto> obterTodos() {

        return this.pedidoRepository.findAll()
                .stream()
                .map(PedidoMapper::ofPedidoExibicaoDto)
                .toList();
    }

    public PilhaObj<PedidoExibicaoDto> obterPedidosPendentes(int idProfessor){

        List<Pedido> pedidosPendentes = pedidoRepository.encontrarPedidosPendentesPorIdProfessor(idProfessor);
        PilhaObj<PedidoExibicaoDto> pilhaPedidos = new PilhaObj<>(pedidosPendentes.size());

        pedidosPendentes.stream().map(PedidoMapper::ofPedidoExibicaoDto).forEach(pilhaPedidos::push);

        return pilhaPedidos;
    }


    public PedidoExibicaoDto criar(PedidoCriacaoDto pedidoCriacaoDto) {

        Aluno aluno = this.alunoService.buscarPorId(pedidoCriacaoDto.getAlunoId());
        Professor professor = this.professorService.buscarPorId(pedidoCriacaoDto.getProfessorId());
        Aula aula = this.aulaService.buscarPorId(pedidoCriacaoDto.getAulaId());
        Status status = this.statusService.buscarPorDescricao("Pendente");

        return PedidoMapper.ofPedidoExibicaoDto(this.pedidoRepository.save(
                PedidoMapper.of(pedidoCriacaoDto, aluno, professor, status, aula)
        ));

    }

    /* ============= PESQUISA ============== */

    public Pedido buscarPorId(Integer integer) {
        return this.pedidoRepository.findById(integer).orElseThrow(() -> new EntitadeNaoEncontradaException("Pedido não encontrado"));
    }


    public PedidoExibicaoDto aceitarPedido(Pedido pedidoPendente){
        Integer idPedidoPendente = pedidoPendente.getId();

        Optional<Pedido> pedidoEncontradoNoBancoOpt = pedidoRepository.findById(idPedidoPendente);
        if(pedidoEncontradoNoBancoOpt.isEmpty()){
            throw new EntitadeNaoEncontradaException("Pedido não encontrado");
        }


        Pedido pedidoEncontradoNoBanco = pedidoEncontradoNoBancoOpt.get();

        pedidoEncontradoNoBanco.setStatus(statusService.buscarPorDescricao("Confirmado")); //Status - Confirmado é de ID 2
        pedidoEncontradoNoBanco.setHoraResposta(LocalDateTime.now());

        return PedidoMapper.ofPedidoExibicaoDto(pedidoRepository.save(pedidoEncontradoNoBanco));
    }

    public PedidoExibicaoDto recusarPedido(Pedido pedidoPendente){
        Integer idPedidoPendente = pedidoPendente.getId();

        Optional<Pedido> pedidoEncontradoNoBancoOpt = pedidoRepository.findById(idPedidoPendente);
        if(pedidoEncontradoNoBancoOpt.isEmpty()){
            throw new EntitadeNaoEncontradaException("Pedido não encontrado");
        }

        Pedido pedidoEncontradoNoBanco = pedidoEncontradoNoBancoOpt.get();

        pedidoEncontradoNoBanco.setStatus(statusService.buscarPorDescricao("Recusado")); //Status - Recusado é de ID 4
        pedidoEncontradoNoBanco.setHoraResposta(LocalDateTime.now());

        return PedidoMapper.ofPedidoExibicaoDto(pedidoRepository.save(pedidoEncontradoNoBanco));
    }


    public List<PedidoExibicaoDto> buscarPorUsuarioId(Integer id) {
        List<Pedido> pedidos = this.pedidoRepository.buscarPorUsuarioId(id);
        if (pedidos.isEmpty()) throw new EntitadeNaoEncontradaException("Pedido não encontrado");
        return pedidos.stream().map(PedidoMapper::ofPedidoExibicaoDto).toList();
    }

    public PedidoExibicaoDto buscarPorIdParaExibicao(Integer id) {
        Pedido pedido = this.pedidoRepository.findById(id).orElseThrow(() -> new EntitadeNaoEncontradaException("Pedido não encontrado"));
        return PedidoMapper.ofPedidoExibicaoDto(pedido);
    }

    /* ============= MUDAR STATUS ============== */
    public PedidoExibicaoDto aceitarPropostaDoAluno(Integer id) {
        Pedido pedido = this.buscarPorId(id);
        Status status = this.statusService.buscarPorDescricao("Aguardando Pagamento");
        pedido = atualizarStatus(pedido, status);

        return PedidoMapper.ofPedidoExibicaoDto(pedido);
    }

    public PedidoExibicaoDto recusarPropostaDoAluno(Integer id) {
        Pedido pedido = this.buscarPorId(id);
        Status status = this.statusService.buscarPorDescricao("Recusado");
        pedido = atualizarStatus(pedido, status);

        return PedidoMapper.ofPedidoExibicaoDto(pedido);
    }

    public PedidoExibicaoDto cancelarPedido(Integer idPedido){
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(idPedido);
        if(pedidoOpt.isEmpty()) throw new EntitadeNaoEncontradaException("Pedido não encontrado");

        Pedido pedido = pedidoOpt.get();
        Status status = statusService.buscarPorDescricao("Cancelado");
        pedido.setStatus(status);

        return PedidoMapper.ofPedidoExibicaoDto(pedidoRepository.save(pedido));

    }

    private Pedido atualizarStatus(Pedido pedido, Status status) {
        pedido.setStatus(status);
        pedido.setHoraResposta(LocalDateTime.now());
//      Colocar para salvar mudança de status
        return this.pedidoRepository.save(pedido);
    }
}
