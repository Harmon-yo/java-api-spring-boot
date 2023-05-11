package school.sptech.harmonyospringapi.service.aula.dto;

import school.sptech.harmonyospringapi.domain.Aula;

public class AulaMapper {

    public static Aula of(AulaCriacaoDto aulaCriacaoDto) {
        return new Aula();
    }

    public static AulaExibicaoDto ofAulaExibicaoDto(Aula aula) {
        return new AulaExibicaoDto();
    }
}
