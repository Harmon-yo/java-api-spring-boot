package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class FilaEsperaKey implements Serializable {

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Aula aula;



    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }
}
