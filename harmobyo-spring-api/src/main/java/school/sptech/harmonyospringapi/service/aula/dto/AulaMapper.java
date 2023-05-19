package school.sptech.harmonyospringapi.service.aula.dto;

import school.sptech.harmonyospringapi.domain.*;
import school.sptech.harmonyospringapi.service.instrumento.dto.InstrumentoMapper;
import school.sptech.harmonyospringapi.service.usuario.dto.UsuarioMapper;

public class AulaMapper {

    public static Aula of(AulaCriacaoDto aulaCriacaoDto, Usuario usuario, Instrumento instrumento) {

        AulaKey aulaKey = new AulaKey();
        aulaKey.setUsuarioFk(usuario.getId());
        aulaKey.setInstrumentoFk(instrumento.getId());

        Aula aula = new Aula();
        aula.setId(aulaKey);
        aula.setValorAula(aulaCriacaoDto.getValorAula());
        aula.setUsuario(usuario);
        aula.setInstrumento(instrumento);

        return aula;
    }

    public static AulaExibicaoDto ofAulaExibicaoDto(Aula aula) {
       AulaExibicaoDto aulaExibicaoDto = new AulaExibicaoDto();

       aulaExibicaoDto.setId(aula.getId());
       aulaExibicaoDto.setValorAula(aula.getValorAula());
       aulaExibicaoDto.setUsuario(UsuarioMapper.ofUsuarioExibicao(aula.getUsuario()));
       aulaExibicaoDto.setInstrumento(InstrumentoMapper.ofInstrumentoExibicao(aula.getInstrumento()));

       return aulaExibicaoDto;
    }
}
