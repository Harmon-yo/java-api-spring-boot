package school.sptech.harmonyospringapi.service.aula.dto;

import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.AulaKey;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Professor;

import java.time.LocalDateTime;

public class AulaExibicaoDto {

    private AulaKey aulaKey;

    private Aluno aluno;

    private Professor professor;

    private Instrumento instrumento;

    private LocalDateTime horario;

    private Double avaliacao;

    private String comentario;

    private Double valor;

    private String status;

}
