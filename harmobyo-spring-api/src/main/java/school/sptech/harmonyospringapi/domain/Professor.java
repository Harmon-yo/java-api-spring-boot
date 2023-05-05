package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("Professor")
public class Professor extends Usuario{

    @OneToMany(mappedBy = "professor")
    private List<ProfessorInstrumento> professorInstrumentos;
}
