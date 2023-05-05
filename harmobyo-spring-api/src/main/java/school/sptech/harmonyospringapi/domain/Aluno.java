package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("Aluno")
public class Aluno extends Usuario{

    @OneToMany(mappedBy = "aluno")
    private List<AlunoInstrumento> alunoInstrumentos;
}
