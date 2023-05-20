package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import school.sptech.harmonyospringapi.service.usuario.AlunoService;
import school.sptech.harmonyospringapi.utils.PilhaObj;

import java.util.List;

@Entity
@DiscriminatorValue("Aluno")
public class Aluno extends Usuario{

    @OneToMany(mappedBy = "aluno")
    private List<AlunoInstrumento> alunoInstrumentos;


    @OneToMany(mappedBy = "aluno")
    private List<Pedido> pedidos;

    private PilhaObj<Aula> aulas;

    public Aluno() {
        this.aulas = new PilhaObj<Aula>(AlunoService.MAX_AULAS);
    }

    public PilhaObj<Aula> getAulas() {
        return aulas;
    }

}
