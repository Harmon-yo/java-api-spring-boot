package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Aula {

    @EmbeddedId
    private AulaKey id;

    private Double valorAula;

    @ManyToOne
    @MapsId("professorFk")
    @JoinColumn(name = "professor_fk")
    private Professor professor;

    @ManyToOne
    @MapsId("instrumentoFk")
    @JoinColumn(name = "instrumento_fk")
    private Instrumento instrumento;

    public AulaKey getId() {
        return id;
    }

    public void setId(AulaKey id) {
        this.id = id;
    }

    public Double getValorAula() {
        return valorAula;
    }

    public void setValorAula(Double valorAula) {
        this.valorAula = valorAula;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }
}
