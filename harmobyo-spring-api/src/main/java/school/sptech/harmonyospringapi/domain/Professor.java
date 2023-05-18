package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import school.sptech.harmonyospringapi.utils.FilaObj;

import java.util.List;

@Entity
@DiscriminatorValue("Professor")
public class Professor extends Usuario{

    @OneToMany(mappedBy = "professor")
    private List<ProfessorInstrumento> professorInstrumentos;

    //TODO atualizar a capacidade da fila de acordo com o banco
    private FilaObj<Aluno> filaDeEsperaAlunos = new FilaObj<>(10);
}
