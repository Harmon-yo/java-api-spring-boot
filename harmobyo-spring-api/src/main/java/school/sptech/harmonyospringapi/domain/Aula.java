package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Aula {

    @EmbeddedId
    private AulaKey id;

    @ManyToOne
    @MapsId("alunoFk")
    @JoinColumn(name = "aluno_fk")
    private Aluno aluno;

    @ManyToOne
    @MapsId("professorFk")
    @JoinColumn(name = "professor_fk")
    private Professor professor;

    @ManyToOne
    @MapsId("instrumentoFk")
    @JoinColumn(name = "instrumento_fk")
    private Instrumento instrumento;

    private LocalDateTime horario;

    private Double avaliacao;

    private String comentario;

    private Double valor;

    private String status;

    public AulaKey getId() {
        return id;
    }

    public void setId(AulaKey id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
