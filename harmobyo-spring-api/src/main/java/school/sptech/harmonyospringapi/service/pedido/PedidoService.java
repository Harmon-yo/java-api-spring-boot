package school.sptech.harmonyospringapi.service.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.domain.Status;
import school.sptech.harmonyospringapi.repository.PedidoRepository;
import school.sptech.harmonyospringapi.repository.UsuarioRepository;
import school.sptech.harmonyospringapi.service.aula.AulaService;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoCriacaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoMapper;
import school.sptech.harmonyospringapi.service.status.StatusService;
import school.sptech.harmonyospringapi.service.usuario.AlunoService;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;

import java.util.List;

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

    public PedidoExibicaoDto criar(PedidoCriacaoDto pedidoCriacaoDto) {

        Aluno aluno = this.alunoService.obterAlunoPorId(pedidoCriacaoDto.getAlunoId());
        Professor professor = this.professorService.obterProfessorPorId(pedidoCriacaoDto.getProfessorId());
        Aula aula = this.aulaService.obterAulaPorId(pedidoCriacaoDto.getAulaId());
        Status status = this.statusService.obterPorId(pedidoCriacaoDto.getStatusId());

        return PedidoMapper.ofPedidoExibicaoDto(this.pedidoRepository.save(
                PedidoMapper.of(pedidoCriacaoDto, aluno, professor, status, aula)
        ));
    }

}
