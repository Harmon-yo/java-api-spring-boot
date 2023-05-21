package school.sptech.harmonyospringapi.service.aula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.Aula;

import school.sptech.harmonyospringapi.domain.AulaKey;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Usuario;
import school.sptech.harmonyospringapi.repository.AulaRepository;
import school.sptech.harmonyospringapi.service.aula.dto.AulaCriacaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaExibicaoDto;
import school.sptech.harmonyospringapi.service.aula.dto.AulaMapper;
import school.sptech.harmonyospringapi.service.exceptions.EntitadeNaoEncontradaException;
import school.sptech.harmonyospringapi.service.instrumento.InstrumentoService;
import school.sptech.harmonyospringapi.service.usuario.UsuarioService;

import java.util.List;

import java.util.List;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private InstrumentoService instrumentoService;

    public List<AulaExibicaoDto> obterTodos() {
        return this.aulaRepository.findAll()
                .stream()
                .map(AulaMapper::ofAulaExibicaoDto)
                .toList();
    }

    public AulaExibicaoDto criar(AulaCriacaoDto aulaCriacaoDto) {

        Usuario usuario = this.usuarioService.obterUsuarioPorId(aulaCriacaoDto.getUsuarioId());
        Instrumento instrumento = this.instrumentoService.obterInstrumentoPorId(aulaCriacaoDto.getInstrumentoId());

        Aula aula = AulaMapper.of(aulaCriacaoDto, usuario, instrumento);
        return AulaMapper.ofAulaExibicaoDto(this.aulaRepository.save(aula));
    }

//    public List<Aula> listarPorIdAluno(int id){
//        return this.aulaRepository.findAllByIdAluno(id);
//
//    }

    public Aula obterAulaPorId(AulaKey id) {
        return aulaRepository.findById(id).orElseThrow(
                () -> new EntitadeNaoEncontradaException("Aula não encontrada")
        );
    }

}
