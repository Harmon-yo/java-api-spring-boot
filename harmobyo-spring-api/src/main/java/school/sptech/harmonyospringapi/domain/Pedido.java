package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Pedido {

    @EmbeddedId
    private PedidoKey id;

    @ManyToOne
    @MapsId("alunoFk")
    @JoinColumn(name = "aluno_fk")
    private Aluno aluno;

    @ManyToOne
    @MapsId("professorFk")
    @JoinColumn(name = "professor_fk")
    private Professor professor;

    @ManyToOne
    @MapsId("statusFk")
    @JoinColumn(name = "status_fk")
    private Status status;

    @ManyToOne
    @MapsId("aulaFk")
    @JoinColumns({
            @JoinColumn(name = "aula_usuario_fk", referencedColumnName = "usuario_fk"),
            @JoinColumn(name = "aula_instrumento_fk", referencedColumnName = "instrumento_fk")
    })
    private Aula aula;

    private LocalDateTime horaCriacao;

    private LocalDateTime horaResposta;

    private LocalDateTime dataAula;

    public PedidoKey getId() {
        return id;
    }

    public void setId(PedidoKey id) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(LocalDateTime horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

    public LocalDateTime getHoraResposta() {
        return horaResposta;
    }

    public void setHoraResposta(LocalDateTime horaResposta) {
        this.horaResposta = horaResposta;
    }

    public LocalDateTime getDataAula() {
        return dataAula;
    }

    public void setDataAula(LocalDateTime dataAula) {
        this.dataAula = dataAula;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }
}
