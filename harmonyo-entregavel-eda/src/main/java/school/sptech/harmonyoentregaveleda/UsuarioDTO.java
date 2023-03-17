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

    public UsuarioDTO() {
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setEstadoConta(EstadoConta estadoConta) {
        this.estadoConta = estadoConta;
    }

    public void setInstrumentos(List<Instrumento> instrumentos) {
        this.instrumentos = instrumentos;
    }
}
