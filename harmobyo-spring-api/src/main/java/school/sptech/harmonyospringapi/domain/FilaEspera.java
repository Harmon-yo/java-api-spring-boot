package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
public class FilaEspera {

    @EmbeddedId
    private FilaEsperaKey id;

    public FilaEsperaKey getId() {
        return id;
    }

    public void setId(FilaEsperaKey id) {
        this.id = id;
    }
}
