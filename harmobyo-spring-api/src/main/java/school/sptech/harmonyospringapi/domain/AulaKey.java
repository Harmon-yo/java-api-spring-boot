package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AulaKey implements Serializable {

    @Column(name = "aula_id")
    private Integer aulaId;

    @Column(name = "aluno_fk")
    private Integer alunoFk;

    @Column(name = "professor_fk")
    private Integer professorFk;

    @Column(name = "instrumento_fk")
    private Integer instrumentoFk;

}
