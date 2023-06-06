package school.sptech.harmonyospringapi.service.aula.dto;

public class AulaGraficoInformacoesDashboardDto {

    private int aulasSolicitadas;

    private int aulasCanceladas;

    private int aulasRealizadas;

    public AulaGraficoInformacoesDashboardDto(Long aulasSolicitadas, Long aulasCanceladas, Long aulasRealizadas) {
        this.aulasSolicitadas = aulasSolicitadas.intValue();
        this.aulasCanceladas = aulasCanceladas.intValue();
        this.aulasRealizadas = aulasRealizadas.intValue();
    }

    public AulaGraficoInformacoesDashboardDto() {
    }

    public int getAulasSolicitadas() {
        return aulasSolicitadas;
    }

    public void setAulasSolicitadas(int aulasSolicitadas) {
        this.aulasSolicitadas = aulasSolicitadas;
    }

    public int getAulasCanceladas() {
        return aulasCanceladas;
    }

    public void setAulasCanceladas(int aulasCanceladas) {
        this.aulasCanceladas = aulasCanceladas;
    }

    public int getAulasRealizadas() {
        return aulasRealizadas;
    }

    public void setAulasRealizadas(int aulasRealizadas) {
        this.aulasRealizadas = aulasRealizadas;
    }
}
