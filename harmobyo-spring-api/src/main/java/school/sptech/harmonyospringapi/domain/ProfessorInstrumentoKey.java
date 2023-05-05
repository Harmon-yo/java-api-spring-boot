package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ProfessorInstrumentoKey implements Serializable {

    @Column(name="professor_fk")
    private Integer professorFk;

    @Column(name="instrumento_fk")
    private Integer instrumentoFk;

    public Integer getProfessorFk() {
        return professorFk;
    }

    public void setProfessorFk(Integer professorFk) {
        this.professorFk = professorFk;
    }

    public Integer getInstrumentoFk() {
        return instrumentoFk;
    }

    public void setInstrumentoFk(Integer instrumentoFk) {
        this.instrumentoFk = instrumentoFk;
    }
}
