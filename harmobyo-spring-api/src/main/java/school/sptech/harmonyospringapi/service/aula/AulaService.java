package school.sptech.harmonyospringapi.service.aula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.*;

import school.sptech.harmonyospringapi.repository.AulaRepository;
import school.sptech.harmonyospringapi.service.aula.dto.AulaCriacaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaExibicaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaMapper;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.instrumento.InstrumentoService;
import school.sptech.harmonyospringapi.service.usuario.ProfessorService;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;

import java.util.List;
import java.util.Optional;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;


    @Autowired
    private ProfessorService professorService;

    @Autowired
    private InstrumentoService instrumentoService;

    public List<AulaExibicaoDto> obterTodos() {
        return this.aulaRepository.findAll()
                .stream()
                .map(AulaMapper::ofAulaExibicaoDto)
                .toList();
    }

    public AulaExibicaoDto criar(AulaCriacaoDto aulaCriacaoDto) {

        Professor professor = this.professorService.buscarPorId(aulaCriacaoDto.getProfessorId());
        Instrumento instrumento = this.instrumentoService.buscarPorId(aulaCriacaoDto.getInstrumentoId());

        Aula aula = AulaMapper.of(aulaCriacaoDto, professor, instrumento);
        return AulaMapper.ofAulaExibicaoDto(this.aulaRepository.save(aula));
    }

//    public List<Aula> listarPorIdAluno(int id){
//        return this.aulaRepository.findAllByIdAluno(id);
//
//    }

    public List<AulaExibicaoDto> buscarAulasPorIdProfessor(int fkProfessor){

        List<Aula> ltAulas = this.aulaRepository.findAllByIdProfessorFk(fkProfessor);

        return ltAulas.stream().map(AulaMapper::ofAulaExibicaoDto).toList();
    }

    public Aula obterAulaPorId(AulaKey id) {
        return aulaRepository.findById(id).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Aula não encontrada")
        );
    }

}
