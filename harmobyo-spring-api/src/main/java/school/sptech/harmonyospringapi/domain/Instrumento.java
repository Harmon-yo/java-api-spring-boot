package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @OneToMany(mappedBy = "instrumento")
    private List<ProfessorInstrumento> professorInstrumentos;

    @OneToMany(mappedBy = "instrumento")
    private List<AlunoInstrumento> alunoInstrumentos;

    @OneToMany(mappedBy = "instrumento")
    private List<Aula> aulas;

    @ManyToOne
    private Naipe naipe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ProfessorInstrumento> getProfessorInstrumentos() {
        return professorInstrumentos;
    }

    public void setProfessorInstrumentos(List<ProfessorInstrumento> professorInstrumentos) {
        this.professorInstrumentos = professorInstrumentos;
    }

    public List<AlunoInstrumento> getAlunoInstrumentos() {
        return alunoInstrumentos;
    }

    public void setAlunoInstrumentos(List<AlunoInstrumento> alunoInstrumentos) {
        this.alunoInstrumentos = alunoInstrumentos;
    }

    public Naipe getNaipe() {
        return naipe;
    }

    public void setNaipe(Naipe naipe) {
        this.naipe = naipe;
    }
}
