package school.sptech.harmonyospringapi.service.aula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.*;

import school.sptech.harmonyospringapi.repository.AulaRepository;
import school.sptech.harmonyospringapi.service.aula.dto.AulaAtualizacaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaCriacaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaExibicaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaMapper;
import school.sptech.harmonyospringapi.service.exceptions.EntidadeConflitanteException;
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

        if (this.aulaRepository.existsByProfessorIdAndInstrumentoId(aulaCriacaoDto.getProfessorId(), aulaCriacaoDto.getInstrumentoId())) {
            throw new EntidadeConflitanteException("Aula já cadastrada !");
        }

        Professor professor = this.professorService.buscarPorId(aulaCriacaoDto.getProfessorId());
        Instrumento instrumento = this.instrumentoService.buscarPorId(aulaCriacaoDto.getInstrumentoId());

        Aula aula = AulaMapper.of(aulaCriacaoDto, professor, instrumento);

        Aula aulaCadastrada = this.aulaRepository.save(aula);


        return AulaMapper.ofAulaExibicaoDto(aulaCadastrada);
    }

    public Aula buscarPorId(Integer id) {
        return aulaRepository.findById(id).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Aula não encontrada")
        );
    }

    public List<AulaExibicaoDto> buscarAulasPorIdProfessor(int fkProfessor) {

        List<Aula> ltAulas = this.aulaRepository.findAllByProfessorId(fkProfessor);

        return ltAulas.stream().map(AulaMapper::ofAulaExibicaoDto).toList();
    }

    public AulaExibicaoDto atualizarAulaPorId(int idAula, AulaAtualizacaoDto aulaAtualizacaoDto) {

        Optional<Aula> aulaOpt = this.aulaRepository.findById(idAula);

        if (aulaOpt.isPresent()) {

            aulaOpt.get().setValorAula(aulaAtualizacaoDto.getValorAula());

            Aula aulaAtualizada = this.aulaRepository.save(aulaOpt.get());

            return AulaMapper.ofAulaExibicaoDto(aulaAtualizada);
        }

        throw new EntitadeNaoEncontradaException("ID de Aula Inválido. Aula não encontrada !");
    }


    public void deletarAulaPorId(Integer id) {

        if (this.aulaRepository.existsById(id)){

            this.aulaRepository.deleteById(id);
        }
        else {
            throw new EntitadeNaoEncontradaException("ID de Aula Inválido. Aula não encontrada !");
        }
    }
}
