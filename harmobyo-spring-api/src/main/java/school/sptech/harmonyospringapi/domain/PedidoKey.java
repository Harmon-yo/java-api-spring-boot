package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PedidoKey implements Serializable {

    @Column(name = "aluno_fk")
    private Integer alunoFk;

    @Column(name = "professor_fk")
    private Integer professorFk;

    @Column(name = "aula_fk")
    private AulaKey aulaFk;

    public Integer getAlunoFk() {
        return alunoFk;
    }

    public void setAlunoFk(Integer alunoFk) {
        this.alunoFk = alunoFk;
    }

    public Integer getProfessorFk() {
        return professorFk;
    }

    public void setProfessorFk(Integer professorFk) {
        this.professorFk = professorFk;
    }
}
