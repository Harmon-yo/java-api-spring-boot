package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Naipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricaoNaipe;

    @OneToMany(mappedBy = "naipe")
    private List<Instrumento> instrumentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricaoNaipe() {
        return descricaoNaipe;
    }

    public void setDescricaoNaipe(String descricaoNaipe) {
        this.descricaoNaipe = descricaoNaipe;
    }

    public List<Instrumento> getInstrumentos() {
        return instrumentos;
    }

    public void setInstrumentos(List<Instrumento> instrumentos) {
        this.instrumentos = instrumentos;
    }
}
