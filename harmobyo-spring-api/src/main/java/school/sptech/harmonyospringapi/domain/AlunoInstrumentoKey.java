package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AlunoInstrumentoKey implements Serializable {

    @Column(name = "aluno_fk")
    private Integer alunoFk;

    @Column(name = "instrumento_fk")
    private Integer instrumentoFk;

    public Integer getAlunoFk() {
        return alunoFk;
    }

    public void setAlunoFk(Integer alunoFk) {
        this.alunoFk = alunoFk;
    }

    public Integer getInstrumentoFk() {
        return instrumentoFk;
    }

    public void setInstrumentoFk(Integer instrumentoFk) {
        this.instrumentoFk = instrumentoFk;
    }
}
