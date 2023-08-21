package school.sptech.harmonyospringapi.service.experiencia;

import school.sptech.harmonyospringapi.domain.Experiencia;
import school.sptech.harmonyospringapi.domain.Professor;

public abstract class ExperienciaMapper {

    public static ExperienciaResumidaDto of(Experiencia experiencia){

        ExperienciaResumidaDto experienciaResumidaDto = new ExperienciaResumidaDto();

        experienciaResumidaDto.setId(experiencia.getId());
        experienciaResumidaDto.setTitulo(experiencia.getTitulo());
        experienciaResumidaDto.setDescricao(experiencia.getDescricao());

        return experienciaResumidaDto;
    }

    public static Experiencia of(ExperienciaCriacaoDto experienciaCriacaoDto, Professor professor){

        Experiencia novaExperiencia = new Experiencia();

        novaExperiencia.setTitulo(experienciaCriacaoDto.getTitulo());
        novaExperiencia.setDescricao(experienciaCriacaoDto.getDescricao());
        novaExperiencia.setProfessor(professor);

        return novaExperiencia;
    }
}
