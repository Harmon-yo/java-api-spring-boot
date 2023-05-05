package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.*;

@Entity
public class ProfessorInstrumento {

    @EmbeddedId
    private ProfessorInstrumentoKey id;

    @ManyToOne
    @MapsId("professorFk")
    @JoinColumn(name = "professor_fk")
    private Professor professor;

    @ManyToOne
    @MapsId("instrumentoFk")
    @JoinColumn(name = "instrumento_fk")
    private Instrumento instrumento;

    private String nivelConhecimento;

    private boolean emprestaInstrumento;

    public ProfessorInstrumentoKey getId() {
        return id;
    }

    public void setId(ProfessorInstrumentoKey id) {
        this.id = id;
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

    public String getNivelConhecimento() {
        return nivelConhecimento;
    }

    public void setNivelConhecimento(String nivelConhecimento) {
        this.nivelConhecimento = nivelConhecimento;
    }

    public boolean isEmprestaInstrumento() {
        return emprestaInstrumento;
    }

    public void setEmprestaInstrumento(boolean emprestaInstrumento) {
        this.emprestaInstrumento = emprestaInstrumento;
    }
}
