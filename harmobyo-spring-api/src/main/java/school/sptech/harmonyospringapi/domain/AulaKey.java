package school.sptech.harmonyospringapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AulaKey implements Serializable {


    @Column(name = "usuario_fk")
    private Integer usuarioFk;

    @Column(name = "instrumento_fk")
    private Integer instrumentoFk;

    public Integer getUsuarioFk() {
        return usuarioFk;
    }

    public void setUsuarioFk(Integer usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

    public Integer getInstrumentoFk() {
        return instrumentoFk;
    }

    public void setInstrumentoFk(Integer instrumentoFk) {
        this.instrumentoFk = instrumentoFk;
    }
}
