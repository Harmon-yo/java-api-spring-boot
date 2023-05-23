package school.sptech.harmonyospringapi.service.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.Aula;
import school.sptech.harmonyospringapi.domain.Professor;
import school.sptech.harmonyospringapi.domain.Status;
import school.sptech.harmonyospringapi.repository.*;
import school.sptech.harmonyospringapi.service.aula.AulaService;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoCriacaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoMapper;
import school.sptech.harmonyospringapi.service.status.StatusService;
import school.sptech.harmonyospringapi.service.usuario.AlunoService;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ProfessorRepository professorRepository;
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

        Optional<Aluno> alunoOpt = this.alunoRepository.findById(pedidoCriacaoDto.getAlunoId());
        Optional<Professor> professorOpt = this.professorRepository.findById(pedidoCriacaoDto.getProfessorId());
        Optional<Aula> aulaOpt = this.aulaRepository.findById(pedidoCriacaoDto.getAulaId());
        Optional<Status> statusOpt = this.statusRepository.findById(pedidoCriacaoDto.getStatusId());

        if(alunoOpt.isEmpty()) throw new EntitadeNaoEncontradaException("Aluno não encontrado");
        if(professorOpt.isEmpty()) throw new EntitadeNaoEncontradaException("Professor não encontrado");
        if(aulaOpt.isEmpty()) throw new EntitadeNaoEncontradaException("Aula não encontrada");
        if(statusOpt.isEmpty()) throw new EntitadeNaoEncontradaException("Status não encontrado");


        Aluno aluno = alunoOpt.get();
        Professor professor = professorOpt.get();
        Aula aula = aulaOpt.get();
        Status status = statusOpt.get();

        return PedidoMapper.ofPedidoExibicaoDto(this.pedidoRepository.save(
                PedidoMapper.of(pedidoCriacaoDto, aluno, professor, status, aula)
        ));
    }

}
