package school.sptech.harmonyospringapi.service.aula.dto;

public class AulaGraficoInformacoesDashboardDto {

    private int aulasRecusadas;

    private int aulasCanceladas;

    private int aulasRealizadas;

    public AulaGraficoInformacoesDashboardDto(Long aulasRecusadas, Long aulasCanceladas, Long aulasRealizadas) {
        this.aulasRecusadas = aulasRecusadas.intValue();
        this.aulasCanceladas = aulasCanceladas.intValue();
        this.aulasRealizadas = aulasRealizadas.intValue();
    }

    public AulaGraficoInformacoesDashboardDto() {
    }

    public int getAulasRecusadas() {
        return aulasRecusadas;
    }

    public void setAulasRecusadas(int aulasRecusadas) {
        this.aulasRecusadas = aulasRecusadas;
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
