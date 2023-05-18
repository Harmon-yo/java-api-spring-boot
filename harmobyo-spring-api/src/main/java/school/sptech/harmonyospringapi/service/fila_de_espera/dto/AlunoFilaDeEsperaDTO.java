package school.sptech.harmonyospringapi.service.fila_de_espera.dto;

import school.sptech.harmonyospringapi.domain.Aluno;
import school.sptech.harmonyospringapi.domain.AlunoInstrumento;

public class AlunoFilaDeEsperaDTO {
    private int idAlunoFila;
    private String alunoFila;
    private String alunoInstrumento;

    public AlunoFilaDeEsperaDTO(int idAlunoFila, String alunoFila, String alunoInstrumento) {
        this.idAlunoFila = idAlunoFila;
        this.alunoFila = alunoFila;
        this.alunoInstrumento = alunoInstrumento;
    }

    public int getIdAlunoFila() {
        return idAlunoFila;
    }

    public void setIdAlunoFila(int idAlunoFila) {
        this.idAlunoFila = idAlunoFila;
    }

    public String getAlunoFila() {
        return alunoFila;
    }

    public void setAlunoFila(String alunoFila) {
        this.alunoFila = alunoFila;
    }

    public String getAlunoInstrumento() {
        return alunoInstrumento;
    }

    public void setAlunoInstrumento(String alunoInstrumento) {
        this.alunoInstrumento = alunoInstrumento;
    }
}
