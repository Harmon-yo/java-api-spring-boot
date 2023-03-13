package school.sptech.harmonyoentregaveleda;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class UsuarioDTO {
    private String nome;
    private String sobrenome;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataNasc;
    private String sexo;
    private EstadoConta estadoConta;
    private List<Instrumento> instrumentos;

    public UsuarioDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.sobrenome = usuario.getSobrenome();
        this.dataNasc = usuario.getDataNasc();
        this.sexo = usuario.getSexo();
        this.estadoConta = usuario.getEstadoConta();
        this.instrumentos = usuario.getInstrumentos();
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public EstadoConta getEstadoConta() {
        return estadoConta;
    }

    public List<Instrumento> getInstrumentos() {
        return instrumentos;
    }
}
