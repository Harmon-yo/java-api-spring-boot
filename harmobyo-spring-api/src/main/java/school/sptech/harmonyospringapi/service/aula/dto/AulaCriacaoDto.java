package school.sptech.harmonyospringapi.service.aula.dto;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.AulaKey;
import school.sptech.harmonyospringapi.domain.Instrumento;
import school.sptech.harmonyospringapi.domain.Professor;

import java.time.LocalDateTime;

public class AulaCriacaoDto {

    private Integer alunoId;

    private Integer professorId;

    private Integer instrumentoId;

    private LocalDateTime horario;

    private Double avaliacao;

    private String comentario;

    private Double valor;

    private String status;
}
