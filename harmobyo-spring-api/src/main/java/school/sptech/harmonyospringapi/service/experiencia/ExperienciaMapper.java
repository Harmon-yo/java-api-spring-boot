package school.sptech.harmonyospringapi.service.experiencia;

import school.sptech.harmonyospringapi.domain.Experiencia;

public abstract class ExperienciaMapper {

    public static ExperienciaResumidaDto of(Experiencia experiencia){

        ExperienciaResumidaDto experienciaResumidaDto = new ExperienciaResumidaDto();

        experienciaResumidaDto.setId(experiencia.getId());
        experienciaResumidaDto.setTitulo(experiencia.getTitulo());
        experienciaResumidaDto.setDescricao(experiencia.getDescricao());

        return experienciaResumidaDto;
    }
}
