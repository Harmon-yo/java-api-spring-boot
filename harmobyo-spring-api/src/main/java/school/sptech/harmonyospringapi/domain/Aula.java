package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Aula {

    @EmbeddedId
    private AulaKey id;

    private Double valorAula;

    @ManyToOne
    @MapsId("usuarioFk")
    @JoinColumn(name = "usuario_fk")
    private Usuario usuario;

    @ManyToOne
    @MapsId("instrumentoFk")
    @JoinColumn(name = "instrumento_fk")
    private Instrumento instrumento;

    public AulaKey getId() {
        return id;
    }

    public void setId(AulaKey id) {
        this.id = id;
    }

    public Double getValorAula() {
        return valorAula;
    }

    public void setValorAula(Double valorAula) {
        this.valorAula = valorAula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }
}
