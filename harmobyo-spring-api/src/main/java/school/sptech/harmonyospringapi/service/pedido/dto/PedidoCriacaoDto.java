package school.sptech.harmonyospringapi.service.pedido.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import school.sptech.harmonyospringapi.domain.*;

import java.lang.Integer;
import java.time.LocalDateTime;

public class PedidoCriacaoDto {

    @NotNull
    private Integer alunoId;

    @NotNull
    private Integer professorId;

    @NotNull
    private Integer statusId;

    @NotNull
    private AulaKey aulaId;

    @FutureOrPresent
    private LocalDateTime dataAula;

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public LocalDateTime getDataAula() {
        return dataAula;
    }

    public void setDataAula(LocalDateTime dataAula) {
        this.dataAula = dataAula;
    }

    public AulaKey getAulaId() {
        return aulaId;
    }

    public void setAulaId(AulaKey aulaId) {
        this.aulaId = aulaId;
    }
}
